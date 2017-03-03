package com.brianestrada.moviebrowser.ui.viewmovie;


public interface ViewMovieActivityView {
    //Setters

    void setPosterImage(String url);

    void setMovieTitle(String title);

    void setRating(float rating);

    void setReleaseYear(String releaseYear);

    void setToolbarTitle(String title);

    void setFavorite(boolean isFavorite);

    //Getters

    String getMovieTitle();

    float getRating();

    String getReleaseYear();

    int getMovieID();

//misc

    void selectPage(int page);
}
