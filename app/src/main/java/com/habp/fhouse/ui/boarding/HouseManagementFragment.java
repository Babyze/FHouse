package com.habp.fhouse.ui.boarding;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.habp.fhouse.MainActivity;
import com.habp.fhouse.R;
import com.habp.fhouse.data.model.House;
import com.habp.fhouse.ui.boarding.house.create.CreateHouseActivity;
import com.habp.fhouse.ui.boarding.house.housedetail.HouseDetailFragment;

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
        listHouse.add(new House("Richkid_01", "House01", "Vinhomes Grand Park", "https://www.apartmentforrent.com.vn/upload/@files/vinhome-1479039656.jpg", "Quận 9, Thành phố Hồ Chí Minh"));
        listHouse.add(new House("Richkid_01", "House01", "Vinhomes Grand Park", "https://www.apartmentforrent.com.vn/upload/@files/vinhome-1479039656.jpg", "Quận 9, Thành phố Hồ Chí Minh"));
        listHouse.add(new House("Richkid_01", "House01", "Vinhomes Grand Park", "https://www.apartmentforrent.com.vn/upload/@files/vinhome-1479039656.jpg", "Quận 9, Thành phố Hồ Chí Minh"));
        listHouse.add(new House("Richkid_01", "House01", "Vinhomes Grand Park", "https://www.apartmentforrent.com.vn/upload/@files/vinhome-1479039656.jpg", "Quận 9, Thành phố Hồ Chí Minh"));
        listHouse.add(new House("Richkid_01", "House01", "Vinhomes Grand Park", "https://www.apartmentforrent.com.vn/upload/@files/vinhome-1479039656.jpg", "Quận 9, Thành phố Hồ Chí Minh"));
        listHouse.add(new House("Richkid_01", "House01", "Vinhomes Grand Park", "https://www.apartmentforrent.com.vn/upload/@files/vinhome-1479039656.jpg", "Quận 9, Thành phố Hồ Chí Minh"));
        listHouse.add(new House("Richkid_01", "House01", "Vinhomes Grand Park", "https://www.apartmentforrent.com.vn/upload/@files/vinhome-1479039656.jpg", "Quận 9, Thành phố Hồ Chí Minh"));
        listHouse.add(new House("Richkid_01", "House01", "Vinhomes Grand Park", "https://www.apartmentforrent.com.vn/upload/@files/vinhome-1479039656.jpg", "Quận 9, Thành phố Hồ Chí Minh"));
        listHouse.add(new House("Richkid_01", "House01", "Vinhomes Grand Park", "https://www.apartmentforrent.com.vn/upload/@files/vinhome-1479039656.jpg", "Quận 9, Thành phố Hồ Chí Minh"));
        listHouse.add(new House("Richkid_01", "House01", "Vinhomes Grand Park", "https://www.apartmentforrent.com.vn/upload/@files/vinhome-1479039656.jpg", "Quận 9, Thành phố Hồ Chí Minh"));
        adapter = new HouseAdapter(listHouse);

        lvHomeMamage.setAdapter(adapter);
        lvHomeMamage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                House houseCurrent = listHouse.get(i);
                if (houseCurrent != null){
                    Bundle bundle = new Bundle();
                    bundle.putString("id", houseCurrent.getHouseID());
                    Fragment houseDetail = new HouseDetailFragment();
                    houseDetail.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, houseDetail);
                    fragmentTransaction.commit();
                }
            }
        });


        ImageButton btnCreate = view.findViewById(R.id.btnCreateHouseActivity);
        btnCreate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateHouseActivity.class);
                startActivity(intent);


            }
        });
        return view;
    }


}
