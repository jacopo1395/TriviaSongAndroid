package com.triviamusic.triviamusicandroid.resources;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jadac_000 on 08/11/2016.
 */

public class Turn implements Parcelable {
    private int numberSong = 0;
    private ArrayList<Song> songs;

    public Turn(JSONObject result) {
        songs = new ArrayList<Song>(5);
        try {
            int n = result.getInt("total");
            for (int i = 0; i < n; i++) {
                JSONObject o = result.getJSONObject("song" + Integer.toString(i));
                //System.out.println(o);
                Song s = new Song(o);
                //System.out.println(s);
                songs.add(s);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public Song getSong() {
        return songs.get(numberSong);
    }

    public int getNumberOfSongs() {
        if (songs == null) return 0;
        return songs.size();
    }

    public int getNumberSong() {
        return numberSong;
    }

    public void setNumberSong(int numberSong) {
        this.numberSong = numberSong;
    }

    public void nextTurn() {
        this.numberSong++;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(songs);
        parcel.writeInt(numberSong);
    }

    public static final Parcelable.Creator<Turn> CREATOR
            = new Parcelable.Creator<Turn>() {
        public Turn createFromParcel(Parcel in) {
            return new Turn(in);
        }

        public Turn[] newArray(int size) {
            return new Turn[size];
        }
    };

    private Turn(Parcel in) {
        songs = new ArrayList<Song>();
        songs = in.readArrayList(ArrayList.class.getClassLoader());
        numberSong = in.readInt();
    }
}