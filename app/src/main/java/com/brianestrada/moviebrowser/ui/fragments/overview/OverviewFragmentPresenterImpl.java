package com.brianestrada.moviebrowser.ui.fragments.overview;


import com.brianestrada.moviebrowser.data.DataService;
import com.brianestrada.moviebrowser.models.Movie;
import com.brianestrada.moviebrowser.models.State;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class OverviewFragmentPresenterImpl implements OverviewFragmentPresenter {
    //State
    private boolean initialized;
    private int movieID;
    private Movie movie;

    //
    private DataService dataService;
    private OverviewFragmentView view;

    public OverviewFragmentPresenterImpl(DataService dataService) {
        this.dataService = dataService;
    }

    @Override
    public void attachView(OverviewFragmentView view) {
        this.view = view;
    }

    @Override
    public void initialize() {
        if (!initialized) {
            loadMovieID();

            loadMovie();

            initialized = !initialized;
        } else {
            passOverviewToView();
        }
    }

    @Override
    public void loadMovieID() {
        this.movieID = view.getMovieID();
    }

    @Override
    public void loadMovie() {
        Timber.d("Loading Movie: %s", movieID);

        dataService.getMovie(movieID)
                .subscribe(new SingleObserver<Movie>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Movie data) {
                        Timber.d("Successfully Loaded Movie");

                        movie = data;

                        passOverviewToView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d("Loading Movie Failed");
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void passOverviewToView() {

    }

    @Override
    public void setState(State state) {
        initialized = state.isInitialized();
        movieID = state.getMovieID();
        movie = state.getMovie();
    }

    @Override
    public State getState() {
        State state = new State();

        state.setInitialized(initialized);

        state.setMovieID(movieID);

        state.setMovie(movie);

        return state;
    }

    @Override
    public Movie getMovie() {
        return movie;
    }
}
