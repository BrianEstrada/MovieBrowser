package com.brianestrada.moviebrowser.ui.fragments.reviews;


import com.brianestrada.moviebrowser.data.DataService;
import com.brianestrada.moviebrowser.models.Review;
import com.brianestrada.moviebrowser.models.Reviews;
import com.brianestrada.moviebrowser.models.State;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class ReviewsFragmentPresenterImpl implements ReviewsFragmentPresenter {
    //State
    private boolean initialized;
    private int movieID;
    private Reviews reviews;
    //View
    private ReviewsFragmentView view;
    //Data
    private DataService dataService;

    public ReviewsFragmentPresenterImpl(DataService dataService) {
        this.dataService = dataService;
    }


    @Override
    public void attachView(ReviewsFragmentView view) {
        this.view = view;
    }

    @Override
    public void initialize() {
        if (!initialized) {
            loadMovieID();

            loadReviews();

            initialized = !initialized;
        } else {
            passReviewsToView();
        }
    }

    @Override
    public void loadMovieID() {
        this.movieID = view.getMovieID();
    }

    @Override
    public void loadReviews() {
        Timber.d("Loading Reviews");

        dataService.getReviews(movieID)
                .subscribe(new SingleObserver<Reviews>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Reviews data) {
                        Timber.d("Loaded Reviews Successfully");

                        reviews = data;

                        passReviewsToView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d("Loading Reviews Failed");
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void passReviewsToView() {
        List<Review> reviewsList = reviews.getReviewList();

        view.setReviews(reviewsList);
    }

    @Override
    public void setState(State state) {
        initialized = state.isInitialized();
        movieID = state.getMovieID();
        reviews = state.getReviews();
    }

    @Override
    public State getState() {
        State state = new State();

        state.setInitialized(initialized);

        state.setMovieID(movieID);

        state.setReviews(reviews);

        return state;
    }

    @Override
    public Reviews getReviews() {
        return reviews;
    }
}
