package com.brianestrada.moviebrowser.ui.fragments.overview;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brianestrada.moviebrowser.MainApplication;
import com.brianestrada.moviebrowser.R;
import com.brianestrada.moviebrowser.data.DataService;
import com.brianestrada.moviebrowser.models.State;

import butterknife.ButterKnife;

public class OverviewFragmentFragment extends Fragment implements OverviewFragmentView {
    static String bundle_key_movie = "bundle_key_movie";
    private OverviewFragmentPresenter presenter;
    private TextView tvOverview;

    public static OverviewFragmentFragment newInstance(int movieID) {
        Bundle args = new Bundle();

        args.putInt(bundle_key_movie, movieID);

        OverviewFragmentFragment overviewFragment = new OverviewFragmentFragment();

        overviewFragment.setArguments(args);

        return overviewFragment;
    }

    //Life Cycle

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_overview, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        if (presenter == null) {
            DataService dataService = MainApplication.get(this).getDataService();

            presenter = new OverviewFragmentPresenterImpl(dataService);
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
    //Setters & Getters

    @Override
    public void setOverview(String overview) {
        tvOverview.setText(overview);
    }

    @Override
    public int getMovieID() {
        Bundle bundle = getArguments();

        if (bundle.containsKey(bundle_key_movie)) {
            return bundle.getInt(bundle_key_movie);
        }

        return -1;
    }
}
