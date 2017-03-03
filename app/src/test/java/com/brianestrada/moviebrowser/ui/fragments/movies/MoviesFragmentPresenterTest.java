package com.brianestrada.moviebrowser.ui.fragments.movies;

import com.brianestrada.moviebrowser.data.DataService;
import com.brianestrada.moviebrowser.models.Movie;
import com.brianestrada.moviebrowser.models.Movies;
import com.brianestrada.moviebrowser.models.Reviews;
import com.brianestrada.moviebrowser.models.SearchResults;
import com.brianestrada.moviebrowser.models.Videos;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public class MoviesFragmentPresenterTest {
    DataService dataService;

    MoviesFragmentView view;

    MoviesFragmentPresenterImpl presenter;

    @Before
    public void setUp() throws Exception {
        dataService = new MockDataService();

        view = new MockMoviesFragmentView();

        presenter = new MoviesFragmentPresenterImpl(dataService);

        presenter.attachView(view);
    }

    @Test
    public void shouldPass() {
        Assert.assertEquals(1, 1);
    }

    @Test
    public void testLoadMovie() {
        presenter.initialize();

        List<Movie> movieList = view.getMovies();

        Assert.assertNotNull(movieList);
    }

    @Test
    public void testLoadSortMethod() {
        presenter.initialize();

        String method = presenter.getSortMethod();

        Assert.assertEquals(method, "test");
    }

    private class MockMoviesFragmentView implements MoviesFragmentView {
        String sortMethod = "test";
        List<Movie> movies;

        @Override
        public String getSortMethod() {
            return sortMethod;
        }

        @Override
        public void setMovies(List<Movie> data) {
            this.movies = data;
        }

        @Override
        public List<Movie> getMovies() {
            return movies;
        }
    }

    private class MockDataService implements DataService {

        @Override
        public Single<Movies> getMovies(String sortType) {
            return Single.create(new SingleOnSubscribe<Movies>() {
                @Override
                public void subscribe(SingleEmitter<Movies> e) throws Exception {
                    Movies movies = new Movies(new ArrayList<Movie>());
                    e.onSuccess(movies);
                }
            });
        }

        @Override
        public Single<Movie> getMovie(int id) {
            return null;
        }

        @Override
        public Single<Reviews> getReviews(int id) {
            return null;
        }

        @Override
        public Single<Videos> getVideos(int id) {
            return null;
        }

        @Override
        public Single<SearchResults> searchMovies(String search) {
            return null;
        }

        @Override
        public Single<SearchResults> searchMovies(String search, int page) {
            return null;
        }

        @Override
        public Single<List<Movie>> getFavorites() {
            return null;
        }

        @Override
        public Movie getFavorite(int id) {
            return null;
        }

        @Override
        public void addFavorite(Movie data) {

        }

        @Override
        public boolean isFavorite(int id) {
            return false;
        }
    }
}