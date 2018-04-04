package com.jagamypriera.thetruthnews.fragment.newslist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jagamypriera.thetruthnews.api.ApiService;
import com.jagamypriera.thetruthnews.fragment.newslist.model.NewsListHeadlinesRequestModel;
import com.jagamypriera.thetruthnews.fragment.newslist.model.NewsListRequestModel;
import com.jagamypriera.thetruthnews.fragment.newslist.model.NewsListResponseModel;
import com.jagamypriera.thetruthnews.presenter.PresenterInterface;

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class NewsListPresenter implements PresenterInterface<NewsListInterface> {
    private ApiService api;
    private NewsListInterface view;
    private CompositeDisposable disposable=new CompositeDisposable();
    public NewsListPresenter(ApiService api) {
        this.api = api;
    }
    public void getNews(int page){
        disposable.clear();
        disposable.add(disposableNews(page)) ;
    }
    @Override
    public void attachView(NewsListInterface view) {
        this.view=view;
    }

    @Override
    public void detachView() {
        this.view=null;
    }
    private String country(int index){
        ArrayList<String>countries=new ArrayList<>();
        countries.add("us");
        return countries.get(index);
    }
    private DisposableObserver disposableNews(int page){
        view.showLoading();
        Map<String,String> news=new NewsListRequestModel().setPage(page).toMap();
        Map<String,String> headlines=new NewsListHeadlinesRequestModel().setCountry("us").toMap();
        return Observable.zip(api.getAllNews(news), api.getHeadlines(headlines), new BiFunction<NewsListResponseModel, NewsListResponseModel, NewsListResponseModel>() {
            @Override
            public NewsListResponseModel apply(NewsListResponseModel news, NewsListResponseModel headlines) {
                NewsListResponseModel.News newHeadlines=new NewsListResponseModel.News();
                newHeadlines.headlines=headlines.newsList;
                news.newsList.add(1,newHeadlines);
                return news;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<NewsListResponseModel>() {
                    @Override
                    public void onNext(NewsListResponseModel value) {
                        view.onGetNewsSuccess(value.newsList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onGetNewsFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        view.hideLoading();
                    }
                });
    }
}
