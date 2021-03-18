package com.habp.fhouse.ui.boarding.house.create;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.habp.fhouse.R;
import com.habp.fhouse.data.datasource.FirebaseStorageRemote;
import com.habp.fhouse.data.datasource.HouseFirestoreRepository;
import com.habp.fhouse.data.model.House;
import com.habp.fhouse.util.ConvertHelper;

import java.io.IOException;
import java.util.UUID;

public class CreateHouseActivity extends AppCompatActivity implements CreateHouseContract.View {
    private Uri filePath;
    private CreateHousePresenter createHousePresenter;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_house);
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        FirebaseStorageRemote firebaseStorageRemote = new FirebaseStorageRemote(firebaseStorage);
        createHousePresenter =
                new CreateHousePresenter(this,
                        new HouseFirestoreRepository(firebaseFirestore, firebaseAuth), firebaseStorageRemote);
    }

    public void clickToCreateHouse(View view) {
        String houseId = UUID.randomUUID().toString();
        EditText edtHouseName = findViewById(R.id.edtHouseName);
        String houseName = edtHouseName.getText().toString();
        EditText edtHouseAddress = findViewById(R.id.edtHouseAddress);
        String houseAddress = edtHouseAddress.getText().toString();
        byte[] imageByte = ConvertHelper.convertImageViewToByte(findViewById(R.id.imgUploadPhoto));
        String userId = firebaseAuth.getUid();
        House house = new House(houseId, houseName, houseAddress, userId);
        createHousePresenter.createHouse(house, imageByte);
    }

    public void clickToUploadPhoto(View view) {
        chooseImage();
    }

    public void clickToBackActivity(View view) {
        finish();
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
        if(requestCode == 1 && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {

                ImageView imgUploadPhoto = findViewById(R.id.imgUploadPhoto);
                ImageView imgIconUpload = findViewById(R.id.imgIconUpload);
                TextView tvUpload = findViewById(R.id.tvUpload);

                imgIconUpload.setVisibility(View.INVISIBLE);
                tvUpload.setVisibility(View.INVISIBLE);
                imgUploadPhoto.setImageURI(filePath);
                imgUploadPhoto.setVisibility(View.VISIBLE);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreateSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onCreateFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onHouseNameError(String message) {

    }

    @Override
    public void onAddressError(String message) {

    }
}