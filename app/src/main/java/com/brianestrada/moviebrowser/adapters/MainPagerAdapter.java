package com.brianestrada.moviebrowser.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.brianestrada.moviebrowser.ui.fragments.favorites.FavoritesFragment;
import com.brianestrada.moviebrowser.ui.fragments.movies.MoviesFragment;
import com.brianestrada.moviebrowser.ui.fragments.search.SearchFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {
    int pageCount;

    public MainPagerAdapter(FragmentManager fm, int pageCount) {
        super(fm);

        this.pageCount = pageCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MoviesFragment.newInstance(MoviesFragment.SORT_POPULAR);
            case 1:
                return MoviesFragment.newInstance(MoviesFragment.SORT_TOP_RATED);
            case 2:
                return SearchFragment.newInstance();
            case 3:
                return FavoritesFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return pageCount;
    }
}
