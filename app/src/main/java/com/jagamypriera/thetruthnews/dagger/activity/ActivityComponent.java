package com.jagamypriera.thetruthnews.dagger.activity;


import com.jagamypriera.thetruthnews.dagger.activity.modules.ActivityModule;
import com.jagamypriera.thetruthnews.dagger.activity.modules.WidgetModule;
import com.jagamypriera.thetruthnews.fragment.FragmentModule;
import com.jagamypriera.thetruthnews.fragment.newslist.NewsListView;

import dagger.Subcomponent;

/**
 * Created by jagamypriera on 11/3/17.
 */

@ActivityScope
@Subcomponent( modules = {WidgetModule.class, ActivityModule.class, FragmentModule.class})
public interface ActivityComponent {
    void inject(BaseActivity activity);
    void inject(NewsListView fragment);
}
