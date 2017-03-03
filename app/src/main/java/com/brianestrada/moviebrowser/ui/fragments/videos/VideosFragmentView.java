package com.brianestrada.moviebrowser.ui.fragments.videos;


import com.brianestrada.moviebrowser.models.Video;

import java.util.List;

public interface VideosFragmentView {
    int getMovieID();

    List<Video> getVideos();

    void setVideos(List<Video> videos);

    void showEmptyView(boolean status);


}
