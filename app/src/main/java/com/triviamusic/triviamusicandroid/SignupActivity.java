package com.triviamusic.triviamusicandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.triviamusic.triviamusicandroid.resources.Records;
import com.triviamusic.triviamusicandroid.resources.Records;
import com.triviamusic.triviamusicandroid.resources.User;

import java.util.regex.Pattern;

/**
 * Created by jadac_000 on 20/01/2017.
 */

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "TAG";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        final EditText email = (EditText) findViewById(R.id.edit_email);
        final EditText name = (EditText) findViewById(R.id.edit_username);
        final EditText password = (EditText) findViewById(R.id.edit_password);
        final EditText password_confirm = (EditText) findViewById(R.id.edit_password_confirm);
        Button signup = (Button) findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_text = email.getText().toString();
                String name_text = name.getText().toString();
                String password_text = password.getText().toString();
                String pass_conf_text = password_confirm.getText().toString();
                if (!password_text.equals(pass_conf_text)) {
                    Snackbar.make(view, getResources().getString(R.string.password_error), Snackbar.LENGTH_SHORT);
                } else {
                    String regex = "[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}";
                    if (!Pattern.matches(regex, email_text)) {
                        Snackbar.make(view, getResources().getString(R.string.email_error), Snackbar.LENGTH_SHORT);
                    } else {
                        if(password_text.length()<6){
                            Snackbar.make(view, getResources().getString(R.string.password_error_len), Snackbar.LENGTH_SHORT);
                        }
                        else {
                            signUp(name_text, email_text, password_text);
                        }
                    }
                }
                System.out.println("fine");
            }
        });
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Intent intent = new Intent(getApplicationContext(), LauncherActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }

            }
        };



    }

    private void signUp(final String name, final String email_text, String password_text) {
        System.out.println(email_text + " " + password_text);
        mAuth.createUserWithEmailAndPassword(email_text, password_text)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            DatabaseReference mDatabase;
                            mDatabase = FirebaseDatabase.getInstance().getReference();
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            User u = new User(email_text, name);
                            mDatabase.child("users").child(user.getUid()).setValue(u);
                            Intent i = new Intent(getApplicationContext(), LauncherActivity.class);
                            startActivity(i);
                            finish();
                        }

                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
