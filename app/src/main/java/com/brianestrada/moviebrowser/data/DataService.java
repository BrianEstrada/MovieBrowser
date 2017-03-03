package com.brianestrada.moviebrowser.data;


import com.brianestrada.moviebrowser.models.Movie;
import com.brianestrada.moviebrowser.models.Movies;
import com.brianestrada.moviebrowser.models.Reviews;
import com.brianestrada.moviebrowser.models.SearchResults;
import com.brianestrada.moviebrowser.models.Videos;

import java.util.List;

import io.reactivex.Single;

public interface DataService {
    String SORT_POPULAR = "popular";
    String SORT_TOP_RATED = "top_rated";

    Single<Movies> getMovies(String sortType);

    Single<Movie> getMovie(int id);

    Single<Reviews> getReviews(int id);

    Single<Videos> getVideos(int id);

    Single<SearchResults> searchMovies(String search);

    Single<SearchResults> searchMovies(String search, int page);

    Single<List<Movie>> getFavorites();

    Movie getFavorite(int id);

    void addFavorite(Movie data);

    boolean isFavorite(int id);
}
