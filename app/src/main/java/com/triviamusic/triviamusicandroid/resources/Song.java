package com.triviamusic.triviamusicandroid.resources;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jadac_000 on 05/11/2016.
 */

public class Song {
    private String title;
    private String album;
    private String link;
    private String author;

    public Song(JSONObject result) {
        try {
            this.album = result.getString("album");
            this.title = result.getString("title");
            this.author = result.getString("author");
            this.link = result.getString("link");

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

    @Override
    public String toString() {
        return "Song{" +
                "title='" + title + '\'' +
                ", album='" + album + '\'' +
                ", link='" + link + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
