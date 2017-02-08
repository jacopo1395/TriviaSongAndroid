package com.triviamusic.triviamusicandroid.resources;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jacopo on 07/02/2017.
 */

@IgnoreExtraProperties
public class Records {

    public Map<String,Long> scores;

    public Records() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
        scores = new HashMap<String, Long>();
        scores.put("rock", (long) 0);
        scores.put("metal", (long) 0);
        scores.put("pop", (long) 0);
        scores.put("indie_alt", (long) 0);
        scores.put("edm_dance", (long) 0);
        scores.put("rnb", (long) 0);
        scores.put("county", (long) 0);
        scores.put("folk_americana", (long) 0);
        scores.put("soul", (long) 0);
        scores.put("jazz", (long) 0);
        scores.put("blues", (long) 0);
        scores.put("hiphop", (long) 0);
    }

    public void addRecord(String c, long point){
        scores.put(c,point);
    }

    public void setScores(Map<String,Long> scores) {
        this.scores = scores;
    }

    @Exclude
    public Map<String, Long> toMap() {
        return scores;
    }

}
