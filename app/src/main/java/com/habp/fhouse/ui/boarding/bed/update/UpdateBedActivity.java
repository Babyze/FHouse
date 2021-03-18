package com.habp.fhouse.ui.boarding.bed.update;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.habp.fhouse.R;
import com.habp.fhouse.data.model.Bed;

public class UpdateBedActivity extends AppCompatActivity {
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_bed);

        Intent intent = getIntent();
        Bed currentBed = (Bed) intent.getSerializableExtra("currentBed");
        Glide.with(this).load(currentBed.getPhotoPath()).into((ImageView) this.findViewById(R.id.imgUploadPhoto));
        EditText edtBedNameUpdate = findViewById(R.id.edtBedNameUpdate);
        edtBedNameUpdate.setText(currentBed.getBedName());
        ImageView imgIconUpload = findViewById(R.id.imgIconUpload);
        TextView tvUpload = findViewById(R.id.tvUpload);

        imgIconUpload.setVisibility(View.INVISIBLE);
        tvUpload.setVisibility(View.INVISIBLE);

    }

    public void clickToBackActivity(View view) {
        finish();
    }

    public void clickToUpdateBedDetail(View view) {

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
}