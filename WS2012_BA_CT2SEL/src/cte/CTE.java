package cte;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.swing.JFileChooser;

public class CTE {

	
	public static StringBuffer readCTEfile() {
		StringBuffer cte_sb = new StringBuffer();
		try {
			// Open the file that is the first
			// command line parameter
			JFileChooser chooser= new JFileChooser();


			chooser.setCurrentDirectory(new File("."));
			int choice = chooser.showOpenDialog(chooser);

			if (choice != JFileChooser.APPROVE_OPTION) return null;
			File chosenFile = chooser.getSelectedFile();

			FileInputStream fstream = new FileInputStream(chosenFile.getName());
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			int i = 0;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				if (strLine.matches(".*[^a-z^A-Z^0-9].*")) {
					cte_sb.append(i + " " + strLine + "\n");
					System.out.print(i + " " + strLine + "\n");
					i++;
				}
			}
			// Close the input stream
			in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		return cte_sb;
	}
	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		readCTEfile();
//
//	}

}
