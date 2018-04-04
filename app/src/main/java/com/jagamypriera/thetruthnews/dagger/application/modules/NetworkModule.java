package com.jagamypriera.thetruthnews.dagger.application.modules;

import android.content.Context;

import com.jagamypriera.thetruthnews.api.ApiEndPoint;
import com.jagamypriera.thetruthnews.api.ApiService;
import com.jagamypriera.thetruthnews.dagger.application.ApplicationScope;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by jagamypriera on 11/3/17.
 */

@Module
public class NetworkModule {
    @Provides
    @ApplicationScope
    HttpUrl url() {
        return HttpUrl.parse(ApiEndPoint.BASE_URL);
    }

    @Provides
    @ApplicationScope
    Retrofit retrofit(HttpUrl httpUrl, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(String.valueOf(httpUrl))
                .client(okHttpClient)
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @ApplicationScope
    ApiService api(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Provides
    @ApplicationScope
    OkHttpClient okhttp(final Context context) {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        HttpUrl originalHttpUrl = original.url();

                        HttpUrl url = originalHttpUrl.newBuilder()
                                .addQueryParameter("apiKey", "a850b7a9d2cc473a8e74f6e1cc064fad")
                                .build();

                        // Request customization: add request headers
                        Request.Builder requestBuilder = original.newBuilder()
                                .url(url);

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                })
                .build();
    }

}
