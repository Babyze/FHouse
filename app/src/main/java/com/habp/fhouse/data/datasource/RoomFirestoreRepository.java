package com.habp.fhouse.data.datasource;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.habp.fhouse.data.model.Article;
import com.habp.fhouse.data.model.Bed;
import com.habp.fhouse.data.model.Room;
import com.habp.fhouse.util.CallBack;
import com.habp.fhouse.util.ConvertHelper;
import com.habp.fhouse.util.DatabaseConstraints;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RoomFirestoreRepository {
    private CollectionReference collection;

    public RoomFirestoreRepository(FirebaseFirestore firebaseFirestore) {
        this.collection = firebaseFirestore.collection(DatabaseConstraints.ROOM_COLLECTION_NAME);
    }

    public void createRoom(Room room, CallBack<Boolean> callBack) {
        isRoomExist(room.getRoomId(), isRoomExist -> {
            if(!isRoomExist) {
                collection.document(room.getRoomId())
                        .set(room);
            }
            callBack.onSuccessListener(!isRoomExist);
        });
    }

    private void isRoomExist(String roomId, CallBack<Boolean> callBack) {
        collection.document(roomId).get()
                .addOnCompleteListener(task -> {
                    if(task.getResult() != null) {
                        Room room = task.getResult().toObject(Room.class);
                        callBack.onSuccessListener(room != null);
                    } else {
                        callBack.onSuccessListener(false);
                    }
                });
    }

    public void getRoom(String roomId, CallBack<Room> callback) {
        collection.document(roomId)
                .get().addOnCompleteListener(task -> {
                    Room room = new Room();
                    if(task.getResult() != null) {
                        room = task.getResult().toObject(Room.class);
                    }
                    callback.onSuccessListener(room);
        });
    }

    public void getRoomList(String houseId, CallBack<List<Room>> callBack) {
        FirebaseStorageRemote firebaseStorageRemote = new FirebaseStorageRemote(FirebaseStorage.getInstance());
        collection.whereEqualTo(DatabaseConstraints.HOUSE_ID_KEY_NAME, houseId)
                .get().addOnCompleteListener(task -> {
                    List<Room> rooms = new ArrayList<>();
                    QuerySnapshot snapshot = task.getResult();
                    if(snapshot != null) {
                        if(snapshot.getDocuments().size() == 0) {
                            callBack.onSuccessListener(rooms);
                        }
                        for(DocumentSnapshot documentSnapshot : snapshot.getDocuments()) {
                            Room room = documentSnapshot.toObject(Room.class);
                            firebaseStorageRemote.getImageURL(room.getPhotoPath(), imageURL -> {
                                room.setPhotoPath(imageURL.toString());
                                rooms.add(room);
                                callBack.onSuccessListener(rooms);
                            });
                        }
                    } else {
                        callBack.onSuccessListener(rooms);
                    }
        });
    }

    public void updateRoom(Room room, CallBack<Room> callBack) {
        getRoom(room.getRoomId(), roomDB -> {
            room.setPhotoPath(roomDB.getPhotoPath());
            Map<String, Object> map = ConvertHelper.convertObjectToMap(room);
            collection.document(room.getRoomId())
                    .update(map).addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            callBack.onSuccessListener(room);
                        } else {
                            callBack.onSuccessListener(null);
                        }
            });
        });
    }

    public void deleteRoom(String roomId, CallBack<Boolean> callBack) {
        BedFirestoreRepository bedFirestoreRepository = new BedFirestoreRepository(FirebaseFirestore.getInstance());
        ArticleFirestoreRepository articleFirestoreRepository = new ArticleFirestoreRepository(FirebaseFirestore.getInstance());

        bedFirestoreRepository.getBedList(roomId, bedList -> {
            for(Bed bed : bedList) {
                bedFirestoreRepository.deleteBed(bed.getBedId(), isSuccess -> {});
                articleFirestoreRepository.getArticleListByBedId(bed.getBedId(), articleList -> {
                    for(Article article : articleList) {
                        articleFirestoreRepository.deleteArticle(article.getArticleId(), task -> {});
                    }
                });
            }
        });
        articleFirestoreRepository.getArticleListByRoomId(roomId, articleList -> {
            for(Article article : articleList) {
                articleFirestoreRepository.deleteArticle(article.getArticleId(), task -> {});
            }
        });
        collection.document(roomId).delete()
                .addOnCompleteListener(task -> callBack.onSuccessListener(task.isSuccessful()));
    }
}
