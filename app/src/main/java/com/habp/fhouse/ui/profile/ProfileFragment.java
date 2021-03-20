package com.habp.fhouse.ui.profile;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.habp.fhouse.MainActivity;
import com.habp.fhouse.R;
import com.habp.fhouse.data.datasource.FirebaseAuthRepository;
import com.habp.fhouse.data.datasource.UserFirestoreRepository;
import com.habp.fhouse.data.model.User;
import com.habp.fhouse.ui.article.ArticleFragment;
import com.habp.fhouse.ui.boarding.HouseManagementFragment;
import com.habp.fhouse.ui.editprofile.EditProfileActivity;
import com.habp.fhouse.ui.forgotpassword.ForgotPasswordActivity;
import com.habp.fhouse.ui.home.HomeFragment;
import com.habp.fhouse.ui.sign.SignInActivity;
import com.habp.fhouse.ui.sign.SignInPresenter;
import com.habp.fhouse.ui.wishlist.WishlistFragment;
import com.habp.fhouse.util.ConvertHelper;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment implements ProfileContract.View {

    private EditText edtUsername;
    private TextInputLayout edtEmail, edtPhoneNumber;
    private ImageView imgAvatar;
    private Button btnSignOut, btnForgotPsw, btnUpdate;
    private Uri filePath;
    private View view;

    private FirebaseAuthRepository firebaseAuthRepository;
    private UserFirestoreRepository userFirestoreRepository;
    private ProfilePresenter profilePresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        firebaseAuthRepository = new FirebaseAuthRepository(FirebaseAuth.getInstance());
        userFirestoreRepository =
                new UserFirestoreRepository(FirebaseFirestore.getInstance(), FirebaseAuth.getInstance());
        profilePresenter = new ProfilePresenter(firebaseAuthRepository, userFirestoreRepository, this);

        edtUsername = view.findViewById(R.id.edtUsername);
        edtEmail = view.findViewById(R.id.edtEmail);
        edtPhoneNumber = view.findViewById(R.id.edtPhoneNumber);
        btnSignOut = view.findViewById(R.id.btnSignOut);
        btnForgotPsw = view.findViewById(R.id.btnForgotPsw);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        imgAvatar = view.findViewById(R.id.imgAvatar);

        // Check authorization
        // If user has logged in, get User Profile and show on screen
        // Else, if return isReturn false, switch to Sign In Activity, else switch to Main
        profilePresenter.checkAuthorization(false);

        // Set on click to change fragment
        this.setOnClick();
        return view;
    }

    private void setOnClick() {
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profilePresenter.onSignOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "Signed out", Toast.LENGTH_SHORT).show();
            }
        });
        btnForgotPsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] imageByte = ConvertHelper.convertImageViewToByte(view.findViewById(R.id.imgAvatar));
                profilePresenter.onUpdate(
                        edtUsername.getText().toString().trim(),
                        edtPhoneNumber.getEditText().getText().toString().trim(),
                        imageByte);
            }
        });
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
    }

    @Override
    public void onGetUserProfileSuccess(User user) {
        showUserProfile(user);
        Toast.makeText(getActivity(), "Get Profile success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetUserProfileFailed(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);

    }

    @Override
    public void onUpdateSuccess() {
        Toast.makeText(getActivity(), "Update success", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onUpdateFailed(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        return;
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

    @Override
    public void startSignInActivity() {
        Intent intent = new Intent(getActivity(), SignInActivity.class);
        startActivity(intent);
    }

    @Override
    public void closeActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    private void showUserProfile(User user) {
        edtUsername.setText(user.getFullName());
        edtEmail.getEditText().setText(user.getEmail());
        edtEmail.setEnabled(false);
        edtPhoneNumber.getEditText().setText(user.getPhone());
        Glide.with(this).load(user.getPhotoPath()).into((ImageView) view.findViewById(R.id.imgAvatar));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
}
