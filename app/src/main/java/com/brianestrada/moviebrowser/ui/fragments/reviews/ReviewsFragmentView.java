package com.brianestrada.moviebrowser.ui.fragments.reviews;


import com.brianestrada.moviebrowser.models.Review;

import java.util.List;

public interface ReviewsFragmentView {
    //Getters
    int getMovieID();

    List<Review> getReviews();

    //Setters

    void setReviews(List<Review> reviews);

    //View Related

    void showEmptyView(boolean status);
}
