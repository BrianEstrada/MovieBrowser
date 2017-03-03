package com.brianestrada.moviebrowser.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Video implements Parcelable {
    @SerializedName("id")
    public String id;
    @SerializedName("iso_639_1")
    public String iso6391;
    @SerializedName("iso_3166_1")
    public String iso31661;
    @SerializedName("key")
    public String key;
    @SerializedName("name")
    public String name;
    @SerializedName("site")
    public String site;
    @SerializedName("size")
    public int size;
    @SerializedName("type")
    public String type;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.iso6391);
        dest.writeString(this.iso31661);
        dest.writeString(this.key);
        dest.writeString(this.name);
        dest.writeString(this.site);
        dest.writeInt(this.size);
        dest.writeString(this.type);
    }

    protected Video(Parcel in) {
        this.id = in.readString();
        this.iso6391 = in.readString();
        this.iso31661 = in.readString();
        this.key = in.readString();
        this.name = in.readString();
        this.site = in.readString();
        this.size = in.readInt();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<Video> CREATOR = new Parcelable.Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel source) {
            return new Video(source);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };
}