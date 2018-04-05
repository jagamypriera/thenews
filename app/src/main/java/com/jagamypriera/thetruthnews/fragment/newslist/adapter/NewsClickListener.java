package com.jagamypriera.thetruthnews.fragment.newslist.adapter;

import android.view.View;

import com.jagamypriera.thetruthnews.fragment.newslist.model.NewsListResponseModel;

public interface NewsClickListener {
    void onClick(NewsListResponseModel.News news, View view, int position);
}