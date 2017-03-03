package com.brianestrada.moviebrowser;


import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.brianestrada.moviebrowser.data.DataService;
import com.brianestrada.moviebrowser.injector.ApplicationComponent;
import com.brianestrada.moviebrowser.injector.DaggerApplicationComponent;
import com.brianestrada.moviebrowser.injector.module.ApplicationModule;
import com.brianestrada.moviebrowser.injector.module.DataServiceModule;
import com.brianestrada.moviebrowser.injector.module.MovieDBModule;
import com.brianestrada.moviebrowser.injector.module.NetworkModule;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

public class MainApplication extends Application {
    DataService dataInteractor;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        //Initialize Realm
        Realm.init(this);
        //Create our configuration
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        //Apply it
        Realm.setDefaultConfiguration(realmConfiguration);

        //Create our Data layer
        ApplicationComponent dataServiceComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule())
                .movieDBModule(new MovieDBModule(BuildConfig.Website))
                .dataServiceModule(new DataServiceModule())
                .build();

        dataInteractor = dataServiceComponent.getDataService();
    }

    public static MainApplication get(Activity activity) {
        return (MainApplication) activity.getApplication();
    }

    public static MainApplication get(Fragment fragment) {
        return (MainApplication) fragment.getActivity().getApplication();
    }

    public DataService getDataService() {
        return dataInteractor;
    }
}
