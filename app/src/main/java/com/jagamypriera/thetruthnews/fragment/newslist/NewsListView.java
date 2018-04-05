package com.jagamypriera.thetruthnews.fragment.newslist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ViewAnimator;

import com.jagamypriera.thetruthnews.Utils;
import com.jagamypriera.thetruthnews.fragment.FragmentChangeObservable;
import com.jagamypriera.thetruthnews.R;
import com.jagamypriera.thetruthnews.dagger.Injector;
import com.jagamypriera.thetruthnews.fragment.newsdetails.NewsDetailsView;
import com.jagamypriera.thetruthnews.fragment.newslist.adapter.NewsAdapter;
import com.jagamypriera.thetruthnews.fragment.newslist.adapter.NewsClickListener;
import com.jagamypriera.thetruthnews.fragment.newslist.model.NewsListResponseModel;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


public class NewsListView extends Fragment implements NewsListInterface, ViewTreeObserver.OnGlobalLayoutListener, NewsClickListener, SwipeRefreshLayout.OnRefreshListener {
    @Inject FragmentChangeObservable observer;
    @Inject FragmentManager manager;
    @Inject NewsListPresenter presenter;
    @Inject NewsDetailsView detailsNews;
    @BindView(R.id.animator)ViewAnimator animator;
    @BindView(R.id.loading)View loading;
    @BindView(R.id.empty)View empty;
    @BindView(R.id.list_news)RecyclerView newsList;
    @BindView(R.id.refresh)SwipeRefreshLayout refresh;
    private int page = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private NewsAdapter adapter=new NewsAdapter().setListener(this);
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_news,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Injector.getActivityComponent().inject(this);
        configList(newsList);
        ViewTreeObserver vto = newsList.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(this);
        presenter.attachView(this);
        getNews(page);
    }
    @Override
    public void onGlobalLayout() {
        newsList.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        int width = newsList.getMeasuredWidth();
        int height=width/16*9;
        adapter.setImageSize(height,width);
    }
    public void configList(RecyclerView mRecycler) {
        mRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(manager);
        mRecycler.getItemAnimator().setChangeDuration(0);
        mRecycler.setAdapter(adapter);
        mRecycler.addOnScrollListener(paging);
        refresh.setOnRefreshListener(this);
    }
    @Override
    public void showLoading() {
        if(adapter.getItemCount()==0)animator.setDisplayedChild(animator.indexOfChild(loading));
        else adapter.showLoading();
    }

    @Override
    public void hideLoading() {
        refresh.setRefreshing(false);
        animator.setDisplayedChild(animator.indexOfChild(adapter.getItemCount() > 0 ? newsList : empty));
    }

    @Override
    public void onGetNewsSuccess(ArrayList<NewsListResponseModel.News> news) {
        if (page == 1) adapter.replace(news);
        if (page > 1 && news.size() > 0) adapter.update(news);
        isLoading = false;
        if (news.size() < Utils.MAX_NEWS) isLastPage = true;
    }

    @Override
    public void onGetNewsFail(String error) {
        Timber.d(error);
        isLoading = false;
        if (page > 1) page--;
    }

    @Override
    public void onClick(NewsListResponseModel.News news, View view, int position) {
        Timber.d("clicked at "+news.title+" "+position+" id "+view.getId()+" root id "+R.id.news_item_root+" is favorite "+news.favorite);
        switch (view.getId()){
            case R.id.favorite: presenter.setFavorite(news.url, news.favorite);break;
            case R.id.news_item_root:observer.setFragment(detailsNews.setData(news.url));break;
        }
    }
    private RecyclerView.OnScrollListener paging = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
            if (!isLoading && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= Utils.MAX_NEWS) {
                    page++;
                    getNews(page);
                }
            }
        }
    };
    private void getNews(int page){
        isLoading = true;
        presenter.getNews(page);
    }

    @Override
    public void onRefresh() {
        refresh();
    }
    private void refresh(){
        page = 1;
        isLastPage = false;
        getNews(page);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
