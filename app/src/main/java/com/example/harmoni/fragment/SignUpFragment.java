package com.example.harmoni.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harmoni.R;
import com.example.harmoni.activity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpFragment extends Fragment {

    private TextView signIn;
    private EditText username;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private Button signUpButton;

    private ProgressBar signUpProgress;
    private FrameLayout frameLayout;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        signIn = view.findViewById(R.id.textView_signIn);

        username = view.findViewById(R.id.textfieldUsername);
        email = view.findViewById(R.id.textfieldEmail);
        password = view.findViewById(R.id.textfieldPassword);
        confirmPassword = view.findViewById(R.id.textfieldConfirmPassword);

        signUpButton = view.findViewById(R.id.buttonSignUp);

        frameLayout = getActivity().findViewById(R.id.fragment_auth);

        signUpButton.setEnabled(false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        signIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                setFragment(new SignInFragment());
            }
        });
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpWithFirebase();
            }
        });

    }

    private void signUpWithFirebase() {
        if (email.getText().toString().matches("[a-zA-Z0-9]+@[a-z]+\\.+[a-z]+")  ) {
            if (password.getText().toString().equals(confirmPassword.getText().toString())) {
                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            getActivity().startActivity(intent);
                            getActivity().finish();
                        } else {
                            Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            signUpButton.setEnabled(true);
                        }
                    }
                });
            } else {
                confirmPassword.setError("Password doesn't match");
            }
        } else {
            email.setError("Invalid email pattern");
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.from_left, R.anim.out_from_right);
        fragmentTransaction.replace(frameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

    private void checkInputs() {
         if (username.getText().toString().isEmpty() || email.getText().toString().isEmpty()|| password.getText().toString().isEmpty() || confirmPassword.getText().toString().isEmpty()) {
             signUpButton.setEnabled(false);
         }
    }
}