package com.habp.fhouse.util;

public class ArticleTypeHelper {

    public static String getArticleType(int articleId) {
        switch (articleId) {
            case DatabaseConstraints.HOUSE_ARTICLE:
                return "House For Rent";
            case DatabaseConstraints.ROOM_ARTICLE:
                    return "Room For Rent";
            case DatabaseConstraints.BED_ARTICLE:
                        return "Bed For Rent";
        }
        return "";
    }

}
