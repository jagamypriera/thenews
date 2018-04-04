package com.jagamypriera.thetruthnews.dagger.application.modules;

import android.content.Context;


import com.jagamypriera.thetruthnews.dagger.application.ApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jagamypriera on 11/7/17.
 */
@Module
public class ApplicationModule {
    private Context applicationContext;

    public ApplicationModule(Context applicationContext) {
        this.applicationContext = applicationContext;
    }
    @Provides
    @ApplicationScope
    Context applicationContext(){
        return applicationContext;
    }
}
