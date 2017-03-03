package com.brianestrada.moviebrowser.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Videos implements Parcelable {
    @SerializedName("results")
    List<Video> videos;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.videos);
    }

    protected Videos(Parcel in) {
        this.videos = in.createTypedArrayList(Video.CREATOR);
    }

    public static final Creator<Videos> CREATOR = new Creator<Videos>() {
        @Override
        public Videos createFromParcel(Parcel source) {
            return new Videos(source);
        }

        @Override
        public Videos[] newArray(int size) {
            return new Videos[size];
        }
    };
}