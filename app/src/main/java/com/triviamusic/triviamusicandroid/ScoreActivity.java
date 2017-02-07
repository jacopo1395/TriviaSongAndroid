package com.triviamusic.triviamusicandroid;

import android.content.Intent;
import android.icu.text.AlphabeticIndex;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.triviamusic.triviamusicandroid.resources.Records;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jacopo on 04/02/2017.
 */
public class ScoreActivity extends AppCompatActivity {

    private long point;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String category;
    private int right;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        point = intent.getIntExtra("score", 0);
        right = intent.getIntExtra("right", 0);
        category = intent.getStringExtra("category");
        TextView score = (TextView) findViewById(R.id.score);
        TextView rightV = (TextView) findViewById(R.id.right);
        score.setText(String.valueOf(point));
        rightV.setText(String.valueOf(right));
        final Button ok = (Button) findViewById(R.id.ok);
        database = FirebaseDatabase.getInstance();
        user =  FirebaseAuth.getInstance().getCurrentUser();
        myRef = database.getReference().child("users").child(user.getUid()).child("records");

        // Read from the database
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Records record = new Records();
                Map m1 = (Map<String, Object>) dataSnapshot.getValue();


                if(m1 == null){
                    record=new Records();
                    record.scores=new HashMap<String,Long>();
                    record.scores.put(category, point);
                    myRef.setValue(record);
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    });
                }
                else{
                    Map<String,Long> m = (Map<String, Long>) m1.get("scores");
                    System.out.println(m);
                    record.setScores(m);
                    if (record.scores.get(category)==null){
                        System.out.println("null");
                        record.addRecord(category,point);
                        myRef.setValue(record);
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                            }
                        });
                    }
                    else {
                        long n=(long)record.scores.get(category);
                        if (point > n) {
                            System.out.println("maggiore");
                            //user =  FirebaseAuth.getInstance().getCurrentUser();
                            record.scores.put(category, point);
                            myRef.setValue(record);
                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    finish();
                                }
                            });
                        }
                        else{
                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    finish();
                                }
                            });
                        }
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });



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
