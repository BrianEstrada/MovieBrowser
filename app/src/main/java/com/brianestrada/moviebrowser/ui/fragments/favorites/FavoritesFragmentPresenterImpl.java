package com.brianestrada.moviebrowser.ui.fragments.favorites;


import com.brianestrada.moviebrowser.data.DataService;
import com.brianestrada.moviebrowser.models.Movie;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class FavoritesFragmentPresenterImpl implements FavoritesFragmentPresenter {
    DataService dataService;
    FavoritesFragmentView view;
    List<Movie> favorites = new ArrayList<>();

    public FavoritesFragmentPresenterImpl(DataService dataService) {
        this.dataService = dataService;
    }

    @Override
    public void attachView(FavoritesFragmentView view) {
        this.view = view;
    }

    @Override
    public void initialize() {
        loadFavorites();
    }

    @Override
    public void loadFavorites() {
        Timber.d("Loading Favorites");

        dataService
                .getFavorites()
                .subscribe(new SingleObserver<List<Movie>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Movie> data) {
                        Timber.d("Loading Favorites Successful");

                        favorites = data;

                        passFavoritesToView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d("Loading Favorites Failed");

                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void passFavoritesToView() {
        view.setFavorites(favorites);
    }
}
