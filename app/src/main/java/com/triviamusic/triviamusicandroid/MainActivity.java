package com.triviamusic.triviamusicandroid;

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

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener {
    private MediaPlayer mediaPlayer;
    private int mediaFileLengthInMilliseconds; // this value contains the song duration in milliseconds. Look at getDuration() method in MediaPlayer class

    private final Handler handler = new Handler();
    private Api api;
    // private ImageButton imageButton;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button buttonRight;
    private ImageView imageView;

    public String category = "rock";

    private Turn turn;

    private String possibility1;
    private String possibility2;
    private String possibility3;
    private String possibility4;
    private String possibility5;
    private ProgressBar progressBar;

    private Random random;
    private int n;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        random = new Random();
        api = new Api(this);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnCompletionListener(this);
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
                getPossibilities();
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

    private void setButton() {
        n = random.nextInt(4) + 1;
        if (n == 1) {
            button1.setText(turn.getSongs().get(numberTurn).getTitle());
            buttonRight = button1;
        } else button1.setText(possibility1);
        if (n == 2) {
            button2.setText(turn.getSongs().get(numberTurn).getTitle());
            buttonRight = button2;
        } else button2.setText(possibility2);
        if (n == 3) {
            button3.setText(turn.getSongs().get(numberTurn).getTitle());
            buttonRight = button3;
        } else button3.setText(possibility3);
        if (n == 4) {
            button4.setText(turn.getSongs().get(numberTurn).getTitle());
            buttonRight = button4;
        } else button4.setText(possibility4);
    }

    private void initView() {
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
//        imageView = (ImageView) findViewById(R.id.imageView);
        //imageButton = (ImageButton) findViewById(R.id.imageButton);

        button1.setOnClickListener(new MyOnClickListener());
        button2.setOnClickListener(new MyOnClickListener());
        button3.setOnClickListener(new MyOnClickListener());
        button4.setOnClickListener(new MyOnClickListener());

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(99); // It means 100% .0-99

//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                view.setVisibility(View.INVISIBLE);
//                ((ImageButton)view).setOnClickListener(null);
//            }
//        });
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


    class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Button b = (Button) view;
            if (checkAnswer(b)) {
                MainActivity.this.addPoint();
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                buttonRight.setBackground(getResources().getDrawable(R.drawable.custom_button_green,getTheme()));
            }
            else{
                buttonRight.setBackground(getResources().getDrawable(R.drawable.custom_button_green));
            }
            if (!b.equals(buttonRight)) b.setBackgroundColor(Color.parseColor("red"));
            MainActivity.this.mediaPlayer.reset();

            nextTurn();
        }
    }



    public void getPossibilities() {
        api.possibilities(MainActivity.this.turn.getSongs().get(numberTurn), new Api.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    if (result.getString("status").equals("error")) {
                        return;
                    }
                    String title = MainActivity.this.turn.getSongs().get(numberTurn).getTitle();
                    possibility1 = result.getString("possibility1");
                    if (possibility1.equals(title))
                        possibility1 = result.getString("possibility5");

                    possibility2 = result.getString("possibility2");
                    if (possibility2.equals(title))
                        possibility2 = result.getString("possibility5");

                    possibility3 = result.getString("possibility3");
                    if (possibility3.equals(title))
                        possibility3 = result.getString("possibility5");

                    possibility4 = result.getString("possibility4");
                    if (possibility4.equals(title))
                        possibility4 = result.getString("possibility5");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setButton();
                setPlayer();
            }
        });
    }

    private void primaryProgressBarUpdater() {
        progressBar.setProgress((int)(((float)mediaPlayer.getCurrentPosition()/mediaFileLengthInMilliseconds)*100)); // This math construction give a percentage of "was playing"/"song length"
        if (mediaPlayer.isPlaying()) {
            Runnable notification = new Runnable() {
                public void run() {
                    primaryProgressBarUpdater();
                }
            };
            handler.postDelayed(notification,1000);
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
}
