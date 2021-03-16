package com.habp.fhouse.data.datasource;

import com.google.firebase.auth.FirebaseAuth;
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
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    public HouseFirestoreRepository(FirebaseFirestore firebaseFirestore, FirebaseAuth firebaseAuth) {
        this.firebaseFirestore = firebaseFirestore;
        this.firebaseAuth = firebaseAuth;
    }

    public void createHouse(House house, CallBack<Boolean> callBack) {
        firebaseFirestore.collection(DatabaseConstraints.HOUSE_COLLECTION_NAME)
                .document(house.getHouseId())
                .set(house).addOnCompleteListener(task -> callBack.onSuccessListener(task.isSuccessful()));
    }

    public void getHouseList(CallBack<List<House>> callBack) {
        firebaseFirestore.collection(DatabaseConstraints.HOUSE_COLLECTION_NAME)
                .whereEqualTo(DatabaseConstraints.USER_ID_KEY_NAME, firebaseAuth.getUid())
                .get().addOnCompleteListener(task -> {
                    List<House> houses = new ArrayList<>();
                   QuerySnapshot snapshots = task.getResult();
                   for(DocumentSnapshot documentSnapshot : snapshots.getDocuments()) {
                       House house = documentSnapshot.toObject(House.class);
                       houses.add(house);
                   }
                   callBack.onSuccessListener(houses);
                });
    }

    public void updateHouse(House house, CallBack<Boolean> callBack) {
        Map<String, Object> houseData = ConvertHelper.convertObjectToMap(house);
        firebaseFirestore.collection(DatabaseConstraints.HOUSE_COLLECTION_NAME)
                .document(house.getHouseId())
                .update(houseData)
                .addOnCompleteListener(task -> callBack.onSuccessListener(task.isSuccessful()));
    }

    public void deleteHouse(String houseId, CallBack<Boolean> callBack) {
        firebaseFirestore.collection(DatabaseConstraints.HOUSE_COLLECTION_NAME)
                .document(houseId)
                .delete().addOnCompleteListener(task -> callBack.onSuccessListener(task.isSuccessful()));
    }
 }
