package com.habp.fhouse.ui.forgotpassword;

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

public class ForgotPasswordActivity extends AppCompatActivity implements ForgotPasswordContract.View {
    private FirebaseAuthRepository firebaseAuthRepository;
    private ForgotPasswordPresenter forgotPasswordPresenter;
    private TextInputLayout edtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        firebaseAuthRepository = new FirebaseAuthRepository(FirebaseAuth.getInstance());
        forgotPasswordPresenter = new ForgotPasswordPresenter(firebaseAuthRepository, this);

        edtEmail = findViewById(R.id.edtEmail);
    }

    public void clickToBack(View view) {
        finish();
    }

    public void clickToResetPassword(View view) {
        forgotPasswordPresenter.onResetPassword(edtEmail.getEditText().getText().toString().trim());
    }

    @Override
    public void onResetPasswordSuccess() {
        Toast.makeText(this, "Reset password success", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResetPasswordFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        return;
    }

    @Override
    public void onInvalidEmail(String message) {
        edtEmail.setError(message);
        return;
    }
}