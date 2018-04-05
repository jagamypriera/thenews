package com.jagamypriera.thetruthnews.fragment.newslist.model;

import com.jagamypriera.thetruthnews.Constants;

import java.util.HashMap;
import java.util.Map;

public class NewsListRequestModel  {
    private int page=1;
    public NewsListRequestModel setPage(int page){
        this.page=page;
        return this;
    }
    public Map<String,String> toMap(){
        Map<String,String> map =new HashMap<>();
        map.put("domains", Constants.SOURCE);
        map.put("page",page+"");
        map.put("pageSize", Constants.MAX_NEWS+"");
        return map;
    }
}
