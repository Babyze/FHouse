package com.habp.fhouse.ui.article;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.habp.fhouse.R;
import com.habp.fhouse.data.datasource.ArticleFirestoreRepository;
import com.habp.fhouse.data.model.Article;

public class ArticleInformationFragment extends Fragment {
    private Article currentArticle;
    private TextView updateArticle;
    private String price;
    private String description;
    private Article updateArticleDetail;


    public ArticleInformationFragment(Article currentArticle) {
        this.currentArticle = currentArticle;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_information, container, false);
        EditText editPrice = view.findViewById(R.id.editPrice);
        editPrice.setText(currentArticle.getPrice());
        EditText editDescription = view.findViewById(R.id.editDescription);
        editDescription.setText(currentArticle.getArticleDescription());
        updateArticle = view.findViewById(R.id.txtUpdateArticle);
        ArticleFirestoreRepository articleFirestoreRepository =
                new ArticleFirestoreRepository(FirebaseFirestore.getInstance(), FirebaseAuth.getInstance());
        updateArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateArticleDetail = currentArticle;

                if(!editPrice.getText().toString().equals("") && !editDescription.getText().toString().equals("")){
                    price = editPrice.getText().toString();
                    description = editDescription.getText().toString();
                    updateArticleDetail.setPrice(price);
                    updateArticleDetail.setArticleDescription(description);
                    articleFirestoreRepository.updateArticle(updateArticleDetail, task -> {
                        Toast.makeText(getContext(), "Update Article "+updateArticleDetail.getArticleName()+" Successful", Toast.LENGTH_SHORT).show();
                    });
                    getActivity().finish();
                }else{
                    Toast.makeText(getContext(), "Update Article "+updateArticleDetail.getArticleName()+" Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;

    }
}