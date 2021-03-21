package com.habp.fhouse.ui.article;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.habp.fhouse.R;
import com.habp.fhouse.data.model.Article;
import com.habp.fhouse.data.model.User;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;

public class WishListAdapter extends BaseAdapter {
    List<User> wishList;

    public WishListAdapter(List<User> wishList) {
        this.wishList = wishList;
    }

    public List<User> getWishList() {
        return wishList;
    }

    public void setWishList(List<User> wishList) {
        this.wishList = wishList;
    }

    @Override

    public int getCount() {
        return wishList.size();
    }

    @Override
    public Object getItem(int position) {
        return wishList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.article_management_wishlist_item,parent, false);
        }
        User user = wishList.get(position);
        Glide.with(convertView).load(user.getPhotoPath()).into((ImageView) convertView.findViewById(R.id.imgUser));
        TextView txtName = convertView.findViewById(R.id.txtUserName);
        TextView txtEmail = convertView.findViewById(R.id.txtUserEmail);
        txtName.setText(user.getFullName());
        txtEmail.setText(user.getEmail());
        return convertView;
    }
}
