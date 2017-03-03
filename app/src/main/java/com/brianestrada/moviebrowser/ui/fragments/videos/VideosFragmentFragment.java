package com.brianestrada.moviebrowser.ui.fragments.videos;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brianestrada.moviebrowser.MainApplication;
import com.brianestrada.moviebrowser.R;
import com.brianestrada.moviebrowser.adapters.videos.AdapterVideos;
import com.brianestrada.moviebrowser.data.DataService;
import com.brianestrada.moviebrowser.models.Video;
import com.brianestrada.moviebrowser.models.State;
import com.brianestrada.moviebrowser.utils.MovieUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class VideosFragmentFragment extends Fragment implements VideosFragmentView {
    static String bundle_key_movie = "bundle_key_movie";

    RecyclerView.Adapter adapter;
    @BindView(R.id.recyclerVideos)
    RecyclerView recyclerVideos;
    @BindView(R.id.tvEmpty)
    TextView tvEmpty;

    ArrayList<Video> videosList = new ArrayList<>();
    VideosFragmentPresenter presenter;

    public static VideosFragmentFragment newInstance(int movieID) {
        Bundle args = new Bundle();

        args.putInt(bundle_key_movie, movieID);

        VideosFragmentFragment fragment = new VideosFragmentFragment();

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_videos, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        setupRecycler();

        if (presenter == null) {
            DataService dataService = MainApplication.get(this).getDataService();

            presenter = new VideosFragmentPresenterImpl(dataService);
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
    public int getMovieID() {
        Bundle bundle = getArguments();

        if (bundle.containsKey(bundle_key_movie)) {
            return bundle.getInt(bundle_key_movie);
        }

        return -1;
    }

    @Override
    public List<Video> getVideos() {
        return videosList;
    }

    @Override
    public void setVideos(List<Video> videos) {
        Timber.d("Setting Videos: %s", videos.size());

        videosList.clear();

        videosList.addAll(videos);

        adapter.notifyDataSetChanged();


    }

    @Override
    public void showEmptyView(boolean status) {
        tvEmpty.setVisibility(status ? View.VISIBLE : View.GONE);
        recyclerVideos.setVisibility(status ? View.GONE : View.VISIBLE);
    }

    //Non testables

    void setupRecycler() {
        Timber.d("Setting Recycler");

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerVideos.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);

        recyclerVideos.addItemDecoration(dividerItemDecoration);

        adapter = new AdapterVideos(videosList, new AdapterVideos.OnClickListener() {
            @Override
            public void onClick(Video video) {
                openURLIntent(video);
            }
        });

        recyclerVideos.setAdapter(adapter);
    }

    public void openURLIntent(Video video) {
        String intentURL = MovieUtils.getYoutubeURL(video.getKey());

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(intentURL));

        startActivity(intent);
    }


}
