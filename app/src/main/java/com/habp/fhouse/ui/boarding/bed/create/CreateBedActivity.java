package com.habp.fhouse.ui.boarding.bed.create;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.habp.fhouse.R;
import com.habp.fhouse.data.datasource.BedFirestoreRepository;
import com.habp.fhouse.data.datasource.FirebaseStorageRemote;
import com.habp.fhouse.data.model.Bed;
import com.habp.fhouse.data.model.Room;
import com.habp.fhouse.util.ConvertHelper;

import java.util.UUID;

public class CreateBedActivity extends AppCompatActivity implements CreateBedContract.View {
    private Uri filePath;
    private CreateBedPresenter createBedPresenter;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bed);

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseStorageRemote firebaseStorageRemote = new FirebaseStorageRemote(firebaseStorage);
        createBedPresenter =
                new CreateBedPresenter(this, new BedFirestoreRepository(firebaseFirestore), firebaseStorageRemote);
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
                ImageView icIconUpload = findViewById(R.id.icIconUpload);

                icIconUpload.setVisibility(View.VISIBLE);
                imgIconUpload.setVisibility(View.INVISIBLE);
                tvUpload.setVisibility(View.INVISIBLE);
                imgUploadPhoto.setImageURI(filePath);
                imgUploadPhoto.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void clickToCreateBed(View view) {
        Intent intent = getIntent();
        Room currentRoom = (Room) intent.getSerializableExtra("currentRoom");
        String currentRoomId = currentRoom.getRoomId();
        String bedId = UUID.randomUUID().toString();
        EditText edtBedName = findViewById(R.id.edtBedName);
        String bedName = edtBedName.getText().toString();
        byte[] imageByte = ConvertHelper.convertImageViewToByte(findViewById(R.id.imgUploadPhoto));
        if (!bedName.isEmpty()) {
            Bed bed = new Bed(bedId, bedName, currentRoomId);
            createBedPresenter.createBed(bed, imageByte);
        } else onBedNameError("Please enter bed name");
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
    public void onBedNameError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}