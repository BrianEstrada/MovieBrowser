package com.brianestrada.moviebrowser.utils;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.brianestrada.moviebrowser.R;
import com.brianestrada.moviebrowser.models.Movie;
import com.brianestrada.moviebrowser.ui.viewmovie.ViewMovieActivity;

import timber.log.Timber;

public class MovieUtils {

    public static String generatePosterURL(String posterPath, String size) {
        String return_url;

        String base_path = "http://image.tmdb.org/t/p/";

        return_url = base_path + "/" + size + "/" + posterPath;

        return return_url;
    }

    public static String getYoutubeURL(String ID) {
        Uri uri = Uri.parse("https://www.youtube.com/watch")
                .buildUpon()
                .appendQueryParameter("v", ID)
                .build();

        Timber.d("Youtube URL: %s", ID);
        return uri.toString();
    }

    public static String getYoutubeThumbnail(String ID) {
        String returnString = "http://img.youtube.com/vi/" + ID + "/0.jpg";

        Timber.d("Youtube Thumbnail: %s ", returnString);

        return returnString;
    }

    public static void showMovieIntent(Activity activity, Movie movie, View view, String transitionName) {
        //Get our bundle key for out movie
        String bundle_key_movie = activity.getString(R.string.bundle_key_movie);

        //Create our intent
        Intent showMovieIntent = new Intent(activity, ViewMovieActivity.class);

        //Pass our movie ID
        showMovieIntent.putExtra(bundle_key_movie, movie.getId());

        //For our transition animation
        Bundle options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, transitionName).toBundle();

        //Start the activity
        activity.startActivity(showMovieIntent, options);
    }

    public static void showMovieIntent(Activity activity, Movie movie) {
        //Get our bundle key for out movie
        String bundle_key_movie = activity.getString(R.string.bundle_key_movie);

        //Create our intent
        Intent showMovieIntent = new Intent(activity, ViewMovieActivity.class);

        //Pass our movie ID
        showMovieIntent.putExtra(bundle_key_movie, movie.getId());

        //Start the activity
        activity.startActivity(showMovieIntent);
    }
}
