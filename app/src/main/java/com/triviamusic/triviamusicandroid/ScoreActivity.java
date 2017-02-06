package com.triviamusic.triviamusicandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.BundleCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jacopo on 04/02/2017.
 */
public class ScoreActivity extends AppCompatActivity {

    private int score;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        score=intent.getIntExtra("score",0);
        TextView point = (TextView) findViewById(R.id.score);
        point.setText(String.valueOf(score));

        database = FirebaseDatabase.getInstance();
        user =  FirebaseAuth.getInstance().getCurrentUser();
        myRef = database.getReference().child("users").child(user.getUid()).child("record");

        // Read from the database
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Long record = dataSnapshot.getValue(Long.class);
                if (score > record){
                    user =  FirebaseAuth.getInstance().getCurrentUser();
                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("/users/" + user.getUid()+"/record", score);
                    database.getReference().updateChildren(childUpdates);

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
