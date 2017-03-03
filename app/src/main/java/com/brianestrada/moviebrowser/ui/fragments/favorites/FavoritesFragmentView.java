package com.brianestrada.moviebrowser.ui.fragments.favorites;


import com.brianestrada.moviebrowser.models.Movie;

import java.util.List;

public interface FavoritesFragmentView {
    //Setters
    void setFavorites(List<Movie> favorites);

    //Getters
    List<Movie> getFavorites();

    //View

    void showEmptyView(boolean status);

}
