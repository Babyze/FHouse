package com.habp.fhouse.ui.article.createArticle.room;

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
import com.habp.fhouse.data.datasource.HouseFirestoreRepository;
import com.habp.fhouse.data.datasource.RoomFirestoreRepository;
import com.habp.fhouse.data.model.House;
import com.habp.fhouse.data.model.Room;
import java.util.List;


public class CreateRoomFragment extends Fragment implements CreateRoomContract.View {
    private Spinner spinnerHouse;
    private Spinner spinnerRoom;
    private TextView tvCreateArticle;
    private EditText editArticleName;
    private EditText editArticlePrice;
    private EditText editArticleDescription;
    private NestedScrollView scrlBoarding;
    private TextView txtEmptyCreate;


    private ConstraintLayout createRoomLayout;

    private CreateRoomPresenter createRoomPresenter;

    private TextView txtGuide;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_room, container, false);

        initData(view);

        createRoomPresenter.loadHouseData();

        configSpinnerData();

        configButton();

        return view;
    }

    private void initData(View view) {
        spinnerHouse = view.findViewById(R.id.spinnerHouse_createRoom);
        spinnerRoom = view.findViewById(R.id.spinnerRoom_createRoom);
        scrlBoarding = view.findViewById(R.id.scrlBoarding);
        createRoomLayout = view.findViewById(R.id.createRoomLayout);

        editArticleName = view.findViewById(R.id.editArticleName_CreateRoom);
        editArticlePrice = view.findViewById(R.id.editPrice_CreateRoom);
        editArticleDescription = view.findViewById(R.id.editDescription_CreateRoom);

        tvCreateArticle = view.findViewById(R.id.tvCreateArticle);
        txtEmptyCreate = view.findViewById(R.id.txtEmptyCreate);
        txtGuide = view.findViewById(R.id.txtGuide);

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        HouseFirestoreRepository houseFirestoreRepository =
                new HouseFirestoreRepository(firebaseFirestore, firebaseAuth);

        RoomFirestoreRepository roomFirestoreRepository =
                new RoomFirestoreRepository(firebaseFirestore);

        ArticleFirestoreRepository articleFirestoreRepository =
                new ArticleFirestoreRepository(firebaseFirestore, firebaseAuth);

        createRoomPresenter = new CreateRoomPresenter(houseFirestoreRepository,
                roomFirestoreRepository, articleFirestoreRepository, this, firebaseAuth);
    }

    private void configSpinnerData() {
        spinnerHouse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                House house = (House) spinnerHouse.getSelectedItem();
                String houseId = house.getHouseId();
                createRoomPresenter.loadRoomData(houseId);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

                createRoomPresenter.createArticle(articleName, description, price, house, room);
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
            createRoomLayout.setVisibility(View.INVISIBLE);
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
            createRoomLayout.setVisibility(View.INVISIBLE);
            txtEmptyCreate.setVisibility(View.VISIBLE);
            txtEmptyCreate.setText("You don't have any room in this house");
        } else {
            txtEmptyCreate.setVisibility(View.INVISIBLE);
            tvCreateArticle.setVisibility(View.VISIBLE);
            txtGuide.setVisibility(View.INVISIBLE);
            createRoomLayout.setVisibility(View.VISIBLE);
        }
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