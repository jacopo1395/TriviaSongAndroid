package com.triviamusic.triviamusicandroid.resources;

/**
 * Created by Jacopo on 23/11/2016.
 */

public class Game {
    private int numberTurn = 0;
    private int points = 0;


    private void nextTurn() {
//        imageButton.setVisibility(View.VISIBLE);
//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                view.setVisibility(View.INVISIBLE);
//                ((ImageButton)view).setOnClickListener(null);
//            }
//        });
        numberTurn++;
//        button1.setBackgroundColor(Color.parseColor("grey"));
//        button2.setBackgroundColor(Color.parseColor("grey"));
//        button3.setBackgroundColor(Color.parseColor("grey"));
//        button4.setBackgroundColor(Color.parseColor("grey"));
        if (numberTurn < turn.getNumberOfSongs()) getPossibilities();
        else return;
    }

    public void addPoint() {
        this.points++;
    }
}
