package com.habp.fhouse.util;

import com.habp.fhouse.data.model.Article;

import java.util.List;

public class ListHelper {

    public static List<Article> addCollection(List<Article> list, List<Article> anotherCollection) {
        for (Article aArticle : anotherCollection) {
            boolean isExist = false;
            for(Article article : list) {
                if(aArticle.getArticleId().equals(article.getArticleId())) {
                    isExist = true;
                    break;
                }
            }
            if(!isExist) {
                list.add(aArticle);
            }
        }
        return list;
    }

}
