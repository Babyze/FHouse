package com.habp.fhouse.data.datasource;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.habp.fhouse.data.model.Article;
import com.habp.fhouse.data.model.User;
import com.habp.fhouse.data.model.WishList;
import com.habp.fhouse.util.CallBack;
import com.habp.fhouse.util.ConvertHelper;
import com.habp.fhouse.util.DatabaseConstraints;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WishListFirestoreRepository {
    private CollectionReference collection;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    public WishListFirestoreRepository(FirebaseFirestore firebaseFirestore, FirebaseAuth firebaseAuth) {
        this.firebaseFirestore = firebaseFirestore;
        this.firebaseAuth = firebaseAuth;
        this.collection = firebaseFirestore.collection(DatabaseConstraints.WISHLIST_COLLECTION_NAME);
    }

    public WishListFirestoreRepository(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
        this.collection = firebaseFirestore.collection(DatabaseConstraints.WISHLIST_COLLECTION_NAME);
    }

    public void createWishList(WishList wishList, CallBack<Boolean> callback) {
        Map<String, Object> map = ConvertHelper.convertObjectToMap(wishList);
        collection.document(wishList.getWishListId())
                .set(map)
                .addOnCompleteListener(task -> callback.onSuccessListener(task.isSuccessful()));
    }

    public void getWishListByArticleId(String articleId, CallBack<List<WishList>> callBack) {
        List<WishList> wishLists = new ArrayList<>();
        collection.whereEqualTo(DatabaseConstraints.ARTICLE_ID_KEY_NAME, articleId)
                .get()
                .addOnCompleteListener(data -> {
                   for(DocumentSnapshot doc : data.getResult()) {
                       wishLists.add(doc.toObject(WishList.class));
                   }
                   callBack.onSuccessListener(wishLists);
                });
    }

    public void getArticleWishListByUserId(String userId, CallBack<List<Article>> callBack) {
        List<Article> articleList = new ArrayList<>();
        ArticleFirestoreRepository articleFirestoreRepository =
                new ArticleFirestoreRepository(firebaseFirestore,firebaseAuth);

        collection.whereEqualTo(DatabaseConstraints.USER_ID_KEY_NAME, userId)
                .get().addOnCompleteListener(wishLists -> {
                    QuerySnapshot snap = wishLists.getResult();
                    if(snap != null) {
                        for (DocumentSnapshot doc : wishLists.getResult()) {
                            WishList wishList = doc.toObject(WishList.class);
                            articleFirestoreRepository.getArticle(wishList.getArticleId(), article -> {
                                articleList.add(article);
                                callBack.onSuccessListener(articleList);
                            });
                        }
                        callBack.onSuccessListener(articleList);
                    } else {
                        callBack.onSuccessListener(articleList);
                    }
        });
    }

    public void getUserListByArticleId(String articleId, CallBack<List<User>> callBack) {
        List<User> users = new ArrayList<>();
        UserFirestoreRepository userFirestoreRepository = new UserFirestoreRepository(firebaseFirestore, firebaseAuth);
        collection.whereEqualTo(DatabaseConstraints.ARTICLE_ID_KEY_NAME, articleId)
                .get()
                .addOnCompleteListener(wishlists -> {
                   for(DocumentSnapshot doc : wishlists.getResult()) {
                       WishList wishList = doc.toObject(WishList.class);
                       userFirestoreRepository.getUserInfo(wishList.getUserId(), user -> {
                           users.add(user);
                           callBack.onSuccessListener(users);
                       });
                   }
                });
    }

    public void getWishList(String userId, String articleId, CallBack<WishList> callBack) {
        collection.whereEqualTo(DatabaseConstraints.USER_ID_KEY_NAME, userId)
                .whereEqualTo(DatabaseConstraints.ARTICLE_ID_KEY_NAME, articleId)
                .get()
                .addOnCompleteListener(task -> {
                    QuerySnapshot snap = task.getResult();
                    WishList wishList = new WishList();
                    if(snap != null) {
                        for(DocumentSnapshot doc : task.getResult().getDocuments()) {
                            wishList = doc.toObject(WishList.class);
                        }
                    }
                    callBack.onSuccessListener(wishList);
                });
    }

    public void deleteWishList(String wishListId, CallBack<Boolean> callBack) {
        collection.document(wishListId).delete().addOnCompleteListener(task -> callBack.onSuccessListener(task.isSuccessful()));
    }


}
