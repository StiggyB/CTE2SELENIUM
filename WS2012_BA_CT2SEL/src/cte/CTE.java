package cte;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class CTE {
	
	private DataInputStream in;
	private BufferedReader br;
	private ArrayList<String> strList = new ArrayList<String>();
	
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
		if (strLine != null) {// && strLine.matches(".*[^a-z^A-Z^0-9].*")) {
//			return strLine;
			strList.add(strLine);
		}

		return strLine;
	}
	
	
	List<CteObject> cteObjects = new ArrayList<CteObject>();
	Document dom;
	
	public void runExample() {
		
		//parse the xml file and get the dom object
		parseXmlFile();
		
		//get each element and create a Cte object
		parseDocument("TestCase");
		
		//Iterate through the list and print the data
		printData();
		
	}
	
	private void parseXmlFile(){
		//get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {

			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			//parse using builder to get DOM representation of the XML file
			dom = db.parse("password.cte");


		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	private void parseDocument(String cteObj){
		//get the root elememt
		Element docEle = dom.getDocumentElement();
		
		//get a nodelist of <employee> elements
		NodeList nl = docEle.getElementsByTagName(cteObj);

		if(nl != null && nl.getLength() > 0) {
			for(int i = 0 ; i < nl.getLength();i++) {
				
				//get the cte element
				Element el = (Element)nl.item(i);
				//get the cte object
				CteObject c = getCteObject(el);
				
				//add it to list
				cteObjects.add(c);
			}
		}
	}

	private CteObject getCteObject(Element compEl) {
		
		//for each <employee> element get text or int values of 
		//name ,id, age and name
		String name = compEl.getAttribute("name");//getTextValue(compEl,"name");
		String id = compEl.getAttribute("id");//getTextValue(compEl,"id");
		
		System.out.println(compEl.toString() + (Element)compEl.getChildNodes().item(0));
		//Create a new Employee with the value read from the xml nodes
		CteObject c = new Composition(name,id);
		
		return c;
	}
	
	private String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}

		return textVal;
	}
	
	private void printData(){
		
		System.out.println("No of Compositions '" + cteObjects.size() + "'.");
		
		Iterator<CteObject> it = cteObjects.iterator();
		while(it.hasNext()) {
			System.out.println(it.next().toString());
		}
	}
	
	public void tearDownStream() throws IOException {
		// Close the input stream
		in.close();
	}

}
