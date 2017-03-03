package com.brianestrada.moviebrowser.adapters.search;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.brianestrada.moviebrowser.R;
import com.brianestrada.moviebrowser.models.Movie;

public class SearchViewHolder extends RecyclerView.ViewHolder {
    private TextView tvTitle;

    public SearchViewHolder(View itemView) {
        super(itemView);

        tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
    }

    void bind(final Movie movie, final SearchAdapter.OnSearchResultClicked onSearchResultClicked) {
        String year = movie.getYear();

        String title = movie.getTitle();

        String formatted = String.format("%s - %s", title, year);

        tvTitle.setText(formatted);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearchResultClicked.onClicked(movie);
            }
        });
    }
}
