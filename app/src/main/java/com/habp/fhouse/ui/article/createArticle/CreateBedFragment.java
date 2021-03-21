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
import com.habp.fhouse.data.datasource.BedFirestoreRepository;
import com.habp.fhouse.data.datasource.HouseFirestoreRepository;
import com.habp.fhouse.data.datasource.RoomFirestoreRepository;
import com.habp.fhouse.data.model.Article;
import com.habp.fhouse.data.model.Bed;
import com.habp.fhouse.data.model.House;
import com.habp.fhouse.data.model.Room;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class CreateBedFragment extends Fragment {
    private Spinner spinnerHouse;
    private List<House> listHouse = new ArrayList<>();
    private Spinner spinnerRoom;
    private List<Room> listRoom = new ArrayList<>();
    private Spinner spinnerBed;
    private List<Bed> listBed = new ArrayList<>();
    private String houseId;
    private String userId;
    private String roomId;
    private String bedId;
    private TextView createBed;
    private EditText editArticleName;
    private EditText editArticlePrice;
    private EditText editArticleDescription;
    private String articleName;
    private Float price;
    private String description;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_bed, container, false);
        // khai bao
        spinnerHouse = view.findViewById(R.id.spinnerHouse_createBed);
        spinnerRoom = view.findViewById(R.id.spinnerRoom_createBed);
        spinnerBed = view.findViewById(R.id.spinnerBed_createBed);
        createBed = view.findViewById(R.id.tvCreateArticleBed);
        editArticleName = view.findViewById(R.id.editArticleName_CreateBed);
        editArticlePrice = view.findViewById(R.id.editPrice_CreateBed);
        editArticleDescription = view.findViewById(R.id.editDescription_CreateBed);

        HouseFirestoreRepository houseFirestoreRepository =
                new HouseFirestoreRepository(FirebaseFirestore.getInstance(), FirebaseAuth.getInstance());

        RoomFirestoreRepository roomFirestoreRepository =
                new RoomFirestoreRepository(FirebaseFirestore.getInstance());

        BedFirestoreRepository bedFirestoreRepository =
                new BedFirestoreRepository(FirebaseFirestore.getInstance());

        ArticleFirestoreRepository articleFirestoreRepository =
                new ArticleFirestoreRepository(FirebaseFirestore.getInstance(),FirebaseAuth.getInstance());

        houseFirestoreRepository.getHouseList(list ->{
            listHouse = list;
            ArrayAdapter<House> adapterHome = new ArrayAdapter<House>(getActivity(),
                    android.R.layout.simple_spinner_dropdown_item, list);
            spinnerHouse.setAdapter(adapterHome);

        });
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

        spinnerBed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bedId = listBed.get(position).getBedId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        createBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UUID articleId = UUID.randomUUID();
                if (!editArticleName.equals("") && !editArticlePrice.equals("")&& !editArticleDescription.equals("")){
                    articleName = editArticleName.getText().toString();
                    price = Float.parseFloat(editArticlePrice.getText().toString());
                    description = editArticleDescription.getText().toString();
                    Article article = new Article(articleId.toString(), articleName, description, houseId, roomId,bedId, userId, price, 3);
                    articleFirestoreRepository.createNewArticle(article,task -> {
                        Toast.makeText(getContext(), "Create Article "+articleName+" successful", Toast.LENGTH_SHORT).show();
                    });
                }else{
                    Toast.makeText(getContext(), "Create Article Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }
}