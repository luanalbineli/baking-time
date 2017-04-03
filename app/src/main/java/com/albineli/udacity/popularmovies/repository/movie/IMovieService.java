package com.albineli.udacity.popularmovies.repository.movie;


import com.albineli.udacity.popularmovies.model.MovieModel;
import com.albineli.udacity.popularmovies.repository.ArrayRequestAPI;
import com.albineli.udacity.popularmovies.repository.IServiceBase;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IMovieService {

    @GET("movie/top_rated?api_key=" + IServiceBase.API_KEY)
    Observable<ArrayRequestAPI<List<MovieModel>>> getTopRatedList(@Query("page") Integer pageNumber);

    @GET("movie/popular?api_key=" + IServiceBase.API_KEY)
    Observable<ArrayRequestAPI<List<MovieModel>>> getPopularList(@Query("page") Integer pageNumber);
}
