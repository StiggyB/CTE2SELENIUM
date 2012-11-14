package cte;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class JFileGenerator {

	private String pack;
	private String fileName;
	private String[] methodNames;
	
	public void setPackageName(String pack) {
		this.pack = pack;
	}
	
	public void setFileName(String fName) {
		this.fileName = fName + System.currentTimeMillis();
	}
	
	public void setMethodNames(String[] mNames) {
		this.methodNames = mNames;
	}
	
	public void generateFile() throws IOException {
		
		FileWriter fstream = new FileWriter(".//src//" + pack + "//" + fileName + ".java");
        BufferedWriter out = new BufferedWriter(fstream);
	    out.write("package " + pack + ";\n");
	    out.write("import org.junit.Before; \n");
	    out.write("import org.junit.Test;\n");
	    out.write("public class " + fileName + " {\n");
	    
	    out.write("}");
	    //Close the output stream
	    out.close();
		
	}
}
