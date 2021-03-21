package com.habp.fhouse.ui.article.createArticle;

import android.os.Bundle;

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
import com.habp.fhouse.data.model.Article;
import com.habp.fhouse.data.model.House;
import com.habp.fhouse.data.model.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class CreateRoomFragment extends Fragment {
    private Spinner spinnerHouse;
    private List<House> listHouse = new ArrayList<>();
    private Spinner spinnerRoom;
    private List<Room> listRoom = new ArrayList<>();
    private TextView createRoom;
    private EditText editArticleName;
    private EditText editArticlePrice;
    private EditText editArticleDescription;
    private Room room;
    private String articleName;
    private Float price;
    private String description;
    private String roomId;
    private String houseId;
    private String userId;
    private Article article;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_room, container, false);
        // khai bao cac bien
        spinnerHouse = (Spinner)view.findViewById(R.id.spinnerHouse_createRoom);
        spinnerRoom = view.findViewById(R.id.spinnerRoom_createRoom);
        createRoom = view.findViewById(R.id.tvCreateArticleRoom);
        editArticleName = view.findViewById(R.id.editArticleName_CreateRoom);
        editArticlePrice = view.findViewById(R.id.editPrice_CreateRoom);
        editArticleDescription = view.findViewById(R.id.editDescription_CreateRoom);

        // khai bao database
        HouseFirestoreRepository houseFirestoreRepository =
                new HouseFirestoreRepository(FirebaseFirestore.getInstance(), FirebaseAuth.getInstance());

        RoomFirestoreRepository roomFirestoreRepository =
                new RoomFirestoreRepository(FirebaseFirestore.getInstance());

        ArticleFirestoreRepository articleFirestoreRepository =
                new ArticleFirestoreRepository(FirebaseFirestore.getInstance(), FirebaseAuth.getInstance());

        // get list
        houseFirestoreRepository.getHouseList(list ->{
            listHouse = list;
            ArrayAdapter<House> adapterHome = new ArrayAdapter<House>(getActivity(),
                    android.R.layout.simple_spinner_dropdown_item, listHouse);
            spinnerHouse.setAdapter(adapterHome);
        });
        //select item
        spinnerHouse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                houseId = listHouse.get(position).getHouseId();
                userId = listHouse.get(position).getUserId();
                roomFirestoreRepository.getRoomList(listHouse.get(position).getHouseId(), roomList -> {
                    listRoom = roomList;
                    ArrayAdapter<Room> adapterRoom = new ArrayAdapter<Room>(getActivity(),
                            android.R.layout.simple_spinner_dropdown_item, roomList);
                    spinnerRoom.setAdapter(adapterRoom);
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 roomId = listRoom.get(position).getRoomId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        createRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editArticleName.equals("") && !editArticlePrice.equals("")&& !editArticleDescription.equals("")){
                    UUID articleId = UUID.randomUUID();
                    articleName = editArticleName.getText().toString();
                    price = Float.parseFloat(editArticlePrice.getText().toString());
                    description = editArticleDescription.getText().toString();

                    article = new Article(articleId.toString(), articleName, description, houseId, roomId, userId, price, 2);
                    articleFirestoreRepository.createNewArticle(article,task -> {
                        Toast.makeText(getContext(), "Create Article "+articleName+" successful", Toast.LENGTH_SHORT).show();
                    });

                }else
                    Toast.makeText(getContext(), "Create Article Failed", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}