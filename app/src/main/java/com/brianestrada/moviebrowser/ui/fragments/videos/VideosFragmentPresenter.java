package com.brianestrada.moviebrowser.ui.fragments.videos;


import com.brianestrada.moviebrowser.models.Videos;
import com.brianestrada.moviebrowser.models.State;

public interface VideosFragmentPresenter {
    void attachView(VideosFragmentView view);

    void initialize();

    //Loaders

    void loadMovieID();

    void loadVideos();

    //Setters
    void setState(State state);

    //Getters

    State getState();

    Videos getVideos();

    //Misc

    void passVideosToView();
}
