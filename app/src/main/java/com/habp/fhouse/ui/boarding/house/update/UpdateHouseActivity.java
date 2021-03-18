package com.habp.fhouse.ui.boarding.house.update;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.habp.fhouse.R;
import com.habp.fhouse.data.datasource.FirebaseStorageRemote;
import com.habp.fhouse.data.model.Bed;
import com.habp.fhouse.data.model.House;
import com.habp.fhouse.util.ConvertHelper;

public class UpdateHouseActivity extends AppCompatActivity {
    private Uri filePath;
    private House currentHouse;
    private final int INTENT_RESULT_DELETE_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_house);

        Intent intent = getIntent();
        currentHouse = (House) intent.getSerializableExtra("currentHouse");
        //set Image
        Glide.with(this).load(currentHouse.getPhotoPath()).into((ImageView) this.findViewById(R.id.imgUploadPhoto));

        EditText edtHouseNameUpdate = findViewById(R.id.edtHouseNameUpdate);
        EditText edtHouseAddressUpdate = findViewById(R.id.edtHouseAddressUpdate);

        edtHouseNameUpdate.setText(currentHouse.getHouseName());
        edtHouseAddressUpdate.setText(currentHouse.getHouseAddress());

        ImageView imgIconUpload = findViewById(R.id.imgIconUpload);
        TextView tvUpload = findViewById(R.id.tvUpload);

        imgIconUpload.setVisibility(View.INVISIBLE);
        tvUpload.setVisibility(View.INVISIBLE);
    }

    public void clickToBackActivity(View view) {
        finish();
    }

    public void clickToUploadPhoto(View view) {
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

                ImageView imgUploadPhoto = findViewById(R.id.imgUploadPhoto);
                ImageView imgIconUpload = findViewById(R.id.imgIconUpload);
                TextView tvUpload = findViewById(R.id.tvUpload);

                imgIconUpload.setVisibility(View.INVISIBLE);
                tvUpload.setVisibility(View.INVISIBLE);
                imgUploadPhoto.setImageURI(filePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void clickToUpdateHouseDetail(View view) {
        //chuẩn bị data update
        EditText edtHouseNameUpdate = findViewById(R.id.edtHouseNameUpdate);
        EditText edtHouseAddressUpdate = findViewById(R.id.edtHouseAddressUpdate);
        currentHouse.setHouseName(edtHouseNameUpdate.getText().toString());
        currentHouse.setHouseAddress(edtHouseAddressUpdate.getText().toString());
        byte[] imageByte = ConvertHelper.convertImageViewToByte(findViewById(R.id.imgUploadPhoto));


    }

    public void clickToDeleteHouse(View view) {
        Intent intent = getIntent();
        intent.putExtra("deleteHouse", currentHouse);
        setResult(INTENT_RESULT_DELETE_CODE, intent);
        finish();
    }
}