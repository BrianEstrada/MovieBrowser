package com.brianestrada.moviebrowser.ui.fragments.search;


import com.brianestrada.moviebrowser.data.DataService;
import com.brianestrada.moviebrowser.models.Movie;
import com.brianestrada.moviebrowser.models.SearchResults;
import com.brianestrada.moviebrowser.models.State;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

public class SearchFragmentPresenterImpl implements SearchFragmentPresenter {
    private SearchResults searchResults;
    private boolean initialized;

    private DataService dataService;

    private SearchFragmentView view;
    private Disposable disposable;

    public SearchFragmentPresenterImpl(DataService dataService) {
        this.dataService = dataService;
    }


    @Override
    public void attachView(SearchFragmentView view) {
        this.view = view;
    }

    @Override
    public void initialized() {
        if (!initialized) {
            initialized = !initialized;
        } else {
            showResults();
        }
    }

    @Override
    public void searchForMovie(String query) {
        if (disposable != null) {
            Timber.d("Disposing Search Results");

            disposable.dispose();
        }


        disposable = dataService.searchMovies(query)
                .subscribe(new Consumer<SearchResults>() {
                    @Override
                    public void accept(SearchResults data) throws Exception {
                        searchResults = data;

                        showResults();
                    }


                });
    }

    @Override
    public void showResults() {
        if (searchResults != null && searchResults.getResults() != null) {
            List<Movie> movies = searchResults.getResults();

            view.setResults(movies);
        }
    }

    @Override
    public State getState() {
        State state = new State();

        state.setInitialized(initialized);

        state.setSearchResults(searchResults);

        return state;
    }

    @Override
    public void setState(State state) {
        initialized = state.isInitialized();

        searchResults = state.getSearchResults();
    }
}
