package cte;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

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
	private final static String composition = "Composition";
	private final static String classification = "Classification";
	private final static String testcase = "TestCase";
	private List<CteObject> cteObjects = new ArrayList<CteObject>();
	private TreeMap<Integer, CteObject> cteObjectTree = new TreeMap<Integer, CteObject>();
	private Document dom;

	public boolean setUpFile(File chosenFile) throws IOException {
		boolean res;
		FileInputStream fstream = new FileInputStream(chosenFile.getName());
		in = new DataInputStream(fstream);
		res = ((br = new BufferedReader(new InputStreamReader(in))).ready());
		return res;
	}

	public String readCTEfileByLine() throws IOException {
		String strLine = "";

		// Read File Line By Line
		strLine = br.readLine();
		// // Delete empty Lines... Ne das ist schrott ^^
		if (strLine != null) {// && strLine.matches(".*[^a-z^A-Z^0-9].*")) {
		// return strLine;
			strList.add(strLine);
		}

		return strLine;
	}

	public boolean getNodes() {
		boolean res = false;
		//parse the xml file and get the dom object
		if ((res = parseXmlFile())) {
			return res;
		}
		//get each element and create a Cte object
		if ((res = parseDocument())) {
			return res;
		}
		//Iterate through the list and print the data
		printData();

		return res;
	}

	private boolean parseXmlFile() {
		// get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {

			// Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			// parse using builder to get DOM representation of the XML file
			dom = db.parse("password.cte");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return dom.equals(null);
	}

	private boolean parseDocument() {
		// get the root elememt
		Element docEle = dom.getDocumentElement();

		// get a nodelist of <employee> elements
		NodeList comp_nl = docEle.getElementsByTagName(composition);
		NodeList classi_nl = docEle.getElementsByTagName(classification);
		NodeList tc_nl = docEle.getElementsByTagName(testcase);

		getAllNodes(comp_nl);
		getAllNodes(classi_nl);
		getAllNodes(tc_nl);
		return docEle.equals(null);
	}

	private void getAllNodes(NodeList nl) {
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {

				// get the cte element
				Element el = (Element) nl.item(i);
				// get the cte object
				CteObject c = getCteObject(el);

				// add it to list
				cteObjects.add(c);
			}
		}
	}

	private CteObject getCteObject(Element compEl) {

		CteObject cteObj = null;
		// for each <cteObject> element get text or int values of
		// name ,id, age and name
		String name = compEl.getAttribute("name");// getTextValue(compEl,"name");
		String id = compEl.getAttribute("id");// getTextValue(compEl,"id");

		if (compEl.getNodeName().equals(testcase)) {
			String marks = getValue(compEl, "Marks")[0];
			cteObj = new CteTestCase(name, id, marks);
		} else if (compEl.getNodeName().equals(classification)) {
			String[] cteClass = getValue(compEl, "Class");
			cteObj = new Classification(name, id, cteClass);
		} else {
			cteObj = new Composition(name, id, null);
		}

		return cteObj;
	}

	private String[] getValue(Element ele, String tagName) {
		String[] textVal = { "", "" };
		NodeList nl = ele.getElementsByTagName(tagName);
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				Element el = (Element) nl.item(i);
				textVal[i] = el.getAttributes().item(i).getNodeValue();
			}
		}

		return textVal;
	}

	private void printData() {

		System.out.println("No of CteObjects '" + cteObjects.size() + "'.");
		Iterator<CteObject> it = cteObjects.iterator();
//		while (it.hasNext()) {
//			System.out.println(it.next().toString());
//		}

		// arraylist to treemap
		System.out.println("Tree:");
		Iterator<CteObject> it2 = cteObjects.iterator();
		CteObject ceto = null;
		while (it2.hasNext()) {
			ceto = it2.next();
			cteObjectTree.put(ceto.getId(), ceto);
			System.out.println("Object: " + ceto + " ID: " + ceto.getId()
					+ "map: " + cteObjectTree.containsKey(ceto.getId()));
		}

		for (Integer elem : cteObjectTree.keySet())
			System.out.println(elem + " - " + cteObjectTree.get(elem));

	}

	
	public void getTestData() {
		for (CteObject tcele : cteObjectTree.values()) {
			if (tcele.getClass().equals(CteTestCase.class)) {
				System.out.println("TC FOUND: " + tcele.getName());
				Integer[] marks = ((CteTestCase) tcele).getMarks();
				for (int i = 0; i < marks.length; i++) {
					for (CteObject cfcele : cteObjectTree.values()) {
						if (cfcele.getClass().equals(Classification.class)) {
							if (marks[i].equals(((Classification)cfcele).getTestDataId())) {
								System.out.println(marks[i] + " equals " + ((Classification)cfcele).getTestDataId());
							}
						}
					}
					
				}
			}
		}
	}
	
	public TreeMap<Integer, CteObject> getCteObjects() {
		return cteObjectTree;
	}

	public void tearDownStream() throws IOException {
		// Close the input stream
		in.close();
	}

}
