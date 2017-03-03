package com.brianestrada.moviebrowser.injector.module;

import com.brianestrada.moviebrowser.data.MovieDatabaseService;
import com.brianestrada.moviebrowser.injector.scope.ApplicationScope;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = NetworkModule.class)
public class MovieDBModule {

    String mBaseUrl;

    public MovieDBModule(String baseUrl) {
        this.mBaseUrl = baseUrl;
    }

    @Provides
    @ApplicationScope
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(mBaseUrl)
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    @Provides
    @ApplicationScope
    MovieDatabaseService provideMovieService(Retrofit retrofit) {
        return retrofit.create(MovieDatabaseService.class);
    }
}
