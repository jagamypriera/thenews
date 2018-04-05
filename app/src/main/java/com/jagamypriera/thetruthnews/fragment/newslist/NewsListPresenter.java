package com.jagamypriera.thetruthnews.fragment.newslist;

import com.jagamypriera.thetruthnews.Constants;
import com.jagamypriera.thetruthnews.api.ApiService;
import com.jagamypriera.thetruthnews.fragment.newslist.model.NewsListFavoriteRealmModel;
import com.jagamypriera.thetruthnews.fragment.newslist.model.NewsListHeadlinesRequestModel;
import com.jagamypriera.thetruthnews.fragment.newslist.model.NewsListRequestModel;
import com.jagamypriera.thetruthnews.fragment.newslist.model.NewsListResponseModel;
import com.jagamypriera.thetruthnews.presenter.PresenterInterface;

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;
import timber.log.Timber;

public class NewsListPresenter implements PresenterInterface<NewsListInterface> {
    private ApiService api;
    private NewsListInterface view;
    private CompositeDisposable disposable;
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
        disposable=new CompositeDisposable();
    }

    @Override
    public void detachView() {
        this.view=null;
        if(disposable.isDisposed())return;
        disposable.dispose();
    }
    private String country(int index){
        ArrayList<String>countries=new ArrayList<>();
        countries.add("us");
        return countries.get(index);
    }
    private DisposableObserver disposableNews(int page){
        final String country=page%2==0? Constants.UK: Constants.USA;
        view.showLoading();
        Map<String,String> news=new NewsListRequestModel().setPage(page).toMap();
        Map<String,String> headlines=new NewsListHeadlinesRequestModel().setCountry(country).toMap();
        return Observable.zip(api.getAllNews(news), api.getHeadlines(headlines), new BiFunction<NewsListResponseModel, NewsListResponseModel, NewsListResponseModel>() {
            @Override
            public NewsListResponseModel apply(NewsListResponseModel news, NewsListResponseModel headlines) {
                for (int i=0;i<news.newsList.size();i++)
                    news.newsList.get(i).favorite=isFavorite(news.newsList.get(i).url);
                for (int i=0;i<headlines.newsList.size();i++)
                    headlines.newsList.get(i).favorite=isFavorite(headlines.newsList.get(i).url);
                if(headlines.newsList.size()==0)return news;
                NewsListResponseModel.News newHeadlines=new NewsListResponseModel.News();
                newHeadlines.headlines=headlines.newsList;
                newHeadlines.headlines.get(0).country=country;
                news.newsList.add(news.newsList.size()< Constants.MAX_NEWS?news.newsList.size()-1: Constants.HEADLINE_POSITION,newHeadlines);
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
    public void setFavorite(final String url, final boolean favorite){
        Timber.d("clicked favorite "+favorite);
        Realm realm= Realm.getDefaultInstance();
        realm.refresh();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                NewsListFavoriteRealmModel savedUrl=realm.where(NewsListFavoriteRealmModel.class)
                        .equalTo(NewsListFavoriteRealmModel.URL_FIELD, url).findFirst();
                if(!favorite && savedUrl!=null)
                    savedUrl.deleteFromRealm();
                else if(favorite && savedUrl==null){
                    NewsListFavoriteRealmModel model=new NewsListFavoriteRealmModel();
                    model.setUrl(url);
                    realm.insertOrUpdate(model);
                }

                RealmResults<NewsListFavoriteRealmModel> favorites=realm.where(NewsListFavoriteRealmModel.class).findAll();
                if(favorites==null||favorites.size()==0)return;
                for (int i=0;i<favorites.size();i++)Timber.d(favorites.get(i).url);
              //Timber.d("clicked");
            }
        });
    }
    private boolean isFavorite(String url){
        Realm realm=Realm.getDefaultInstance();
        realm.refresh();
        Timber.d("requested url "+url);
        NewsListFavoriteRealmModel savedUrl=realm.where(NewsListFavoriteRealmModel.class).equalTo(NewsListFavoriteRealmModel.URL_FIELD, url).findFirst();
        RealmResults<NewsListFavoriteRealmModel> favorites=realm.where(NewsListFavoriteRealmModel.class).findAll();
        if(favorites!=null)
        for (int i=0;i<favorites.size();i++)Timber.d("saved url "+favorites.get(i).url);
        return savedUrl!=null;
    }
}
