package com.udacity.bakingtime;

import com.google.gson.Gson;
import com.udacity.bakingtime.repository.RecipeRepository;

import org.mockito.Mockito;

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
public class MockApplicationModule {
    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";
    private final BakingTimeApplication mPopularMovieApplication;

    public MockApplicationModule(BakingTimeApplication popularMovieApplication) {
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
        return Mockito.mock(Retrofit.class);
    }

    @Provides
    @Singleton
    RecipeRepository provideRepository() {
        return Mockito.mock(RecipeRepository.class);
    }
}
