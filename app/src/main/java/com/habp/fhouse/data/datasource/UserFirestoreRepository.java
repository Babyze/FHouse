package com.habp.fhouse.data.datasource;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.habp.fhouse.data.model.User;
import com.habp.fhouse.util.CallBack;
import com.habp.fhouse.util.ConvertHelper;
import com.habp.fhouse.util.DatabaseConstraints;
import java.util.Map;

public class UserFirestoreRepository {
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    public UserFirestoreRepository(FirebaseFirestore firebaseFirestore, FirebaseAuth firebaseAuth) {
        this.firebaseFirestore = firebaseFirestore;
        this.firebaseAuth = firebaseAuth;
    }

    public UserFirestoreRepository(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }

    public void getUserInfo(CallBack<User> callBack) {
        FirebaseStorageRemote firebaseStorageRemote = new FirebaseStorageRemote(FirebaseStorage.getInstance());
        firebaseFirestore.collection(DatabaseConstraints.USER_COLLECTION_NAME)
                .document(firebaseAuth.getUid())
                .get().addOnCompleteListener(task -> {
                    User user = task.getResult().toObject(User.class);
                    firebaseStorageRemote.getImageURL(user.getPhotoPath(), imageURL -> {
                        user.setPhotoPath(imageURL.toString());
                        callBack.onSuccessListener(user);
                    });
                });
    }

    public void getUserInfo(String userId, CallBack<User> callBack) {
        FirebaseStorageRemote firebaseStorageRemote = new FirebaseStorageRemote(FirebaseStorage.getInstance());
        firebaseFirestore.collection(DatabaseConstraints.USER_COLLECTION_NAME)
                .document(userId)
                .get().addOnCompleteListener(task -> {
            User user = task.getResult().toObject(User.class);
            firebaseStorageRemote.getImageURL(user.getPhotoPath(), imageURL -> {
                user.setPhotoPath(imageURL.toString());
                callBack.onSuccessListener(user);
            });
        });
    }

    public void updateUser(User user, CallBack<Boolean> callback) {
        Map<String, Object> data = ConvertHelper.convertObjectToMap(user);
        firebaseFirestore.collection(DatabaseConstraints.USER_COLLECTION_NAME)
                .document(firebaseAuth.getUid())
                .update(data)
                .addOnCompleteListener(task -> callback.onSuccessListener(task.isSuccessful()));
    }

}
