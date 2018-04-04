package com.jagamypriera.thetruthnews.dagger.application;


import com.jagamypriera.thetruthnews.dagger.activity.ActivityComponent;
import com.jagamypriera.thetruthnews.dagger.activity.modules.ActivityModule;
import com.jagamypriera.thetruthnews.dagger.activity.modules.WidgetModule;
import com.jagamypriera.thetruthnews.dagger.application.modules.ApplicationModule;
import com.jagamypriera.thetruthnews.dagger.application.modules.DataStorageModule;
import com.jagamypriera.thetruthnews.dagger.application.modules.NetworkModule;
import com.jagamypriera.thetruthnews.dagger.application.modules.PresenterModule;
import com.jagamypriera.thetruthnews.dagger.application.modules.ResourceModule;
import com.jagamypriera.thetruthnews.fragment.FragmentModule;

import dagger.Component;

/**
 * Created by jagamypriera on 11/7/17.
 */

@ApplicationScope
@Component( modules = {ApplicationModule.class, NetworkModule.class, ResourceModule.class, PresenterModule.class, DataStorageModule.class})
public interface ApplicationComponent {
    ActivityComponent addActivityModules(ActivityModule activityModule, WidgetModule widgetModule, FragmentModule fragmentModule);
}