package com.triviamusic.triviamusicandroid;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.triviamusic.triviamusicandroid.http.Api;
import com.triviamusic.triviamusicandroid.resources.Song;
import com.triviamusic.triviamusicandroid.resources.Turn;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener {
    private MediaPlayer mediaPlayer;
    private int mediaFileLengthInMilliseconds; // this value contains the song duration in milliseconds. Look at getDuration() method in MediaPlayer class

    private final Handler handler = new Handler();
    private Api api;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private ImageView imageView;

    public String category = "rock";

    private Turn turn;
    private int numberTurn = 0;
    private int points = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                    if(result.getString("result").equals("error")){
                        Log.d("MAinActivity","error");
                        return;
                    }
                } catch (JSONException e) {

                }
                MainActivity.this.turn = new Turn(result);
                setButton();
                setPlayer();
            }
        });


    }

    private void setPlayer() {
        //System.out.println(this.turn.getSongs().get(numberTurn));
        String link=this.turn.getSongs().get(numberTurn).getLink();
        System.out.println(link);
        try {
            mediaPlayer.setDataSource(link);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaFileLengthInMilliseconds = mediaPlayer.getDuration(); // gets the song length in milliseconds from URL
        mediaPlayer.start();
    }

    private void setButton() {
        button1.setOnClickListener(new MyOnClickListener());
        button2.setOnClickListener(new MyOnClickListener());
        button3.setOnClickListener(new MyOnClickListener());
        button4.setOnClickListener(new MyOnClickListener());

        button1.setText(this.turn.getSongs().get(this.numberTurn).getTitle());
        button2.setText(this.turn.getSongs().get(this.numberTurn).getTitle());
        button3.setText(this.turn.getSongs().get(this.numberTurn).getTitle());
        button4.setText(this.turn.getSongs().get(this.numberTurn).getTitle());
    }

    private void initView() {
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    @Override
    protected void onStop() {
        super.onStop();
        api.cancel();
    }

    private boolean checkAnswer() {
        return true;
    }


    class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (checkAnswer()) {
                MainActivity.this.addPoint();
            }
            MainActivity.this.numberTurn++;
            MainActivity.this.mediaPlayer.reset();
            nextTurn();
        }
    }

    private void nextTurn() {

    }

    public void addPoint() {
        this.points++;
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        /** MediaPlayer onCompletion event handler. Method which calls then song playing is complete*/
        // buttonPlayPause.setImageResource(R.drawable.button_play);
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        /** Method which updates the SeekBar secondary progress by current song loading from URL position*/
        //seekBarProgress.setSecondaryProgress(percent);

    }
}
