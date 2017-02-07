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
            "county",
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
}
