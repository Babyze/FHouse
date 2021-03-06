package com.habp.fhouse.data.datasource;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
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
                .addOnCompleteListener(task -> {
                    if(task.getResult() != null) {
                        Bed bed = task.getResult().toObject(Bed.class);
                        callBack.onSuccessListener(bed != null);
                    } else {
                        callBack.onSuccessListener(false);
                    }
                });
    }

    public void getBed(String bedId, CallBack<Bed> callback) {
        collection.document(bedId)
                .get().addOnCompleteListener(task -> {
                    Bed bed = new Bed();
                    if(task.getResult() != null) {
                        bed = task.getResult().toObject(Bed.class);
                    }
                    callback.onSuccessListener(bed);
        });
    }

    public void getBedList(String roomId, CallBack<List<Bed>> callBack) {
        FirebaseStorageRemote firebaseStorageRemote = new FirebaseStorageRemote(FirebaseStorage.getInstance());
        collection.whereEqualTo(DatabaseConstraints.ROOM_ID_KEY_NAME, roomId)
                .get().addOnCompleteListener(task -> {
            List<Bed> beds = new ArrayList<>();
            QuerySnapshot snap = task.getResult();
            if(snap != null) {
                if(snap.getDocuments().size() == 0) {
                    callBack.onSuccessListener(beds);
                }
                for(DocumentSnapshot documentSnapshot : snap.getDocuments()) {
                    Bed bed = documentSnapshot.toObject(Bed.class);
                    firebaseStorageRemote.getImageURL(bed.getPhotoPath(), imageURL -> {
                        bed.setPhotoPath(imageURL.toString());
                        beds.add(bed);
                        callBack.onSuccessListener(beds);
                    });
                }
            } else {
                callBack.onSuccessListener(beds);
            }
        });
    }

    public void updateBed(Bed bed, CallBack<Bed> callBack) {
        getBed(bed.getBedId(), bedDB -> {
            bed.setPhotoPath(bedDB.getPhotoPath());
            Map<String, Object> map = ConvertHelper.convertObjectToMap(bed);
            collection.document(bed.getBedId())
                    .update(map).addOnCompleteListener(task -> {
                if(task.isSuccessful()) {
                    callBack.onSuccessListener(bed);
                } else {
                    callBack.onSuccessListener(null);
                }
            });
        });
    }

    public void deleteBed(String bedId, CallBack<Boolean> callBack) {
        ArticleFirestoreRepository articleFirestoreRepository =
                new ArticleFirestoreRepository(FirebaseFirestore.getInstance());
        articleFirestoreRepository.getArticleListByBedId(bedId, articleList -> {
            for(Article article : articleList) {
                articleFirestoreRepository.deleteArticle(article.getArticleId(), task -> {});
            }
        });
        collection.document(bedId).delete()
                .addOnCompleteListener(task -> callBack.onSuccessListener(task.isSuccessful()));
    }
}
