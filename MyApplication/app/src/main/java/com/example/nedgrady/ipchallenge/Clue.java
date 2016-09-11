package com.example.nedgrady.ipchallenge;

import android.content.res.AssetManager;
import android.net.Uri;
import android.support.v4.view.ViewGroupCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.*;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.FileReader;

public class Clue extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private int currentLevel;
    private LevelData currentLevelData;

    public static final int MAX_LEVELS = 4;

    private TextView hintText;
    private EditText userEditText;
    private ImageView userImageView;
    private Button hintButtonText;
    private Button hintButtonZoom;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clue);
        hintText = (TextView) findViewById(R.id.textView);
        userEditText = (EditText) findViewById(R.id.editText);
        userImageView = (ImageView) findViewById(R.id.imageView);
        hintButtonText = (Button) findViewById(R.id.hintButtonText);
        hintButtonZoom = (Button) findViewById(R.id.hintButtonZoom);
        currentLevelData = nextLevel();
        showLevel(currentLevelData);
        addActionListeners();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private LevelData nextLevel() {
        Log.d("", "Next Level!");
        //Get the level data: the image, the answer and the hint
        return new LevelData(++currentLevel, this);
    }

    private void onSubmit(TextView v){
        //Checking whether the user's answer is correct
        Log.d("action", "Checking if the users answer, '" + getUserAnswer() + "' is correct (" + currentLevelData.getAnswer()+ ")");
        if(getUserAnswer().toLowerCase().equals(currentLevelData.getAnswer().toLowerCase())){
            //If you've still got more levels to play, get the next level
            if(currentLevel < MAX_LEVELS){
                hintText.setText("");
                currentLevelData = nextLevel();
                showLevel(currentLevelData);
            } else
                 endGame();
        }
        resetUI();
    }

    private void endGame() {
        hintText.setText("Well Done");
    }

    /*
     *@param currentLevelData LevelData - the level to show to the screen.
     */
    private void showLevel(LevelData currentLevelData) {
        //Make image appear
        userImageView.setImageResource(currentLevelData.getImage());
    }

    private void resetUI(){
        userEditText.setText("");
    }
    /**
     * @return String - text that user has input into the answer TextView
     */
    private String getUserAnswer(){
        return userEditText.getText().toString();
    }

    private void addActionListeners(){
        userEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //if the tick button is clicked
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    onSubmit(v);
                }
                return false;
            }
        });
        hintButtonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hintButtonTextClicked();
            }
        });
        hintButtonZoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hintButtonZoomClicked();
            }
        });
    }

    private void hintButtonTextClicked() {
        hintText.setText(currentLevelData.getHint());
    }

    private void hintButtonZoomClicked() {
        userImageView.setImageResource(currentLevelData.getImageHint());
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Clue Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.nedgrady.ipchallenge/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Clue Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.nedgrady.ipchallenge/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
