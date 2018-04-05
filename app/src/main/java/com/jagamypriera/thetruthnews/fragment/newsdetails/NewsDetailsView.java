package com.jagamypriera.thetruthnews.fragment.newsdetails;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ViewAnimator;

import com.jagamypriera.thetruthnews.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewsDetailsView extends Fragment  {
    @BindView(R.id.animator)ViewAnimator animator;
    @BindView(R.id.loading)View loading;
    @BindView(R.id.web_view)WebView webView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_news_detail,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assert getArguments() != null;
        String url=getArguments().getString("data");
        webView.getSettings().setJavaScriptEnabled(false);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                super.onPageStarted(view, url, favicon);
                animator.setDisplayedChild(animator.indexOfChild(loading));
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                super.onPageFinished(view, url);
                animator.setDisplayedChild(animator.indexOfChild(webView));
            }
        });
        webView.loadUrl(url);
    }
    public NewsDetailsView setData(String url){
        Bundle bundle=new Bundle();
        bundle.putString("data",url);
        setArguments(bundle);
        return this;
    }
}
