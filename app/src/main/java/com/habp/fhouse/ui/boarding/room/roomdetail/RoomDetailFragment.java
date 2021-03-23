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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.habp.fhouse.R;
import com.habp.fhouse.data.datasource.FirebaseAuthRepository;
import com.habp.fhouse.data.datasource.RoomFirestoreRepository;
import com.habp.fhouse.data.model.Bed;
import com.habp.fhouse.data.model.House;
import com.habp.fhouse.data.model.Room;
import com.habp.fhouse.ui.boarding.bed.BedAdapter;
import com.habp.fhouse.ui.boarding.bed.BedContract;
import com.habp.fhouse.ui.boarding.bed.BedPresenter;
import com.habp.fhouse.ui.boarding.bed.create.CreateBedActivity;
import com.habp.fhouse.ui.boarding.bed.update.UpdateBedActivity;
import com.habp.fhouse.ui.boarding.house.housedetail.HouseDetailFragment;
import com.habp.fhouse.ui.boarding.room.update.UpdateRoomActivity;

import java.util.List;

public class RoomDetailFragment extends Fragment implements BedContract.View {
    private ListView lvBed;
    private BedAdapter adapter;
    private House currentHouse;
    private Room currentRoom;
    private BedPresenter bedPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_detail, container, false);
        currentHouse = (House) this.getArguments().getSerializable("currentHouse");
        currentRoom = (Room) this.getArguments().getSerializable("currentRoom");
        lvBed = view.findViewById(R.id.lvBed);
        bedPresenter = new BedPresenter(this);
        bedPresenter.loadBed(currentRoom.getRoomId());
        loadData(view);
        return view;
    }

    @Override
    public void showBedList(List<Bed> listBed) {
        TextView txtEmptyBed = this.getView().findViewById(R.id.txtEmptyBed);
        adapter = new BedAdapter(listBed);
        lvBed.setAdapter(adapter);
        if (listBed.size() > 0) {
            lvBed.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Bed currentBed = listBed.get(i);
                    if (currentBed != null) {
                        Intent intent = new Intent(getActivity(), UpdateBedActivity.class);
                        intent.putExtra("currentBed", currentBed);
                        intent.putExtra("currentRoom", currentRoom);
                        startActivity(intent);
                    }
                }
            });
            txtEmptyBed.setVisibility(View.INVISIBLE);
        } else {
            lvBed.setBackgroundColor(Color.WHITE);
            txtEmptyBed.setVisibility(View.VISIBLE);
            txtEmptyBed.setText("Empty");
        }
    }

    public void loadData(View view) {
        //set title
        TextView txtRoomName = view.findViewById(R.id.txtRoomName);
        txtRoomName.setText(currentRoom.getRoomName());
        //set image
        Glide.with(view).load(currentRoom.getPhotoPath()).into((ImageView) view.findViewById(R.id.imgRoomPhoto));
        //CreateBedActivity
        ImageButton btnCreate = view.findViewById(R.id.btnCreateBedActivity);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateBedActivity.class);
                intent.putExtra("currentRoom", currentRoom);
                startActivity(intent);
            }
        });
        //UpdateRoomActivity
        MaterialButton btnUpdateRoomActivity = view.findViewById(R.id.btnUpdateRoomActivity);
        btnUpdateRoomActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UpdateRoomActivity.class);
                intent.putExtra("currentRoom", currentRoom);
                startActivityForResult(intent, 1);
            }
        });
        //button back
        ImageButton btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("currentHouse", currentHouse);

                Fragment houseDetailFragment = new HouseDetailFragment();
                houseDetailFragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, houseDetailFragment);
                fragmentTransaction.commit();
            }
        });
        //btn Delete
        MaterialButton btnDeleteRoom = view.findViewById(R.id.btnDeleteRoom);
        btnDeleteRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deleteRoomId = currentRoom.getRoomId();
                //Delete
                RoomFirestoreRepository roomFirestoreRepository =
                        new RoomFirestoreRepository(FirebaseFirestore.getInstance());
                roomFirestoreRepository.deleteRoom(deleteRoomId, isSuccess -> {
                    if (isSuccess) {
                        Toast.makeText(getContext(), "Delete successul", Toast.LENGTH_SHORT).show();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("currentHouse", currentHouse);
                        Fragment listRoomFragment = new HouseDetailFragment();
                        listRoomFragment.setArguments(bundle);
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, listRoomFragment);
                        fragmentTransaction.commit();
                    } else {
                        Toast.makeText(getContext(), "Delete error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            currentRoom = (Room) data.getSerializableExtra("currentRoom");
            loadData(this.getView());
            Toast.makeText(getContext(), "Update " + currentRoom.getRoomName() + " successful", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        bedPresenter.loadBed(currentRoom.getRoomId());
    }
}