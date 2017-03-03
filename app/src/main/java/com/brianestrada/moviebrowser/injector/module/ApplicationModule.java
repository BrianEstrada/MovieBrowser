package com.brianestrada.moviebrowser.injector.module;


import android.app.Application;

import com.brianestrada.moviebrowser.injector.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @ApplicationScope
    Application provideApplication() {
        return application;
    }
}
