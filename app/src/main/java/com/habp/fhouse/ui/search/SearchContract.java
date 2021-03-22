package com.habp.fhouse.ui.search;

import com.google.firebase.firestore.DocumentSnapshot;
import com.habp.fhouse.data.model.Article;
import com.habp.fhouse.data.model.ArticleSnap;

import java.util.List;

public interface SearchContract {
    interface View {
        void showResult(boolean isLoadMore, ArticleSnap articleSnap);
    }
    interface Presenter {
        void loadArticleResult(DocumentSnapshot snap, String keyword);

    }
}
