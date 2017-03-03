package com.brianestrada.moviebrowser.data;


import com.brianestrada.moviebrowser.BuildConfig;
import com.brianestrada.moviebrowser.models.Movie;
import com.brianestrada.moviebrowser.models.Movies;
import com.brianestrada.moviebrowser.models.Reviews;
import com.brianestrada.moviebrowser.models.SearchResults;
import com.brianestrada.moviebrowser.models.Videos;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDatabaseService {
    String API_KEY = BuildConfig.MY_API_KEY;

    @GET("movie/{sort}")
    Single<Movies> getMovies(@Path("sort") String sortType, @Query("api_key") String api_key);

    @GET("movie/{id}")
    Single<Movie> getMovie(@Path("id") int id, @Query("api_key") String api_key);

    @GET("movie/{id}/reviews")
    Single<Reviews> getReviews(@Path("id") int id, @Query("api_key") String api_key);

    @GET("movie/{id}/videos")
    Single<Videos> getVideos(@Path("id") int id, @Query("api_key") String api_key);

    @GET("search/movie")
    Single<SearchResults> searchMovies(@Query("query") String search, @Query("api_key") String api_key);

    @GET("search/movie")
    Single<SearchResults> searchMovies(@Query("query") String search, @Query("page") int page, @Query("api_key") String api_key);
}
