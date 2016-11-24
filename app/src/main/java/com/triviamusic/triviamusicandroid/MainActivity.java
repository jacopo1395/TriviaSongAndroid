package com.triviamusic.triviamusicandroid;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.triviamusic.triviamusicandroid.http.Api;
import com.triviamusic.triviamusicandroid.resources.Turn;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;

import static android.R.attr.fragment;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener,
        MediaPlayer.OnBufferingUpdateListener, ButtonsFragment.Callback{
    private MediaPlayer mediaPlayer;
    private int mediaFileLengthInMilliseconds; // this value contains the song duration in milliseconds. Look at getDuration() method in MediaPlayer class

    private final Handler handler = new Handler();
    private Api api;
    // private ImageButton imageButton;

    private ImageView imageView;

    public String category = "rock";

    private Turn turn;
    private int numberTurn = 0;
    private int points = 0;

    private ProgressBar progressBar;


    private ImageView nextButton;
    private ImageView pauseButton;
    private FragmentManager fm;
    private ButtonsFragment fragment2;
    private PlayerFragment fragment1;
    private FragmentTransaction ft;
    private Button pressedButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //random = new Random();
        api = new Api(this);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnCompletionListener(this);

        fm = getFragmentManager(); //if this statement is moved inside the if condition
        //the application crashes when the device is rotated

        if (savedInstanceState == null) {


            fragment1 = new PlayerFragment();
            fragment2 = new ButtonsFragment();

            ft = fm.beginTransaction();
            ft.add(R.id.player_fragment, fragment1, "1")
                    .add(R.id.buttons_fragment, fragment2, "2")
                    .commit();
        }

        initView();
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
                fragment2.setTurn(turn);
            }
        });


    }

    private void setPlayer() {
        //System.out.println(this.turn.getSongs().get(numberTurn));
        String link = this.turn.getSongs().get(numberTurn).getLink();
        System.out.println(link);
        try {
            mediaPlayer.setDataSource(link);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaFileLengthInMilliseconds = mediaPlayer.getDuration(); // gets the song length in milliseconds from URL
        mediaPlayer.start();
        primaryProgressBarUpdater();
    }


    private void initView() {


        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(99); // It means 100% .0-99

//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                view.setVisibility(View.INVISIBLE);
//                ((ImageButton)view).setOnClickListener(null);
//            }
//        });

        nextButton = (ImageView) findViewById(R.id.next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //nextSong();
            }
        });

        pauseButton = (ImageView) findViewById(R.id.pause);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        api.cancel();
    }

    private boolean checkAnswer(Button button) {
        if (button.getText().equals(turn.getSongs().get(numberTurn).getTitle())) return true;
        else return false;
    }


//    class MyOnClickListener implements View.OnClickListener {
//
//        @Override
//        public void onClick(View view) {
//            Button b = (Button) view;
//            if (checkAnswer(b)) {
//                MainActivity.this.addPoint();
//            }
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                buttonRight.setBackground(getResources().getDrawable(R.drawable.custom_button_green, getTheme()));
//                if (!b.equals(buttonRight))
//                    b.setBackground(getResources().getDrawable(R.drawable.custom_button_red, getTheme()));
//            } else {
//                buttonRight.setBackground(getResources().getDrawable(R.drawable.custom_button_green));
//                if (!b.equals(buttonRight))
//                    b.setBackground(getResources().getDrawable(R.drawable.custom_button_red));
//            }
//
//            MainActivity.this.mediaPlayer.reset();
//
//            //nextSong();
//        }
//    }
//
//    private void nextSong() {
//
//        numberTurn++;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            button1.setBackground(getResources().getDrawable(R.drawable.custom_button,getTheme()));
//            button2.setBackground(getResources().getDrawable(R.drawable.custom_button,getTheme()));
//            button3.setBackground(getResources().getDrawable(R.drawable.custom_button,getTheme()));
//            button4.setBackground(getResources().getDrawable(R.drawable.custom_button,getTheme()));
//        }
//        else{
//            button1.setBackground(getResources().getDrawable(R.drawable.custom_button));
//            button2.setBackground(getResources().getDrawable(R.drawable.custom_button));
//            button3.setBackground(getResources().getDrawable(R.drawable.custom_button));
//            button4.setBackground(getResources().getDrawable(R.drawable.custom_button));
//        }
//        if (numberTurn < turn.getNumberOfSongs()) getPossibilities();
//        else return;
//    }

    public void addPoint() {
        this.points++;
    }

    private void primaryProgressBarUpdater() {
        progressBar.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / mediaFileLengthInMilliseconds) * 100)); // This math construction give a percentage of "was playing"/"song length"
        if (mediaPlayer.isPlaying()) {
            Runnable notification = new Runnable() {
                public void run() {
                    primaryProgressBarUpdater();
                }
            };
            handler.postDelayed(notification, 1000);
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        /** MediaPlayer onCompletion event handler. Method which calls then song playing is complete*/
        // buttonPlayPause.setImageResource(R.drawable.button_play);
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        /** Method which updates the SeekBar secondary progress by current song loading from URL position*/
        //progressBar.setSecondaryProgress(percent);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void ClickEvent(Button pressedButton) {
        FragmentManager fm = getFragmentManager();
        ButtonsFragment bf = (ButtonsFragment) fm.findFragmentById(R.id.buttons_fragment);

        this.pressedButton=pressedButton;
    }
}