package com.jagamypriera.thetruthnews.dagger.application.modules;

import android.content.Context;


import com.jagamypriera.thetruthnews.Preferences;
import com.jagamypriera.thetruthnews.R;
import com.jagamypriera.thetruthnews.dagger.application.ApplicationScope;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by jagamypriera on 11/7/17.
 */
@Module
public class DataStorageModule {
    @Provides
    @ApplicationScope
    Preferences preferences(Context context){
        return new Preferences(context);
    }
}
