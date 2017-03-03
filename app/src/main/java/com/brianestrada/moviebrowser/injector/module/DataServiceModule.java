package com.brianestrada.moviebrowser.injector.module;


import com.brianestrada.moviebrowser.data.DataService;
import com.brianestrada.moviebrowser.data.DataServiceImpl;
import com.brianestrada.moviebrowser.data.MovieDatabaseService;
import com.brianestrada.moviebrowser.injector.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module(includes = MovieDBModule.class)
public class DataServiceModule {
    @Provides
    @ApplicationScope
    DataService provideDataService(MovieDatabaseService MovieDatabaseService) {
        return new DataServiceImpl(MovieDatabaseService);
    }
}
