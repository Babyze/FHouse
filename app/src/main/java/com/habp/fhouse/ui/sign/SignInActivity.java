package com.habp.fhouse.ui.sign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import com.habp.fhouse.MainActivity;
import com.habp.fhouse.R;
import com.habp.fhouse.data.datasource.FirebaseAuthRepository;
import com.habp.fhouse.ui.signup.SignUpActivity;

public class SignInActivity extends AppCompatActivity implements SignInContract.View {
    TextInputLayout edtEmail, edtPassword;
    private FirebaseAuthRepository firebaseAuthRepository;
    private SignInPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuthRepository = new FirebaseAuthRepository(FirebaseAuth.getInstance());
        loginPresenter = new SignInPresenter(firebaseAuthRepository, this);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
    }

    public void clickToBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void clickOnForgotPsw(View view) {
//        Intent intent = new Intent(this, ForgotPasswordActivity.class);
//        startActivity(intent);
    }

    public void clickToSignUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void clickToSignInAccount(View view) {
        String email = edtEmail.getEditText().getText().toString();
        String password = edtPassword.getEditText().getText().toString();
        loginPresenter.onLogin(email, password);
    }

    public void clickToLoginWithGG(View view) {
    }

    public void clickToLoginWithFB(View view) {
    }


    @Override
    public void onLoginSuccess() {
        Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onLoginFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInvalidEmail(String message) {
        edtEmail.setError(message);
    }

    @Override
    public void onInvalidPassword(String message) {
        edtPassword.setError(message);
    }
}