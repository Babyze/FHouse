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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.habp.fhouse.R;
import com.habp.fhouse.data.datasource.FirebaseStorageRemote;
import com.habp.fhouse.data.datasource.HouseFirestoreRepository;
import com.habp.fhouse.data.model.House;
import com.habp.fhouse.data.model.Room;
import com.habp.fhouse.ui.boarding.HouseAdapter;
import com.habp.fhouse.ui.boarding.HouseManagementFragment;
import com.habp.fhouse.ui.boarding.house.create.CreateHouseActivity;
import com.habp.fhouse.ui.boarding.house.update.UpdateHouseActivity;
import com.habp.fhouse.ui.boarding.room.RoomAdapter;
import com.habp.fhouse.ui.boarding.room.create.CreateRoomActivity;
import com.habp.fhouse.ui.boarding.room.roomdetail.RoomDetailFragment;

import java.util.ArrayList;
import java.util.List;

public class HouseDetailFragment extends Fragment {

    private ListView lvRoom;
    private RoomAdapter adapter;
    private House currentHouse;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_house_detail, container, false);
        lvRoom = view.findViewById(R.id.lvRoom);
        currentHouse = (House) this.getArguments().getSerializable("currentHouse");

        //set list room
        List<Room> listRoom = new ArrayList<>();
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
                        Fragment roomDetail = new RoomDetailFragment();
                        roomDetail.setArguments(bundle);
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, roomDetail);
                        fragmentTransaction.commit();
                    }
                }
            });
            lvRoom.setBackgroundColor(getResources().getColor(R.color.grey));
            TextView txtEmptyRoom = view.findViewById(R.id.txtEmptyRoom);
            txtEmptyRoom.setText("");
        } else {
            lvRoom.setBackgroundColor(Color.WHITE);
            TextView txtEmptyRoom = view.findViewById(R.id.txtEmptyRoom);
            txtEmptyRoom.setText("Empty");
        }
        loadData(view);
        return view;
    }
    public void loadData(View view){

        //String id = this.getArguments().getString("id");    //Lấy string từ fragment trước
        //set Title
        TextView txtHouseName = view.findViewById(R.id.txtHouseName);
        txtHouseName.setText(currentHouse.getHouseName());
        //set Image
        Glide.with(view).load(currentHouse.getPhotoPath()).into((ImageView) view.findViewById(R.id.imgHousePhoto));

        //button back
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

        //click to go to CreateRoomActivity
        ImageButton btnCreate = view.findViewById(R.id.btnCreateRoomActivity);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateRoomActivity.class);
                startActivity(intent);
            }
        });

        //click to go to UpdateHouseActivity
        MaterialButton btnEditHouse = view.findViewById(R.id.btnEditHouse);
        btnEditHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UpdateHouseActivity.class);
                intent.putExtra("currentHouse", currentHouse);
                startActivityForResult(intent, 1);
            }
        });

        //click to DeleteHouse
        MaterialButton btnDeleteHouse = view.findViewById(R.id.btnDeleteHouse);
        btnDeleteHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deleteHouseId = currentHouse.getHouseId();
                System.out.println(deleteHouseId + " YOYO");
                // xóa
                HouseFirestoreRepository houseFirestoreRepository =
                        new HouseFirestoreRepository(FirebaseFirestore.getInstance());

                houseFirestoreRepository.deleteHouse(deleteHouseId, isSuccess -> {
                    if (isSuccess) {
                        Toast.makeText(getContext(), "Delete successful ", Toast.LENGTH_SHORT).show();
                        Fragment listHouseFragment = new HouseManagementFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, listHouseFragment);
                        fragmentTransaction.commit();
                    } else {

                        Toast.makeText(getContext(), "Delete error ", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            currentHouse = (House)data.getSerializableExtra("currentHouse");
            loadData(this.getView());
            Toast.makeText(getContext(), "upddate"+ currentHouse.getHouseName(), Toast.LENGTH_SHORT).show();
        }
    }
}