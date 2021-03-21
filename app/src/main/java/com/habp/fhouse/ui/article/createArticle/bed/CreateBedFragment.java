package com.habp.fhouse.ui.article.createArticle.bed;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.habp.fhouse.R;
import com.habp.fhouse.data.datasource.ArticleFirestoreRepository;
import com.habp.fhouse.data.datasource.BedFirestoreRepository;
import com.habp.fhouse.data.datasource.HouseFirestoreRepository;
import com.habp.fhouse.data.datasource.RoomFirestoreRepository;
import com.habp.fhouse.data.model.Bed;
import com.habp.fhouse.data.model.House;
import com.habp.fhouse.data.model.Room;

import java.util.ArrayList;
import java.util.List;


public class CreateBedFragment extends Fragment implements CreateBedContract.View {
    private CreateBedPresenter createBedPresenter;

    private NestedScrollView scrlBoarding;
    private ConstraintLayout createBedLayout;

    private Spinner spinnerHouse;
    private Spinner spinnerRoom;
    private Spinner spinnerBed;

    private EditText editArticleName;
    private EditText editArticlePrice;
    private EditText editArticleDescription;

    private TextView tvCreateArticle;
    private TextView txtGuide;
    private TextView txtEmptyCreate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_bed, container, false);

        initData(view);

        createBedPresenter.loadHouseData();

        configSpinnerData();

        configButton();

        return view;
    }

    private void initData(View view) {
        scrlBoarding = view.findViewById(R.id.scrlBoarding);
        createBedLayout = view.findViewById(R.id.createBedLayout);

        spinnerHouse = view.findViewById(R.id.spinnerHouse_createBed);
        spinnerRoom = view.findViewById(R.id.spinnerRoom_createBed);
        spinnerBed = view.findViewById(R.id.spinnerBed_createBed);

        editArticleName = view.findViewById(R.id.editArticleName_CreateBed);
        editArticlePrice = view.findViewById(R.id.editPrice_CreateBed);
        editArticleDescription = view.findViewById(R.id.editDescription_CreateBed);

        tvCreateArticle = view.findViewById(R.id.tvCreateArticle);
        txtGuide = view.findViewById(R.id.txtGuide);
        txtEmptyCreate = view.findViewById(R.id.txtEmptyCreate);

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        HouseFirestoreRepository houseFirestoreRepository =
                new HouseFirestoreRepository(firebaseFirestore, firebaseAuth);

        RoomFirestoreRepository roomFirestoreRepository =
                new RoomFirestoreRepository(firebaseFirestore);

        BedFirestoreRepository bedFirestoreRepository =
                new BedFirestoreRepository(firebaseFirestore);

        ArticleFirestoreRepository articleFirestoreRepository =
                new ArticleFirestoreRepository(firebaseFirestore, firebaseAuth);

        createBedPresenter = new CreateBedPresenter(this, houseFirestoreRepository,
                roomFirestoreRepository, bedFirestoreRepository, articleFirestoreRepository, firebaseAuth);
    }

    private void configSpinnerData() {
        spinnerHouse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                House house = (House) spinnerHouse.getSelectedItem();
                String houseId = house.getHouseId();
                createBedPresenter.loadRoomData(houseId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        spinnerRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Room room = (Room) spinnerRoom.getSelectedItem();
                String roomId = room.getRoomId();
                createBedPresenter.loadBedData(roomId);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void showHouseData(List<House> houses) {
        ArrayAdapter<House> adapterHome = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, houses);
        spinnerHouse.setAdapter(adapterHome);

        if(houses.size() == 0) {
            scrlBoarding.setVisibility(View.INVISIBLE);
            tvCreateArticle.setVisibility(View.INVISIBLE);
            txtGuide.setVisibility(View.INVISIBLE);
            txtEmptyCreate.setVisibility(View.VISIBLE);
            txtEmptyCreate.setText("You don't have any house to create article");
        } else {
            txtGuide.setVisibility(View.VISIBLE);
            txtGuide.setVisibility(View.INVISIBLE);
            createBedLayout.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showRoomData(List<Room> rooms) {
        ArrayAdapter<Room> adapterRoom = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, rooms);
        spinnerRoom.setAdapter(adapterRoom);
        if(rooms.size() == 0) {
            tvCreateArticle.setVisibility(View.INVISIBLE);
            txtGuide.setVisibility(View.INVISIBLE);
            createBedLayout.setVisibility(View.INVISIBLE);
            txtEmptyCreate.setVisibility(View.VISIBLE);
            txtEmptyCreate.setText("You don't have any room in this house");
            ArrayAdapter<Bed> adapterBed = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_spinner_dropdown_item, new ArrayList<>());
            spinnerBed.setAdapter(adapterBed);
        } else {
            txtEmptyCreate.setVisibility(View.INVISIBLE);
            tvCreateArticle.setVisibility(View.VISIBLE);
            txtGuide.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showBedData(List<Bed> beds) {
        ArrayAdapter<Bed> adapterBed = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, beds);
        spinnerBed.setAdapter(adapterBed);
        if(beds.size() == 0) {
            tvCreateArticle.setVisibility(View.INVISIBLE);
            txtGuide.setVisibility(View.INVISIBLE);
            txtEmptyCreate.setVisibility(View.VISIBLE);
            createBedLayout.setVisibility(View.INVISIBLE);
            txtEmptyCreate.setText("You don't have any bed in this room");
        } else {
            txtEmptyCreate.setVisibility(View.INVISIBLE);
            tvCreateArticle.setVisibility(View.VISIBLE);
            txtGuide.setVisibility(View.INVISIBLE);
            createBedLayout.setVisibility(View.VISIBLE);
        }
    }

    private void configButton() {
        tvCreateArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String articleName = editArticleName.getText().toString();
                String price = editArticlePrice.getText().toString();
                String description = editArticleDescription.getText().toString();

                House house = (House) spinnerHouse.getSelectedItem();
                Room room = (Room) spinnerRoom.getSelectedItem();
                Bed bed = (Bed) spinnerBed.getSelectedItem();

                createBedPresenter.createArticle(articleName, description, price, house, room, bed);
            }
        });
    }

    @Override
    public void onInvalidName(String msg) {
        editArticleName.setError(msg);
    }

    @Override
    public void onInvalidDescription(String msg) {
        editArticleDescription.setError(msg);
    }

    @Override
    public void onInvalidPrice(String msg) {
        editArticlePrice.setError(msg);
    }

    @Override
    public void onCreateSuccess() {
        Toast.makeText(getActivity(), "Create New Article Success", Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }

    @Override
    public void onCreateFail() {
        Toast.makeText(getActivity(), "Create New Article Fail", Toast.LENGTH_SHORT).show();
    }
}