package com.jagamypriera.thetruthnews.dagger.application.modules;


import com.jagamypriera.thetruthnews.api.ApiService;
import com.jagamypriera.thetruthnews.dagger.application.ApplicationScope;
import com.jagamypriera.thetruthnews.fragment.newslist.NewsListPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jagamypriera on 11/7/17.
 */
@Module
public class PresenterModule {
    @Provides
    @ApplicationScope
    NewsListPresenter news(ApiService api){return new NewsListPresenter(api);}
}
