package com.jagamypriera.thetruthnews.dagger.activity.modules;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.jagamypriera.thetruthnews.fragment.FragmentChangeObservable;
import com.jagamypriera.thetruthnews.dagger.activity.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jagamypriera on 11/7/17.
 */
@Module
public class ActivityModule {
    private AppCompatActivity activityContext;
    public ActivityModule(AppCompatActivity activityContext) {
        this.activityContext = activityContext;
    }
    @Provides
    @ActivityScope
    AppCompatActivity activityContext(){
        return activityContext;
    }

    @Provides
    @ActivityScope
    FragmentManager fragmentManager(){
        return activityContext.getSupportFragmentManager();
    }

    @Provides
    @ActivityScope
    FragmentChangeObservable observer(FragmentManager manager){
        return new FragmentChangeObservable(manager);
    }
}
