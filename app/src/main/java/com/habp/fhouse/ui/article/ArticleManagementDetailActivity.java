package com.habp.fhouse.ui.article;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.habp.fhouse.R;
import com.habp.fhouse.data.model.Article;
import com.habp.fhouse.data.model.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ArticleManagementDetailActivity extends AppCompatActivity implements Serializable {
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_management_detail_activity);
        Intent intent = getIntent();
        TextView txtArticleName = findViewById(R.id.txtNameArticle);
        TextView txtRequirement = findViewById(R.id.txtRequirementArticle);
        TextView txtAddress = findViewById(R.id.txtAddress);
        Article dto = (Article) intent.getSerializableExtra("articleDetail");
        txtArticleName.setText(dto.getArticleName());
        txtRequirement.setText(dto.getArticleDescription());
        txtAddress.setText(dto.getHouseAddress());
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new ArticleInformationFragment(dto), "Article Information");

        List<User> listUser = new ArrayList<>();
        listUser.add(new User("1","Nguyễn Xuân Bách","bachnx.2013@gmail.com","HCM","https://i.pinimg.com/originals/de/dd/c4/deddc46f2eb4049d2e13c13cb4e1a72d.png","0839769168"));
        listUser.add(new User("2","Nguyễn Xuân Bách","bachnx.2013@gmail.com","HCM","https://i.pinimg.com/originals/de/dd/c4/deddc46f2eb4049d2e13c13cb4e1a72d.png","0839769168"));
        listUser.add(new User("3","Nguyễn Xuân Bách","bachnx.2013@gmail.com","HCM","https://i.pinimg.com/originals/de/dd/c4/deddc46f2eb4049d2e13c13cb4e1a72d.png","0839769168"));
        listUser.add(new User("4","Nguyễn Xuân Bách","bachnx.2013@gmail.com","HCM","https://i.pinimg.com/originals/de/dd/c4/deddc46f2eb4049d2e13c13cb4e1a72d.png","0839769168"));

        adapter.addFragment(new ArticleManagementWishList(listUser), "Wishlist(" +listUser.size() + ")");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void clickToBackActivity(View view) {
        finish();
    }

    public void clickToDeleteArticleManagement(View view) {
        Intent intent = getIntent();
        Article dto = (Article) intent.getSerializableExtra("articleDetail");
        System.out.println(dto.getArticleId() + " Ahihi " + dto.getArticleName());

    }
}