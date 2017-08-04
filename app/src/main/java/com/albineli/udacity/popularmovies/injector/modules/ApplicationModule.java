package com.albineli.udacity.popularmovies.injector.modules;

import com.albineli.udacity.popularmovies.BuildConfig;
import com.albineli.udacity.popularmovies.PopularMovieApplication;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApplicationModule {
    private static final String BASE_URL = "http://go.udacity.com/";
    private final PopularMovieApplication mPopularMovieApplication;

    public ApplicationModule(PopularMovieApplication popularMovieApplication) {
        this.mPopularMovieApplication = popularMovieApplication;
    }

    @Provides
    @Singleton
    PopularMovieApplication providePopularMovieApplicationContext() {
        return mPopularMovieApplication;
    }

    @Provides
    @Singleton
    Retrofit providesRetrofit() {
        // Add a log interceptor
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
        }

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder requestBuilder = chain.request().newBuilder();
                HttpUrl.Builder urlBuilder = chain.request().url().newBuilder();
                urlBuilder.addQueryParameter("api_key", BuildConfig.API_KEY);
                requestBuilder.url(urlBuilder.build());
                return chain.proceed(requestBuilder.build());
            }
        });

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(httpClient.build())
                .build();
    }
}
