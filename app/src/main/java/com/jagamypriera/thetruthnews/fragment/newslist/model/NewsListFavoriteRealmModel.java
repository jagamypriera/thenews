package com.jagamypriera.thetruthnews.fragment.newslist.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class NewsListFavoriteRealmModel extends RealmObject{
    public static final String URL_FIELD="url";
    public NewsListFavoriteRealmModel() { }
    @PrimaryKey
    public String url;
    public NewsListFavoriteRealmModel setUrl(String url) {
        this.url = url;
        return this;
    }
}
