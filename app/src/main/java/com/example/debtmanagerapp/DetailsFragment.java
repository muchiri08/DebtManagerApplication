package com.example.debtmanagerapp;

import static com.example.debtmanagerapp.homeFragment.DBT_ID;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.debtmanagerapp.adapter.DebtorRecyViewAdapter;
import com.example.debtmanagerapp.adapter.ItemClickInterface;
import com.example.debtmanagerapp.db.DatabaseHelper;
import com.example.debtmanagerapp.model.DebtorModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class DetailsFragment extends Fragment implements ItemClickInterface {
    private TextView fName,lName, dayCount;
    private TextInputEditText phoneNo, amount, dateGiven, deadlineDate;
    private MaterialButton buttonPay;
    DatabaseHelper myDb;
    MainActivity mainActivity;
    private NavController navController;
    DebtorRecyViewAdapter recyViewAdapter;
    private static final int fNo = 2;
    public static final String DebtId= "id";


    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fName = view.findViewById(R.id.tvFN);
        lName = view.findViewById(R.id.tvLN);
        dayCount = view.findViewById(R.id.tv_days_count);
        phoneNo = view.findViewById(R.id.text_field_phone);
        amount = view.findViewById(R.id.text_field_amount);
        dateGiven = view.findViewById(R.id.text_field_dateGiven);
        deadlineDate = view.findViewById(R.id.text_field_deadline);
        buttonPay = view.findViewById(R.id.buttonPaid);
        navController = Navigation.findNavController(view);

        mainActivity = (MainActivity) getActivity();
        Bundle bundle = mainActivity.getSavedData();
        int debtor_id;
        DebtorModel debtor;
        if (bundle != null) {
            debtor_id = bundle.getInt(DBT_ID, -1);

            if (debtor_id != -1){
                myDb = new DatabaseHelper(getContext());
                debtor = myDb.getDebtorById(debtor_id);
                if (debtor != null) {

//                        int debt_id = debtor.getId();
                        String first_Name = debtor.getFirstName();
                        String last_Name = debtor.getLastName();
                        int phoneNo_phone = debtor.getPhoneNumber();
                        int amount_amount = debtor.getAmount();
                        LocalDate dateGiven_date = debtor.getDateGiven();
                        LocalDate deadlineDate_date = debtor.getDeadlineDate();

                    long period = ChronoUnit.DAYS.between(LocalDate.now(), deadlineDate_date);

                        fName.setText(first_Name);
                        lName.setText(last_Name);
                        phoneNo.setText(String.valueOf(phoneNo_phone));
                        amount.setText(String.valueOf(amount_amount));
                        dateGiven.setText(String.valueOf(dateGiven_date));
                        deadlineDate.setText(String.valueOf(deadlineDate_date));
                        dayCount.setText(String.valueOf(period));
                }
                myDb.close();
            }
        }

    }

    @Override
    public void onItemClicked(int position) {
        buttonPay.setOnClickListener(v->{
            ArrayList<DebtorModel> debtorModel = new ArrayList<>(myDb.getAllDebtors());
            recyViewAdapter = new DebtorRecyViewAdapter(debtorModel, this);
            Bundle bundle = new Bundle();
            bundle.putInt(DebtId, debtorModel.get(position).getId());
            mainActivity = (MainActivity) getActivity();
            mainActivity.saveData(fNo, bundle);
            navController.navigate(R.id.action_detailsFragment_to_paidFragment);

            debtorModel.remove(position);
            recyViewAdapter.notifyItemRemoved(position);
            recyViewAdapter.notifyDataSetChanged();

        });
    }
}
