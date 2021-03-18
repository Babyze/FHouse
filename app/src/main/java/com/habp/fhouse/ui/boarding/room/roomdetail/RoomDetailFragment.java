package com.habp.fhouse.ui.boarding.room.roomdetail;

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
import com.google.android.material.button.MaterialButton;
import com.habp.fhouse.R;
import com.habp.fhouse.data.model.Bed;
import com.habp.fhouse.data.model.House;
import com.habp.fhouse.data.model.Room;
import com.habp.fhouse.ui.boarding.HouseManagementFragment;
import com.habp.fhouse.ui.boarding.bed.BedAdapter;
import com.habp.fhouse.ui.boarding.bed.create.CreateBedActivity;
import com.habp.fhouse.ui.boarding.bed.update.UpdateBedActivity;
import com.habp.fhouse.ui.boarding.house.housedetail.HouseDetailFragment;
import com.habp.fhouse.ui.boarding.room.RoomAdapter;
import com.habp.fhouse.ui.boarding.room.create.CreateRoomActivity;
import com.habp.fhouse.ui.boarding.room.update.UpdateRoomActivity;

import java.util.ArrayList;
import java.util.List;


public class RoomDetailFragment extends Fragment {
    private ListView lvBed;
    private BedAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_detail, container, false);
        lvBed = view.findViewById(R.id.lvBed);

        //data test
        Room testRoom = new Room("R001", "Lovely Room",
                "https://media-cdn.tripadvisor.com/media/photo-s/13/d8/ea/1b/a-room-at-the-beach.jpg", "H01");

        List<Bed> listBed = new ArrayList<>();
        listBed.add(new Bed("Bed01", "Lovely Bed",
                "https://img.castlery.com.au/products/images/181846/large/Joseph-Bed-k-front.jpg?1592385268","R01"));
        listBed.add(new Bed("Bed01", "Happy Bed",
                "https://img.castlery.com.au/products/images/181846/large/Joseph-Bed-k-front.jpg?1592385268", "R01"));
        listBed.add(new Bed("Bed01", "Bla Bla Bed",
                "https://img.castlery.com.au/products/images/181846/large/Joseph-Bed-k-front.jpg?1592385268", "R01"));
        listBed.add(new Bed("Bed01", "Blo blo Bed",
                "https://img.castlery.com.au/products/images/181846/large/Joseph-Bed-k-front.jpg?1592385268", "R01"));
        listBed.add(new Bed("Bed01", "Ahihi Bed",
                "https://img.castlery.com.au/products/images/181846/large/Joseph-Bed-k-front.jpg?1592385268", "R01"));
        listBed.add(new Bed("Bed01", "Ahaha Bed",
                "https://img.castlery.com.au/products/images/181846/large/Joseph-Bed-k-front.jpg?1592385268", "R01"));

        //////////////////////////////

        if (listBed.size() > 0) {
            adapter = new BedAdapter(listBed);
            lvBed.setAdapter(adapter);
            lvBed.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Bed currentBed = listBed.get(i);
                    if (currentBed != null) {
                        Intent intent = new Intent(getActivity(), UpdateBedActivity.class);
                        intent.putExtra("currentBed", currentBed);
                        startActivity(intent);
                    }

                }
            });
        } else {
            lvBed.setBackgroundColor(Color.WHITE);
            TextView txtEmptyBed = view.findViewById(R.id.txtEmptyBed);
            txtEmptyBed.setText("Empty");
        }

        String id = this.getArguments().getString("id");    //Lấy string từ fragment trước
        TextView txtRoomName = view.findViewById(R.id.txtRoomName);
        txtRoomName.setText(testRoom.getRoomName());
        Glide.with(view).load(testRoom.getPhotoPath()).into((ImageView) view.findViewById(R.id.imgRoomPhoto));

        //button back
        ImageButton btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment houseDetailFragment = new HouseDetailFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, houseDetailFragment);
                fragmentTransaction.commit();
            }
        });

        //click to go to CreateRoomActivity
        ImageButton btnCreate = view.findViewById(R.id.btnCreateBedActivity);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateBedActivity.class);
                startActivity(intent);
            }
        });
        //click to go to UpdateRoomActivity
        MaterialButton btnUpdateRoomActivity = view.findViewById(R.id.btnUpdateRoomActivity);
        btnUpdateRoomActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (testRoom != null) {
                    Intent intent = new Intent(getActivity(), UpdateRoomActivity.class);
                    intent.putExtra("currentRoom", testRoom);
                    startActivity(intent);
                }
            }
        });

        return view;

    }
}