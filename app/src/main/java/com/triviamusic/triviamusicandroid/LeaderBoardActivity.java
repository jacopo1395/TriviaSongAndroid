package com.triviamusic.triviamusicandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.triviamusic.triviamusicandroid.adapter.RecordAdapter;
import com.triviamusic.triviamusicandroid.resources.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jacopo on 07/02/2017.
 */
public class LeaderBoardActivity extends AppCompatActivity{
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private RecyclerView recyclerview;
    private LinearLayoutManager mLayoutManager;
    private RecordAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        recyclerview = (RecyclerView) findViewById(R.id.list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerview.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(mLayoutManager);



        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Map<String,Object> map = (HashMap<String,Object>)dataSnapshot.getValue();
                ArrayList<User> users = new ArrayList<User>();
                System.out.println(map);
                for(Map.Entry<String, Object> entry : map.entrySet()){
                    System.out.println(entry.getKey() +" :: "+ entry.getValue());
                    users.add(new User((HashMap<String, Object>) entry.getValue()));
                }
                mAdapter = new RecordAdapter(users);
                recyclerview.setAdapter(mAdapter);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
    }
}
