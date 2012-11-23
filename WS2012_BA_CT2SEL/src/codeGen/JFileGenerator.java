package codeGen;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.WebDriver;

import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;

public class JFileGenerator {

	private String pack;
	private String fileName;
	private String[] methodNames;
//	private String sourceCode = "package " + pack + ";\n"
//			+ "import org.junit.*; \n" + "public class " + fileName + " {\n"
//			+ "}";

	public void setPackageName(String pack) {
		this.pack = pack;
	}

	public void setFileName(String fName) {
		this.fileName = fName + System.currentTimeMillis();
	}

	public void setMethodNames(String[] mNames) {
		this.methodNames = mNames;
	}

	public void generateFile() throws IOException, JClassAlreadyExistsException {
//		String sourceCode = "package " + pack + ";\n"
//				+ "import org.junit.*; \n" + "public class " + fileName + " {\n"
//				+ "}";

		String testObjectMethod = "testPassword";
		JCodeModel cm = new JCodeModel();
		JDefinedClass dc = cm._class("test.Bar");
		JFieldVar bla = dc.field(0, WebDriver.class, "driver");
		JMethod m = dc.method(0, int.class, "foo");
		JMethod testPW = dc.method(0, void.class, testObjectMethod);
		m.body()._return(JExpr.lit(5));
		testPW.body().directStatement("assertTrue(true);");

		File file = new File("./src");
		file.mkdirs();
		cm.build(file);
//		FileWriter fstream = new FileWriter(".//src//" + pack + "//" + fileName
//				+ ".java");
//		BufferedWriter out = new BufferedWriter(fstream);
//
//		out.write("package " + pack + ";\n");
//		out.write("import org.junit.Before; \n");
//		out.write("import org.junit.Test;\n");
//		out.write("public class " + fileName + " {\n");
//
//		out.write("}");
//		// Close the output stream
//		out.close();

	}
}
