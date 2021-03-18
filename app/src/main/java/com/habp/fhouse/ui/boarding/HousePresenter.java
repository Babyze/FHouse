package com.habp.fhouse.ui.boarding;

import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.habp.fhouse.data.datasource.FirebaseStorageRemote;
import com.habp.fhouse.data.datasource.HouseFirestoreRepository;

public class HousePresenter implements HouseContract.Presenter {
    private HouseContract.View mView;

    public HousePresenter(HouseContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void loadHouse() {
        FirebaseAuth.getInstance().signInWithEmailAndPassword("hoangpdse130719@fpt.edu.vn","123456")
                .addOnCompleteListener(task -> {
                    if(task.isComplete()) {
                        HouseFirestoreRepository houseFirestoreRepository = new HouseFirestoreRepository(FirebaseFirestore.getInstance(), FirebaseAuth.getInstance());
                        houseFirestoreRepository.getHouseList(listHouseData -> {
                            System.out.println("Ahihi" + listHouseData.size());
                            mView.showHouseList(listHouseData);
                        });
                    }
                });
    }
}
