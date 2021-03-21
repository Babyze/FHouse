package com.habp.fhouse.ui.article.createArticle.house;

import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.habp.fhouse.data.model.House;

import java.util.ArrayList;
import java.util.List;


public class CreateHouseFragment extends Fragment implements CreateHouseContract.View {
    private List<House> listHouse = new ArrayList<>();
    private Spinner spinnerHouse;
    private EditText editPrice;
    private EditText editDescription;
    private EditText editArticleName;
    private TextView tvCreateHouse;
    private TextView txtEmptyHouseCreate;
    private NestedScrollView scrlBoarding;

    private CreateHousePresenter createHousePresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_house, container, false);

        initData(view);

        createHousePresenter.loadHouseData();

        configButton();

        return view;
    }

    private void initData(View view) {
        spinnerHouse = view.findViewById(R.id.spinnerHouse_createHouse);
        tvCreateHouse = view.findViewById(R.id.tvCreateArticle);
        editPrice = view.findViewById(R.id.editPrice_CreateHouse);
        editDescription = view.findViewById(R.id.editDescription_CreateHouse);
        editArticleName = view.findViewById(R.id.editArticleName_CreateHouse);
        txtEmptyHouseCreate = view.findViewById(R.id.txtEmptyHouseCreate);
        scrlBoarding = view.findViewById(R.id.scrlBoarding);

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        HouseFirestoreRepository houseFirestoreRepository =
                new HouseFirestoreRepository(firebaseFirestore, firebaseAuth);
        ArticleFirestoreRepository articleFirestoreRepository =
                new ArticleFirestoreRepository(firebaseFirestore, firebaseAuth);

        createHousePresenter = new CreateHousePresenter(this,
                houseFirestoreRepository, articleFirestoreRepository, firebaseAuth);
    }

    private void configButton() {
        tvCreateHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String articleName = editArticleName.getText().toString();
                String price = editPrice.getText().toString();
                String description = editDescription.getText().toString();
                House house = (House) spinnerHouse.getSelectedItem();

                createHousePresenter.createNewArticle(articleName, description, price, house);
            }
        });
    }

    @Override
    public void showHouseData(List<House> houses) {
        this.listHouse = houses;

        ArrayAdapter<House> adapterHome = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, listHouse);
        spinnerHouse.setAdapter(adapterHome);

        if(listHouse.size() == 0) {
            scrlBoarding.setVisibility(View.INVISIBLE);
            tvCreateHouse.setVisibility(View.INVISIBLE);
            txtEmptyHouseCreate.setVisibility(View.VISIBLE);
        } else {
            txtEmptyHouseCreate.setVisibility(View.INVISIBLE);
            scrlBoarding.setVisibility(View.VISIBLE);
            tvCreateHouse.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onInvalidName(String msg) {
        editArticleName.setError(msg);
    }

    @Override
    public void onInvalidDescription(String msg) {
        editDescription.setError(msg);
    }

    @Override
    public void onInvalidPrice(String msg) {
        editPrice.setError(msg);
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