package com.example.nedgrady.ipchallenge;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v4.view.ViewGroupCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
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
    public static final long MAX_TIME = 1000 * 60; //second number is # of seconds
    private TextView levelText;
    private TextView hintText;
    private TextView countDownTextView;
    private TextView scoreText;
    private EditText inputField;
    private ImageView userImageView;
    private Button correctBtn;
    private Button hintButtonText;
    private Button hintButtonZoom;
    private CountDownTimer s;
    public long score = 100;
    private long seconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clue);

        //Page Elements
        levelText = (TextView) findViewById(R.id.level);
        userImageView = (ImageView) findViewById(R.id.imageView);
        hintText = (TextView) findViewById(R.id.hint);
        countDownTextView = (TextView) findViewById(R.id.time);
        scoreText = (TextView) findViewById(R.id.scoreText);
        inputField = (EditText) findViewById(R.id.inputText);
        correctBtn = (Button) findViewById(R.id.correct);
        hintButtonText = (Button) findViewById(R.id.hintButtonText);
        hintButtonZoom = (Button) findViewById(R.id.hintButtonZoom);

        //Set font
        Typeface font= Typeface.createFromAsset(getAssets(),"fonts/tradewinds.ttf");
        hintText.setTypeface(font);
        levelText.setTypeface(font);
        countDownTextView.setTypeface(font);
        scoreText.setTypeface(font);
        inputField.setTypeface(font);

        // Get up the first level
        currentLevelData = nextLevel();
        userImageView.setImageResource(currentLevelData.getImage());
        correctBtn.setVisibility(View.INVISIBLE);

        // The time remaining

        startTimer();
        addActionListeners();

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }

    //Get the image, the answer and the hint for the next level
    private LevelData nextLevel() {
        levelText.setText("Level " + ++currentLevel);
        return new LevelData(currentLevel, this);
    }

    /*
     *@param currentLevelData TextView - the input from the user.
     *
     * Check if the user has entered a correct answer
     * If they have, then show them the original image and let them be able to
     * get to the next level via a button that appears. If they entered a wrong score,
     * they lose points and their answer gets removed from the input box.
     *
     */
    private void startTimer() {
        if (s != null)
            s.cancel();
        s = new CountDownTimer(MAX_TIME, 1000) {
            public void onTick(long millisUntilFinished) {
                seconds = millisUntilFinished / 1000;
                countDownTextView.setText(Long.toString(seconds));
                scoreDown(2);
            }
            public void onFinish() {
                countDownTextView.setText("Times up!");
                Intent i = new Intent(Clue.this, GameEnd.class);
                i.putExtra("win", false);
                i.putExtra("score", Long.toString(score));
                startActivity(i);
            }
        }.start();
    }

    private void onSubmit(TextView v){
        Log.d("action", "Checking if the users answer, '" + getUserAnswer() + "' is correct (" + currentLevelData.getAnswer()+ ")");
        if(getUserAnswer().toLowerCase().equals(currentLevelData.getAnswer().toLowerCase())){
            scoreUp(seconds * 20);
            revealAnswer();

        } else {
            //Decrease score if you entered a wrong answer
            scoreDown(100);
        }
        resetUI();
    }

    private void endGame() {
        s.cancel();
        Intent i = new Intent(Clue.this, GameEnd.class);
        i.putExtra("win", true);
        i.putExtra("score", Long.toString(score));
        startActivity(i);
    }

    //Clear the text that the user has entered
    private void resetUI(){
        inputField.setText("");
    }

    /**
     * @return String - text that user has input into the answer TextView
     */
    private String getUserAnswer(){
        return inputField.getText().toString();
    }

    private void addActionListeners(){
        inputField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
        correctBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Inactivate button
                correctBtn.setVisibility(View.INVISIBLE);
                if(currentLevel < MAX_LEVELS) {
                    hintButtonZoom.setVisibility(View.VISIBLE);
                    hintButtonText.setVisibility(View.VISIBLE);
                    inputField.setVisibility(View.VISIBLE);
                    newLevel();
                } else {
                    endGame();
                }
            }
        });
    }

    private void newLevel() {
        //Show next level
        hintText.setText("");
        currentLevelData = nextLevel();
        userImageView.setImageResource(currentLevelData.getImage());
        startTimer();
    }

    private void hintButtonTextClicked() {
        hintText.setText(currentLevelData.getHint());
    }

    private void hintButtonZoomClicked() {
        userImageView.setImageResource(currentLevelData.getImageHint());
    }

    private void revealAnswer() {
        s.cancel();
        userImageView.setImageResource(currentLevelData.getImageAns());
        hintButtonZoom.setVisibility(View.INVISIBLE);
        hintButtonText.setVisibility(View.INVISIBLE);
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        inputField.setVisibility(View.INVISIBLE);
        correctBtn.setVisibility(View.VISIBLE);
    }

    private void scoreUp(long score){
        this.score += score;
        scoreText.setText("Score: " + Long.toString(this.score));
    }

    private void scoreDown(long score){
        this.score -= score;
        scoreText.setText("Score: " + Long.toString(this.score));
    }
}
