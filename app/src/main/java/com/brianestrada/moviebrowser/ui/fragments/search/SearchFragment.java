package com.brianestrada.moviebrowser.ui.fragments.search;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.brianestrada.moviebrowser.MainApplication;
import com.brianestrada.moviebrowser.R;
import com.brianestrada.moviebrowser.adapters.search.SearchAdapter;
import com.brianestrada.moviebrowser.data.DataService;
import com.brianestrada.moviebrowser.models.Movie;
import com.brianestrada.moviebrowser.models.State;
import com.brianestrada.moviebrowser.utils.MovieUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class SearchFragment extends Fragment implements SearchFragmentView {
    @BindView(R.id.searchView)
    FloatingSearchView searchView;
    @BindView(R.id.recyclerSearchResults)
    RecyclerView recyclerSearchResults;

    List<Movie> searchResults;

    SearchAdapter searchAdapter;

    SearchFragmentPresenter presenter;

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        setupRecycler();

        setupSearch();

        if (presenter == null) {
            DataService dataService = MainApplication.get(this).getDataService();

            presenter = new SearchFragmentPresenterImpl(dataService);
        }

        presenter.attachView(this);


        //Check for a previous state
        if (savedInstanceState != null) {
            String bundle_key_movie_state = getString(R.string.bundle_key_movie_state);

            if (savedInstanceState.containsKey(bundle_key_movie_state)) {
                State movieState = savedInstanceState.getParcelable(bundle_key_movie_state);

                //If we have a previous state pass that to the presenter before we initialize
                presenter.setState(movieState);
            }
        }

        presenter.initialized();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        String bundle_key_movie_state = getString(R.string.bundle_key_movie_state);

        State state = presenter.getState();

        outState.putParcelable(bundle_key_movie_state, state);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        presenter.attachView(null);
    }

    //Non testables

    private void setupRecycler() {
        searchResults = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerSearchResults.setLayoutManager(layoutManager);


        recyclerSearchResults.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        searchAdapter = new SearchAdapter(searchResults, new SearchAdapter.OnSearchResultClicked() {
            @Override
            public void onClicked(Movie movie) {
                MovieUtils.showMovieIntent(getActivity(), movie);
            }
        });

        recyclerSearchResults.setAdapter(searchAdapter);
    }

    private void setupSearch() {
        searchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                Timber.d("Query: %s", newQuery);

                if (newQuery.length() > 1) {
                    presenter.searchForMovie(newQuery);
                }
            }
        });


    }

    @Override
    public void setResults(List<Movie> movies) {
        Timber.d("Setting Results: %s", movies.size());
        searchResults.clear();

        searchResults.addAll(movies);

        searchAdapter.notifyDataSetChanged();
    }

    @Override
    public List<Movie> getResults() {
        return searchResults;
    }
}
