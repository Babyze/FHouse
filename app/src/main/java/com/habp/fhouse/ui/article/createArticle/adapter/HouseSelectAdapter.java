package com.habp.fhouse.ui.article.createArticle.adapter;

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

public class HouseSelectAdapter extends BaseAdapter {

    List<House> listHouse;

    public List<House> getListHouse() {
        return listHouse;
    }

    public void setListHouse(List<House> listHouse) {
        this.listHouse = listHouse;
    }

    public HouseSelectAdapter() {
    }

    public HouseSelectAdapter(List<House> listHouse) {
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
            view = inflater.inflate(R.layout.card_create_article, viewGroup, false);
        }
        House itemHouse = listHouse.get(i);
        Glide.with(view).load(itemHouse.getPhotoPath()).into((ImageView) view.findViewById(R.id.imgHouse));
        TextView txtName = view.findViewById(R.id.txtName);
        TextView txtTypeSelect = view.findViewById(R.id.txtTypeSelect);
        txtName.setText(itemHouse.getHouseName());
        txtTypeSelect.setText("Type: House");
        return view;
    }
}
