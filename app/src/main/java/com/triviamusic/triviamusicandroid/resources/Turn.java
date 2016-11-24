package com.triviamusic.triviamusicandroid.resources;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jadac_000 on 08/11/2016.
 */

public class Turn {
    ArrayList<Song> songs;

    public Turn(JSONObject result) {
        songs=new ArrayList<Song>(5);
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

    public int getNumberOfSongs() {
        if (songs == null) return 0;
        return songs.size();
    }
}