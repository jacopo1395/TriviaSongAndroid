package com.triviamusic.triviamusicandroid.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.triviamusic.triviamusicandroid.R;
import com.triviamusic.triviamusicandroid.resources.Turn;

import java.util.Random;

/**
 * Created by jadac_000 on 24/11/2016.
 */

public class ButtonsFragment extends Fragment {
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button rightButton;

    private String possibility1;
    private String possibility2;
    private String possibility3;
    private String possibility4;
    private String possibility5;
    private View view;
    private ButtonCallback callback;

    private Turn turn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main_buttons, container, false);
        initView();
//        if(text!=null){
//            button1.setText(text[0]);
//            button1.setText(text[1]);
//            button1.setText(text[2]);
//            button1.setText(text[3]);
//        }

        Log.d("bf", "oncreate");

        return view;
    }

    private void initView() {
        button1 = (Button) view.findViewById(R.id.button1);
        button2 = (Button) view.findViewById(R.id.button2);
        button3 = (Button) view.findViewById(R.id.button3);
        button4 = (Button) view.findViewById(R.id.button4);
//        imageView = (ImageView) findViewById(R.id.imageView);
        //imageButton = (ImageButton) findViewById(R.id.imageButton);

        setListener();
    }


    public void setListener(){
        button1.setOnClickListener(new MyOnClickListener());
        button2.setOnClickListener(new MyOnClickListener());
        button3.setOnClickListener(new MyOnClickListener());
        button4.setOnClickListener(new MyOnClickListener());
    }

    public void resetListener(){
        button1.setOnClickListener(null);
        button2.setOnClickListener(null);
        button3.setOnClickListener(null);
        button4.setOnClickListener(null);
    }



    private void setButtons() {
        Random random = new Random();
        int n = random.nextInt(4) + 1;
        if (n == 1) {
            button1.setText(turn.getSongs().get(turn.getNumberSong()).getTitle());
            rightButton = button1;
        } else button1.setText(possibility1);
        if (n == 2) {
            button2.setText(turn.getSongs().get(turn.getNumberSong()).getTitle());
            rightButton = button2;
        } else button2.setText(possibility2);
        if (n == 3) {
            button3.setText(turn.getSongs().get(turn.getNumberSong()).getTitle());
            rightButton = button3;
        } else button3.setText(possibility3);
        if (n == 4) {
            button4.setText(turn.getSongs().get(turn.getNumberSong()).getTitle());
            rightButton = button4;
        } else button4.setText(possibility4);
    }


    public void setPossibilities(String[] poss) {
        String title = turn.getSongs().get(turn.getNumberSong()).getTitle();
        Random random = new Random();
        int n = random.nextInt(4);
        poss[n]=title;
        setText(poss);
        if(n==0) rightButton=button1;
        if(n==1) rightButton=button2;
        if(n==2) rightButton=button3;
        if(n==3) rightButton=button4;
//        possibility1 = poss[0];
//        if (possibility1.equals(title))
//            possibility1 = poss[4];
//
//        possibility2 = poss[1];
//        if (possibility2.equals(title))
//            possibility2 = poss[4];
//
//        possibility3 = poss[2];
//        if (possibility3.equals(title))
//            possibility3 = poss[4];
//
//        possibility4 = poss[3];
//        if (possibility4.equals(title))
//            possibility4 = poss[4];

//        setButtons();

    }


    public void setTurn(Turn turn){
        this.turn=turn;
    }

    public void resetColor() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    button1.setBackground(getResources().getDrawable(R.drawable.custom_button, getActivity().getTheme()));
                    button2.setBackground(getResources().getDrawable(R.drawable.custom_button, getActivity().getTheme()));
                    button3.setBackground(getResources().getDrawable(R.drawable.custom_button, getActivity().getTheme()));
                    button4.setBackground(getResources().getDrawable(R.drawable.custom_button, getActivity().getTheme()));
                } else {
                    button1.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    button2.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    button3.setBackground(getResources().getDrawable(R.drawable.custom_button));
                    button4.setBackground(getResources().getDrawable(R.drawable.custom_button));
                }
//            }
//        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (ButtonCallback) context;
    }

    public void setColor(Button pressedButton) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            rightButton.setBackground(getResources().getDrawable(R.drawable.custom_button_green, getActivity().getTheme()));
            if (!pressedButton.equals(rightButton))
                pressedButton.setBackground(getResources().getDrawable(R.drawable.custom_button_red, getActivity().getTheme()));
        } else {
            rightButton.setBackground(getResources().getDrawable(R.drawable.custom_button_green));
            if (!pressedButton.equals(rightButton))
                pressedButton.setBackground(getResources().getDrawable(R.drawable.custom_button_red));
        }
    }

    public String[] getPossibilities() {
        String[] s = new String[4];
        s[0] = (String) button1.getText();
        s[1] = (String) button2.getText();
        s[2] = (String) button3.getText();
        s[3] = (String) button4.getText();
        return s;
    }

    public void setText(String[] text) {
        button1.setText(text[0]);
        button2.setText(text[1]);
        button3.setText(text[2]);
        button4.setText(text[3]);
    }

    public int getRightButtonPos() {
        if (rightButton.equals(button1)) return 1;
        if (rightButton.equals(button2)) return 2;
        if (rightButton.equals(button3)) return 3;
        if (rightButton.equals(button4)) return 4;
        return 0;
    }

    public void setRightButtonPos(int i) {
        if (i == 0) rightButton = null;
        if (i == 1) rightButton = button1;
        if (i == 2) rightButton = button2;
        if (i == 3) rightButton = button3;
        if (i == 4) rightButton = button4;
    }


    class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Log.d("ButtonFragment", "click");
            resetListener();
            callback.ClickEvent((Button) view);
        }

    }


    public interface ButtonCallback {
        void ClickEvent(Button pressedButton);
    }

    public Button getRightButton() {
        return rightButton;
    }


}
