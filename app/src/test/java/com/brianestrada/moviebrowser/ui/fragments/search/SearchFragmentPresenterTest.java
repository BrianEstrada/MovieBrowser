package com.brianestrada.moviebrowser.ui.fragments.search;

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

public class SearchFragmentPresenterTest {
    DataService dataService;

    SearchFragmentView view;

    SearchFragmentPresenterImpl presenter;

    @Before
    public void setUp() throws Exception {
        dataService = new MockDataService();

        view = new MockSearchFragmentView();

        presenter = new SearchFragmentPresenterImpl(dataService);

        presenter.attachView(view);
    }

    @Test
    public void shouldPass() {
        Assert.assertEquals(1, 1);
    }

    @Test
    public void testSearchForMovie() {
        String testQuery = "Test";

        presenter.searchForMovie(testQuery);

        List<Movie> results = view.getResults();

        //Test if our result is null

        Assert.assertNotNull(results);

        //Test the result size

        int size = results.size();

        Assert.assertEquals(size, 10);

        //Test our title

        Movie movie = results.get(0);

        Assert.assertEquals(movie.getTitle(), testQuery);
    }

    private class MockSearchFragmentView implements SearchFragmentView {
        List<Movie> movies;

        @Override
        public void setResults(List<Movie> movies) {
            this.movies = movies;
        }

        @Override
        public List<Movie> getResults() {
            return movies;
        }
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
        public Single<SearchResults> searchMovies(final String search) {
            return Single.create(new SingleOnSubscribe<SearchResults>() {
                @Override
                public void subscribe(SingleEmitter<SearchResults> e) throws Exception {
                    SearchResults searchResults = new SearchResults();

                    List<Movie> movieList = new ArrayList<Movie>();

                    for (int i = 0; i < 10; i++) {
                        Movie movie = new Movie();

                        movie.setTitle(search);

                        movieList.add(movie);
                    }

                    searchResults.setResults(movieList);

                    e.onSuccess(searchResults);
                }
            });
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