package com.brianestrada.moviebrowser.ui.viewmovie;


import com.brianestrada.moviebrowser.models.State;

public interface ViewMoviePresenter {
    void attachView(ViewMovieActivityView view);

    void initialize();

    //Loaders

    void loadMovieID();

    void loadMovie();

    //Setters

    void setState(State movieState);

    void setMoviePoster();

    void setMovieTitle();

    void setMovieReleaseDate();

    void setMovieRating();

    void setFavorite();

    void setFragment(int i);

    //Actions
    void toggleFavorite();

    //Getters
    State getState();

    //Misc

    void showFragment();

    void passDataToView();
}
