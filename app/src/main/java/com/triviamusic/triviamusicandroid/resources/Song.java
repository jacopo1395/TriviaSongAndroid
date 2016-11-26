package com.triviamusic.triviamusicandroid.resources;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jadac_000 on 05/11/2016.
 */

public class Song implements Parcelable {
    private String title;
    private String album;
    private String link;
    private String author;
    private String album_id;
    private String album_image;
    private int track_number;

    public Song(JSONObject result) {
        try {
            this.album = result.getString("album");
            this.title = result.getString("title");
            this.author = result.getString("author");
            this.link = result.getString("link");
            this.album_id =result.getString("album_id");
            this.album_image =result.getString("album_image");
            this.track_number = result.getInt("track_number");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(String album_id) {
        this.album_id = album_id;
    }

    public String getAlbum_image() {
        return album_image;
    }

    public void setAlbum_image(String album_image) {
        this.album_image = album_image;
    }

    public int getTrack_number() {
        return track_number;
    }

    public void setTrack_number(int track_number) {
        this.track_number = track_number;
    }

    @Override
    public String toString() {
        return "Song{" +
                "title='" + title + '\'' +
                ", album='" + album + '\'' +
                ", link='" + link + '\'' +
                ", author='" + author + '\'' +
                ", album_id='" + album_id + '\'' +
                ", album_image='" + album_image + '\'' +
                ", track_number=" + track_number +
                '}';
    }

    public String getAlbumId() {
        return album_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(album);
        parcel.writeString(author);
        parcel.writeString(link);
        parcel.writeString(album_id);
        parcel.writeString(album_image);
        parcel.writeInt(track_number);
    }

    public static final Parcelable.Creator<Song> CREATOR
            = new Parcelable.Creator<Song>() {
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    private Song(Parcel in) {
        title = in.readString();
        album = in.readString();
        author = in.readString();
        link = in.readString();
        album_id = in.readString();
        album_image = in.readString();
        track_number = in.readInt();
    }
}