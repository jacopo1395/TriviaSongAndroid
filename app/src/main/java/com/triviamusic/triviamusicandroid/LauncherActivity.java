package com.triviamusic.triviamusicandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Jacopo on 24/11/2016.
 */


public class LauncherActivity extends AppCompatActivity {


    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        //prima cosa da fare è controllare se c'è connessione ad internet!!

        Log.d("LauncherActivity", "Network OK");

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        finish();

    }


}