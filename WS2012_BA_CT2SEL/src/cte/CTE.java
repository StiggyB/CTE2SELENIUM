package cte;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


public class CTE {
	
	DataInputStream in;
	BufferedReader br;
	int row = 0;
	
	public void setUpFile(File chosenFile) throws FileNotFoundException {
		FileInputStream fstream = new FileInputStream(chosenFile.getName());
		in = new DataInputStream(fstream);
		br = new BufferedReader(new InputStreamReader(in));
	}
	
	public String readCTEfileByLine() throws IOException {
		String strLine = "";

		// Read File Line By Line
		strLine = br.readLine();
		
		if (strLine != null && strLine.matches(".*[^a-z^A-Z^0-9].*")) {
			strLine = strLine + "\n";
			System.out.print(strLine);
//			row++;
		}

		return strLine;
	}
	
	public void tearDownStream() throws IOException {
		// Close the input stream
		in.close();
	}
	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		readCTEfile();
//
//	}

}
