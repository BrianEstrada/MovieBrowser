package com.brianestrada.moviebrowser.ui.fragments.videos;


import com.brianestrada.moviebrowser.data.DataService;
import com.brianestrada.moviebrowser.models.Video;
import com.brianestrada.moviebrowser.models.Videos;
import com.brianestrada.moviebrowser.models.State;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class VideosFragmentPresenterImpl implements VideosFragmentPresenter {
    //State
    private boolean initialized;
    private int movieID;
    private Videos videos;
    //View
    private VideosFragmentView view;
    //Data
    private DataService dataService;

    public VideosFragmentPresenterImpl(DataService dataService) {
        this.dataService = dataService;
    }

    @Override
    public void attachView(VideosFragmentView view) {
        this.view = view;
    }

    @Override
    public void initialize() {
        if (!initialized) {
            loadMovieID();

            loadVideos();

            initialized = !initialized;
        } else {
            passVideosToView();
        }
    }

    @Override
    public void loadMovieID() {
        movieID = view.getMovieID();
    }

    @Override
    public void loadVideos() {
        Timber.d("Loading Videos");
        dataService.getVideos(movieID)
                .subscribe(new SingleObserver<Videos>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Videos data) {
                        Timber.d("Loading Videos Successfully: %s", data.getVideos().size());

                        videos = data;

                        passVideosToView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d("Loading Videos Failed");
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void setState(State state) {
        initialized = state.isInitialized();
        movieID = state.getMovieID();
        videos = state.getVideos();
    }

    @Override
    public State getState() {
        State state = new State();

        state.setInitialized(initialized);

        state.setMovieID(movieID);

        state.setVideos(videos);

        return state;
    }

    @Override
    public Videos getVideos() {
        return videos;
    }

    @Override
    public void passVideosToView() {
        Timber.d("Passing Videos To View: %s", videos.getVideos().size());

        List<Video> videoList = videos.getVideos();

        view.setVideos(videoList);
    }
}
