package com.brianestrada.moviebrowser.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.brianestrada.moviebrowser.R;
import com.brianestrada.moviebrowser.adapters.MainPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.navigationView)
    BottomNavigationView navigationView;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    MainPagerAdapter mainPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind our views
        ButterKnife.bind(this);

        //Not going to use MVP on this activity because there isn't much to test it's just a fragment controller

        setupViewPager();

        setupNavigationBar();

    }

    public void setupNavigationBar() {
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Should always return true because we always want the bar to move
                int selectItem = 0;
                switch (item.getItemId()) {
                    case R.id.actionPopular:
                        selectItem = 0;
                        break;
                    case R.id.actionTop:
                        selectItem = 1;
                        break;
                    case R.id.actionSearch:
                        selectItem = 2;
                        break;
                    case R.id.actionFavorites:
                        selectItem = 3;
                        break;
                }

                Timber.d("Switching to View Page %s", selectItem);

                viewPager.setCurrentItem(selectItem);
                return true;
            }
        });
    }

    public void setupViewPager() {
        Timber.d("Setting up view pager");

        int menuSize = navigationView.getMenu().size();

        Timber.d("Menu Size: %s", menuSize);

        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), menuSize);

        viewPager.setAdapter(mainPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Timber.d("Selecting tab at position %s", position);
                //If we move pages then lets select the corresponding navigation tab
                navigationView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
