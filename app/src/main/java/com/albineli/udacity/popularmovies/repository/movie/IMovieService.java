package com.albineli.udacity.popularmovies.repository.movie;


import com.albineli.udacity.popularmovies.model.MovieModel;
import com.albineli.udacity.popularmovies.model.MovieReviewModel;
import com.albineli.udacity.popularmovies.model.MovieTrailerModel;
import com.albineli.udacity.popularmovies.repository.ArrayRequestAPI;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IMovieService {
    @GET("movie/top_rated")
    Observable<ArrayRequestAPI<MovieModel>> getTopRatedList(@Query("page") Integer pageNumber);

    @GET("movie/popular")
    Observable<ArrayRequestAPI<MovieModel>> getPopularList(@Query("page") Integer pageNumber);

    @GET("movie/{movieId}/reviews")
    Observable<ArrayRequestAPI<MovieReviewModel>> getReviewsByMovieId(@Path("movieId") int movieId, @Query("page") Integer pageNumber);

    @GET("movie/{movieId}/reviews")
    Observable<ArrayRequestAPI<MovieTrailerModel>> getTrailersByMovieId(@Path("movieId") int movieId);
}
