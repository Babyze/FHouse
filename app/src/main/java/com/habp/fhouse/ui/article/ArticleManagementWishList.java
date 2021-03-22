package com.habp.fhouse.ui.article;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.habp.fhouse.R;
import com.habp.fhouse.data.model.User;
import java.util.List;


public class ArticleManagementWishList extends Fragment {
    private ListView lvWishList;
    private WishListAdapter adapter;
    private List<User> listUser;
    private TextView empty;

    public ArticleManagementWishList() {

    }

    public  ArticleManagementWishList(List<User> listUser) {
        this.listUser = listUser;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_article_wishlist, container, false);
        lvWishList = view.findViewById(R.id.lvWishListArticle);
        empty = view.findViewById(R.id.txtEmptyWishlist);
        if(listUser != null){
            adapter = new WishListAdapter(listUser);
            lvWishList.setAdapter(adapter);
            empty.setVisibility(View.INVISIBLE);
        }else{
            lvWishList.setVisibility(View.INVISIBLE);
        }


        return view;

    }
}