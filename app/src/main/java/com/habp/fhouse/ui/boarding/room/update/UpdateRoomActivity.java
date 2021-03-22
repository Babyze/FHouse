package com.habp.fhouse.ui.boarding.room.update;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.habp.fhouse.R;
import com.habp.fhouse.data.datasource.FirebaseStorageRemote;
import com.habp.fhouse.data.datasource.RoomFirestoreRepository;
import com.habp.fhouse.data.model.Room;
import com.habp.fhouse.util.ConvertHelper;

public class UpdateRoomActivity extends AppCompatActivity {
    private Uri filePath;
    private Room currentRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_room);
        Intent intent = getIntent();
        currentRoom = (Room) intent.getSerializableExtra("currentRoom");
        Glide.with(this).load(currentRoom.getPhotoPath()).into((ImageView) this.findViewById(R.id.imgUploadPhoto));
        EditText edtRoomNameUpdate = findViewById(R.id.edtRoomNameUpdate);
        edtRoomNameUpdate.setText(currentRoom.getRoomName());
        ImageView imgIconUpload = findViewById(R.id.imgIconUpload);
        TextView tvUpload = findViewById(R.id.tvUpload);
        imgIconUpload.setVisibility(View.INVISIBLE);
        tvUpload.setVisibility(View.INVISIBLE);
    }

    public void clickToBackActivity(View view) {
        Intent intent = getIntent();
        setResult(0, intent);
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
        //Chuẩn bị data update
        EditText edtRoomNameUpdate = findViewById(R.id.edtRoomNameUpdate);
        if (!edtRoomNameUpdate.getText().toString().isEmpty()) {
            currentRoom.setRoomName(edtRoomNameUpdate.getText().toString());
            byte[] imageByte = ConvertHelper.convertImageViewToByte(findViewById(R.id.imgUploadPhoto));
            FirebaseStorageRemote firebaseStorageRemote = new FirebaseStorageRemote(FirebaseStorage.getInstance());
            RoomFirestoreRepository roomFirestoreRepository = new RoomFirestoreRepository(FirebaseFirestore.getInstance());
            roomFirestoreRepository.updateRoom(currentRoom, room -> {
                if (room != null) {
                    Toast.makeText(this, "Update successful", Toast.LENGTH_SHORT).show();
                    firebaseStorageRemote.uploadImage(imageByte, room.getPhotoPath(), isSuccessful -> {
                        firebaseStorageRemote.getImageURL(room.getPhotoPath(), imageURL -> {
                            currentRoom.setPhotoPath(imageURL.toString());
                            Intent intent = getIntent();
                            intent.putExtra("currentRoom", currentRoom);
                            setResult(1, intent);
                            finish();
                        });
                    });
                } else {
                    Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        } else Toast.makeText(this, "Please input room name", Toast.LENGTH_SHORT).show();

    }
}