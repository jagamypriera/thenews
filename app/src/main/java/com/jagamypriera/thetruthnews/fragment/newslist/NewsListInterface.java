package com.jagamypriera.thetruthnews.fragment.newslist;

import com.jagamypriera.thetruthnews.fragment.newslist.model.NewsListResponseModel;

import java.util.ArrayList;

public interface NewsListInterface {
    void showLoading();
    void hideLoading();
    void onGetNewsSuccess(ArrayList<NewsListResponseModel.News> news);
    void onGetNewsFail(String error);
}
