package com.habp.fhouse.ui.editprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.habp.fhouse.MainActivity;
import com.habp.fhouse.R;
import com.habp.fhouse.data.datasource.FirebaseAuthRepository;
import com.habp.fhouse.data.datasource.UserFirestoreRepository;
import com.habp.fhouse.data.model.User;
import com.habp.fhouse.ui.signup.SignUpPresenter;
import com.habp.fhouse.util.ConvertHelper;

public class EditProfileActivity extends AppCompatActivity implements EditProfileContract.View {
    private UserFirestoreRepository userFirestoreRepository;
    private EditProfilePresenter editProfilePresenter;
    private EditText edtUsername;
    private TextInputLayout edtEmail, edtPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        userFirestoreRepository =
                new UserFirestoreRepository(FirebaseFirestore.getInstance(), FirebaseAuth.getInstance());
        editProfilePresenter = new EditProfilePresenter(userFirestoreRepository, this);

        edtUsername = findViewById(R.id.edtUsername);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        // Get User Profile from DB and Show on screen
        this.getUserProfile();
    }

    private void getUserProfile() {
        editProfilePresenter.getUserProfile();
    }


    public void clickToBack(View view) {
        finish();
    }

    public void clickToUpdate(View view) {
        editProfilePresenter.onUpdate(
                edtUsername.getText().toString().trim(),
                edtPhoneNumber.getEditText().getText().toString().trim());
    }

    private void showUserProfile(User user) {
        edtUsername.setText(user.getFullName());
        edtEmail.getEditText().setText(user.getEmail());
        edtEmail.setEnabled(false);
        edtPhoneNumber.getEditText().setText(user.getPhone());
    }

    @Override
    public void onGetUserProfileSuccess(User user) {
        showUserProfile(user);
        Toast.makeText(this, "Get Profile success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetUserProfileFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateSuccess() {
        Toast.makeText(this, "Update success", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onUpdateFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInvalidName(String message) {
        edtUsername.setError(message);
    }

    @Override
    public void onInvalidEmail(String message) {
        edtEmail.setError(message);
    }

    @Override
    public void onInvalidPhoneNumber(String message) {
        edtPhoneNumber.setError(message);
    }
}