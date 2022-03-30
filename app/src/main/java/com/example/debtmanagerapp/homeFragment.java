package com.example.debtmanagerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.debtmanagerapp.adapter.DebtorRecyViewAdapter;
import com.example.debtmanagerapp.adapter.ItemClickInterface;
import com.example.debtmanagerapp.db.DatabaseHelper;
import com.example.debtmanagerapp.model.DebtorModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class homeFragment extends Fragment implements ItemClickInterface {

    private TextView totalDebts;
    private FloatingActionButton addDebtDetailsButton;
    private NavController navController;
    private RecyclerView recyclerView;
    private DebtorRecyViewAdapter recyViewAdapter;
    private DatabaseHelper myDb;
    ArrayList<DebtorModel> debtors;
    public static final String DBT_ID = "Dbt_Id";
    private static final int fNo = 1;



    public homeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        totalDebts = view.findViewById(R.id.totalDebts);
        navController = Navigation.findNavController(view);
        addDebtDetailsButton = view.findViewById(R.id.btnAdd);
        addDebtDetailsButton.setOnClickListener(v-> navController.navigate(R.id.action_homeFragment_to_addDebtFragment));

        recyclerView = view.findViewById(R.id.peopleRecView);
        myDb = new DatabaseHelper(getContext());
        debtors = new ArrayList<>(myDb.getAllDebtors());
        recyViewAdapter = new DebtorRecyViewAdapter(debtors, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recyViewAdapter);


        int totals = myDb.getSum();
        totalDebts.setText("Ksh."+totals);

    }

    @Override
    public void onItemClicked(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(DBT_ID, debtors.get(position).getId());
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.saveData(fNo, bundle);
        navController.navigate(R.id.action_homeFragment_to_detailsFragment);

        Toast.makeText(getContext(), "Id is " + (debtors.get(position)).getId(), Toast.LENGTH_SHORT).show();
    }


}