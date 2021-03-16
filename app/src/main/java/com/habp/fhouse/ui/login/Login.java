package com.habp.fhouse.ui.login;

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
import com.habp.fhouse.data.repository.FirebaseAuthRepository;

public class Login extends AppCompatActivity implements LoginContract.View {
    TextInputLayout edtEmail, edtPassword;
    private FirebaseAuthRepository firebaseAuthRepository;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Làm việc với DB, dùng: 
        // FirebaseFirestore.getInstance(); nhét vào FirebaseAuthRepository
        firebaseAuthRepository = new FirebaseAuthRepository(FirebaseAuth.getInstance());
        loginPresenter = new LoginPresenter(firebaseAuthRepository, this);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
    }

    public void clickToBack(View view) {
        finish();
    }

    public void clickOnForgotPsw(View view) {
//        Intent intent = new Intent(this, ForgotPasswordActivity.class);
//        startActivity(intent);
    }

    public void clickToSignUp(View view) {
//        Intent intent = new Intent(this, RegisterActivity.class);
//        startActivity(intent);
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
        // Sang activity khác
        Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLoginFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        return;
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
}