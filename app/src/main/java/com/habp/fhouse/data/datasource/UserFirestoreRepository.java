package com.habp.fhouse.data.datasource;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserFirestoreRepository {
    private FirebaseFirestore firebaseFirestore;
    private String userId;

    public UserFirestoreRepository(FirebaseFirestore firebaseFirestore, String userId) {
        this.firebaseFirestore = firebaseFirestore;
        this.userId = userId;
    }

    public void getUserInfo() {
        FirebaseAuthRepository firebaseAuthRepository = new FirebaseAuthRepository(FirebaseAuth.getInstance());

    }
}
