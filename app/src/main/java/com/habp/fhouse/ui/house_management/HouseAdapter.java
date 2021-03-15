package com.habp.fhouse.ui.house_management;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.habp.fhouse.R;
import com.habp.fhouse.data.model.House;

import java.util.List;


public class HouseAdapter extends BaseAdapter {
    List<House> listHouse;

    public List<House> getListHouse() {
        return listHouse;
    }

    public void setListHouse(List<House> listHouse) {
        this.listHouse = listHouse;
    }

    public HouseAdapter() {
    }

    public HouseAdapter(List<House> listHouse) {
        this.listHouse = listHouse;
    }

    @Override
    public int getCount() {
        return listHouse.size();
    }

    @Override
    public Object getItem(int i) {
        return listHouse.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.house_manage_item, viewGroup, false);
        }
        House itemHouse = listHouse.get(i);
        Glide.with(view).load(itemHouse.getPhotoPath()).into((ImageView) view.findViewById(R.id.imgHouse));
        TextView txtName = view.findViewById(R.id.txtName);
        TextView txtAddress = view.findViewById(R.id.txtAddress);
        txtName.setText(itemHouse.getHouseName());
        txtAddress.setText(itemHouse.getAddress());

        return view;
    }
}
