package com.habp.fhouse.ui.boarding;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.habp.fhouse.data.datasource.FirebaseAuthRepository;
import com.habp.fhouse.data.datasource.HouseFirestoreRepository;

public class HousePresenter implements HouseContract.Presenter {
    private HouseContract.View mView;
    private FirebaseAuthRepository firebaseAuthRepository;

    public HousePresenter(HouseContract.View mView, FirebaseAuthRepository firebaseAuthRepository) {
        this.mView = mView;
        this.firebaseAuthRepository = firebaseAuthRepository;
    }

    @Override
    public void loadHouse() {
        if(firebaseAuthRepository.getUser() != null) {
            HouseFirestoreRepository houseFirestoreRepository =
                    new HouseFirestoreRepository(FirebaseFirestore.getInstance(), FirebaseAuth.getInstance());
            houseFirestoreRepository.getHouseList(listHouseData -> {
                mView.showHouseList(listHouseData);
            });
        }
    }

    @Override
    public void checkAuthorize(boolean isReturn) {
        FirebaseUser user = firebaseAuthRepository.getUser();
        if(user == null) {
            if(isReturn)
                mView.redirectToHomeFragment();
            else
                mView.startSignInActivity();
        }
    }
}
