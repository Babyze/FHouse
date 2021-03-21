package com.habp.fhouse.ui.article.createArticle.bed;

import com.google.firebase.auth.FirebaseAuth;
import com.habp.fhouse.data.datasource.ArticleFirestoreRepository;
import com.habp.fhouse.data.datasource.BedFirestoreRepository;
import com.habp.fhouse.data.datasource.HouseFirestoreRepository;
import com.habp.fhouse.data.datasource.RoomFirestoreRepository;
import com.habp.fhouse.data.model.Article;
import com.habp.fhouse.data.model.Bed;
import com.habp.fhouse.data.model.House;
import com.habp.fhouse.data.model.Room;
import com.habp.fhouse.util.DatabaseConstraints;

import java.util.UUID;

public class CreateBedPresenter implements CreateBedContract.Presenter {
    private CreateBedContract.View mView;
    private HouseFirestoreRepository houseRep;
    private RoomFirestoreRepository roomRep;
    private BedFirestoreRepository bedRep;
    private ArticleFirestoreRepository articleRep;
    private FirebaseAuth firebaseAuth;

    public CreateBedPresenter(CreateBedContract.View mView,
                              HouseFirestoreRepository houseRep,
                              RoomFirestoreRepository roomRep,
                              BedFirestoreRepository bedRep,
                              ArticleFirestoreRepository articleRep,
                              FirebaseAuth firebaseAuth) {
        this.mView = mView;
        this.houseRep = houseRep;
        this.roomRep = roomRep;
        this.bedRep = bedRep;
        this.articleRep = articleRep;
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public void loadHouseData() {
        houseRep.getHouseList(houses -> {
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
    public void loadBedData(String roomId) {
        bedRep.getBedList(roomId, beds -> {
            mView.showBedData(beds);
        });
    }

    @Override
    public void createArticle(String articleName, String description,
                              String price, House house, Room room, Bed bed) {
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
                room.getRoomId(), bed.getBedId(), userId, price, DatabaseConstraints.BED_ARTICLE);
        articleRep.createNewArticle(article, isSuccess -> {
            if(isSuccess) {
                mView.onCreateSuccess();
            } else {
                mView.onCreateFail();
            }
        });
    }
}
