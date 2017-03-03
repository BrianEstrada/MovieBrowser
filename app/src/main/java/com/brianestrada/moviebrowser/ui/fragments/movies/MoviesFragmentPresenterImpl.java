package com.brianestrada.moviebrowser.ui.fragments.movies;


import com.brianestrada.moviebrowser.data.DataService;
import com.brianestrada.moviebrowser.models.Movie;
import com.brianestrada.moviebrowser.models.Movies;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

class MoviesFragmentPresenterImpl implements MoviesFragmentPresenter {
    private boolean initialize;
    public String sortMethod;
    DataService dataService;
    MoviesFragmentView view;

    public MoviesFragmentPresenterImpl(DataService dataService) {
        this.dataService = dataService;
    }

    @Override
    public void attachView(MoviesFragmentView view) {
        this.view = view;
    }

    public void initialize() {
        if (!initialize) {
            loadSortMethod();

            loadMovies();

            initialize = true;
        } else {
            loadMovies();
        }
    }

    @Override
    public void loadMovies() {
        Timber.d("Start loading Movies");

        dataService
                .getMovies(sortMethod)
                .subscribe(new SingleObserver<Movies>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Movies movies) {
                        Timber.d("Successfully Loaded Movies: %s", movies.getMovies().size());

                        List<Movie> movieList = movies.getMovies();

                        view.setMovies(movieList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d("Failed loading movies");

                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void loadSortMethod() {
        Timber.d("Loading Sort Method");

        sortMethod = view.getSortMethod();

        if (sortMethod != null) {
            Timber.d("Sort Method: %s", sortMethod);
        } else {
            Timber.d("Failed loading sort method");
        }
    }

    @Override
    public String getSortMethod() {
        return sortMethod;
    }
}
