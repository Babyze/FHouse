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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.habp.fhouse.R;
import com.habp.fhouse.data.datasource.ArticleFirestoreRepository;
import com.habp.fhouse.data.datasource.HouseFirestoreRepository;
import com.habp.fhouse.data.model.Article;
import com.habp.fhouse.data.model.House;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class CreateHouseFragment extends Fragment {
    private Spinner spinnerHouse;
    private List<House> listHouse = new ArrayList<>();
    private EditText editPrice;
    private EditText editDescription;
    private EditText editArticleName;
    private TextView tvCreateHouse;
    private House house;
    private Article article;
    private Float price;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_house, container, false);
        // khai bao cac nut va spinner
        spinnerHouse = (Spinner)view.findViewById(R.id.spinnerHouse_createHouse);
        tvCreateHouse = view.findViewById(R.id.tvCreateArticleHouse);

        editPrice = view.findViewById(R.id.editPrice_CreateHouse);
        editDescription = view.findViewById(R.id.editDescription_CreateHouse);
        editArticleName = view.findViewById(R.id.editArticleName_CreateHouse);
            String description = editDescription.getText().toString();
            String articleName = editArticleName.getText().toString();
            //khoi tao firebase
            HouseFirestoreRepository houseFirestoreRepository =
                    new HouseFirestoreRepository(FirebaseFirestore.getInstance(), FirebaseAuth.getInstance());
            ArticleFirestoreRepository articleFirestoreRepository =
                    new ArticleFirestoreRepository(FirebaseFirestore.getInstance(), FirebaseAuth.getInstance());
            //set View
            houseFirestoreRepository.getHouseList(list ->{
                listHouse = list;
                ArrayAdapter<House> adapterHome = new ArrayAdapter<House>(getActivity(),
                        android.R.layout.simple_spinner_dropdown_item, listHouse);
                spinnerHouse.setAdapter(adapterHome);
            });
            //event
            spinnerHouse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    house = listHouse.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            //create event
            tvCreateHouse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!editPrice.getText().toString().equals("") && !editDescription.getText().toString().equals("") &&
                            !editArticleName.getText().toString().equals("")){
                        UUID articleId = UUID.randomUUID();
                        article = new Article(articleId.toString(),articleName, description, house.getHouseId(), house.getUserId(),price,1);
                        articleFirestoreRepository.createNewArticle(article, task -> {
                            System.out.println("Done");
                        });
                    }else{
                        System.out.println("Nhu Loz");
                    }

                }
            });




        return view;
    }
}