package com.example.nedgrady.ipchallenge;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by lauren.weston on 11/09/2016.
 */
public class Start extends AppCompatActivity {

    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clue);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        TextView tx = (TextView)findViewById(R.id.title);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/jamscript.ttf");
        tx.setTypeface(custom_font);

        startButton = (Button) findViewById(R.id.start);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Start.this, Clue.class));
            }
        });
    }
}
