package com.jagamypriera.thetruthnews.api;

import com.jagamypriera.thetruthnews.fragment.newslist.model.NewsListHeadlinesRequestModel;
import com.jagamypriera.thetruthnews.fragment.newslist.model.NewsListRequestModel;
import com.jagamypriera.thetruthnews.fragment.newslist.model.NewsListResponseModel;

import java.util.Map;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;


/**
 * Created by jagamypriera on 11/7/17.
 */
public interface ApiService {
    @GET(ApiEndPoint.EVERYTHING) Observable<NewsListResponseModel> getAllNews(@QueryMap Map<String,String> request);
    @GET(ApiEndPoint.HEADLINES) Observable<NewsListResponseModel> getHeadlines(@QueryMap Map<String,String> request);
}
