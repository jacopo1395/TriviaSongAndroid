package com.triviamusic.triviamusicandroid.resources;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by jadac_000 on 08/11/2016.
 */

public class Categories {
    static String[] categories = {
            "rock",
            "metal",
            "pop",
            "indie_alt",
            "edm_dance",
            "rnb",
            "country",
            "folk_americana",
            "soul",
            "jazz",
            "blues",
            "hiphop"};

    public static String[] getCategories() {
        return categories;
    }

    public ArrayList<String> getArrayList(){
        return new ArrayList<String>(Arrays.asList(categories));
    }

    public static String get(String s) {
        if(s.equals("Rock")) return "rock";
        if(s.equals("Metal")) return "metal";
        if(s.equals("Pop")) return "pop";
        if(s.equals("Indie/Alternative")) return "indie_alt";
        if(s.equals("EDM Dance")) return "edm_dance";
        if(s.equals("RnB")) return "rnb";
        if(s.equals("Country")) return "country";
        if(s.equals("Folk Americana")) return "folk_americana";
        if(s.equals("Soul")) return "soul";
        if(s.equals("Jazz")) return "jazz";
        if(s.equals("Blues")) return "blues";
        if(s.equals("HipHop")) return "hiphop";
        return "rock";
    }
}
