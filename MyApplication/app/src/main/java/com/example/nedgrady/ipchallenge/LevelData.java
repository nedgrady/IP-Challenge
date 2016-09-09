package com.example.nedgrady.ipchallenge;
import android.content.res.AssetManager;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

public class LevelData {

	private String ans;	
	private String hint;
	private int img;
    private AssetManager am;
    private boolean debug = false;

	public LevelData(int id, AppCompatActivity app) {
        am = app.getApplicationContext().getAssets();
        if (!debug)
          getLine(id, app);
        else
        {
            ans = "ans";
            hint = "hint";
        }
	}

	public String getAnswer() {
		return ans;
	}

	public String getHint() {
		return hint;
	}

	public int getImage() {
		return img;
	}

	public void getLine(int id, AppCompatActivity app) {
		try {
			Log.d("DEBUG", "Opening test");
            InputStream in = am.open("test.txt");
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(isr);
			String idLine;
			String ansLine;
			String hintLine;
			boolean idFound = false;
            Log.d("", " and id is " + id);
			while (((idLine = br.readLine()) != null) && (Integer.parseInt(idLine) != id)) 
			{
				br.readLine();
				br.readLine();
				br.readLine();
			}
            Log.d("", "idLine is " + idLine + " and id is " + id);
			if (Integer.parseInt(idLine) == id) {
				img = app.getResources().getIdentifier(br.readLine(), "drawable", app.getPackageName());
				ans = br.readLine();
				hint = br.readLine();
                Log.d("Debug", "Answer is " + ans + " and the hint is " + hint);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
    }
}