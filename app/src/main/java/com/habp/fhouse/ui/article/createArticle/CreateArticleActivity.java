package com.habp.fhouse.ui.article.createArticle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import com.google.android.material.tabs.TabLayout;
import com.habp.fhouse.R;
import com.habp.fhouse.data.model.Bed;
import com.habp.fhouse.data.model.House;
import com.habp.fhouse.data.model.Room;
import com.habp.fhouse.ui.article.TabAdapter;
import com.habp.fhouse.ui.article.createArticle.bed.CreateBedFragment;
import com.habp.fhouse.ui.article.createArticle.house.CreateHouseFragment;
import com.habp.fhouse.ui.article.createArticle.room.CreateRoomFragment;

import java.util.ArrayList;
import java.util.List;

public class CreateArticleActivity extends AppCompatActivity {
    private Spinner spinnerHouse;
    private Spinner spinnerRoom;
    private Spinner spinnerBed;
    private List<House> listHouse = new ArrayList<>();
    private List<Room> listRoom = new ArrayList<>();
    private List<Bed> listBed = new ArrayList<>();
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_article);
        viewPager = (ViewPager) findViewById(R.id.viewPagerCreateArticle);
        tabLayout = (TabLayout) findViewById(R.id.tabLayoutCreateArticle);
        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new CreateHouseFragment(), "Create House");
        adapter.addFragment(new CreateRoomFragment(), "Create Room");
        adapter.addFragment(new CreateBedFragment(), "Create Bed");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void clickToBackActivity(View view) {
        finish();
    }
}