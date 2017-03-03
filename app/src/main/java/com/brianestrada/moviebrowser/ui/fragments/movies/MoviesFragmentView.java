package com.brianestrada.moviebrowser.ui.fragments.movies;


import com.brianestrada.moviebrowser.models.Movie;

import java.util.List;

interface MoviesFragmentView {
    String getSortMethod();

    void setMovies(List<Movie> data);

    List<Movie> getMovies();
}
