package com.jagamypriera.thetruthnews.fragment.newslist.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.jagamypriera.thetruthnews.R;
import com.jagamypriera.thetruthnews.fragment.newslist.model.NewsListResponseModel;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<NewsListResponseModel.News>news=new ArrayList<>();
    private Context context;
    private int HEADLINES=1, CONTENT=0, LOADING=-1;
    private int height;
    private int width;
    private NewsClickListener listener;
    public NewsAdapter setListener(NewsClickListener listener){
        this.listener=listener;
        return this;
    }
    @Override
    public int getItemViewType(int position) {
        return news.get(position).loading?LOADING:!news.get(position).headlines.isEmpty()?HEADLINES:CONTENT;
    }

    public void replace(ArrayList<NewsListResponseModel.News>news){
        this.news=news;
        notifyDataSetChanged();
    }
    public void update(ArrayList<NewsListResponseModel.News>news){
        if(this.news.get(this.news.size()-1).loading)this.news.remove(this.news.size()-1);
        this.news.addAll(news);
        notifyDataSetChanged();
    }
    public void showLoading(){
        NewsListResponseModel.News loading=new NewsListResponseModel.News();
        loading.loading=true;
        this.news.add(loading);
        notifyItemInserted(this.news.size()-1);
    }
    @Override
    public NewsAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context=parent.getContext();
        return new NewsAdapter.Holder(LayoutInflater.from(parent.getContext())
                .inflate(viewType==LOADING?R.layout.item_loading:viewType==HEADLINES?R.layout.item_news_headline:R.layout.item_news, parent, false));
    }
    @SuppressWarnings("all")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vh, int position) {
        Holder holder = (Holder) vh;
        if(vh.getItemViewType()==HEADLINES){
            holder.setUpHeadlines(news.get(position).headlines);
            holder.headlinesList.scrollToPosition(news.get(position).headlinePosition);
        }
        else if(vh.getItemViewType()==CONTENT){
            holder.title.setText(news.get(position).title);
            holder.setImage(news.get(position).image);
            holder.favoritImage.setImageDrawable(news.get(position).favorite?holder.favorite:holder.unfavorite);
        }
    }
    @Override
    public int getItemCount() {
        return news.size();
    }

    public void setImageSize(int height, int width){
        this.width=width;
        this.height=height;
    }
    public void toggleFavorite(int position){
        news.get(position).favorite=!news.get(position).favorite;
        notifyItemChanged(position);
    }
    @SuppressWarnings("all")
    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener, NewsClickListener {
        @Nullable @BindView(R.id.title) public TextView title;
        @Nullable @BindView(R.id.headlines_title) public TextView headlinesTitle;
        @Nullable @BindView(R.id.headlines_list) public RecyclerView headlinesList;
        @Nullable @BindView(R.id.image) public ImageView image;
        @Nullable @BindView(R.id.animator) public ViewAnimator animator;
        @Nullable @BindView(R.id.loading) public View loading;
        @Nullable @BindView(R.id.news_item_root) public View newsItemRoot;
        @BindColor(R.color.colorAccent)int accent;
        @BindDrawable(R.drawable.ic_photo_black_24px)Drawable launcher;
        @BindDrawable(R.drawable.ic_favorite_black_24px)Drawable favorite;
        @BindDrawable(R.drawable.ic_favorite_border_black_24px)Drawable unfavorite;
        @Nullable @BindView(R.id.favorite)ImageView favoritImage;
        private SnapHelper snapHelper = new LinearSnapHelper();
        private NewsAdapter adapter=new NewsAdapter().setListener(this);
        Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            if(favoritImage!=null)favoritImage.setOnClickListener(this);
            if(newsItemRoot!=null)newsItemRoot.setOnClickListener(this);
        }
        public  int randInt(int min, int max) {
            Random rand = new Random();
            return rand.nextInt((max - min) + 1) + min;
        }
        public void setImage(String url){
            animator.setDisplayedChild(animator.indexOfChild(loading));
            if(width!=0||height!=0){
                animator.setLayoutParams(new LinearLayout.LayoutParams(width, height));
                animator.requestLayout();
            }
            //url=randInt(0,10)>5?"":url;
            Glide.with(context).load(url).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    image.setScaleType(ImageView.ScaleType.FIT_XY);
                    image.setImageBitmap(resource);
                    animator.setDisplayedChild(animator.indexOfChild(image));
                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    super.onLoadFailed(e, errorDrawable);
                    image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    image.setBackgroundColor(accent);
                    image.setImageDrawable(launcher);
                    animator.setDisplayedChild(animator.indexOfChild(image));
                }
            });
        }
        void setUpHeadlines(ArrayList<NewsListResponseModel.News> headlines){
            headlinesTitle.setText(Html.fromHtml(context.getString(R.string.head_lines,headlines.get(0).country)));
            adapter.setImageSize(height,width);
            headlinesList.setAdapter(adapter);
            configList(headlinesList);
            adapter.replace(headlines);
            headlinesList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    LinearLayoutManager manager= (LinearLayoutManager) recyclerView.getLayoutManager();
                    if(newState==RecyclerView.SCROLL_STATE_IDLE)
                        news.get(getAdapterPosition())
                                .headlinePosition=manager.findFirstCompletelyVisibleItemPosition();
                }
            });
        }
        void configList(RecyclerView mRecycler) {
            mRecycler.setHasFixedSize(true);
            RecyclerView.LayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            mRecycler.setLayoutManager(manager);
            mRecycler.getItemAnimator().setChangeDuration(0);
            snapHelper.attachToRecyclerView(mRecycler);
            mRecycler.setOnFlingListener(null);
            mRecycler.setAdapter(adapter);
        }

        @Override
        public void onClick(View view) {
            if(view.getId()==R.id.favorite) toggleFavorite(getAdapterPosition());
            listener.onClick(news.get(getAdapterPosition()),view, getAdapterPosition());
        }

        @Override
        public void onClick(NewsListResponseModel.News aNews, View view, int position) {
            listener.onClick(aNews,view, position);
        }
    }
}