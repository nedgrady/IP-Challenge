package com.example.nedgrady.ipchallenge;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

/**
 * Created by lauren.weston on 11/09/2016.
 */
public class main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clue);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
}
