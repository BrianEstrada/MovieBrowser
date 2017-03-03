package com.brianestrada.moviebrowser.ui.fragments.search;


import com.brianestrada.moviebrowser.models.Movie;

import java.util.List;

public interface SearchFragmentView {

    void setResults(List<Movie> movies);

    List<Movie> getResults();
}
