package com.jagamypriera.thetruthnews.dagger.application;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.jagamypriera.thetruthnews.R;
import com.jagamypriera.thetruthnews.dagger.application.modules.ApplicationModule;
import com.jagamypriera.thetruthnews.dagger.application.modules.DataStorageModule;
import com.jagamypriera.thetruthnews.dagger.application.modules.NetworkModule;
import com.jagamypriera.thetruthnews.dagger.application.modules.PresenterModule;
import com.jagamypriera.thetruthnews.dagger.application.modules.ResourceModule;

import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by jagamypriera on 11/1/17.
 */

public class BaseApplication extends Application {
    private static ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent= DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule())
                .dataStorageModule(new DataStorageModule())
                .presenterModule(new PresenterModule())
                .resourceModule(new ResourceModule())
                .build();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/OpenSans-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        Timber.plant(new Timber.DebugTree() {
            @Override
            protected String createStackElementTag(StackTraceElement element) {
                return String.format("Asdf " +
                                "Line Number:%s " +
                                "Method Name:%s " +
                                "%s ",
                        element.getLineNumber(),
                        element.getMethodName(),
                        super.createStackElementTag(element)
                );
            }
        });

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static ApplicationComponent getApplicationComponent(){
        return applicationComponent;
    }
}
