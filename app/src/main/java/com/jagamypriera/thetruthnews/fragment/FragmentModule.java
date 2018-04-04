package com.jagamypriera.thetruthnews.fragment;


import com.jagamypriera.thetruthnews.fragment.newslist.NewsListView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jagamypriera on 19/12/17.
 */
@Module
public class FragmentModule {
    @Provides /*@ActivityScope*/
    NewsListView base() {return new NewsListView();}
}
