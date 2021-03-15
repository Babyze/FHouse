package com.habp.fhouse.data.repository;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.habp.fhouse.data.datasource.FirebaseAuthRemote;

public class FirebaseAuthRepository {
    private final FirebaseAuthRemote firebaseAuthRemote;
    private boolean[] result = new boolean[1];

    public FirebaseAuthRepository(FirebaseAuth firebaseAuth, FirebaseFirestore firebaseFirestore) {
        firebaseAuthRemote = new FirebaseAuthRemote(firebaseAuth, firebaseFirestore);
    }

    public FirebaseAuthRepository(FirebaseFirestore firebaseFirestore) {
        firebaseAuthRemote = new FirebaseAuthRemote(firebaseFirestore);
    }

    public FirebaseAuthRepository(FirebaseAuth firebaseAuth) {
        firebaseAuthRemote = new FirebaseAuthRemote(firebaseAuth);
    }

    public boolean signIn(String email, String password) {
        firebaseAuthRemote.signIn(email, password, isSuccess -> result[0] = isSuccess);
        return result[0];
    }

    public boolean signUp(String email, String password, String name, String phone) {
        firebaseAuthRemote.signUp(email, password, name, phone, isSuccess -> result[0] = isSuccess);
        return result[0];
    }

    public boolean resetPassword(String email) {
        firebaseAuthRemote.resetPassword(email, isSuccess -> result[0] = isSuccess);
        return result[0];
    }

    public void signOut() {
        firebaseAuthRemote.signOut();
    }
}
