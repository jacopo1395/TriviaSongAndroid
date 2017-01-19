package com.triviamusic.triviamusicandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.triviamusic.triviamusicandroid.resources.Categories;

/**
 * Created by Jacopo on 24/11/2016.
 */


public class LauncherActivity extends AppCompatActivity {


    private Context context;
    private Button mainbutton;
    private Button login;
    private Button categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        //prima cosa da fare è controllare se c'è connessione ad internet!!

        Log.d("LauncherActivity", "Network OK");

        mainbutton = (Button) findViewById(R.id.main);
        login = (Button) findViewById(R.id.login);
        categories = (Button) findViewById(R.id.categories);
        mainbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }


}