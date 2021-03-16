package com.habp.fhouse.data.datasource;

import android.net.Uri;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.habp.fhouse.util.CallBack;
import com.habp.fhouse.util.DatabaseConstraints;

import java.io.File;

public class FirebaseStorageRemote {
    private FirebaseStorage firebaseStorage;

    public FirebaseStorageRemote(FirebaseStorage firebaseStorage) {
        this.firebaseStorage = firebaseStorage;
    }

    public void uploadImage(byte[] imageData, String imagePath, CallBack<Boolean> callBack) {
        StorageReference storageReference = firebaseStorage.getReference();
        StorageReference imageSto = storageReference.child(DatabaseConstraints.IMAGE_PATH + imagePath);
        imageSto.putBytes(imageData)
                .addOnSuccessListener(task -> callBack.onSuccessListener(true))
                .addOnFailureListener(task -> callBack.onSuccessListener(false));
    }

    public void uploadImage(File imageFile, String imagePath, CallBack<Boolean> callBack) {
        Uri imageUri = Uri.fromFile(imageFile);
        StorageReference storageReference = firebaseStorage.getReference();
        StorageReference imageSto = storageReference.child(DatabaseConstraints.IMAGE_PATH + imagePath);
        imageSto.putFile(imageUri)
                .addOnSuccessListener(task -> callBack.onSuccessListener(true))
                .addOnFailureListener(task -> callBack.onSuccessListener(false));
    }

    public void getImageURL(String imagePath, CallBack<Uri> callBack) {
        StorageReference storageReference = firebaseStorage.getReference();
        StorageReference imageFolder = storageReference.child(imagePath);
        imageFolder.getDownloadUrl()
                .addOnCompleteListener(task -> callBack.onSuccessListener(task.getResult()));
    }
}
