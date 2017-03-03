package com.brianestrada.moviebrowser.ui.fragments.movies;


interface MoviesFragmentPresenter {
    void initialize();

    void attachView(MoviesFragmentView view);

    void loadMovies();

    void loadSortMethod();

    String getSortMethod();
}
