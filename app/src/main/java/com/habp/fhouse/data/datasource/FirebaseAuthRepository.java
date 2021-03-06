package com.habp.fhouse.data.datasource;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.habp.fhouse.data.model.User;
import com.habp.fhouse.util.CallBack;
import com.habp.fhouse.util.DatabaseConstraints;

public class FirebaseAuthRepository {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    public FirebaseAuthRepository(FirebaseAuth firebaseAuth, FirebaseFirestore firebaseFirestore) {
        this.firebaseAuth = firebaseAuth;
        this.firebaseFirestore = firebaseFirestore;
    }

    public FirebaseAuthRepository(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    public FirebaseAuthRepository(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }

    public void signIn(String email, String password, CallBack<Boolean> callBack) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> callBack.onSuccessListener(task.isSuccessful()));
    }

    public void signUp(String email, String password, String name, String phone, CallBack<Boolean> callBack) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        User user = new User(getUserId(), name, email, "", DatabaseConstraints.PROFILE_DEFAULT_IMAGE_PATH, phone);
                        firebaseFirestore.collection(DatabaseConstraints.USER_COLLECTION_NAME)
                                .document(user.getUserId())
                                .set(user)
                                .addOnCompleteListener(dbTask -> callBack.onSuccessListener(dbTask.isSuccessful()));
                    }
                });
    }

    public void resetPassword(String email, CallBack<Boolean> callBack) {
        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> callBack.onSuccessListener(task.isSuccessful()));
    }

    public void signOut() {
        firebaseAuth.signOut();
    }

    public FirebaseUser getUser() {
        return firebaseAuth.getCurrentUser();
    }

    public String getUserId() {
        return firebaseAuth.getCurrentUser() == null ?
                "" : firebaseAuth.getCurrentUser().getUid();
    }
}
