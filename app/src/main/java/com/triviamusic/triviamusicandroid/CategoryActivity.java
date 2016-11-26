package com.triviamusic.triviamusicandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.triviamusic.triviamusicandroid.http.Api;
import com.triviamusic.triviamusicandroid.resources.Song;
import com.triviamusic.triviamusicandroid.resources.Turn;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jadac_000 on 08/11/2016.
 */

public class CategoryActivity extends AppCompatActivity {
    public String category = "rock";
    private Api api;
    public Turn turn;
    private ListView listview;

    static String[] categories = {
            "rock",
            "metal",
            "pop",
            "indie_alt",
            "edm_dance",
            "rnb",
            "county",
            "folk_americana",
            "soul",
            "jazz",
            "blues",
            "hiphop"};

    private int lastsong = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        initView();

        api =new Api(getApplicationContext());
        final CategoriesAdapter adapter = new CategoriesAdapter(getApplicationContext(),categories);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new MyClickListener());


    }

    private void initView(){
        listview = (ListView) findViewById(R.id.listView);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setTitle(R.string.categories);
    }

    private synchronized void setFlag() {
        this.lastsong++;
    }

    class MyClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String s = categories[(int) l];
            api = new Api(getApplicationContext());
            api.songs(s, new Api.VolleyCallback() {
                @Override
                public void onSuccess(JSONObject result) {
                    try {
                        if (result.getString("status").equals("error")) {
                            Log.d("MainActivity", "error");
                            return;
                        }
                    } catch (JSONException e) {

                    }
                    turn = new Turn(result);
                    for (int i = 0; i < turn.getNumberOfSongs(); i++) {
                        System.out.println("chiamata api...");
                        final Song s = turn.getSongs().get(i);
                        api.possibilities(s, new Api.VolleyCallback() {
                            @Override
                            public void onSuccess(JSONObject result) {
                                s.setPossibilities(result);
                                setFlag();
                                if (lastsong == turn.getNumberOfSongs()) {
                                    Intent in = new Intent(CategoryActivity.this, MainActivity.class);
                                    in.putExtra("turn", turn);
                                    startActivity(in);
                                    finish();
                                }
                            }
                        });


                    }


                }
            });
        }
    }

}

