package com.triviamusic.triviamusicandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.triviamusic.triviamusicandroid.http.Api;
import com.triviamusic.triviamusicandroid.resources.Turn;

import org.json.JSONObject;

/**
 * Created by jadac_000 on 08/11/2016.
 */

public class CategoryActivity extends AppCompatActivity {
    public String category = "rock";
    private Api api;
    public Turn turn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        api = new Api(getApplicationContext());

        api.songs(this.category, new Api.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                turn = new Turn(result);
                Intent intent = new Intent(CategoryActivity.this , MainActivity.class);
                //intent.putExtra("category",);
                startActivity(intent);
                finish();
            }
        });

    }
}
