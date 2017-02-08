package com.triviamusic.triviamusicandroid;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.triviamusic.triviamusicandroid.adapter.RecordAdapter;
import com.triviamusic.triviamusicandroid.resources.Categories;
import com.triviamusic.triviamusicandroid.resources.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jacopo on 07/02/2017.
 */
public class LeaderBoardActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
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
        Toolbar bar = (Toolbar) findViewById(R.id.bar);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);





        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerview.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(mLayoutManager);



        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("users");

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        retrive(Categories.get(adapterView.getAdapter().getItem(i).toString()));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        retrive("rock");
    }

    private void retrive(final String cat){
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
                mAdapter = new RecordAdapter(order(users, cat), cat);
                recyclerview.setAdapter(mAdapter);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
    }

    public ArrayList<User> order(ArrayList<User> user, String cat) {
        System.out.println(cat);
        ArrayList<User> result = new ArrayList<>();
        long max;
        int j;
        User r = null;
        System.out.println(user.size());
        while (!user.isEmpty()) {
            max = user.get(0).getRecord().scores.get(cat);
            r = user.get(0);
            j=0;
            for (int i = 0; i < user.size(); i++) {
                if (user.get(i).getRecord().scores.get(cat) > max) {
                    max = user.get(i).getRecord().scores.get(cat);
                    r = user.get(i);
                    j=i;
                }
            }
            System.out.println(user.size());
            System.out.println(r);
            user.remove(j);
            result.add(r);
        }
        System.out.println("fine");
        return result;

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
