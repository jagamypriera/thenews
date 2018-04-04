package com.jagamypriera.thetruthnews;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.ButterKnife;

/**
 * Created by jagamypriera on 9/25/17.
 */

public class Resources extends View{
    /*@BindColor(R.color.greeny_blue)public int greeny_blue;
    @BindColor(R.color.white)public int white;
    @BindColor(R.color.white_50)public int white_50;
    @BindDrawable(R.drawable.ic_bounds)public Drawable bounds;
    @BindDrawable(R.drawable.ic_statistik)public Drawable statistic;*/
    public Resources(Context context) {
        super(context);
        ButterKnife.bind(this);
    }
    public  Typeface getOpenSansSemiBold() {
        return Typeface.createFromAsset(getContext().getAssets(), "font/OpenSans-Semibold.ttf");
    }

    public  Typeface getOpenSansReguler() {
        return Typeface.createFromAsset(getContext().getAssets(), "font/OpenSans-Regular.ttf");
    }

    public  Typeface getOpenSansBold() {
        return Typeface.createFromAsset(getContext().getAssets(), "font/OpenSans-Bold.ttf");
    }

}
