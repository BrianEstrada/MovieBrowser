package com.brianestrada.moviebrowser.ui.fragments.movies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import timber.log.Timber;

public class MoviesFragment extends Fragment implements MoviesFragmentView {
    @BindView(R.id.recyclerMovies)
    RecyclerView recyclerView;

    static String SORT_METHOD_KEY = "sortMethodKey";
    public static String SORT_POPULAR = "popular";
    public static String SORT_TOP_RATED = "top_rated";

    private MoviesAdapter moviesAdapter;
    private List<Movie> movies;
    private MoviesFragmentPresenter presenter;

    public static MoviesFragment newInstance(String sortMethod) {
        MoviesFragment fragment = new MoviesFragment();

        Bundle args = new Bundle();

        args.putString(SORT_METHOD_KEY, sortMethod);

        fragment.setArguments(args);

        return fragment;
    }

    //Life Cycle Methods

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        //Setup our recycler
        setupRecycler();

        //If we dont have a presenter we should create one

        if (presenter == null) {
            //Inject our data service to our presenter
            DataService dataService = MainApplication.get(this).getDataService();
            //TODO: Auto injection via Dagger
            presenter = new MoviesFragmentPresenterImpl(dataService);
        }

        presenter.attachView(this);

        presenter.initialize();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        presenter.attachView(null);
    }

    //View related Methods

    public void setupRecycler() {
        Timber.d("Setting up recycler");

        //Get our grid size from our resources
        int grid_size = getResources().getInteger(R.integer.grid_size);

        movies = new ArrayList<>();

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), grid_size);

        recyclerView.setLayoutManager(layoutManager);

        moviesAdapter = new MoviesAdapter(movies, new MoviesAdapter.OnClickListener() {
            @Override
            public void onItemClick(Movie movie, View view) {
                MovieUtils.showMovieIntent(getActivity(), movie, view, view.getTransitionName());
            }
        });

        recyclerView.setAdapter(moviesAdapter);
    }

    //Testable Methods

    @Override
    public String getSortMethod() {
        Timber.d("Getting Sorting Method");
        Bundle bundle = getArguments();

        if (bundle.containsKey(SORT_METHOD_KEY)) {
            return bundle.getString(SORT_METHOD_KEY);
        } else {

            return null;
        }
    }

    @Override
    public void setMovies(List<Movie> list) {
        Timber.d("Setting Movies: %s", list.size());
        movies.clear();
        movies.addAll(list);
        moviesAdapter.notifyDataSetChanged();
    }

    @Override
    public List<Movie> getMovies() {
        return movies;
    }


}
