package com.habp.fhouse.data.datasource;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.habp.fhouse.data.model.Article;
import com.habp.fhouse.util.CallBack;
import com.habp.fhouse.util.ConvertHelper;
import com.habp.fhouse.util.DatabaseConstraints;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleFirestoreRepository {
    private CollectionReference collection;
    private FirebaseAuth firebaseAuth;

    public ArticleFirestoreRepository(FirebaseFirestore firebaseFirestore) {
        this.collection = firebaseFirestore.collection(DatabaseConstraints.ARTCILE_COLLECTION_NAME);
    }

    public ArticleFirestoreRepository(FirebaseFirestore firebaseFirestore, FirebaseAuth firebaseAuth) {
        this.collection = firebaseFirestore.collection(DatabaseConstraints.ARTCILE_COLLECTION_NAME);
        this.firebaseAuth = firebaseAuth;
    }

    public void createNewArticle(Article article, CallBack<Boolean> callBack) {
        Map<String, Object> map = ConvertHelper.convertObjectToMap(article);
        map.put("createAt", FieldValue.serverTimestamp());
        collection.document(article.getArticleId())
                .set(map).addOnCompleteListener(task -> callBack.onSuccessListener(task.isSuccessful()));
    }

    public void updateArticle(Article article, CallBack<Boolean> callBack) {
        Map<String, Object> map = ConvertHelper.convertObjectToMap(article);
        collection.document(article.getArticleId())
                .set(map).addOnCompleteListener(task -> callBack.onSuccessListener(task.isSuccessful()));
    }

    public void deleteArticle(String articleId, CallBack<Boolean> callback) {
        collection.document(articleId).delete()
                .addOnCompleteListener(task -> callback.onSuccessListener(task.isSuccessful()));
    }

    public void getArticleListByUser(CallBack<List<Article>> callBack) {
        HouseFirestoreRepository houseFirestoreRepository = new HouseFirestoreRepository(FirebaseFirestore.getInstance(), firebaseAuth);
        List<Article> articleList = new ArrayList<>();
        collection.whereEqualTo(DatabaseConstraints.USER_ID_KEY_NAME, firebaseAuth.getUid())
                .get()
                .addOnCompleteListener(task -> {
                   for(DocumentSnapshot doc : task.getResult()) {
                       Article article = doc.toObject(Article.class);
                       houseFirestoreRepository.getHouse(article.getHouseId(), house -> {
                           article.setHouseAddress(house.getHouseAddress());
                           articleList.add(article);
                       });
                   }
                   callBack.onSuccessListener(articleList);
                });
    }

    public void getNewestArticleList(CallBack<List<Article>> callBack) {
        HouseFirestoreRepository houseFirestoreRepository = new HouseFirestoreRepository(FirebaseFirestore.getInstance(), firebaseAuth);
        List<Article> articleList = new ArrayList<>();
        collection.whereEqualTo(DatabaseConstraints.USER_ID_KEY_NAME, firebaseAuth.getUid())
                .orderBy(DatabaseConstraints.ARTICLE_TIME_KEY_NAME, Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    for(DocumentSnapshot doc : task.getResult()) {
                        Article article = doc.toObject(Article.class);
                        houseFirestoreRepository.getHouse(article.getHouseId(), house -> {
                            article.setHouseAddress(house.getHouseAddress());
                            articleList.add(article);
                        });
                    }
                    callBack.onSuccessListener(articleList);
                });
    }

    public void getArticle(String articleId, CallBack<Article> callBack) {
        collection.document(articleId)
                .get()
                .addOnCompleteListener(task -> callBack.onSuccessListener(task.getResult().toObject(Article.class)));
    }

    public void getArticleListByBedId(String bedId, CallBack<List<Article>> callBack) {
        List<Article> articleList = new ArrayList<>();
        collection.whereEqualTo(DatabaseConstraints.BED_ID_KEY_NAME, bedId)
                .get().addOnCompleteListener(task -> {
                    for(DocumentSnapshot doc : task.getResult().getDocuments()) {
                        Article article = doc.toObject(Article.class);
                        articleList.add(article);
                    }
                    callBack.onSuccessListener(articleList);
                });
    }

    public void getArticleListByRoomId(String roomId, CallBack<List<Article>> callBack) {
        List<Article> articleList = new ArrayList<>();
        collection.whereEqualTo(DatabaseConstraints.ROOM_ID_KEY_NAME, roomId)
                .get().addOnCompleteListener(task -> {
            for(DocumentSnapshot doc : task.getResult().getDocuments()) {
                Article article = doc.toObject(Article.class);
                articleList.add(article);
            }
            callBack.onSuccessListener(articleList);
        });
    }

    public void getArticleListByHouseId(String houseId, CallBack<List<Article>> callBack) {
        List<Article> articleList = new ArrayList<>();
        collection.whereEqualTo(DatabaseConstraints.HOUSE_ID_KEY_NAME, houseId)
                .get().addOnCompleteListener(task -> {
            for(DocumentSnapshot doc : task.getResult().getDocuments()) {
                Article article = doc.toObject(Article.class);
                articleList.add(article);
            }
            callBack.onSuccessListener(articleList);
        });
    }

}
