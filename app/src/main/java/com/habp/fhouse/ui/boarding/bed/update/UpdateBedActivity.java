package com.habp.fhouse.ui.boarding.bed.update;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import com.habp.fhouse.data.datasource.BedFirestoreRepository;
import com.habp.fhouse.data.datasource.FirebaseStorageRemote;
import com.habp.fhouse.data.model.Bed;
import com.habp.fhouse.data.model.Room;
import com.habp.fhouse.ui.boarding.room.roomdetail.RoomDetailFragment;
import com.habp.fhouse.util.ConvertHelper;

public class UpdateBedActivity extends AppCompatActivity {
    private Uri filePath;
    private Bed currentBed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_bed);

        Intent intent = getIntent();
        currentBed = (Bed) intent.getSerializableExtra("currentBed");
        Glide.with(this).load(currentBed.getPhotoPath()).into((ImageView) this.findViewById(R.id.imgUploadPhoto));
        EditText edtBedNameUpdate = findViewById(R.id.edtBedNameUpdate);
        edtBedNameUpdate.setText(currentBed.getBedName());
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

    public void clickToUpdateBedDetail(View view) {
        //Chuẩn bị data
        EditText edtBedNameUpdate = findViewById(R.id.edtBedNameUpdate);
        currentBed.setBedName(edtBedNameUpdate.getText().toString());

        byte[] imageByte = ConvertHelper.convertImageViewToByte(findViewById(R.id.imgUploadPhoto));
        FirebaseStorageRemote firebaseStorageRemote = new FirebaseStorageRemote(FirebaseStorage.getInstance());
        BedFirestoreRepository bedFirestoreRepository = new BedFirestoreRepository(FirebaseFirestore.getInstance());
        bedFirestoreRepository.updateBed(currentBed, bed->{
            if (bed !=null){
                Toast.makeText(this, "Update successful", Toast.LENGTH_SHORT).show();
                firebaseStorageRemote.uploadImage(imageByte, bed.getPhotoPath(), isSuccess->{
                    firebaseStorageRemote.getImageURL(bed.getPhotoPath(), imageURL->{
                        currentBed.setPhotoPath(imageURL.toString());
                        finish();
                    });
                });
            }else {
                Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

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

    public void clickToDeleteBed(View view) {
        String deleteBedId = currentBed.getBedId();
        //Delete
        BedFirestoreRepository bedFirestoreRepository =
                new BedFirestoreRepository(FirebaseFirestore.getInstance());
        bedFirestoreRepository.deleteBed(deleteBedId, isSuccess->{
            if (isSuccess){
                Toast.makeText(this, "Delete successful", Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(this, "Delete error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}