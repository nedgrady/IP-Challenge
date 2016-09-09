package com.example.nedgrady.ipchallenge;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LevelData {

	private String ans;	
	private String hint;
	private int img;

	public LevelData(int id) {
		getLine(id); 
    	img = 1;
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

	public void getLine(int id) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("test.txt"));
			String idLine;
			String ansLine;
			String hintLine;
			boolean idFound = false;
			while (((idLine = br.readLine()) != null) && (Integer.parseInt(idLine) != id)) 
			{
				br.readLine();
				br.readLine();
			}
			if (Integer.parseInt(idLine) == id) {
				ans = br.readLine();
				hint = br.readLine();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}

