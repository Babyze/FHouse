package com.habp.fhouse.data.datasource;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.habp.fhouse.data.model.Room;
import com.habp.fhouse.util.CallBack;
import com.habp.fhouse.util.ConvertHelper;
import com.habp.fhouse.util.DatabaseConstraints;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RoomFirestoreRepository {
    private CollectionReference collection;
    private FirebaseAuth firebaseAuth;

    public RoomFirestoreRepository(FirebaseFirestore firebaseFirestore, FirebaseAuth firebaseAuth) {
        this.collection = firebaseFirestore.collection(DatabaseConstraints.ROOM_COLLECTION_NAME);
        this.firebaseAuth = firebaseAuth;
    }

    public void createRoom(Room room, CallBack<Boolean> callBack) {
        isRoomExist(room.getRoomId(), isRoomExist -> {
            if(!isRoomExist) {
                collection.document(room.getRoomId() + firebaseAuth.getUid())
                        .set(room);
            }
            callBack.onSuccessListener(!isRoomExist);
        });
    }

    private void isRoomExist(String roomId, CallBack<Boolean> callBack) {
        collection.document(roomId + firebaseAuth.getUid()).get()
                .addOnCompleteListener(task -> callBack.onSuccessListener(task.getResult().exists()));
    }

    public void getRoom(String roomId, CallBack<Room> callback) {
        collection.document(roomId + firebaseAuth.getUid())
                .get().addOnCompleteListener(task -> {
                    Room room = task.getResult().toObject(Room.class);
                    callback.onSuccessListener(room);
        });
    }

    public void getRoomList(String houseId, CallBack<List<Room>> callBack) {
        collection.whereEqualTo(DatabaseConstraints.HOUSE_ID_KEY_NAME, houseId)
                .get().addOnCompleteListener(task -> {
                    List<Room> rooms = new ArrayList<>();
                    for(DocumentSnapshot documentSnapshot : task.getResult()) {
                        rooms.add(documentSnapshot.toObject(Room.class));
                    }
                    callBack.onSuccessListener(rooms);
        });
    }

    public void updateRoom(Room room, CallBack<Boolean> callBack) {
        Map<String, Object> map = ConvertHelper.convertObjectToMap(room);
        collection.document(room.getRoomId() + firebaseAuth.getUid())
                .update(map).addOnCompleteListener(task -> callBack.onSuccessListener(task.isSuccessful()));
    }

    public void deleteRoom(String roomId, CallBack<Boolean> callBack) {
        collection.document(roomId + firebaseAuth.getUid()).delete()
                .addOnCompleteListener(task -> callBack.onSuccessListener(task.isSuccessful()));
    }
}
