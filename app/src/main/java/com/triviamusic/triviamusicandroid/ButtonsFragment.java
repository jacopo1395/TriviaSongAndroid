package com.triviamusic.triviamusicandroid;

import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.triviamusic.triviamusicandroid.http.Api;
import com.triviamusic.triviamusicandroid.resources.Turn;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

/**
 * Created by jadac_000 on 24/11/2016.
 */

public class ButtonsFragment extends Fragment {
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button buttonRight;

    private String possibility1;
    private String possibility2;
    private String possibility3;
    private String possibility4;
    private String possibility5;
    private View view;
    private Callback callback;

    private Turn turn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main_buttons, container, false);
        initiView();

        return view;
    }

    private void initiView() {
        button1 = (Button) view.findViewById(R.id.button1);
        button2 = (Button) view.findViewById(R.id.button2);
        button3 = (Button) view.findViewById(R.id.button3);
        button4 = (Button) view.findViewById(R.id.button4);
//        imageView = (ImageView) findViewById(R.id.imageView);
        //imageButton = (ImageButton) findViewById(R.id.imageButton);

        button1.setOnClickListener(new MyOnClickListener());
        button2.setOnClickListener(new MyOnClickListener());
        button3.setOnClickListener(new MyOnClickListener());
        button4.setOnClickListener(new MyOnClickListener());
    }


    private void setButtons() {
        Random random = new Random();
        int n = random.nextInt(4) + 1;
        if (n == 1) {
            button1.setText(turn.getSongs().get(numberTurn).getTitle());
            buttonRight = button1;
        } else button1.setText(possibility1);
        if (n == 2) {
            button2.setText(turn.getSongs().get(numberTurn).getTitle());
            buttonRight = button2;
        } else button2.setText(possibility2);
        if (n == 3) {
            button3.setText(turn.getSongs().get(numberTurn).getTitle());
            buttonRight = button3;
        } else button3.setText(possibility3);
        if (n == 4) {
            button4.setText(turn.getSongs().get(numberTurn).getTitle());
            buttonRight = button4;
        } else button4.setText(possibility4);
    }


    public void getPossibilities() {
        api.possibilities(this.turn.getSongs().get(numberTurn), new Api.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    if (result.getString("status").equals("error")) {
                        return;
                    }
                    String title = turn.getSongs().get(numberTurn).getTitle();
                    possibility1 = result.getString("possibility1");
                    if (possibility1.equals(title))
                        possibility1 = result.getString("possibility5");

                    possibility2 = result.getString("possibility2");
                    if (possibility2.equals(title))
                        possibility2 = result.getString("possibility5");

                    possibility3 = result.getString("possibility3");
                    if (possibility3.equals(title))
                        possibility3 = result.getString("possibility5");

                    possibility4 = result.getString("possibility4");
                    if (possibility4.equals(title))
                        possibility4 = result.getString("possibility5");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setButtons();
            }
        });
    }


    public void setTurn(Turn turn){
        this.turn=turn;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (Callback) context;
    }

    class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            callback.ClickEvent((Button) view);
        }

    }


    interface Callback {
        void ClickEvent(Button pressedButton);
    }
}
