package cte;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.swing.JFileChooser;

public class CTE {

	
	public static void readCTEfile() {
		try {
			// Open the file that is the first
			// command line parameter
			JFileChooser chooser= new JFileChooser();

			int choice = chooser.showOpenDialog(chooser);

			if (choice != JFileChooser.APPROVE_OPTION) return;
			chooser.setCurrentDirectory(new File("Z:/workspace)"));
			File chosenFile = chooser.getSelectedFile();

			FileInputStream fstream = new FileInputStream(chosenFile.getName());
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			StringBuffer sb = new StringBuffer();
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				sb.append(strLine);
				System.out.println(strLine);
			}
			// Close the input stream
			in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		readCTEfile();

	}

}
