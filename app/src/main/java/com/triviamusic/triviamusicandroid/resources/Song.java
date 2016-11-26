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

    private String possibility1;
    private String possibility2;
    private String possibility3;
    private String possibility4;

    public Song(JSONObject result) {
        try {
            this.album = result.getString("album");
            this.title = result.getString("title");
            this.author = result.getString("author");
            this.link = result.getString("link");
            this.album_id =result.getString("album_id");
            this.album_image =result.getString("album_image");
            this.track_number = result.getInt("track_number");
            this.possibility1 = new String();
            this.possibility2 = new String();
            this.possibility3 = new String();
            this.possibility4 = new String();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void setPossibilities(JSONObject result) {
        try {
            if (result.getString("status").equals("error")) {
                return;
            }
            int n = result.getInt("total");
            String[] poss = new String[n];
            for (int i = 0; i < n; i++) {
                String x = "possibility" + (i + 1);
                poss[i] = result.getString(x);
            }
            possibility1 = poss[0];
            possibility2 = poss[1];
            possibility3 = poss[2];
            possibility4 = poss[3];
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

    public String getPossibility1() {
        return possibility1;
    }

    public void setPossibility1(String possibility1) {
        this.possibility1 = possibility1;
    }

    public String getPossibility2() {
        return possibility2;
    }

    public void setPossibility2(String possibility2) {
        this.possibility2 = possibility2;
    }

    public String getPossibility3() {
        return possibility3;
    }

    public void setPossibility3(String possibility3) {
        this.possibility3 = possibility3;
    }

    public String getPossibility4() {
        return possibility4;
    }

    public void setPossibility4(String possibility4) {
        this.possibility4 = possibility4;
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
        parcel.writeString(possibility1);
        parcel.writeString(possibility2);
        parcel.writeString(possibility3);
        parcel.writeString(possibility4);
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
        possibility1 = in.readString();
        possibility2 = in.readString();
        possibility3 = in.readString();
        possibility4 = in.readString();
    }

    public String[] getPossibilities() {
        String[] s = new String[4];
        s[0] = possibility1;
        s[1] = possibility2;
        s[2] = possibility3;
        s[3] = possibility4;
        return s;

    }
}