package com.brianestrada.moviebrowser.ui.fragments.favorites;

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

public class FavoritesFragmentPresenterTest {
    private FavoritesFragmentView view;

    private DataService dataService;

    private FavoritesFragmentPresenterImpl presenter;

    @Before
    public void setUp() {
        view = new MockFavoritesFragmentView();

        dataService = new MockDataService();

        presenter = new FavoritesFragmentPresenterImpl(dataService);

        presenter.attachView(view);
    }

    @Test
    public void shouldPass() {
        Assert.assertEquals(1, 1);
    }

    @Test
    public void testLoadFavorites() {
        presenter.loadFavorites();

        List<Movie> result = view.getFavorites();

        Assert.assertNotNull(result);

    }

    private class MockDataService implements DataService {

        @Override
        public Single<Movies> getMovies(String sortType) {
            return null;
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
            return Single.create(new SingleOnSubscribe<List<Movie>>() {
                @Override
                public void subscribe(SingleEmitter<List<Movie>> e) throws Exception {
                    e.onSuccess(new ArrayList<Movie>());
                }
            });
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

    private class MockFavoritesFragmentView implements FavoritesFragmentView {
        boolean show;
        List<Movie> movies;

        @Override
        public void setFavorites(List<Movie> favorites) {
            this.movies = favorites;
        }

        @Override
        public List<Movie> getFavorites() {
            return movies;
        }

        @Override
        public void showEmptyView(boolean status) {
            show = status;
        }
    }

}