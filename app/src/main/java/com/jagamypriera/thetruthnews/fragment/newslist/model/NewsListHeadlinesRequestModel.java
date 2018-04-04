package com.jagamypriera.thetruthnews.fragment.newslist.model;

import java.util.HashMap;
import java.util.Map;

public class NewsListHeadlinesRequestModel {
    private String country;
    private int pageSize=5;
    public NewsListHeadlinesRequestModel setCountry(String country){
        this.country=country;
        return this;
    }
    public Map<String,String> toMap(){
        Map<String,String> map =new HashMap<>();
        map.put("country",country);
        map.put("pageSize",pageSize+"");
        return map;
    }
}
