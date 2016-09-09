import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LevelData {

	private String ans;	
	private String hint;
	private int img;

	public LevelData(int id) {
		String line = "ans#hint"; // getLine(id);
		String lines[] = line.split("#");
		ans = lines[0];
		hint = lines[1];
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

/*	public getLine(int id) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("test.txt"))
			String cLine;
			boolean lineFound = false;
			do 
			{
				idLine = br.readLine();
				dataLine = br.readLine();
				String = line.split
			} while ();
		    return "ans#hint";
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	} */
}