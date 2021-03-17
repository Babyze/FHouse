package com.habp.fhouse.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.habp.fhouse.R;
import com.habp.fhouse.data.model.Article;
import com.habp.fhouse.data.model.House;
import com.habp.fhouse.ui.house_management.HouseAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private ListView lvHomePage;
    private ArticleAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        lvHomePage = view.findViewById(R.id.lvHomePage);
        List<Article> listArticleInHomePage = new ArrayList<>();
        listArticleInHomePage.add(new Article("1","Lovely House in 2 District","Ho Chi Minh", 5000000, new House("Richkid_01", "House01", "Vinhomes Grand Park", "https://www.apartmentforrent.com.vn/upload/@files/vinhome-1479039656.jpg", "Quận 2, Thành phố Hồ Chí Minh" )));
        listArticleInHomePage.add(new Article("2","Lovely House in 1 District","Ho Chi Minh", 5000000, new House("Richkid_01", "House01", "Vinhomes Grand Park", "https://www.apartmentforrent.com.vn/upload/@files/vinhome-1479039656.jpg", "Quận 1, Thành phố Hồ Chí Minh" )));
        listArticleInHomePage.add(new Article("3","Lovely House in 1 District","Ho Chi Minh", 5000000, new House("Richkid_01", "House01", "Vinhomes Grand Park", "https://www.apartmentforrent.com.vn/upload/@files/vinhome-1479039656.jpg", "Quận 1, Thành phố Hồ Chí Minh" )));
        listArticleInHomePage.add(new Article("4","Lovely House in 5 District","Ho Chi Minh", 5000000, new House("Richkid_01", "House01", "Vinhomes Grand Park", "https://www.apartmentforrent.com.vn/upload/@files/vinhome-1479039656.jpg", "Quận 5, Thành phố Hồ Chí Minh" )));
        listArticleInHomePage.add(new Article("5","Lovely House in Thu Duc","Ho Chi Minh", 5000000, new House("Richkid_01", "House01", "Vinhomes Grand Park", "https://www.apartmentforrent.com.vn/upload/@files/vinhome-1479039656.jpg", "Quận Thủ Đức, Thành phố Hồ Chí Minh" )));
        listArticleInHomePage.add(new Article("6","Lovely House in Thu Duc","Ho Chi Minh", 5000000, new House("Richkid_01", "House01", "Vinhomes Grand Park", "https://www.apartmentforrent.com.vn/upload/@files/vinhome-1479039656.jpg", "Quận Thủ Đức, Thành phố Hồ Chí Minh" )));
        listArticleInHomePage.add(new Article("7","Lovely House in Binh Thanh","Ho Chi Minh", 5000000, new House("Richkid_01", "House01", "Vinhomes Grand Park", "https://www.apartmentforrent.com.vn/upload/@files/vinhome-1479039656.jpg", "Quận Bình Thạnh, Thành phố Hồ Chí Minh" )));
        listArticleInHomePage.add(new Article("8","Lovely House in Binh Thanh","Ho Chi Minh", 5000000, new House("Richkid_01", "House01", "Vinhomes Grand Park", "https://www.apartmentforrent.com.vn/upload/@files/vinhome-1479039656.jpg", "Quận Bình Thạnh, Thành phố Hồ Chí Minh" )));
        listArticleInHomePage.add(new Article("9","Lovely House in Binh Thanh","Ho Chi Minh", 5000000, new House("Richkid_01", "House01", "Vinhomes Grand Park", "https://www.apartmentforrent.com.vn/upload/@files/vinhome-1479039656.jpg", "Quận Bình Thạnh, Thành phố Hồ Chí Minh" )));
        listArticleInHomePage.add(new Article("10","Lovely House in 2 District","Ho Chi Minh", 5000000, new House("Richkid_01", "House01", "Vinhomes Grand Park", "https://www.apartmentforrent.com.vn/upload/@files/vinhome-1479039656.jpg", "Quận 2, Thành phố Hồ Chí Minh" )));

        adapter = new ArticleAdapter(listArticleInHomePage);
        lvHomePage.setAdapter(adapter);

        return view;

    }

}
