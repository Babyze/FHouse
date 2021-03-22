package com.habp.fhouse.ui.article.createArticle.room;

import android.widget.ArrayAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.habp.fhouse.data.datasource.ArticleFirestoreRepository;
import com.habp.fhouse.data.datasource.HouseFirestoreRepository;
import com.habp.fhouse.data.datasource.RoomFirestoreRepository;
import com.habp.fhouse.data.model.Article;
import com.habp.fhouse.data.model.House;
import com.habp.fhouse.data.model.Room;
import com.habp.fhouse.util.DatabaseConstraints;

import java.util.UUID;

public class CreateRoomPresenter implements CreateRoomContract.Presenter {
    private HouseFirestoreRepository houseRep;
    private RoomFirestoreRepository roomRep;
    private ArticleFirestoreRepository articleRep;
    private CreateRoomContract.View mView;
    private FirebaseAuth firebaseAuth;

    public CreateRoomPresenter(HouseFirestoreRepository houseRep,
                               RoomFirestoreRepository roomRep,
                               ArticleFirestoreRepository articleRep,
                               CreateRoomContract.View mView,
                               FirebaseAuth firebaseAuth) {
        this.houseRep = houseRep;
        this.roomRep = roomRep;
        this.articleRep = articleRep;
        this.mView = mView;
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public void loadHouseData() {
        houseRep.getHouseList(houses ->{
            mView.showHouseData(houses);
        });
    }

    @Override
    public void loadRoomData(String houseId) {
        roomRep.getRoomList(houseId, rooms -> {
            mView.showRoomData(rooms);
        });
    }

    @Override
    public void createArticle(String articleName, String description, String price,
                              House house, Room room) {
        if(articleName.isEmpty()) {
            mView.onInvalidName("Please enter article name");
            return;
        }
        if(description.isEmpty()) {
            mView.onInvalidDescription("Please enter description");
            return;
        }
        if(price.isEmpty()) {
            mView.onInvalidPrice("Please enter price");
            return;
        }
        String articleId = UUID.randomUUID().toString();
        String userId = firebaseAuth.getUid();
        Article article = new Article(articleId, articleName, description, house.getHouseId(),
                room.getRoomId(), userId, price, DatabaseConstraints.ROOM_ARTICLE);
        articleRep.createNewArticle(article, isSuccess -> {
            if(isSuccess) {
                mView.onCreateSuccess();
            } else {
                mView.onCreateFail();
            }
        });
    }
}
