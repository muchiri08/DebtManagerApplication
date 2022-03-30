package com.example.debtmanagerapp;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.debtmanagerapp.db.DatabaseHelper;
import com.example.debtmanagerapp.model.DebtorModel;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddDebtFragment extends Fragment {
    private TextInputEditText fieldFirstName, fieldLastName, fieldPhoneNumber, fieldAmount, fieldDateGiven, fieldDeadlineDate;
    private Button buttonClear, buttonSubmit;
    private TextView tvWarning;

    DatePickerDialog datePickerDialog;

    public AddDebtFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_debt, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fieldFirstName = view.findViewById(R.id.tfFN);
        fieldLastName = view.findViewById(R.id.tfLN);
        fieldPhoneNumber = view.findViewById(R.id.tfPN);
        fieldAmount = view.findViewById(R.id.tfA);
        fieldDateGiven = view.findViewById(R.id.tfDG);
        fieldDeadlineDate = view.findViewById(R.id.tfDD);

        buttonClear = view.findViewById(R.id.buttonClear);
        buttonSubmit = view.findViewById(R.id.buttonSubmit);

        tvWarning = view.findViewById(R.id.textViewWarning);

        fieldDateGiven.setOnClickListener(v->{
            final Calendar calendar = Calendar.getInstance();
            int mYear = calendar.get(Calendar.YEAR);
            int mMonth = calendar.get(Calendar.MONTH);
            int mDay = calendar.get(Calendar.DAY_OF_MONTH);

            datePickerDialog = new DatePickerDialog(getContext(), (view1, year, month, dayOfMonth) -> fieldDateGiven.setText(year + "-" + (month + 1) + "-" + dayOfMonth), mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        fieldDeadlineDate.setOnClickListener(v->{
            final Calendar calendar = Calendar.getInstance();
            int mYear = calendar.get(Calendar.YEAR);
            int mMonth = calendar.get(Calendar.MONTH);
            int mDay = calendar.get(Calendar.DAY_OF_MONTH);

            datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    fieldDeadlineDate.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                }
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        buttonClear.setOnClickListener(v->{
            fieldFirstName.setText("");
            fieldLastName.setText("");
            fieldPhoneNumber.setText("");
            fieldAmount.setText("");
            fieldDateGiven.setText("");
            fieldDeadlineDate.setText("");
        });

        buttonSubmit.setOnClickListener(v->{
            if (fieldFirstName.getText().toString().equals("") || fieldLastName.getText().toString().equals("") || fieldPhoneNumber.getText().toString().equals("") || fieldAmount.getText().toString().equals("") || fieldDateGiven.getText().toString().equals("") || fieldDeadlineDate.getText().toString().equals("")){
                tvWarning.setVisibility(View.VISIBLE);
                tvWarning.setTextColor(getResources().getColor(R.color.red));
            }
            else {
                tvWarning.setVisibility(View.INVISIBLE);

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-M-d");
                DebtorModel debtor = new DebtorModel(-1, fieldFirstName.getText().toString(), fieldLastName.getText().toString(), Integer.parseInt(fieldPhoneNumber.getText().toString()), Integer.parseInt(fieldAmount.getText().toString()), LocalDate.parse(fieldDateGiven.getText().toString(), dtf), LocalDate.parse(fieldDeadlineDate.getText().toString(), dtf));
                DatabaseHelper myDb = new DatabaseHelper(getContext());
                boolean success = myDb.insertData(debtor);

                if (success == true){
                    Toast.makeText(getContext(), "Added successfully", Toast.LENGTH_SHORT).show();

                    fieldFirstName.setText("");
                    fieldLastName.setText("");
                    fieldPhoneNumber.setText("");
                    fieldAmount.setText("");
                    fieldDateGiven.setText("");
                    fieldDeadlineDate.setText("");
                }
                else {
                    Toast.makeText(getContext(), "Error during insertion", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}