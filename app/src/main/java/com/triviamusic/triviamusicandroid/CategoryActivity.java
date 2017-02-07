package com.triviamusic.triviamusicandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.triviamusic.triviamusicandroid.adapter.CategoriesAdapter;
import com.triviamusic.triviamusicandroid.http.Api;
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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    class MyClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            final String s = categories[(int) l];
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
                    Intent in = new Intent(CategoryActivity.this, MainActivity.class);
                    in.putExtra("turn", turn);
                    in.putExtra("category",s);
                    startActivity(in);
                    finish();
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

