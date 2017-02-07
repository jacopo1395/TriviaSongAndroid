package com.triviamusic.triviamusicandroid.resources;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Jacopo on 07/02/2017.
 */

@IgnoreExtraProperties
public class User {

    public String username;
    public String email;
    public Records record;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String email, String name) {
        this.username = name;
        this.email = email;
    }

    public User(HashMap<String,Object> value) {
        email = (String)value.get("email");
        username = (String)value.get("name");
        Map m = (Map<String,Object>)value.get("records");
        if(m!=null) {
            record = new Records();
            record.scores = (Map<String, Long>) m.get("scores");
        }
    }

    public Records getRecord() {
        return record;
    }

    public void setRecord(Records record) {
        this.record = record;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("username", username);
        result.put("email", email);
        result.put("name", username);
        result.put("records", record);

        return result;
    }
}