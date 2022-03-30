package com.example.debtmanagerapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.debtmanagerapp.db.DatabaseHelper;
import com.example.debtmanagerapp.model.UserModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {
private MaterialTextView textError;
private TextInputEditText username, phoneNumber, password, rePassword;
private MaterialButton register, login;
private DatabaseHelper myDb;
private NavController navController;


    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textError = view.findViewById(R.id.text_error_reg);
        username = view.findViewById(R.id.text_edit_username_reg);
        phoneNumber = view.findViewById(R.id.text_edit_mobile_reg);
        password = view.findViewById(R.id.text_edit_password_reg);
        rePassword = view.findViewById(R.id.text_edit_re_password_reg);
        register = view.findViewById(R.id.button_register_reg);
        login = view.findViewById(R.id.button_login_reg);
        myDb = new DatabaseHelper(getContext());
        navController = Navigation.findNavController(view);

        register.setOnClickListener(v -> {
            if (username.getText().toString().equals("") || phoneNumber.getText().toString().equals("") || password.getText().toString().equals("") || rePassword.getText().toString().equals("")){
                textError.setVisibility(View.VISIBLE);
            }
            else {
                textError.setVisibility(View.GONE);
                if (password.getText().toString().equals(rePassword.getText().toString())){
                    boolean checkUserIfExist = myDb.checkUser(username.getText().toString());
                    if (!checkUserIfExist){
                        UserModel user = new UserModel(username.getText().toString(), Integer.parseInt(phoneNumber.getText().toString()), password.getText().toString());
                        boolean success = myDb.insertDataUser(user);
                        if (success){
                            Toast.makeText(getContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                            navController.navigate(R.id.action_registerFragment_to_loginFragment);
                        }
                        else {
                            Toast.makeText(getContext(), "Error, Please try again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        textError.setText("User already exists");
                        textError.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    textError.setText("Password Mismatch");
                    textError.setVisibility(View.VISIBLE);
                }
            }
        });

        login.setOnClickListener(v -> {
            navController.navigate(R.id.action_registerFragment_to_loginFragment);
        });

    }
}