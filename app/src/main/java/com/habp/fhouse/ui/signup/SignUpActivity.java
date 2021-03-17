package com.habp.fhouse.ui.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.habp.fhouse.MainActivity;
import com.habp.fhouse.R;
import com.habp.fhouse.data.datasource.FirebaseAuthRepository;

public class SignUpActivity extends AppCompatActivity implements SignUpContract.View {
    private FirebaseAuthRepository firebaseAuthRepository;
    private SignUpPresenter signUpPresenter;
    private TextInputLayout edtEmail, edtPassword, edtConfirmPsw, edtName, edtPhoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firebaseAuthRepository = new FirebaseAuthRepository(FirebaseAuth.getInstance(), FirebaseFirestore.getInstance());
        signUpPresenter = new SignUpPresenter(firebaseAuthRepository, this);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPsw = findViewById(R.id.edtConfirmPsw);
        edtName = findViewById(R.id.edtName);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
    }

    public void clickToBack(View view) {
        finish();
    }

    public void clickToSignUp(View view) {
        signUpPresenter.onSignUp(
                edtEmail.getEditText().getText().toString(),
                edtPassword.getEditText().getText().toString(),
                edtConfirmPsw.getEditText().getText().toString(),
                edtName.getEditText().getText().toString(),
                edtPhoneNumber.getEditText().getText().toString()
        );

    }

    public void clickToSignIn(View view) {
//        Intent intent = new Intent(this, SignIn.class);
//        startActivity(intent);
    }

    @Override
    public void onSignUpSuccess() {
        Toast.makeText(this, "Sign up success", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSignUpFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInvalidEmail(String message) {
        edtEmail.setError(message);
        return;
    }

    @Override
    public void onInvalidPassword(String message) {
        edtPassword.setError(message);
        return;
    }

    @Override
    public void onInvalidConfirmPassword(String message) {
        edtConfirmPsw.setError(message);
        return;
    }

    @Override
    public void onInvalidName(String message) {
        edtName.setError(message);
        return;
    }

    @Override
    public void onInvalidPhoneNumber(String message) {
        edtPhoneNumber.setError(message);
        return;
    }
}