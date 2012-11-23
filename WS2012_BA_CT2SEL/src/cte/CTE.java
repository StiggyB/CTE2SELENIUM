package cte;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CTE {
	
	private DataInputStream in;
	private BufferedReader br;
	
	public void setUpFile(File chosenFile) throws FileNotFoundException {
		FileInputStream fstream = new FileInputStream(chosenFile.getName());
		in = new DataInputStream(fstream);
		br = new BufferedReader(new InputStreamReader(in));
	}
	
	public String readCTEfileByLine() throws IOException {
		String strLine = "";

		// Read File Line By Line
		strLine = br.readLine();
		
//		// Delete empty Lines... Ne das ist schrott ^^
//		if (strLine != null && strLine.matches(".*[^a-z^A-Z^0-9].*")) {
//			return strLine;
//		}

		return strLine;
	}
	
	public void tearDownStream() throws IOException {
		// Close the input stream
		in.close();
	}

	/*
	 * DOES NOT WORK....
	 */
	public void parseForTC(ArrayList<String> cteElements) {
		Pattern pattern = Pattern.compile("(Test)");
		Matcher matcher = pattern.matcher(cteElements.get(0));

		for(String item : cteElements)  {
			matcher = pattern.matcher(item);
			System.out.println( item + ": ");
			System.out.println(matcher.group());
			System.out.println(matcher.find());
		}
	}

}
