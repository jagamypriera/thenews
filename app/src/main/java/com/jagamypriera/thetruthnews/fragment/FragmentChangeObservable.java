package com.jagamypriera.thetruthnews.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by jagamypriera on 19/12/17.
 */

public class FragmentChangeObservable {
    private FragmentManager manager;
    public FragmentChangeObservable clearHistory(){
        manager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        return this;
    }
    public FragmentChangeObservable(FragmentManager manager) { this.manager = manager; }
    private PublishSubject<Fragment> subject = PublishSubject.create();
    public void setFragment(Fragment fragment) {
        subject.onNext(fragment);
    }
    public Observable<Fragment> getFragment() {
        return subject;
    }
}
