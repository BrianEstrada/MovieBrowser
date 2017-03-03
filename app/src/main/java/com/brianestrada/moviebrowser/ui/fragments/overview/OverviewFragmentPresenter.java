package com.brianestrada.moviebrowser.ui.fragments.overview;


import com.brianestrada.moviebrowser.models.Movie;
import com.brianestrada.moviebrowser.models.State;

public interface OverviewFragmentPresenter {
    void attachView(OverviewFragmentView view);

    void initialize();

    void loadMovieID();

    void loadMovie();

    void passOverviewToView();

    void setState(State state);

    State getState();

    Movie getMovie();
}
