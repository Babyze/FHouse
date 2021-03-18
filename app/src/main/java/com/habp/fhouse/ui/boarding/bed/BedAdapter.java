package com.habp.fhouse.ui.boarding.bed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.habp.fhouse.R;
import com.habp.fhouse.data.model.Bed;
import com.habp.fhouse.data.model.Room;

import java.util.List;

public class BedAdapter extends BaseAdapter {
    List<Bed> listBed;

    public List<Bed> getListBed() {
        return listBed;
    }

    public void setListBed(List<Bed> listBed) {
        this.listBed = listBed;
    }

    public BedAdapter() {
    }

    public BedAdapter(List<Bed> listBed) {
        this.listBed = listBed;
    }

    @Override
    public int getCount() {
        return listBed.size();
    }

    @Override
    public Object getItem(int i) {
        return listBed.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.bed_item, viewGroup, false);
        }
        Bed itemBed = listBed.get(i);
        Glide.with(view).load(itemBed.getPhotoPath()).into((ImageView) view.findViewById(R.id.imgBed));
        TextView txtBedName = view.findViewById(R.id.txtBedName);
        txtBedName.setText(itemBed.getBedName());
        return view;

    }
}
