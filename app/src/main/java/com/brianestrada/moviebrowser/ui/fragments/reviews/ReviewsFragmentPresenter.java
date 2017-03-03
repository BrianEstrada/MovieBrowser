package com.brianestrada.moviebrowser.ui.fragments.reviews;


import com.brianestrada.moviebrowser.models.Reviews;
import com.brianestrada.moviebrowser.models.State;

public interface ReviewsFragmentPresenter {
    void attachView(ReviewsFragmentView view);

    void initialize();

    void loadMovieID();

    void loadReviews();

    void passReviewsToView();

    void setState(State state);

    State getState();

    Reviews getReviews();
}
