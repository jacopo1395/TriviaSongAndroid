package com.triviamusic.triviamusicandroid.resources;

import android.app.Application;
import android.media.MediaPlayer;

/**
 * Created by Jacopo on 07/02/2017.
 */

public class ApplicationSupport extends Application {
    private MediaPlayer mp;

    public MediaPlayer getMP() {
        return mp;
    }

    public void setMP(MediaPlayer mp) {
        this.mp = mp;
    }

    @Override
    public void onCreate() {
        super.onCreate();


    }

}
