package com.example.nedgrady.ipchallenge;
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
    public static final long MAX_TIME = 1000 * 30; //second number is # of seconds
    private TextView levelText;
    private TextView hintText;
    private TextView countDownTextView;
    private TextView scoreText;
    private EditText inputField;
    private ImageView userImageView;
    private Button hintButtonText;
    private Button hintButtonZoom;
    private CountDownTimer s;
    private long score = 100;
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
        hintButtonText = (Button) findViewById(R.id.hintButtonText);
        hintButtonZoom = (Button) findViewById(R.id.hintButtonZoom);

        //Set font
        Typeface font= Typeface.createFromAsset(getAssets(),"fonts/tradewinds.ttf");
        hintText.setTypeface(font);
        levelText.setTypeface(font);
        countDownTextView.setTypeface(font);
        inputField.setTypeface(font);

        // Get up the first level
        currentLevelData = nextLevel();
        showLevel(currentLevelData);


        // The time remaining
        s = new CountDownTimer(50000, 1000) {
            public void onTick(long millisUntilFinished) {
                seconds = millisUntilFinished / 1000;
                countDownTextView.setText(Long.toString(seconds));
                scoreDown(1);
            }
            public void onFinish() {
                countDownTextView.setText("Times up!");
            }
        }.start();
        addActionListeners();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    //Get the image, the answer and the hint for the next level
    private LevelData nextLevel() {
        levelText.setText("Level " + ++currentLevel);
        return new LevelData(currentLevel, this);
    }


    private void onSubmit(TextView v){
        //Checking whether the user's answer is correct
        Log.d("action", "Checking if the users answer, '" + getUserAnswer() + "' is correct (" + currentLevelData.getAnswer()+ ")");

        if(getUserAnswer().toLowerCase().equals(currentLevelData.getAnswer().toLowerCase())){
            //Increase score
            scoreUp(seconds * 20);
            //If you've still got more levels to play, get the next level
            if(currentLevel < MAX_LEVELS){
                hintText.setText("");
                currentLevelData = nextLevel();
                showLevel(currentLevelData);
                s.cancel();
                s = new CountDownTimer(50000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        countDownTextView.setText(Long.toString(millisUntilFinished / 1000));
                    }
                    public void onFinish() {
                        countDownTextView.setText("Times up!");
                    }
                }.start();
            } else {
                endGame();
            }
        }else{
            //Decrease score if you entered a wrong answer
            scoreDown(100);
        }
        resetUI();
    }

    private void endGame() {
        hintText.setText("Well Done");
    }

    /*
     *@param currentLevelData LevelData - the level to show to the screen.
     *
     * Make the image appear
     */
    private void showLevel(LevelData currentLevelData) {
        userImageView.setImageResource(currentLevelData.getImage());
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
    }

    private void hintButtonTextClicked() {
        hintText.setText(currentLevelData.getHint());
    }

    private void hintButtonZoomClicked() {
        userImageView.setImageResource(currentLevelData.getImageHint());
    }

    private void scoreUp(long score){
        this.score += score;
        scoreText.setText(Long.toString(this.score));
    }

    private void scoreDown(long score){
        this.score -= score;
        scoreText.setText(Long.toString(this.score));
    }
}
