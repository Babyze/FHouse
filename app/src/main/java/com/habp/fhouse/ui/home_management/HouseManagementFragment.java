package com.habp.fhouse.ui.home_management;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.habp.fhouse.R;
import com.habp.fhouse.data.model.House;

import java.util.ArrayList;
import java.util.List;


public class HouseManagementFragment extends Fragment {
    private ListView lvHomeMamage;
    private HouseAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_house_management, container, false);
        lvHomeMamage = view.findViewById(R.id.lvHomeManage);



        List<House> listHouse = new ArrayList<>();
        listHouse.add(new House("Richkid_01", "Vinhomes Grand Park", R.mipmap.img_house_example, "Quận 9, Thành phố Hồ Chí Minh", "120"));
        listHouse.add(new House("Richkid_02", "Nhà 3 lầu st HXH Lê Trọng Tấn,", R.mipmap.img_house_example, "Đường Lê Trọng Tấn, Quận Tân Phú", "100"));
        listHouse.add(new House("Richkid_03", "Cho Thuê NC 11 Cửu Long", R.mipmap.img_house_example, "11, Đường Cửu Long, Quận 10", "220"));
        listHouse.add(new House("Richkid_04", "Cho Thuê nhà đường Hậu Giang", R.mipmap.img_house_example, "Đường Hậu Giang, Quận 6", "140"));
        listHouse.add(new House("Richkid_02", "Nhà 3 lầu st HXH Lê Trọng Tấn,", R.mipmap.img_house_example, "Đường Lê Trọng Tấn, Quận Tân Phú", "100"));
        listHouse.add(new House("Richkid_03", "Cho Thuê NC 11 Cửu Long", R.mipmap.img_house_example, "11, Đường Cửu Long, Quận 10", "220"));
        listHouse.add(new House("Richkid_04", "Cho Thuê nhà đường Hậu Giang", R.mipmap.img_house_example, "Đường Hậu Giang Quận 6", "140"));
        adapter = new HouseAdapter(listHouse);
        lvHomeMamage.setAdapter(adapter);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }
}
