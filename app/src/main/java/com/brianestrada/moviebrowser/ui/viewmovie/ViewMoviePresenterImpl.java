package com.brianestrada.moviebrowser.ui.viewmovie;


import com.brianestrada.moviebrowser.data.DataService;
import com.brianestrada.moviebrowser.models.Movie;
import com.brianestrada.moviebrowser.models.State;
import com.brianestrada.moviebrowser.utils.MovieUtils;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

class ViewMoviePresenterImpl implements ViewMoviePresenter {
    //States
    private boolean initialized;
    private int movieID;
    private int selectedTab = 0;
    private Movie movie;
    //Data Service
    private DataService dataService;
    //View
    private ViewMovieActivityView view;


    ViewMoviePresenterImpl(DataService dataService) {
        this.dataService = dataService;
    }

    @Override
    public void attachView(ViewMovieActivityView view) {
        this.view = view;
    }

    @Override
    public void initialize() {

        Timber.d("Initializing: %s", initialized);

        if (!initialized) {
            loadMovieID();

            initialized = true;
        } else {
            passDataToView();

            //Show our selected tab
            showFragment();
        }

    }

    //Loaders

    @Override
    public void loadMovie() {
        Timber.d("Loading Movie: %s", movieID);
        dataService
                .getMovie(movieID)
                .subscribe(new SingleObserver<Movie>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Movie data) {
                        Timber.d("Successfully loaded %s", data.getTitle());

                        movie = data;

                        //Show our data
                        passDataToView();
                        //Show our default tab
                        showFragment();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d("Loading Movie Failed");
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void setState(State movieState) {
        initialized = movieState.isInitialized();
        movieID = movieState.getMovieID();
        selectedTab = movieState.getSelectedTab();
        movie = movieState.getMovie();
    }

    @Override
    public void loadMovieID() {
        Timber.d("Loading Movie ID");

        movieID = view.getMovieID();

        Timber.d("Loaded Movie ID: %s", movieID);

        loadMovie();
    }

    //Setters

    @Override
    public void setMoviePoster() {
        String posterURL = movie.getPosterPath();

        String fullURL = MovieUtils.generatePosterURL(posterURL, "w342");

        view.setPosterImage(fullURL);
    }

    @Override
    public void setMovieTitle() {
        String movieTitle = movie.getTitle();

        view.setMovieTitle(movieTitle);
    }

    @Override
    public void setMovieReleaseDate() {
        String yearString = movie.getYear();

        view.setReleaseYear(yearString);
    }

    @Override
    public void setMovieRating() {
        Float movieRating = movie.getVoteAverage();

        view.setRating(movieRating);
    }

    @Override
    public void setFavorite() {
        boolean isFavorite = dataService.isFavorite(movieID);

        view.setFavorite(isFavorite);
    }

    @Override
    public void setFragment(int i) {
        Timber.d("Setting Fragment %s", i);
        selectedTab = i;

        showFragment();
    }


    @Override
    public State getState() {
        State state = new State();

        state.setMovie(movie);

        state.setMovieID(movieID);

        state.setInitialized(initialized);

        state.setSelectedTab(selectedTab);

        return state;
    }

    //Misc

    @Override
    public void toggleFavorite() {
        boolean isFavorite = dataService.isFavorite(movieID);

        this.movie.setFavorite(!isFavorite);

        dataService.addFavorite(movie);

        setFavorite();
    }

    @Override
    public void showFragment() {
        Timber.d("Showing Fragment %s", selectedTab);

        view.selectPage(selectedTab);
    }

    @Override
    public void passDataToView() {
        Timber.d("Passing data to view");

        setMoviePoster();
        setMovieRating();
        setMovieReleaseDate();
        setMovieTitle();
    }
}
