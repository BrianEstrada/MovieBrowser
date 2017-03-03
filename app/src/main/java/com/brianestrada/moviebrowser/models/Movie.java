package com.brianestrada.moviebrowser.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Setter
@Getter
@ToString
public class Movie extends RealmObject implements Parcelable {
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("adult")
    private boolean adult;
    @SerializedName("overview")
    private String overview;
    @SerializedName("release_date")
    private String releaseDate;
    @PrimaryKey
    @SerializedName("id")
    private int id;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("title")
    private String title;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("popularity")
    private float popularity;
    @SerializedName("vote_count")
    private int voteCount;
    @SerializedName("video")
    private boolean video;
    @SerializedName("vote_average")
    private float voteAverage;
    private boolean favorite;

    public Movie() {

    }

    /*
    Reason why I'm exploding the date instead of converting it using a GSON adapter is because
    for some reason the API spits out some invalid dates causing the app to crash so this solution works better :)
     */

    public String getYear() {
        String[] explodedString = releaseDate.split("-");
        if (explodedString.length == 3) {
            return explodedString[0];
        } else {
            return "";
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.posterPath);
        dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
        dest.writeInt(this.id);
        dest.writeString(this.originalTitle);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.title);
        dest.writeString(this.backdropPath);
        dest.writeFloat(this.popularity);
        dest.writeInt(this.voteCount);
        dest.writeByte(this.video ? (byte) 1 : (byte) 0);
        dest.writeFloat(this.voteAverage);
        dest.writeByte(this.favorite ? (byte) 1 : (byte) 0);
    }

    protected Movie(Parcel in) {
        this.posterPath = in.readString();
        this.adult = in.readByte() != 0;
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.id = in.readInt();
        this.originalTitle = in.readString();
        this.originalLanguage = in.readString();
        this.title = in.readString();
        this.backdropPath = in.readString();
        this.popularity = in.readFloat();
        this.voteCount = in.readInt();
        this.video = in.readByte() != 0;
        this.voteAverage = in.readFloat();
        this.favorite = in.readByte() != 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}