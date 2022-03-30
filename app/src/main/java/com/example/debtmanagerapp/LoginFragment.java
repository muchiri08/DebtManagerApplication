package com.example.debtmanagerapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.debtmanagerapp.db.DatabaseHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private TextInputEditText username, password;
    private MaterialButton login, register;
    private MaterialTextView tvError;
    NavController navController;
    DatabaseHelper myDb;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        username = view.findViewById(R.id.text_edit_username);
        password = view.findViewById(R.id.text_edit_password);
        login = view.findViewById(R.id.button_login);
        register = view.findViewById(R.id.button_register);
        tvError = view.findViewById(R.id.text_error);
        navController = Navigation.findNavController(view);
        myDb = new DatabaseHelper(getContext());

        register.setOnClickListener(v -> navController.navigate(R.id.action_loginFragment_to_registerFragment));

        login.setOnClickListener(v ->{
            if (username.getText().toString().equals("") || password.getText().toString().equals("")){
                tvError.setVisibility(View.VISIBLE);
            }
            else {
                boolean checkUserAndPasswordIfTrue = myDb.checkUsernameAndPassword(username.getText().toString(), password.getText().toString());
                if (checkUserAndPasswordIfTrue == true){
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }
                else {
                    tvError.setText("Username or Password error");
                    tvError.setVisibility(View.VISIBLE);
                    username.setText("");
                }
                password.setText("");
            }
        });
    }
}