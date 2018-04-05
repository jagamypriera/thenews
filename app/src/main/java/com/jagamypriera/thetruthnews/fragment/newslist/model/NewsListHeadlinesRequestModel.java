package com.jagamypriera.thetruthnews.fragment.newslist.model;

import com.jagamypriera.thetruthnews.Constants;

import java.util.HashMap;
import java.util.Map;

public class NewsListHeadlinesRequestModel {
    private String country;
    public NewsListHeadlinesRequestModel setCountry(String country){
        this.country=country;
        return this;
    }
    public Map<String,String> toMap(){
        Map<String,String> map =new HashMap<>();
        map.put("country",country);
        map.put("pageSize", Constants.MAX_HEADLINES+"");
        return map;
    }
}
