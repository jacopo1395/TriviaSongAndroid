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
