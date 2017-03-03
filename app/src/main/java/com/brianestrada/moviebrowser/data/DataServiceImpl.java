package com.brianestrada.moviebrowser.data;


import android.support.annotation.NonNull;

import com.brianestrada.moviebrowser.models.Movie;
import com.brianestrada.moviebrowser.models.Movies;
import com.brianestrada.moviebrowser.models.Reviews;
import com.brianestrada.moviebrowser.models.SearchResults;
import com.brianestrada.moviebrowser.models.Videos;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.Sort;
import timber.log.Timber;

public class DataServiceImpl implements DataService {
    MovieDatabaseService service;

    public DataServiceImpl(MovieDatabaseService service) {
        this.service = service;
    }

    @Override
    public Single<Movies> getMovies(String sortType) {
        return service.getMovies(sortType, MovieDatabaseService.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * This method checks for an existing Movie in the DB if the movie isn't found then we load it from the web
     *
     * @param id movie ID
     * @return Single<Movie> Observable
     */

    @Override
    public Single<Movie> getMovie(final int id) {
        return Single.create(new SingleOnSubscribe<Movie>() {
            @Override
            public void subscribe(final SingleEmitter<Movie> e) throws Exception {
                Timber.d("Searching for Movie in Realm");

                Realm realm = Realm.getDefaultInstance();

                Movie realmResult = realm.where(Movie.class).equalTo("id", id).findFirst();

                if (realmResult != null) {
                    Timber.d("Movie found in realm returning Result");

                    Movie movie = realm.copyFromRealm(realmResult);

                    realm.close();

                    e.onSuccess(movie);

                } else {

                    realm.close();

                    Timber.d("Searching for Movie Online");

                    service.getMovie(id, MovieDatabaseService.API_KEY)
                            .subscribe(new SingleObserver<Movie>() {
                                @Override
                                public void onSubscribe(Disposable d) {
                                    e.setDisposable(d);
                                }

                                @Override
                                public void onSuccess(Movie movie) {
                                    e.onSuccess(movie);
                                }

                                @Override
                                public void onError(Throwable err) {
                                    e.onError(err);
                                }
                            });
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<Reviews> getReviews(int id) {
        return service.getReviews(id, MovieDatabaseService.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<Videos> getVideos(int id) {
        return service.getVideos(id, MovieDatabaseService.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<SearchResults> searchMovies(@NonNull String search) {
        return service.searchMovies(search, MovieDatabaseService.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<SearchResults> searchMovies(@NonNull String search, int page) {
        return service.searchMovies(search, page, MovieDatabaseService.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<List<Movie>> getFavorites() {
        return Single.create(new SingleOnSubscribe<List<Movie>>() {
            @Override
            public void subscribe(SingleEmitter<List<Movie>> e) throws Exception {
                Realm realm = Realm.getDefaultInstance();

                List<Movie> realmResults = realm.where(Movie.class).equalTo("favorite", true).findAllSorted("id", Sort.DESCENDING);

                List<Movie> movieList = realm.copyFromRealm(realmResults);

                realm.close();

                e.onSuccess(movieList);
            }
        });
    }

    @Override
    public Movie getFavorite(int id) {
        Timber.d("Getting Favorite: %s", id);
        Realm realm = Realm.getDefaultInstance();

        Movie movie = null;

        Movie realmResult = realm.where(Movie.class).equalTo("id", id).findFirst();

        if (realmResult != null) {
            movie = realm.copyFromRealm(realmResult);
        }

        realm.close();

        return movie;
    }

    @Override
    public void addFavorite(Movie data) {
        Timber.d("Adding Favorite: %s", data.getId());

        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();

        realm.copyToRealmOrUpdate(data);

        realm.commitTransaction();

        realm.close();
    }

    @Override
    public boolean isFavorite(int id) {
        Timber.d("Is Favorite: %s", id);

        Realm realm = Realm.getDefaultInstance();

        boolean isFavorite = false;

        Movie realmResult = realm.where(Movie.class).equalTo("id", id).findFirst();

        if (realmResult != null) {
            isFavorite = realmResult.isFavorite();
        }

        realm.close();

        Timber.d("Favorite Value: %s", isFavorite);

        return isFavorite;
    }
}
