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

    private long score;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String category;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        score = intent.getIntExtra("score", 0);
        category = intent.getStringExtra("category");
        TextView point = (TextView) findViewById(R.id.score);
        point.setText(String.valueOf(score));
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
                Map<String,Long> m = (Map<String, Long>) m1.get("scores");
                System.out.println(m);
                record.setScores(m);

                if(m == null){
                    record=new Records();
                    record.scores=new HashMap<String,Long>();
                    record.scores.put(category,score);
                    myRef.setValue(record);
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    });
                }
                else{
                    if (record.scores.get(category)==null){
                        System.out.println("null");
                        record.addRecord(category,score);
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
                        if (score > n) {
                            System.out.println("maggiore");
                            //user =  FirebaseAuth.getInstance().getCurrentUser();
                            record.scores.put(category, score);
                            myRef.setValue(record);
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
