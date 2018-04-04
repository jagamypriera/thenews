package com.jagamypriera.thetruthnews.fragment.newslist.model;

import java.util.HashMap;
import java.util.Map;

public class NewsListRequestModel  {
    private String domains="cointelegraph.com";
    private int pageSize=10;
    private int page=1;
    public NewsListRequestModel setPage(int page){
        this.page=page;
        return this;
    }
    public Map<String,String> toMap(){
        Map<String,String> map =new HashMap<>();
        map.put("domains",domains);
        map.put("page",page+"");
        map.put("pageSize",pageSize+"");
        return map;
    }
}
