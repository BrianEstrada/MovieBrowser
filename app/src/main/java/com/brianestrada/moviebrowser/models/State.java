package com.brianestrada.moviebrowser.models;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class State implements Parcelable {
    boolean initialized;
    int movieID;
    int selectedTab = 0;
    Movie movie;
    Videos videos;
    Reviews reviews;
    SearchResults searchResults;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.initialized ? (byte) 1 : (byte) 0);
        dest.writeInt(this.movieID);
        dest.writeInt(this.selectedTab);
        dest.writeParcelable(this.movie, flags);
        dest.writeParcelable(this.videos, flags);
        dest.writeParcelable(this.reviews, flags);
        dest.writeParcelable(this.searchResults, flags);
    }

    protected State(Parcel in) {
        this.initialized = in.readByte() != 0;
        this.movieID = in.readInt();
        this.selectedTab = in.readInt();
        this.movie = in.readParcelable(Movie.class.getClassLoader());
        this.videos = in.readParcelable(Videos.class.getClassLoader());
        this.reviews = in.readParcelable(Reviews.class.getClassLoader());
        this.searchResults = in.readParcelable(SearchResults.class.getClassLoader());
    }

    public static final Creator<State> CREATOR = new Creator<State>() {
        @Override
        public State createFromParcel(Parcel source) {
            return new State(source);
        }

        @Override
        public State[] newArray(int size) {
            return new State[size];
        }
    };
}
