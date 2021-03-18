package com.habp.fhouse.data.datasource;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.habp.fhouse.data.model.Article;
import com.habp.fhouse.data.model.WishList;
import com.habp.fhouse.util.CallBack;
import com.habp.fhouse.util.ConvertHelper;
import com.habp.fhouse.util.DatabaseConstraints;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleFirestoreRepository {
    private CollectionReference collection;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    public ArticleFirestoreRepository(FirebaseFirestore firebaseFirestore) {
        this.collection = firebaseFirestore.collection(DatabaseConstraints.ARTCILE_COLLECTION_NAME);
    }

    public ArticleFirestoreRepository(FirebaseFirestore firebaseFirestore, FirebaseAuth firebaseAuth) {
        this.collection = firebaseFirestore.collection(DatabaseConstraints.ARTCILE_COLLECTION_NAME);
        this.firebaseAuth = firebaseAuth;
        this.firebaseFirestore = firebaseFirestore;
    }

    public void createNewArticle(Article article, CallBack<Boolean> callBack) {
        Map<String, Object> map = ConvertHelper.convertObjectToMap(article);
        map.put(DatabaseConstraints.ARTICLE_TIME_KEY_NAME, FieldValue.serverTimestamp());
        collection.document(article.getArticleId())
                .set(map).addOnCompleteListener(task -> callBack.onSuccessListener(task.isSuccessful()));
    }

    public void updateArticle(Article article, CallBack<Boolean> callBack) {
        Map<String, Object> map = ConvertHelper.convertObjectToMap(article);
        collection.document(article.getArticleId())
                .set(map).addOnCompleteListener(task -> callBack.onSuccessListener(task.isSuccessful()));
    }

    public void deleteArticle(String articleId, CallBack<Boolean> callback) {
        WishListFirestoreRepository wishListFirestoreRepository = new WishListFirestoreRepository(firebaseFirestore);
        wishListFirestoreRepository.getWishListByArticleId(articleId, wishlists -> {
            for(WishList wishList : wishlists) {
                wishListFirestoreRepository.deleteWishList(wishList.getWishListId(), isSuccess -> {});
            }
        });
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
                            getBoardingImageURL(article.getArticleType(), article, article::setPhotoPath);
                            if(firebaseAuth.getCurrentUser() != null)
                                getWishListId(article.getArticleId(), firebaseAuth.getUid(), article::setWishListId);
                            articleList.add(article);
                        });

                    }
                    callBack.onSuccessListener(articleList);
                });
    }

    private void getBoardingImageURL(int articleType, Article article, CallBack<String> callBack) {
        FirebaseStorageRemote firebaseStorageRemote = new FirebaseStorageRemote(FirebaseStorage.getInstance());
        switch (articleType) {
            case DatabaseConstraints.HOUSE_ARTICLE:
                getHouseImageURL(firebaseStorageRemote, article.getHouseId(), callBack);
                break;
            case DatabaseConstraints.ROOM_ARTICLE:
                getRoomImageURL(firebaseStorageRemote, article.getRoomId(), callBack);
                break;
            case DatabaseConstraints.BED_ARTICLE:
                getBedImageURL(firebaseStorageRemote, article.getBedId(), callBack);
                break;
        }
    }

    private void getHouseImageURL(FirebaseStorageRemote firebaseStorageRemote, String houseId, CallBack<String> callBack) {
        HouseFirestoreRepository houseFirestoreRepository = new HouseFirestoreRepository(firebaseFirestore);
        houseFirestoreRepository.getHouse(houseId, house -> {
            firebaseStorageRemote.getImageURL(house.getPhotoPath(), imageURL-> callBack.onSuccessListener(imageURL.toString()));
        });
    }

    private void getRoomImageURL(FirebaseStorageRemote firebaseStorageRemote, String roomId, CallBack<String> callBack) {
        RoomFirestoreRepository roomFirestoreRepository = new RoomFirestoreRepository(firebaseFirestore);
        roomFirestoreRepository.getRoom(roomId, room -> {
            firebaseStorageRemote.getImageURL(room.getPhotoPath(), imageURL -> callBack.onSuccessListener(imageURL.toString()));
        });
    }

    private void getBedImageURL(FirebaseStorageRemote firebaseStorageRemote, String bedId, CallBack<String> callBack) {
        BedFirestoreRepository bedFirestoreRepository = new BedFirestoreRepository(firebaseFirestore);
        bedFirestoreRepository.getBed(bedId, bed -> {
            firebaseStorageRemote.getImageURL(bed.getPhotoPath(), imageURL -> callBack.onSuccessListener(imageURL.toString()));
        });
    }

    private void getWishListId(String articleId, String userId, CallBack<String> callBack) {
        WishListFirestoreRepository wishListFirestoreRepository = new WishListFirestoreRepository(firebaseFirestore);
        wishListFirestoreRepository.getWishList(articleId, userId, wishListId -> callBack.onSuccessListener(wishListId.getArticleId()));
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
                        getBoardingImageURL(article.getArticleType(), article, article::setPhotoPath);
                        if(firebaseAuth.getCurrentUser() != null)
                            getWishListId(article.getArticleId(), firebaseAuth.getUid(), article::setWishListId);
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
                getBoardingImageURL(article.getArticleType(), article, article::setPhotoPath);
                if(firebaseAuth.getCurrentUser() != null)
                    getWishListId(article.getArticleId(), firebaseAuth.getUid(), article::setWishListId);
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
                getBoardingImageURL(article.getArticleType(), article, article::setPhotoPath);
                if(firebaseAuth.getCurrentUser() != null)
                    getWishListId(article.getArticleId(), firebaseAuth.getUid(), article::setWishListId);
                articleList.add(article);
            }
            callBack.onSuccessListener(articleList);
        });
    }

}
