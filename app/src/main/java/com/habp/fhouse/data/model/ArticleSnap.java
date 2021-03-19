package com.habp.fhouse.data.model;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public class ArticleSnap {
    private List<Article> articleList;
    private DocumentSnapshot lastSnap;

    public ArticleSnap() {
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    public DocumentSnapshot getLastSnap() {
        return lastSnap;
    }

    public void setLastSnap(DocumentSnapshot lastSnap) {
        this.lastSnap = lastSnap;
    }
}
