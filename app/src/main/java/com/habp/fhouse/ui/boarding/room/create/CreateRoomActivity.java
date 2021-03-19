package com.habp.fhouse.ui.boarding.room.create;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.habp.fhouse.R;
import com.habp.fhouse.data.datasource.FirebaseStorageRemote;
import com.habp.fhouse.data.datasource.RoomFirestoreRepository;
import com.habp.fhouse.data.model.Room;
import com.habp.fhouse.util.ConvertHelper;

import java.util.UUID;

public class CreateRoomActivity extends AppCompatActivity implements CreateRoomContract.View {
    private Uri filePath;
    private CreateRoomPresenter createRoomPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        FirebaseStorageRemote firebaseStorageRemote = new FirebaseStorageRemote(firebaseStorage);
        createRoomPresenter =
                new CreateRoomPresenter(this,
                        new RoomFirestoreRepository(firebaseFirestore), firebaseStorageRemote);
    }

    public void clickToBackActivity(View view) {
        finish();
    }

    public void clickToCreateRoom(View view) {
        Intent intent = getIntent();
        String currentHouseId = intent.getStringExtra("currentHouseId");
        String roomId = UUID.randomUUID().toString();
        EditText edtRoomName = findViewById(R.id.edtRoomName);
        String roomName = edtRoomName.getText().toString();
        byte[] imageByte = ConvertHelper.convertImageViewToByte(findViewById(R.id.imgUploadPhoto));
        if (!roomName.isEmpty()) {
            Room room = new Room(roomId, roomName, currentHouseId);
            createRoomPresenter.createRoom(room, imageByte);
        } else onRoomNameError("Please input room name");
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
                imgUploadPhoto.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreateSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onCreateFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRoomNameError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}