package com.habp.fhouse.ui.article.createArticle;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.habp.fhouse.R;
import com.habp.fhouse.data.datasource.HouseFirestoreRepository;
import com.habp.fhouse.data.datasource.RoomFirestoreRepository;
import com.habp.fhouse.data.model.House;
import com.habp.fhouse.data.model.Room;

import java.util.ArrayList;
import java.util.List;


public class CreateRoomFragment extends Fragment {
    private Spinner spinnerHouse;
    private List<House> listHouse = new ArrayList<>();
    private Spinner spinnerRoom;
    private List<Room> listRoom = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_room, container, false);
        // Inflate the layout for this fragment
        spinnerHouse = (Spinner)view.findViewById(R.id.spinnerHouse_createRoom);
        spinnerRoom = view.findViewById(R.id.spinnerRoom_createRoom);

        HouseFirestoreRepository houseFirestoreRepository =
                new HouseFirestoreRepository(FirebaseFirestore.getInstance(), FirebaseAuth.getInstance());

        RoomFirestoreRepository roomFirestoreRepository =
                new RoomFirestoreRepository(FirebaseFirestore.getInstance());

        houseFirestoreRepository.getHouseList(list ->{
            listHouse = list;
            ArrayAdapter<House> adapterHome = new ArrayAdapter<House>(getActivity(),
                    android.R.layout.simple_spinner_dropdown_item, listHouse);
            spinnerHouse.setAdapter(adapterHome);

        });
        spinnerHouse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                roomFirestoreRepository.getRoomList(listHouse.get(position).getHouseId(), roomList -> {
                    listRoom = roomList;
                    //System.out.println(roomList.get(position).getRoomName() + " ahihihihi");
                    ArrayAdapter<Room> adapterRoom = new ArrayAdapter<Room>(getActivity(),
                            android.R.layout.simple_spinner_dropdown_item, roomList);

                    spinnerRoom.setAdapter(adapterRoom);
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }
}