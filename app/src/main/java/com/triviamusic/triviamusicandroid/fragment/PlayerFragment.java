package com.triviamusic.triviamusicandroid.fragment;

import android.app.Fragment;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.triviamusic.triviamusicandroid.R;
import com.triviamusic.triviamusicandroid.resources.Turn;

import java.io.IOException;

/**
 * Created by Jacopo on 24/11/2016.
 */

public class PlayerFragment extends Fragment implements MediaPlayer.OnCompletionListener,
        MediaPlayer.OnBufferingUpdateListener {

    private MediaPlayer mediaPlayer;
    private int mediaFileLengthInMilliseconds; // this value contains the song duration in milliseconds. Look at getDuration() method in MediaPlayer class

    private final Handler handler = new Handler();

    private PlayerCallback callback;
    private ProgressBar progressBar;
    private ImageView prevButton;
    private ImageView nextButton;
    private ImageView pauseButton;
    private View view;
    private Turn turn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnCompletionListener(this);
        Log.d("pf", "oncreate");
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main_player, container, false);
        initView();
//        primaryProgressBarUpdater();
        return view;
    }

    private void initView() {

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setMax(99); // It means 100% .0-99


        nextButton = (ImageView) view.findViewById(R.id.next);
        pauseButton = (ImageView) view.findViewById(R.id.pause);
        prevButton = (ImageView) view.findViewById(R.id.preview);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.ClickEvent("next");
            }
        });
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                } else {
                    mediaPlayer.start();
                    primaryProgressBarUpdater();
                }
            }
        });
    }


    public void setPlayer() {
        //System.out.println(this.turn.getSongs().get(numberTurn));
        String link = turn.getSongs().get(turn.getNumberSong()).getLink();
        System.out.println(link);
        try {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.setDataSource(link);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaFileLengthInMilliseconds = mediaPlayer.getDuration(); // gets the song length in milliseconds from URL
        mediaPlayer.start();
        primaryProgressBarUpdater();
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
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


    public int getSeconds() {
        return mediaFileLengthInMilliseconds;
    }

    public void setSeconds(int mediaFileLengthInMilliseconds) {
        this.mediaFileLengthInMilliseconds = mediaFileLengthInMilliseconds;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (PlayerFragment.PlayerCallback) context;
    }

    public void nextSong() {
        setPlayer();
    }

    public interface PlayerCallback {
        void ClickEvent(String pressed);
    }

}