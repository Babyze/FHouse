package com.habp.fhouse.ui.article;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.habp.fhouse.R;
import com.habp.fhouse.data.model.Article;
import com.habp.fhouse.data.model.House;
import com.habp.fhouse.data.model.User;
import com.habp.fhouse.ui.home.HomeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArticleManagementWishList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArticleManagementWishList extends Fragment {
    private ListView lvWishList;
    private WishListAdapter adapter;
    private List<User> listUser;

    public ArticleManagementWishList(List<User> listUser) {
        this.listUser = listUser;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_article_wishlist, container, false);
        lvWishList = view.findViewById(R.id.lvWishListArticle);
        adapter = new WishListAdapter(listUser);
        lvWishList.setAdapter(adapter);

        return view;

    }
}