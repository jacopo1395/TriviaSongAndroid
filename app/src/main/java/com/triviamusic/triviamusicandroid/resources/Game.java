package com.triviamusic.triviamusicandroid.resources;

import android.util.Log;

import com.triviamusic.triviamusicandroid.MainActivity;
import com.triviamusic.triviamusicandroid.http.Api;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Jacopo on 23/11/2016.
 */

public class Game {
    private int TotTurns = 5;
    private int numberTurn = 0;
    private int points = 0;
    private ArrayList<Turn> turns;


    public void addPoint() {
        this.points++;
    }
}
