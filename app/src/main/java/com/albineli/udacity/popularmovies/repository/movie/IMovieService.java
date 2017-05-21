package com.albineli.udacity.popularmovies.repository.movie;


import com.albineli.udacity.popularmovies.model.MovieModel;
import com.albineli.udacity.popularmovies.repository.ArrayRequestAPI;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IMovieService {
    @GET("movie/top_rated")
    Observable<ArrayRequestAPI<List<MovieModel>>> getTopRatedList(@Query("page") Integer pageNumber);

    @GET("movie/popular")
    Observable<ArrayRequestAPI<List<MovieModel>>> getPopularList(@Query("page") Integer pageNumber);
}
