package com.habp.fhouse.ui.articledetail;


import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.habp.fhouse.data.datasource.FirebaseAuthRepository;
import com.habp.fhouse.data.datasource.UserFirestoreRepository;
import com.habp.fhouse.data.model.Article;

public class ArticleDetailPresenter implements ArticleDetailContract.Presenter {
    private FirebaseAuthRepository firebaseAuthRepository;
    private ArticleDetailContract.View mView;

    public ArticleDetailPresenter(FirebaseAuthRepository firebaseAuthRepository, ArticleDetailContract.View Mview) {
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
        UserFirestoreRepository userRep = new UserFirestoreRepository(FirebaseFirestore.getInstance());
        userRep.getUserInfo(article.getUserId(), user -> {
            article.setPhoneNumber(user.getPhone());
            if(article != null) {
                mView.setActivityWishlist(article.getWishListId() != null);
            }
            mView.showData();
        });
    }
}
