package com.jagamypriera.thetruthnews.presenter;

public interface PresenterInterface <V> {
    void attachView(V view);
    void detachView();
}
