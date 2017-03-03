package com.brianestrada.moviebrowser.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.brianestrada.moviebrowser.ui.fragments.overview.OverviewFragmentFragment;
import com.brianestrada.moviebrowser.ui.fragments.reviews.ReviewsFragment;
import com.brianestrada.moviebrowser.ui.fragments.videos.VideosFragmentFragment;

import timber.log.Timber;

public class ShowMoviePagerAdapter extends FragmentPagerAdapter {
    private int movieID;
    private int pageCount;

    public ShowMoviePagerAdapter(FragmentManager fm, int movieID, int pageCount) {
        super(fm);

        this.movieID = movieID;

        this.pageCount = pageCount;
    }

    @Override
    public Fragment getItem(int position) {
        Timber.d("Getting Item: %s", position);
        switch (position) {
            case 0:
                return OverviewFragmentFragment.newInstance(movieID);
            case 1:
                return VideosFragmentFragment.newInstance(movieID);
            case 2:
                return ReviewsFragment.newInstance(movieID);
            default:
                return OverviewFragmentFragment.newInstance(movieID);
        }
    }

    @Override
    public int getCount() {
        return pageCount;
    }
}
