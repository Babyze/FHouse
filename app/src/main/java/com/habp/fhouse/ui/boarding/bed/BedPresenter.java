package com.habp.fhouse.ui.boarding.bed;

import com.google.firebase.firestore.FirebaseFirestore;
import com.habp.fhouse.data.datasource.BedFirestoreRepository;

public class BedPresenter implements BedContract.Presenter {
    private BedContract.View mView;

    public BedPresenter(BedContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void loadBed(String roomId) {
        BedFirestoreRepository bedFirestoreRepository =
                new BedFirestoreRepository(FirebaseFirestore.getInstance());
        bedFirestoreRepository.getBedList(roomId, listBedData -> {
            mView.showBedList(listBedData);
        });
    }
}
