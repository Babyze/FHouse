package com.habp.fhouse.ui.boarding.house.housedetail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.habp.fhouse.R;
import com.habp.fhouse.data.model.House;
import com.habp.fhouse.data.model.Room;
import com.habp.fhouse.ui.boarding.HouseAdapter;
import com.habp.fhouse.ui.boarding.HouseManagementFragment;
import com.habp.fhouse.ui.boarding.house.create.CreateHouseActivity;
import com.habp.fhouse.ui.boarding.room.RoomAdapter;
import com.habp.fhouse.ui.boarding.room.create.CreateRoomActivity;
import com.habp.fhouse.ui.boarding.room.roomdetail.RoomDetailFragment;

import java.util.ArrayList;
import java.util.List;

public class HouseDetailFragment extends Fragment {

    private ListView lvRoom;
    private RoomAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_house_detail, container, false);
        House testHouse = new House("Richkid_01", "House01", "Vinhomes Grand Park", "https://www.apartmentforrent.com.vn/upload/@files/vinhome-1479039656.jpg", "Quận 9, Thành phố Hồ Chí Minh");
        lvRoom = view.findViewById(R.id.lvRoom);
        List<Room> listRoom = new ArrayList<>();
        listRoom.add(new Room("R001", "Lovely Room",
                "https://media-cdn.tripadvisor.com/media/photo-s/13/d8/ea/1b/a-room-at-the-beach.jpg", testHouse));
        listRoom.add(new Room("R001", "Activity Room",
                "https://media-cdn.tripadvisor.com/media/photo-s/13/d8/ea/1b/a-room-at-the-beach.jpg", testHouse));

        listRoom.add(new Room("R001", "Storm Room",
                "https://media-cdn.tripadvisor.com/media/photo-s/13/d8/ea/1b/a-room-at-the-beach.jpg", testHouse));
        listRoom.add(new Room("R001", "Normal Room",
                "https://media-cdn.tripadvisor.com/media/photo-s/13/d8/ea/1b/a-room-at-the-beach.jpg", testHouse));
        listRoom.add(new Room("R001", "Storm Room",
                "https://media-cdn.tripadvisor.com/media/photo-s/13/d8/ea/1b/a-room-at-the-beach.jpg", testHouse));
        listRoom.add(new Room("R001", "Normal Room",
                "https://media-cdn.tripadvisor.com/media/photo-s/13/d8/ea/1b/a-room-at-the-beach.jpg", testHouse));
        if (listRoom.size() > 0) {
            adapter = new RoomAdapter(listRoom);
            lvRoom.setAdapter(adapter);
            lvRoom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Room roomCurrent = listRoom.get(i);
                    if (roomCurrent != null) {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", roomCurrent.getRoomId());
                        System.out.println(roomCurrent.getRoomId());
                        Fragment roomDetail = new RoomDetailFragment();
                        roomDetail.setArguments(bundle);
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, roomDetail);
                        fragmentTransaction.commit();
                    }
                }
            });
        } else {
            lvRoom.setBackgroundColor(Color.WHITE);
            TextView txtEmptyRoom = view.findViewById(R.id.txtEmptyRoom);
            txtEmptyRoom.setText("Empty");
        }


//        String id = this.getArguments().getString("id");    //Lấy string từ fragment trước
        TextView txtHouseName = view.findViewById(R.id.txtHouseName);
        txtHouseName.setText(testHouse.getHouseName());
        Glide.with(view).load(testHouse.getPhotoPath()).into((ImageView) view.findViewById(R.id.imgHousePhoto));

        ImageButton btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment listHouseFragment = new HouseManagementFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, listHouseFragment);
                fragmentTransaction.commit();
            }
        });

        ImageButton btnCreate = view.findViewById(R.id.btnCreateRoomActivity);
        btnCreate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateRoomActivity.class);
                startActivity(intent);


            }
        });
        return view;
    }

}