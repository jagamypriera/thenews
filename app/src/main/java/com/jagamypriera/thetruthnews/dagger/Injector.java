package com.jagamypriera.thetruthnews.dagger;


import com.jagamypriera.thetruthnews.dagger.activity.ActivityComponent;
import com.jagamypriera.thetruthnews.dagger.activity.BaseActivity;
import com.jagamypriera.thetruthnews.dagger.application.ApplicationComponent;
import com.jagamypriera.thetruthnews.dagger.application.BaseApplication;

/**
 * Created by jagamypriera on 11/7/17.
 */

public class Injector {
    public static ApplicationComponent getApplicationComponent(){
        return BaseApplication.getApplicationComponent();
    }
    public static ActivityComponent getActivityComponent(){
        return BaseActivity.getActivityComponent();
    }
}
