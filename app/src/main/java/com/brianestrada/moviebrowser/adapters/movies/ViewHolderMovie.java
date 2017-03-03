package com.brianestrada.moviebrowser.adapters.movies;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.brianestrada.moviebrowser.R;
import com.brianestrada.moviebrowser.models.Movie;
import com.brianestrada.moviebrowser.utils.MovieUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

class ViewHolderMovie extends RecyclerView.ViewHolder {
    private Context context;
    private ImageView ivPoster;
    private ProgressBar progressBar;

    ViewHolderMovie(View itemView) {
        super(itemView);

        context = itemView.getContext();

        ivPoster = (ImageView) itemView.findViewById(R.id.iv_poster);

        progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
    }

    void bind(final Movie movie, final MoviesAdapter.OnClickListener clickListener, final int position) {
        //Following sizes can be  "w92", "w154", "w185", "w342", "w500", "w780", or "original"
        String poster_url = MovieUtils.generatePosterURL(movie.getPosterPath(), "w342");

        //Once we have our url we will load it into our view using picasso
        Glide.with(context)
                .load(poster_url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.movie_poster_placeholder)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        showPoster();
                        return false;
                    }
                })
                .crossFade()
                .into(ivPoster);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClick(movie, ivPoster);
            }
        });
    }

    private void showPoster() {
        progressBar.setVisibility(View.GONE);
        ivPoster.setVisibility(View.VISIBLE);
    }

}
