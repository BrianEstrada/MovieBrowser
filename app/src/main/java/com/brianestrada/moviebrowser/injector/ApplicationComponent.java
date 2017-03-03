package com.brianestrada.moviebrowser.injector;

import com.brianestrada.moviebrowser.data.DataService;
import com.brianestrada.moviebrowser.injector.module.DataServiceModule;
import com.brianestrada.moviebrowser.injector.scope.ApplicationScope;

import dagger.Component;

@ApplicationScope
@Component(modules = DataServiceModule.class)
public interface ApplicationComponent {
    DataService getDataService();
}
