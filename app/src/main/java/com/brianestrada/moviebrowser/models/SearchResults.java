package com.brianestrada.moviebrowser.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class SearchResults implements Parcelable {
    @SerializedName("page")
    Integer page;
    @SerializedName("results")
    List<Movie> results = null;
    @SerializedName("total_results")
    Integer totalResults;
    @SerializedName("total_pages")
    Integer totalPages;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.page);
        dest.writeTypedList(this.results);
        dest.writeValue(this.totalResults);
        dest.writeValue(this.totalPages);
    }

    protected SearchResults(Parcel in) {
        this.page = (Integer) in.readValue(Integer.class.getClassLoader());
        this.results = in.createTypedArrayList(Movie.CREATOR);
        this.totalResults = (Integer) in.readValue(Integer.class.getClassLoader());
        this.totalPages = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<SearchResults> CREATOR = new Parcelable.Creator<SearchResults>() {
        @Override
        public SearchResults createFromParcel(Parcel source) {
            return new SearchResults(source);
        }

        @Override
        public SearchResults[] newArray(int size) {
            return new SearchResults[size];
        }
    };
}
