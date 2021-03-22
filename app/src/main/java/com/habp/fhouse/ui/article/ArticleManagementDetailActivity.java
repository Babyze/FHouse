package com.habp.fhouse.ui.article;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.habp.fhouse.R;
import com.habp.fhouse.data.datasource.ArticleFirestoreRepository;
import com.habp.fhouse.data.datasource.WishListFirestoreRepository;
import com.habp.fhouse.data.model.Article;
import com.habp.fhouse.data.model.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ArticleManagementDetailActivity extends AppCompatActivity implements Serializable {
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<User> wishListUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_management_detail_activity);
        wishListUser = new ArrayList<>();
        Intent intent = getIntent();
        TextView txtArticleName = findViewById(R.id.txtNameArticle);
        TextView txtAddress = findViewById(R.id.txtAddress);
        ImageView imgHouse = findViewById(R.id.imgHouse);
        Article dto = (Article) intent.getSerializableExtra("articleDetail");
        checkType(dto.getArticleType());
        txtArticleName.setText(dto.getArticleName());
        txtAddress.setText(dto.getHouseAddress());
        Glide.with(getApplicationContext()).load(dto.getPhotoPath()).into(imgHouse);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getSupportFragmentManager());

        adapter.addFragment(new ArticleInformationFragment(dto), "Article Information");
        adapter.addFragment(new ArticleManagementWishList(wishListUser), "Wishlist(0)");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        //wishlist article management
        WishListFirestoreRepository wishListFirestoreRepository =
                new WishListFirestoreRepository(FirebaseFirestore.getInstance(), FirebaseAuth.getInstance());
        wishListFirestoreRepository.getUserListByArticleId(dto.getArticleId(), userList -> {
            wishListUser = userList;
            checkWishListUserExist(userList);
        });
    }

    private void checkWishListUserExist(List<User> userList) {
        if (wishListUser.size() == 0) {
            adapter.changePageTitle(1, "Wishlist(0)");
        } else {
            adapter.changePageTitle(1, "Wishlist(" + wishListUser.size() + ")");
            ArticleManagementWishList articleMWL = (ArticleManagementWishList) adapter.getItem(1);
            articleMWL.setListUser(wishListUser);
            articleMWL.showUserWishList();
        }
        wishListUser = userList;
        adapter.notifyDataSetChanged();
    }

    public void clickToBackActivity(View view) {
        finish();
    }

    public void clickToDeleteArticleManagement(View view) {
        Intent intent = getIntent();
        Article dto = (Article) intent.getSerializableExtra("articleDetail");
        String articleName = dto.getArticleName();
        ArticleFirestoreRepository articleFirestoreRepository =
                new ArticleFirestoreRepository(FirebaseFirestore.getInstance(),FirebaseAuth.getInstance());
        articleFirestoreRepository.deleteArticle(dto.getArticleId(), task -> {
            Toast.makeText(this, "Delete article "+articleName+" successful", Toast.LENGTH_SHORT).show();
        });
        finish();

    }
    public void checkType(int type){
        TextView txtRequirement = findViewById(R.id.txtRequirementArticle);
        if (type == 1){
            txtRequirement.setText("House For Rent");
        }else if (type == 2){
            txtRequirement.setText("Room For Rent");
        }else if (type == 3){
            txtRequirement.setText("Bed For Rent");
        }
    }
}