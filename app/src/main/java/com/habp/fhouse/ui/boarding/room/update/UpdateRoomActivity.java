package com.habp.fhouse.ui.boarding.room.update;

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
import com.habp.fhouse.data.model.Room;

public class UpdateRoomActivity extends AppCompatActivity {
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_room);

        Intent intent = getIntent();
        Room currentRoom = (Room) intent.getSerializableExtra("currentRoom");
        Glide.with(this).load(currentRoom.getPhotoPath()).into((ImageView) this.findViewById(R.id.imgUploadPhoto));
        EditText edtRoomNameUpdate = findViewById(R.id.edtRoomNameUpdate);
        edtRoomNameUpdate.setText(currentRoom.getRoomName());
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

    public void clickToUpdateRoomDetail(View view) {
    }
}