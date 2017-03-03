package com.brianestrada.moviebrowser.data;

import com.brianestrada.moviebrowser.models.Movie;
import com.brianestrada.moviebrowser.models.Movies;
import com.brianestrada.moviebrowser.models.Reviews;
import com.brianestrada.moviebrowser.models.SearchResults;
import com.brianestrada.moviebrowser.models.Videos;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDatabaseTest {

    private MovieDatabaseService movieDatabaseService;

    private Movies movies;
    private Movie movie;
    private Reviews reviews;
    private Videos videos;
    private SearchResults searchResults;

    @Before
    public void setup() {

        // We need to create our Retrofit Service for our Movie Db
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MMM-dd").create();

        Retrofit service = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        movieDatabaseService = service.create(MovieDatabaseService.class);
    }

    @Test
    public void shouldPass() {
        Assert.assertEquals(1, 1);
    }

    @Test
    public void getMoviesTest() {
        movies = null;

        movieDatabaseService
                .getMovies(DataService.SORT_POPULAR, MovieDatabaseService.API_KEY)
                .subscribe(new SingleObserver<Movies>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Movies data) {
                        movies = data;
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });

        Assert.assertNotNull(movies);
    }

    @Test
    public void getMovieTest() {
        movie = null;

        movieDatabaseService
                .getMovie(205, MovieDatabaseService.API_KEY)
                .subscribe(new SingleObserver<Movie>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Movie data) {
                        movie = data;
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });

        Assert.assertNotNull(movie);
    }

    @Test
    public void getReviewsTest() {
        reviews = null;

        movieDatabaseService
                .getReviews(205, MovieDatabaseService.API_KEY)
                .subscribe(new SingleObserver<Reviews>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Reviews data) {
                        reviews = data;
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });

        Assert.assertNotNull(reviews);
    }

    @Test
    public void getVideosTest() {
        videos = null;

        movieDatabaseService
                .getVideos(205, MovieDatabaseService.API_KEY)
                .subscribe(new SingleObserver<Videos>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Videos data) {
                        videos = data;
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });

        Assert.assertNotNull(videos);
    }

    @Test
    public void getSearchResultsTest() {
        searchResults = null;

        movieDatabaseService
                .searchMovies("Star Wars", MovieDatabaseService.API_KEY)
                .subscribe(new SingleObserver<SearchResults>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(SearchResults data) {

                        searchResults = data;
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });

        Assert.assertNotNull(searchResults);
    }
}