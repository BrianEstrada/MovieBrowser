package com.brianestrada.moviebrowser.ui.viewmovie;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.brianestrada.moviebrowser.MainApplication;
import com.brianestrada.moviebrowser.R;
import com.brianestrada.moviebrowser.adapters.ShowMoviePagerAdapter;
import com.brianestrada.moviebrowser.data.DataService;
import com.brianestrada.moviebrowser.models.State;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ViewMovieActivity extends AppCompatActivity implements ViewMovieActivityView {
    @BindView(R.id.iv_poster)
    ImageView ivPoster;
    @BindView(R.id.tv_movie_title)
    TextView tvMovieTitle;
    @BindView(R.id.tv_rating)
    TextView tvRating;
    @BindView(R.id.tv_release_year)
    TextView tvReleaseYear;
    @BindView(R.id.rb_rating)
    RatingBar ratingBar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    Menu menu;
    ViewMoviePresenter presenter;
    ShowMoviePagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_movie);


        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        setupTabHost();

        if (presenter == null) {
            DataService dataService = MainApplication.get(this).getDataService();
            presenter = new ViewMoviePresenterImpl(dataService);
        }

        setupViewPager();

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
        presenter.initialize();


    }

    //Setters
    @Override
    public void setPosterImage(String url) {
        Timber.d("Setting Poster URL: %s", url);

        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.noimageplaceholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .dontAnimate()
                .into(ivPoster);
    }

    @Override
    public void setMovieTitle(String title) {
        Timber.d("Setting Movie Title: %s", title);

        setToolbarTitle(title);

        tvMovieTitle.setText(title);
    }

    @Override
    public void setRating(float rating) {
        Timber.d("Setting rating: %s", rating);

        String ratingString = String.valueOf(rating);

        tvRating.setText(ratingString);

        ratingBar.setRating(rating);
    }

    @Override
    public void setReleaseYear(String releaseYear) {
        Timber.d("Setting Release Year: %s", releaseYear);
        tvReleaseYear.setText(releaseYear);
    }

    @Override
    public void setToolbarTitle(String title) {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {

            actionBar.setDisplayHomeAsUpEnabled(true);

            //actionBar.setTitle(title);
        }
    }

    @Override
    public void setFavorite(boolean isFavorite) {

        MenuItem menuItem = menu.findItem(R.id.actionFavorite);

        if (isFavorite) {
            menuItem.setIcon(R.drawable.ic_favorite);
        } else {
            menuItem.setIcon(R.drawable.ic_favorite_border);
        }
    }

    //Getters

    @Override
    public String getMovieTitle() {
        return tvMovieTitle.getText().toString();
    }

    @Override
    public float getRating() {
        return ratingBar.getRating();
    }

    @Override
    public String getReleaseYear() {
        return tvReleaseYear.getText().toString();
    }

    @Override
    public int getMovieID() {
        Timber.d("Getting Movie ID");

        String bundle_key_movie = getString(R.string.bundle_key_movie);

        Bundle bundle = getIntent().getExtras();

        if (bundle.containsKey(bundle_key_movie)) {
            int movieID = bundle.getInt(bundle_key_movie);

            Timber.d("Returning Movie ID: %s", movieID);

            return movieID;
        } else {
            Timber.d("Movie ID not found");

            missingMovieID();

            return -1;
        }
    }

    //misc

    @Override
    public void selectPage(int page) {
        viewPager.setCurrentItem(page);
    }

    //Android Life Cycle Stuff

    @Override
    protected void onDestroy() {
        super.onDestroy();

        presenter.attachView(null);

    }

    //Methods which cannot be

    public void setupViewPager() {
        Timber.d("Setting up view pager");

        pagerAdapter = new ShowMoviePagerAdapter(getSupportFragmentManager(), getMovieID(), 3);

        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Timber.d("Selecting tab at position %s", position);
                //If we move pages then lets select the corresponding navigation tab
                TabLayout.Tab selectTab = tabs.getTabAt(position);

                if (selectTab != null) {
                    selectTab.select();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        String bundle_key_movie_state = getString(R.string.bundle_key_movie_state);

        State movieState = presenter.getState();

        outState.putParcelable(bundle_key_movie_state, movieState);

        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_movie_menu, menu);

        this.menu = menu;

        presenter.setFavorite();

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuID = item.getItemId();

        switch (menuID) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.actionFavorite:
                presenter.toggleFavorite();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupTabHost() {
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                int position = tab.getPosition();

                Timber.d("Setting Tab: %s", position);

                presenter.setFragment(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void missingMovieID() {
        finish();
    }
}
