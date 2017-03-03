package com.brianestrada.moviebrowser.ui.fragments.reviews;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brianestrada.moviebrowser.MainApplication;
import com.brianestrada.moviebrowser.R;
import com.brianestrada.moviebrowser.adapters.reviews.AdapterReviews;
import com.brianestrada.moviebrowser.data.DataService;
import com.brianestrada.moviebrowser.models.Review;
import com.brianestrada.moviebrowser.models.State;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewsFragment extends Fragment implements ReviewsFragmentView {
    static String bundle_key_movie = "bundle_key_movie";

    @BindView(R.id.reviewRecycler)
    RecyclerView reviewRecycler;

    @BindView(R.id.tvEmpty)
    TextView tvEmpty;

    RecyclerView.Adapter adapter;
    ArrayList<Review> reviewsList = new ArrayList<>();
    ReviewsFragmentPresenter presenter;

    public static ReviewsFragment newInstance(int movieID) {
        Bundle args = new Bundle();

        args.putInt(bundle_key_movie, movieID);

        ReviewsFragment fragment = new ReviewsFragment();

        fragment.setArguments(args);

        return fragment;
    }

    //Life Cycle

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reviews, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        setupRecycler();

        if (presenter == null) {
            DataService dataService = MainApplication.get(this).getDataService();

            presenter = new ReviewsFragmentPresenterImpl(dataService);
        }

        presenter.attachView(this);

        if (savedInstanceState != null) {
            String bundle_key_movie_state = getString(R.string.bundle_key_movie_state);

            if (savedInstanceState.containsKey(bundle_key_movie_state)) {

                State state = savedInstanceState.getParcelable(bundle_key_movie_state);

                presenter.setState(state);
            }
        }

        presenter.initialize();

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


    //Non Test-ables

    void setupRecycler() {
        reviewRecycler.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        reviewRecycler.setLayoutManager(layoutManager);

        adapter = new AdapterReviews(reviewsList);

        reviewRecycler.setAdapter(adapter);
    }

    //Getters

    @Override
    public int getMovieID() {
        Bundle bundle = getArguments();

        if (bundle.containsKey(bundle_key_movie)) {
            return bundle.getInt(bundle_key_movie);
        }

        return -1;
    }

    @Override
    public List<Review> getReviews() {
        return reviewsList;
    }

    //Setters

    @Override
    public void setReviews(List<Review> reviews) {
        reviewsList.clear();

        reviewsList.addAll(reviews);

        showEmptyView(reviewsList.size() == 0);

        adapter.notifyDataSetChanged();
    }

    //View Related

    @Override
    public void showEmptyView(boolean status) {
        tvEmpty.setVisibility(status ? View.VISIBLE : View.GONE);
        reviewRecycler.setVisibility(status ? View.GONE : View.VISIBLE);
    }
}
