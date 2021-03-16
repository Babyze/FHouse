package com.habp.fhouse.data.datasource;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.habp.fhouse.data.model.User;
import com.habp.fhouse.util.CallBack;
import com.habp.fhouse.util.ConvertHelper;
import com.habp.fhouse.util.DatabaseConstraints;

import java.util.HashMap;
import java.util.Map;

public class UserFirestoreRepository {
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    public UserFirestoreRepository(FirebaseFirestore firebaseFirestore, FirebaseAuth firebaseAuth) {
        this.firebaseFirestore = firebaseFirestore;
        this.firebaseAuth = firebaseAuth;
    }

    public void getUserInfo(CallBack<User> callBack) {
        firebaseFirestore.collection(DatabaseConstraints.USER_COLLECTION_NAME)
                .document(firebaseAuth.getUid())
                .get().addOnCompleteListener(task -> {
                    User user = task.getResult().toObject(User.class);
                    callBack.onSuccessListener(user);
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
