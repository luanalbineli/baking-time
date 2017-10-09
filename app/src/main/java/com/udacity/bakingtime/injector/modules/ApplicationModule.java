package com.udacity.bakingtime.injector.modules;

import com.google.gson.Gson;
import com.udacity.bakingtime.BakingTimeApplication;
import com.udacity.bakingtime.BuildConfig;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApplicationModule {
    //private static final String BASE_URL = "http://go.udacity.com/";
    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";
    private final BakingTimeApplication mPopularMovieApplication;

    public ApplicationModule(BakingTimeApplication popularMovieApplication) {
        this.mPopularMovieApplication = popularMovieApplication;
    }

    @Provides
    @Singleton
    BakingTimeApplication providePopularMovieApplicationContext() {
        return mPopularMovieApplication;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new Gson();
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

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(httpClient.build())
                .build();
    }
}
