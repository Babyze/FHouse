package com.habp.fhouse.ui.articledetail;


import com.google.firebase.auth.FirebaseUser;
import com.habp.fhouse.data.datasource.FirebaseAuthRepository;
import com.habp.fhouse.data.model.Article;

public class ArticleDetailPresneter implements ArticleDetailContract.Presenter {
    private FirebaseAuthRepository firebaseAuthRepository;
    private ArticleDetailContract.View mView;

    public ArticleDetailPresneter(FirebaseAuthRepository firebaseAuthRepository, ArticleDetailContract.View Mview) {
        this.firebaseAuthRepository = firebaseAuthRepository;
        this.mView = Mview;
    }

    @Override
    public void checkAuthorization(boolean isReturn) {
        FirebaseUser user = firebaseAuthRepository.getUser();
        if(user == null) {
            if(isReturn)
                mView.closeActivity();
            else
                mView.startSignInActivity();
        }
    }

    @Override
    public void loadData(Article article) {
        if(article != null) {
            mView.setActivityWishlist(article.getWishListId() != null);
        }
        mView.showData();
    }
}
