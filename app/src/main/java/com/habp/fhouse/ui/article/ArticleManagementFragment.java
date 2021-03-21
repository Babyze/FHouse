package com.habp.fhouse.ui.article;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.habp.fhouse.R;
import com.habp.fhouse.data.datasource.ArticleFirestoreRepository;
import com.habp.fhouse.data.datasource.HouseFirestoreRepository;
import com.habp.fhouse.data.datasource.UserFirestoreRepository;
import com.habp.fhouse.data.model.Article;
import com.habp.fhouse.ui.article.createArticle.CreateArticleActivity;
import com.habp.fhouse.ui.home.HomePresenter;
import com.habp.fhouse.util.CallBack;

import java.util.ArrayList;
import java.util.List;

public class ArticleManagementFragment extends Fragment{
    private ListView lvArticleManagement;
    private ArticleAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        showArticleManagement(inflater, container, savedInstanceState);
        if (lvArticleManagement == null) {
            System.out.println("List Article Management null");
        }else{
            lvArticleManagement.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Article article = (Article) lvArticleManagement.getItemAtPosition(position);
                    Intent intent = new Intent(getActivity(), ArticleManagementDetailActivity.class);
                    intent.putExtra("articleDetail",article);
                    startActivity(intent);
                }
            });
        }

        ImageButton btnCreateArticle = view.findViewById(R.id.btnCreateArticle);
        btnCreateArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateArticleActivity.class);
                startActivity(intent);
                //showArticleManagement();
            }
        });

        return view;
    }

    private void showArticleManagement(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        ArticleFirestoreRepository articleFirestoreRepository =
                new ArticleFirestoreRepository(FirebaseFirestore.getInstance(),FirebaseAuth.getInstance());
        articleFirestoreRepository.getArticleListByUser(listArticle -> {
            if (listArticle.size() == 0){
                System.out.println("ahihi");
            }else{
                lvArticleManagement = view.findViewById(R.id.lvArticleManagement);
                adapter = new ArticleAdapter(listArticle);
                lvArticleManagement.setAdapter(adapter);
            }
        });
    }
}
