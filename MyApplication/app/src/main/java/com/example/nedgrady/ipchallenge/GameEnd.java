package com.example.nedgrady.ipchallenge;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by jake.williams on 11/09/2016.
 */
public class GameEnd extends AppCompatActivity {

    private GoogleApiClient client;
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_end);
        TextView endMessage = (TextView) findViewById(R.id.textView);
        Typeface font= Typeface.createFromAsset(getAssets(),"fonts/tradewinds.ttf");
        endMessage.setTypeface(font);
        TextView scoreMessage = (TextView) findViewById(R.id.textView2);
        scoreMessage.setTypeface(font);
        Bundle extras = getIntent().getExtras();
        Boolean win = false;
        String score = "";
        if (extras != null) {
            win = extras.getBoolean("win");
            score = extras.getString("score");
        }
        if (win)
            endMessage.setText("You Win!");
        else
            endMessage.setText("You Lose!");
        scoreMessage.setText("Score: " + score);
    }
}