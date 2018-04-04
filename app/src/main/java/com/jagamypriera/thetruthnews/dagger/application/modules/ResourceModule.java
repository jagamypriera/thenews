package com.jagamypriera.thetruthnews.dagger.application.modules;

import android.content.Context;

import com.jagamypriera.thetruthnews.Resources;
import com.jagamypriera.thetruthnews.dagger.application.ApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jagamypriera on 11/7/17.
 */
@Module
public class ResourceModule {
    @Provides
    @ApplicationScope
    Resources resources(Context context){
        return new Resources(context);
    }
}
