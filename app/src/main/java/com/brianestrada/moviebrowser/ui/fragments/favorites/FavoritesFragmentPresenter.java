package com.brianestrada.moviebrowser.ui.fragments.favorites;


public interface FavoritesFragmentPresenter {
    void attachView(FavoritesFragmentView view);

    void initialize();

    void loadFavorites();

    void passFavoritesToView();
}
