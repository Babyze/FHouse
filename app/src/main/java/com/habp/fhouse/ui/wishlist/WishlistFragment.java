package com.habp.fhouse.ui.wishlist;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.habp.fhouse.R;
import com.habp.fhouse.data.datasource.WishListFirestoreRepository;
import com.habp.fhouse.data.model.Article;
import com.habp.fhouse.ui.home.HomeFragment;
import com.habp.fhouse.ui.sign.SignInActivity;
import com.habp.fhouse.util.DatabaseConstraints;

import java.util.ArrayList;
import java.util.List;

public class WishlistFragment extends Fragment implements WishlistContract.View {
    private WishlistPresenter wishlistPresenter;
    private ArticleAdapter articleAdapter;
    private ListView lvWishList;
    private List<Article> articles;
    private TextView txtNoResult;
    private SwipeRefreshLayout swipeArticle;
    private int totalResume = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);

        initData(view);

        configSwipeRefresh();

        wishlistPresenter.checkAuthorize(false);

        wishlistPresenter.loadWishListData();

        return view;
    }

    private void initData(View view) {
        lvWishList = view.findViewById(R.id.lvWishList);
        txtNoResult = view.findViewById(R.id.txtNoResult);
        swipeArticle = view.findViewById(R.id.swipeArticle);

        articles = new ArrayList<>();
        articleAdapter = new ArticleAdapter();

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        WishListFirestoreRepository wishListRep =
                new WishListFirestoreRepository(firebaseFirestore, firebaseAuth);

        wishlistPresenter =
                new WishlistPresenter(this, wishListRep, firebaseAuth);

        articleAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                checkDataExist();
            }
        });
    }

    private void configSwipeRefresh() {
        swipeArticle.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                wishlistPresenter.loadWishListData();
                swipeArticle.setRefreshing(false);
            }
        });
    }

    @Override
    public void startSignInActivity() {
        Intent intent = new Intent(getActivity(), SignInActivity.class);
        startActivity(intent);
    }

    @Override
    public void redirectToHomePage() {
        Fragment fragment = new HomeFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.nav_Home).setChecked(true);
    }

    @Override
    public void showWishListData(List<Article> articles) {
        this.articles = articles;
        articleAdapter.setArticles(articles);
        lvWishList.setAdapter(articleAdapter);
        checkDataExist();
    }

    private void checkDataExist() {
        if(articles.size() == 0) {
            lvWishList.setVisibility(View.INVISIBLE);
            txtNoResult.setVisibility(View.VISIBLE);
            txtNoResult.setText("You have not wish list any boarding yet");
        } else {
            lvWishList.setVisibility(View.VISIBLE);
            txtNoResult.setVisibility(View.INVISIBLE);
            txtNoResult.setText("");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        totalResume += 1;
        if(totalResume == DatabaseConstraints.TOTAL_RESUME_FOR_AUTHORIZATION) {
            wishlistPresenter.checkAuthorize(true);
        }
    }
}
