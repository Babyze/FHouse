package com.habp.fhouse.ui.house_management;

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
        listHouse.add(new House("Richkid_01", "House01", "Vinhomes Grand Park", "https://www.apartmentforrent.com.vn/upload/@files/vinhome-1479039656.jpg", "Quận 9, Thành phố Hồ Chí Minh" ));
        listHouse.add(new House("Richkid_01", "House01", "Vinhomes Grand Park", "https://www.apartmentforrent.com.vn/upload/@files/vinhome-1479039656.jpg", "Quận 9, Thành phố Hồ Chí Minh" ));
        listHouse.add(new House("Richkid_01", "House01", "Vinhomes Grand Park", "https://www.apartmentforrent.com.vn/upload/@files/vinhome-1479039656.jpg", "Quận 9, Thành phố Hồ Chí Minh" ));
        listHouse.add(new House("Richkid_01", "House01", "Vinhomes Grand Park", "https://www.apartmentforrent.com.vn/upload/@files/vinhome-1479039656.jpg", "Quận 9, Thành phố Hồ Chí Minh" ));
        listHouse.add(new House("Richkid_01", "House01", "Vinhomes Grand Park", "https://www.apartmentforrent.com.vn/upload/@files/vinhome-1479039656.jpg", "Quận 9, Thành phố Hồ Chí Minh" ));
        listHouse.add(new House("Richkid_01", "House01", "Vinhomes Grand Park", "https://www.apartmentforrent.com.vn/upload/@files/vinhome-1479039656.jpg", "Quận 9, Thành phố Hồ Chí Minh" ));
        listHouse.add(new House("Richkid_01", "House01", "Vinhomes Grand Park", "https://www.apartmentforrent.com.vn/upload/@files/vinhome-1479039656.jpg", "Quận 9, Thành phố Hồ Chí Minh" ));
        listHouse.add(new House("Richkid_01", "House01", "Vinhomes Grand Park", "https://www.apartmentforrent.com.vn/upload/@files/vinhome-1479039656.jpg", "Quận 9, Thành phố Hồ Chí Minh" ));
        listHouse.add(new House("Richkid_01", "House01", "Vinhomes Grand Park", "https://www.apartmentforrent.com.vn/upload/@files/vinhome-1479039656.jpg", "Quận 9, Thành phố Hồ Chí Minh" ));
        listHouse.add(new House("Richkid_01", "House01", "Vinhomes Grand Park", "https://www.apartmentforrent.com.vn/upload/@files/vinhome-1479039656.jpg", "Quận 9, Thành phố Hồ Chí Minh" ));
        adapter = new HouseAdapter(listHouse);
        lvHomeMamage.setAdapter(adapter);

        return view;
    }


}
