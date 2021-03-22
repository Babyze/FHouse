package com.habp.fhouse.ui.boarding.bed;

import com.habp.fhouse.data.model.Bed;

import java.util.List;

public interface BedContract {
    interface View {
        void showBedList(List<Bed> listBed);
    }

    interface Presenter {
        void loadBed(String roomId);
    }
}
