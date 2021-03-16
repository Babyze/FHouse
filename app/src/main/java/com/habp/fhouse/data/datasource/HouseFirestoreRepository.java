package com.habp.fhouse.data.datasource;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.habp.fhouse.data.model.House;
import com.habp.fhouse.util.CallBack;
import com.habp.fhouse.util.ConvertHelper;
import com.habp.fhouse.util.DatabaseConstraints;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HouseFirestoreRepository {
    private FirebaseAuth firebaseAuth;
    private CollectionReference collection;

    public HouseFirestoreRepository(FirebaseFirestore firebaseFirestore, FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
        this.collection = firebaseFirestore.collection(DatabaseConstraints.HOUSE_COLLECTION_NAME);
    }

    public void createHouse(House house, CallBack<Boolean> callBack) {
        isHouseExist(house.getHouseId(), isExist -> {
            if(!isExist) {
                collection.document(house.getHouseId())
                        .set(house);
            }
            callBack.onSuccessListener(!isExist);
        });
    }

    private void isHouseExist(String houseId, CallBack<Boolean> callBack) {
        collection.document(houseId).get()
                .addOnCompleteListener(task -> {
                    callBack.onSuccessListener(task.getResult().exists());
                });
    }

    public void getHouseList(CallBack<List<House>> callBack) {
        collection.whereEqualTo(DatabaseConstraints.USER_ID_KEY_NAME, firebaseAuth.getUid())
                .get().addOnCompleteListener(task -> {
                    List<House> houses = new ArrayList<>();
                    QuerySnapshot snapshots = task.getResult();
                    if(snapshots != null) {
                       for(DocumentSnapshot documentSnapshot : snapshots.getDocuments()) {
                           houses.add(documentSnapshot.toObject(House.class));
                       }
                    }
                    callBack.onSuccessListener(houses);
                });
    }

    public void getHouse(String houseId, CallBack<House> callBack) {
        collection.document(houseId)
                .get().addOnCompleteListener(task -> {
                    House house = new House();
                    if(task.isSuccessful()) {
                        house = task.getResult().toObject(House.class);
                    }
                    callBack.onSuccessListener(house);
                });
    }

    public void updateHouse(House house, CallBack<Boolean> callBack) {
        Map<String, Object> houseData = ConvertHelper.convertObjectToMap(house);
        collection.document(house.getHouseId())
                .update(houseData)
                .addOnCompleteListener(task -> callBack.onSuccessListener(task.isSuccessful()));
    }

    public void deleteHouse(String houseId, CallBack<Boolean> callBack) {
        collection.document(houseId)
                .delete().addOnCompleteListener(task -> callBack.onSuccessListener(task.isSuccessful()));
    }
 }
