package com.triviamusic.triviamusicandroid;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.triviamusic.triviamusicandroid.fragment.ButtonsFragment;
import com.triviamusic.triviamusicandroid.fragment.PlayerFragment;
import com.triviamusic.triviamusicandroid.http.Api;
import com.triviamusic.triviamusicandroid.resources.Turn;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements ButtonsFragment.ButtonCallback, PlayerFragment.PlayerCallback {
    public Context context;
    private Api api;

    public String category = "rock";

    private Turn turn;
    private int points = 0;
    private int round = 0;


    private FragmentManager fm;
    private ButtonsFragment fragment2;
    private PlayerFragment fragment1;
    private FragmentTransaction ft;
    private TextView pointView;
    private TextView roundView;
    private boolean flag = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        api = new Api(this);
        Log.d("Main", "oncreate");
        initView(savedInstanceState);

        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            flag = savedInstanceState.getBoolean("flag");

        }

        if (flag) {
            flag = false;
            getTurn();
        }
    }


    private void initView(Bundle savedInstanceState) {
        pointView = (TextView) findViewById(R.id.points);
        roundView = (TextView) findViewById(R.id.round);

        fm = getFragmentManager(); //if this statement is moved inside the if condition
        //the application crashes when the device is rotated

        if (savedInstanceState == null) {

            fragment1 = new PlayerFragment();
            fragment2 = new ButtonsFragment();

            ft = fm.beginTransaction();
            ft.add(R.id.player_fragment, fragment1, "1")
                    .add(R.id.buttons_fragment, fragment2, "2")
                    .commit();
        } else {
            fragment1 = (PlayerFragment) fm.findFragmentByTag("1");
            fragment2 = (ButtonsFragment) fm.findFragmentByTag("2");
        }


    }


    private void getTurn() {
        //get 5 songs
        api.songs(this.category, new Api.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    if (result.getString("status").equals("error")) {
                        Log.d("MainActivity", "error");
                        return;
                    }
                } catch (JSONException e) {

                }
                MainActivity.this.turn = new Turn(result);
                fragment1.setTurn(turn);
                fragment2.setTurn(turn);
                getPossibilities();
            }
        });

    }

    private void getPossibilities() {
        api.possibilities(this.turn.getSongs().get(turn.getNumberSong()), new Api.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    if (result.getString("status").equals("error")) {
                        return;
                    }
                    int n = result.getInt("total");
                    String[] poss = new String[n];
                    for (int i = 0; i < n; i++) {
                        String x = "possibility" + (i + 1);
                        poss[i] = result.getString(x);
                    }
                    fragment2.setPossibilities(poss);
                    fragment1.setPlayer();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        api.cancel();
    }

    private boolean checkAnswer(Button button) {
        System.out.println(button);
        System.out.println(fragment2);
        System.out.println(fragment2.getRightButton());
        if (button.equals(fragment2.getRightButton())) return true;
        else return false;
    }


    private void nextSong() {
        turn.nextTurn();
        round++;
        fragment1.nextSong();
        fragment2.resetColor();
        if (turn.getNumberSong() < turn.getNumberOfSongs()) getPossibilities();
        else return;
    }

    public void addPoint() {
        this.points++;
        pointView.setText(String.valueOf(points));
    }


    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        Log.d("main", "save");
        savedInstanceState.putBoolean("flag", flag);
        savedInstanceState.putParcelable("turn", turn);
        //savedInstanceState.putInt("turnround",turn.getNumberSong());
        savedInstanceState.putInt("points", points);
        savedInstanceState.putInt("round", round);

        PlayerFragment pf = (PlayerFragment) fm.findFragmentByTag("1");
        savedInstanceState.putInt("seconds", pf.getSeconds());

        ButtonsFragment bf = (ButtonsFragment) fm.findFragmentByTag("2");
        savedInstanceState.putStringArray("possibilities", bf.getPossibilities());
        savedInstanceState.putInt("rightbutton", bf.getRightButtonPos());
        System.out.println(bf.getRightButtonPos());


        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("main", "restore");

        flag = savedInstanceState.getBoolean("flag");
        turn = savedInstanceState.getParcelable("turn");

        points = savedInstanceState.getInt("points");
        round = savedInstanceState.getInt("round");
        turn.setNumberSong(round);

        pointView.setText(String.valueOf(points));
        roundView.setText(String.valueOf(round));

        PlayerFragment pf = (PlayerFragment) fm.findFragmentByTag("1");
        pf.setTurn(turn);
        pf.setSeconds(savedInstanceState.getInt("seconds"));

        ButtonsFragment bf = (ButtonsFragment) fm.findFragmentByTag("2");
        bf.setTurn(turn);
        bf.setText(savedInstanceState.getStringArray("possibilities"));
        int i = savedInstanceState.getInt("rightbutton");
        bf.setRightButtonPos(i);
        System.out.println(i);
    }


    @Override
    public void ClickEvent(Button pressedButton) {
        //this.pressedButton=pressedButton;
        if (checkAnswer(pressedButton)) {
            addPoint();
        }
        fragment2.setColor(pressedButton);
    }

    @Override
    public void ClickEvent(String pressed) {
        nextSong();
    }
}