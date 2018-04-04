package com.jagamypriera.thetruthnews.dagger.activity.modules;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.jagamypriera.thetruthnews.dagger.activity.ActivityScope;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jagamypriera on 11/5/17.
 */
@Module
public class WidgetModule {
    @Provides
    @ActivityScope
    ProgressDialog dialog(AppCompatActivity context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setCancelable(true);
        return progressDialog;
    }
}
