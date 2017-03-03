package com.brianestrada.moviebrowser.ui.fragments.favorites;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brianestrada.moviebrowser.MainApplication;
import com.brianestrada.moviebrowser.R;
import com.brianestrada.moviebrowser.adapters.movies.MoviesAdapter;
import com.brianestrada.moviebrowser.data.DataService;
import com.brianestrada.moviebrowser.models.Movie;
import com.brianestrada.moviebrowser.utils.MovieUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesFragment extends Fragment implements FavoritesFragmentView {
    @BindView(R.id.tvEmpty)
    TextView tvEmpty;
    @BindView(R.id.recyclerFavorites)
    RecyclerView recyclerFavorites;

    List<Movie> favorites;
    MoviesAdapter adapter;
    FavoritesFragmentPresenter presenter;

    public static FavoritesFragment newInstance() {
        FavoritesFragment fragment = new FavoritesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    //Life Cycle

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        setupRecycler();

        if (presenter == null) {
            DataService dataService = MainApplication.get(this).getDataService();

            presenter = new FavoritesFragmentPresenterImpl(dataService);
        }

        presenter.attachView(this);

        presenter.initialize();
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.loadFavorites();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        presenter.attachView(null);
    }

    //Getters & Setters

    @Override
    public void setFavorites(List<Movie> data) {
        favorites.clear();

        favorites.addAll(data);

        adapter.notifyDataSetChanged();

        showEmptyView(favorites.size() == 0);
    }

    @Override
    public List<Movie> getFavorites() {
        return favorites;
    }

    @Override
    public void showEmptyView(boolean status) {
        tvEmpty.setVisibility(status ? View.VISIBLE : View.GONE);
        recyclerFavorites.setVisibility(status ? View.GONE : View.VISIBLE);
    }

    //Untestables

    private void setupRecycler() {
        favorites = new ArrayList<>();

        int grid_size = getResources().getInteger(R.integer.grid_size);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), grid_size);

        recyclerFavorites.setLayoutManager(layoutManager);

        adapter = new MoviesAdapter(favorites, new MoviesAdapter.OnClickListener() {
            @Override
            public void onItemClick(Movie movie, View view) {
                MovieUtils.showMovieIntent(getActivity(), movie, view, view.getTransitionName());
            }
        });

        recyclerFavorites.setAdapter(adapter);
    }

}
