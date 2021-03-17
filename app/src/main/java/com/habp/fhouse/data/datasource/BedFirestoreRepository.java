package com.habp.fhouse.data.datasource;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.habp.fhouse.data.model.Article;
import com.habp.fhouse.data.model.Bed;
import com.habp.fhouse.util.CallBack;
import com.habp.fhouse.util.ConvertHelper;
import com.habp.fhouse.util.DatabaseConstraints;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BedFirestoreRepository {
    private CollectionReference collection;

    public BedFirestoreRepository(FirebaseFirestore firebaseFirestore) {
        this.collection = firebaseFirestore.collection(DatabaseConstraints.BED_COLLECTION_NAME);
    }

    public void createBed(Bed bed, CallBack<Boolean> callBack) {
        isBedExist(bed.getBedId(), isBedExist -> {
            if(!isBedExist) {
                collection.document(bed.getBedId())
                        .set(bed);
            }
            callBack.onSuccessListener(!isBedExist);
        });
    }

    private void isBedExist(String bedId, CallBack<Boolean> callBack) {
        collection.document(bedId).get()
                .addOnCompleteListener(task -> callBack.onSuccessListener(task.getResult().exists()));
    }

    public void getBed(String bedId, CallBack<Bed> callback) {
        collection.document(bedId)
                .get().addOnCompleteListener(task -> {
                    Bed bed = task.getResult().toObject(Bed.class);
                    callback.onSuccessListener(bed);
        });
    }

    public void getBedList(String roomId, CallBack<List<Bed>> callBack) {
        FirebaseStorageRemote firebaseStorageRemote = new FirebaseStorageRemote(FirebaseStorage.getInstance());
        collection.whereEqualTo(DatabaseConstraints.ROOM_ID_KEY_NAME, roomId)
                .get().addOnCompleteListener(task -> {
            List<Bed> beds = new ArrayList<>();
            for(DocumentSnapshot documentSnapshot : task.getResult()) {
                Bed bed = documentSnapshot.toObject(Bed.class);
                firebaseStorageRemote.getImageURL(bed.getPhotoPath(), imageURL -> bed.setPhotoPath(imageURL.toString()));
                beds.add(bed);
            }
            callBack.onSuccessListener(beds);
        });
    }

    public void updateBed(Bed bed, CallBack<Boolean> callBack) {
        Map<String, Object> map = ConvertHelper.convertObjectToMap(bed);
        collection.document(bed.getBedId())
                .update(map).addOnCompleteListener(task -> callBack.onSuccessListener(task.isSuccessful()));
    }

    public void deleteBed(String bedId, CallBack<Boolean> callBack) {
        ArticleFirestoreRepository articleFirestoreRepository = new ArticleFirestoreRepository(FirebaseFirestore.getInstance());
        articleFirestoreRepository.getArticleListByBedId(bedId, articleList -> {
            for(Article article : articleList) {
                articleFirestoreRepository.deleteArticle(article.getArticleId(), task -> {});
            }
        });
        collection.document(bedId).delete()
                .addOnCompleteListener(task -> callBack.onSuccessListener(task.isSuccessful()));
    }
}
