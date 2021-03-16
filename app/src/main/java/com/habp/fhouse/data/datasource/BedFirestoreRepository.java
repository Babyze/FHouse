package com.habp.fhouse.data.datasource;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.habp.fhouse.data.model.Bed;
import com.habp.fhouse.util.CallBack;
import com.habp.fhouse.util.ConvertHelper;
import com.habp.fhouse.util.DatabaseConstraints;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BedFirestoreRepository {
    private CollectionReference collection;
    private FirebaseAuth firebaseAuth;

    public BedFirestoreRepository(FirebaseFirestore firebaseFirestore, FirebaseAuth firebaseAuth) {
        this.collection = firebaseFirestore.collection(DatabaseConstraints.ROOM_COLLECTION_NAME);
        this.firebaseAuth = firebaseAuth;
    }

    public void createBed(Bed bed, CallBack<Boolean> callBack) {
        isBedExist(bed.getBedId(), isBedExist -> {
            if(!isBedExist) {
                collection.document(bed.getBedId())
                        .set(bed + firebaseAuth.getUid());
            }
            callBack.onSuccessListener(!isBedExist);
        });
    }

    private void isBedExist(String bedId, CallBack<Boolean> callBack) {
        collection.document(bedId + firebaseAuth.getUid()).get()
                .addOnCompleteListener(task -> callBack.onSuccessListener(task.getResult().exists()));
    }

    public void getBed(String bedId, CallBack<Bed> callback) {
        collection.document(bedId + firebaseAuth.getUid())
                .get().addOnCompleteListener(task -> {
                    Bed bed = task.getResult().toObject(Bed.class);
                    callback.onSuccessListener(bed);
        });
    }

    public void getBedList(String roomId, CallBack<List<Bed>> callBack) {
        collection.whereEqualTo(DatabaseConstraints.ROOM_ID_KEY_NAME, roomId)
                .get().addOnCompleteListener(task -> {
            List<Bed> beds = new ArrayList<>();
            for(DocumentSnapshot documentSnapshot : task.getResult()) {
                beds.add(documentSnapshot.toObject(Bed.class));
            }
            callBack.onSuccessListener(beds);
        });
    }

    public void updateBed(Bed bed, CallBack<Boolean> callBack) {
        Map<String, Object> map = ConvertHelper.convertObjectToMap(bed);
        collection.document(bed.getBedId() + firebaseAuth.getUid())
                .update(map).addOnCompleteListener(task -> callBack.onSuccessListener(task.isSuccessful()));
    }

    public void deleteBed(String bedId, CallBack<Boolean> callBack) {
        collection.document(bedId + firebaseAuth.getUid()).delete()
                .addOnCompleteListener(task -> callBack.onSuccessListener(task.isSuccessful()));
    }
}
