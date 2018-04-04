package com.jagamypriera.thetruthnews.fragment.newslist.adapter;

import android.view.View;

import com.jagamypriera.thetruthnews.fragment.newslist.model.NewsListResponseModel;

public interface RecyclerViewClickListener {
    void onClick(NewsListResponseModel.News news, int position);
}