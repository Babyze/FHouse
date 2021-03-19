package com.habp.fhouse.ui.boarding;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.habp.fhouse.R;
import com.habp.fhouse.data.datasource.FirebaseAuthRepository;
import com.habp.fhouse.data.datasource.HouseFirestoreRepository;
import com.habp.fhouse.data.model.House;
import com.habp.fhouse.ui.boarding.house.create.CreateHouseActivity;
import com.habp.fhouse.ui.boarding.house.housedetail.HouseDetailFragment;
import com.habp.fhouse.ui.sign.SignInActivity;

import java.util.List;


public class HouseManagementFragment extends Fragment implements HouseContract.View {
    private ListView lvHomeMamage;
    private HouseAdapter adapter;
    private HousePresenter housePresenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_house_management, container, false);
        lvHomeMamage = view.findViewById(R.id.lvHomeManage);

        FirebaseAuthRepository firebaseAuthRepository
                = new FirebaseAuthRepository(FirebaseAuth.getInstance());
        HouseFirestoreRepository houseFirestoreRepository =
                new HouseFirestoreRepository(FirebaseFirestore.getInstance(), FirebaseAuth.getInstance());

        housePresenter = new HousePresenter(this, firebaseAuthRepository);
        housePresenter.checkAuthorize(false);
        housePresenter.loadHouse();

        ImageButton btnCreate = view.findViewById(R.id.btnCreateHouseActivity);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateHouseActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void showHouseList(List<House> listHouse) {
        System.out.println(listHouse.size() + "AAAAA");
        adapter = new HouseAdapter(listHouse);
        if (listHouse.size() > 0) {
            lvHomeMamage.setAdapter(adapter);
            lvHomeMamage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    House currentHouse = listHouse.get(i);
                    if (currentHouse != null) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("currentHouse", currentHouse);
                        Fragment houseDetail = new HouseDetailFragment();
                        houseDetail.setArguments(bundle);
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, houseDetail);
                        fragmentTransaction.commit();
                    }
                }
            });
            lvHomeMamage.setBackgroundColor(getResources().getColor(R.color.grey));
            TextView txtEmptyHouse = this.getView().findViewById(R.id.txtEmptyHouse);
            txtEmptyHouse.setText("");
        } else {
            lvHomeMamage.setBackgroundColor(Color.WHITE);
            TextView txtEmptyHouse = this.getView().findViewById(R.id.txtEmptyHouse);
            txtEmptyHouse.setText("Empty");
        }
    }

    @Override
    public void startSignInActivity() {
        Intent intent = new Intent(getActivity(), SignInActivity.class);
        startActivity(intent);
    }

    @Override
    public void redirectToHomeFragment() {

    }

    @Override
    public void onResume() {
        super.onResume();
        housePresenter.loadHouse();
        housePresenter.checkAuthorize(true);
    }
}
