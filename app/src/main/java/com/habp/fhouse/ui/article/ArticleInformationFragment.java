package com.habp.fhouse.ui.article;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.habp.fhouse.R;
import com.habp.fhouse.data.model.Article;

public class ArticleInformationFragment extends Fragment {
    private Article currentArticle;

    public ArticleInformationFragment(Article currentArticle) {
        this.currentArticle = currentArticle;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_information, container, false);
        EditText editPrice = view.findViewById(R.id.editPrice);
        editPrice.setText(currentArticle.getPrice()+"");
        EditText editDescription = view.findViewById(R.id.editDescription);
        editDescription.setText(currentArticle.getArticleDescription());

        return view;

    }
}