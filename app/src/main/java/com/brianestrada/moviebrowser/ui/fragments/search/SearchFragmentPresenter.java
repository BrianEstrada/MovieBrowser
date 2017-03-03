package com.brianestrada.moviebrowser.ui.fragments.search;


import com.brianestrada.moviebrowser.models.State;

public interface SearchFragmentPresenter {
    void attachView(SearchFragmentView view);

    void initialized();

    void searchForMovie(String query);

    void showResults();

    State getState();

    void setState(State state);
}
