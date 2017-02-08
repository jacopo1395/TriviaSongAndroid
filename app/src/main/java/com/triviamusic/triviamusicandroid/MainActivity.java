package com.triviamusic.triviamusicandroid;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
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

    public String category;

    private Turn turn;
    private int points = 0;
    private int point2 = 0;
    private int round = 0;


    private FragmentManager fm;
    private ButtonsFragment fragment2;
    private PlayerFragment fragment1;
    private FragmentTransaction ft;
    private TextView pointView;
    private TextView point2View;
    private TextView roundView;
    private boolean flag;


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

        if (!flag) {
            System.out.println("flag " + flag);
            flag = true;
            Intent intent = getIntent();
            turn = intent.getParcelableExtra("turn");
            category = intent.getStringExtra("category");
            getTurn();
        }
    }


    private void initView(Bundle savedInstanceState) {
        pointView = (TextView) findViewById(R.id.points);
        point2View = (TextView) findViewById(R.id.point2);
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
        fragment1.setTurn(turn);
        fragment2.setTurn(turn);
        getPossibilities();

    }

    private void getPossibilities() {
        api.possibilities2(category, new Api.VolleyCallback() {
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
                        if(result.getString(x).equals(turn.getSong().getTitle()))
                            poss[i] = result.getString("possibility5");
                        else
                            poss[i] = result.getString(x);
                    }
                    fragment2.setPossibilities(poss);
                    fragment1.setPlayer();
                    System.out.println("main start");
                    fragment1.start();

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
        if (button.equals(fragment2.getRightButton())) return true;
        else return false;
    }


    private void nextSong() {
        turn.nextTurn();
        round++;
        roundView.setText(String.valueOf(round));
        fragment1.resetAlbumImage();
        fragment2.resetColor();
        fragment2.setListener();
        if (turn.getNumberSong() < turn.getNumberOfSongs()) getPossibilities();
        else{
            Intent i = new Intent(context, ScoreActivity.class);
            i.putExtra("right", this.points);
            i.putExtra("score", this.point2);
            i.putExtra("category",this.category);
            startActivity(i);
            finish();
        };


    }

    public void addPoint() {
        this.points++;
        point2+= (fragment1.getSeconds() -fragment1.getPosition());
        pointView.setText(String.valueOf(point2));
        //point2View.setText(String.valueOf(point2));
    }

    public boolean isFlag() {
        return flag;
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        Log.d("main", "save");
        savedInstanceState.putBoolean("flag", flag);
        savedInstanceState.putParcelable("turn", turn);
        savedInstanceState.putString("category", category);
        //savedInstanceState.putInt("turnround",turn.getNumberSong());
        savedInstanceState.putInt("points", points);
        savedInstanceState.putInt("round", round);

        PlayerFragment pf = (PlayerFragment) fm.findFragmentByTag("1");
        savedInstanceState.putInt("seconds", pf.getSeconds());
        savedInstanceState.putInt("position", pf.getPosition());
        savedInstanceState.putBoolean("flag", pf.isFlag());

        ButtonsFragment bf = (ButtonsFragment) fm.findFragmentByTag("2");
        savedInstanceState.putStringArray("possibilities", bf.getPossibilities());
        savedInstanceState.putInt("rightbutton", bf.getRightButtonPos());

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("main", "restore");

        flag = savedInstanceState.getBoolean("flag");
        turn = savedInstanceState.getParcelable("turn");
        category = savedInstanceState.getString("category");

        points = savedInstanceState.getInt("points");
        round = savedInstanceState.getInt("round");
        turn.setNumberSong(round);

        pointView.setText(String.valueOf(points));
        roundView.setText(String.valueOf(round));

        PlayerFragment pf = (PlayerFragment) fm.findFragmentByTag("1");
        pf.setTurn(turn);
        pf.setSeconds(savedInstanceState.getInt("seconds"));
        pf.setPosition(savedInstanceState.getInt("position"));
        pf.setFlag(savedInstanceState.getBoolean("flag"));

        ButtonsFragment bf = (ButtonsFragment) fm.findFragmentByTag("2");
        bf.setTurn(turn);
        bf.setText(savedInstanceState.getStringArray("possibilities"));
        int i = savedInstanceState.getInt("rightbutton");
        bf.setRightButtonPos(i);
    }
    @Override
    public void onBackPressed() {
        fragment1.getMP().pause();
       finish();
    }

    @Override
    public void ClickEvent(Button pressedButton) {
        //this.pressedButton=pressedButton;
        fragment1.stop();
        fragment1.showAlbum();
        fragment1.setNext();
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