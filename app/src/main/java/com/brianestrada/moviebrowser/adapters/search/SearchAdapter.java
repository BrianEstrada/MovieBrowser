package com.brianestrada.moviebrowser.adapters.search;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brianestrada.moviebrowser.R;
import com.brianestrada.moviebrowser.models.Movie;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {
    private List<Movie> movies;
    private OnSearchResultClicked onSearchResultClicked;

    public interface OnSearchResultClicked {
        void onClicked(Movie movie);
    }


    public SearchAdapter(List<Movie> movies, OnSearchResultClicked onSearchResultClicked) {
        this.movies = movies;

        this.onSearchResultClicked = onSearchResultClicked;

    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_search, parent, false);

        return new SearchViewHolder(view);

    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        Movie movie = movies.get(position);

        holder.bind(movie, onSearchResultClicked);

    }

    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size();
    }
}
