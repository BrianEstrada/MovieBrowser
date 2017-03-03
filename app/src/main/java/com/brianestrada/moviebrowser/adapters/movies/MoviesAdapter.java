package com.brianestrada.moviebrowser.adapters.movies;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brianestrada.moviebrowser.R;
import com.brianestrada.moviebrowser.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<ViewHolderMovie> {
    private OnClickListener onClickListener;
    private List<Movie> movies;

    public interface OnClickListener {
        void onItemClick(Movie movie, View view);
    }

    public MoviesAdapter(List<Movie> movies, OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.movies = movies;
    }

    @Override
    public ViewHolderMovie onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_movie, parent, false);
        return new ViewHolderMovie(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderMovie holder, int position) {
        Movie movie = movies.get(position);

        holder.bind(movie, onClickListener, position);
    }

    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size();
    }
}
