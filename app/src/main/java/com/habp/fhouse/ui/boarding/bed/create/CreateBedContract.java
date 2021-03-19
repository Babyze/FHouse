package com.habp.fhouse.ui.boarding.bed.create;

import com.habp.fhouse.data.model.Bed;

public class CreateBedContract {
    interface View{
        void onCreateSuccess(String message);
        void onCreateFailed(String message);
        void onBedNameError(String message);
    }
    interface Presenter{
        void createBed(Bed bed, byte[] imageByte);
    }
}
