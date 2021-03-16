package com.habp.fhouse.ui.boarding.house.create;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.habp.fhouse.R;

import java.io.IOException;

public class CreateHouseActivity extends AppCompatActivity {
    private Uri filePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_house);
    }

    public void clickToCreateHouse(View view) {

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
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}