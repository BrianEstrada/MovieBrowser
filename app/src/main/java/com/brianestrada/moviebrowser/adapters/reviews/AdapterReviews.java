package com.brianestrada.moviebrowser.adapters.reviews;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brianestrada.moviebrowser.R;
import com.brianestrada.moviebrowser.models.Review;

import java.util.List;


public class AdapterReviews extends RecyclerView.Adapter<ViewHolderReview> {
    private List<Review> reviews;

    public AdapterReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public ViewHolderReview onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_review, parent, false);
        return new ViewHolderReview(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderReview holder, int position) {
        Review review = reviews.get(position);
        holder.bind(review);
    }

    @Override
    public int getItemCount() {
        return reviews == null ? 0 : reviews.size();
    }
}
