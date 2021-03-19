package com.habp.fhouse.ui.editprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
    ImageView imgAvatar;
    private Uri filePath;

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
        imgAvatar = findViewById(R.id.imgAvatar);
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
        byte[] imageByte = ConvertHelper.convertImageViewToByte(findViewById(R.id.imgAvatar));
        editProfilePresenter.onUpdate(
                edtUsername.getText().toString().trim(),
                edtPhoneNumber.getEditText().getText().toString().trim(),
                imageByte);
    }

    private void showUserProfile(User user) {
        edtUsername.setText(user.getFullName());
        edtEmail.getEditText().setText(user.getEmail());
        edtEmail.setEnabled(false);
        edtPhoneNumber.getEditText().setText(user.getPhone());
        Glide.with(this).load(user.getPhotoPath()).into((ImageView) findViewById(R.id.imgAvatar));
    }

    @Override
    public void onGetUserProfileSuccess(User user) {
        showUserProfile(user);
        Toast.makeText(this, "Get Profile success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetUserProfileFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        return;
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
        return;
    }

    @Override
    public void onInvalidPhoneNumber(String message) {
        edtPhoneNumber.setError(message);
        return;
    }

    public void clickToUploadAvatar(View view) {
        chooseImage();
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                imgAvatar.setImageURI(filePath);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void clickToResetPassword(View view) {
//        Intent intent = new Intent(this, ForgotPasswordActivity.class);
//        startActivity(intent);
    }
}