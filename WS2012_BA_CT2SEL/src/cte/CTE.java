package cte;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import c2s.CTTestCase;
import cte.xmlObjects.Classification;
import cte.xmlObjects.Composition;
import cte.xmlObjects.CteObject;
import cte.xmlObjects.CteTestCase;

public class CTE {

	private DataInputStream in;
	private BufferedReader br;
	private ArrayList<String> strList = new ArrayList<String>();
	private final static String composition = "Composition";
	private final static String classification = "Classification";
	private final static String testcase = "TestCase";
	private TreeMap<Integer, CteObject> cteObjectTree = new TreeMap<Integer, CteObject>();
	private Document dom;
	private ArrayList<CTTestCase> tcList = new ArrayList<CTTestCase>();
	
	public boolean setUpFile(File chosenFile) throws IOException {
		boolean res;
		FileInputStream fstream = new FileInputStream(chosenFile.getName());
		in = new DataInputStream(fstream);
		res = ((br = new BufferedReader(new InputStreamReader(in))).ready());
		return res;
	}

	/**
	 * Intentionally written to read out xml/txt File -> no more use because of XML Parsing
	 * @return
	 * @throws IOException
	 */
	@Deprecated
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

	public boolean getNodes(File chosenFile) {
		boolean res = false;
		// parse the xml file and get the dom object
		if ((res = parseXmlFile(chosenFile))) {
			return res;
		}
		// get each element and create a Cte object
		if ((res = parseDocument())) {
			return res;
		}
		// Iterate through the list and print the data
		// printData();

		return res;
	}

	private boolean parseXmlFile(File chosenFile) {
		// get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {

			// Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			// parse using builder to get DOM representation of the XML file
			dom = db.parse(chosenFile);

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
				cteObjectTree.put(c.getId(), c);
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
			tcList.add(new CTTestCase(name));
		} else if (compEl.getNodeName().equals(classification)) {
			String[][] cteClass = getClassValue(compEl, "Class");
			cteObj = new Classification(name, id, cteClass);
		} else {
			cteObj = new Composition(name, id);
		}
		return cteObj;
	}

	private String[] getValue(Element ele, String tagName) {
		NodeList nl = ele.getElementsByTagName(tagName);
		String[] textVal = new String[nl.getLength()];
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				Element el = (Element) nl.item(i);
				textVal[i] = el.getAttributes().item(i).getNodeValue(); // das
																		// gibt
																		// kacke,
																		// 0 vom
																		// ersten
																		// und 1
																		// vom
																		// zweiten
																		// classobj
			}
		}

		return textVal;
	}

	private String[][] getClassValue(Element ele, String tagName) {
		NodeList nl = ele.getElementsByTagName(tagName);
		String[][] textVal = new String[nl.getLength()][nl.getLength()];
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				Element el = (Element) nl.item(i);
				// Magic Number 2, to eliminate unused xml data
				for (int j = 0; j < el.getAttributes().getLength() - 2; j++) {
					textVal[i][j] = el.getAttributes().item(j).getNodeValue();
				}
			}
		}
		return textVal;
	}

	@SuppressWarnings("unused")
	private void printData() {

		for (Integer elem : cteObjectTree.keySet())
			System.out.println(elem + " - " + cteObjectTree.get(elem));

	}

	/**
	 * Soll TestCase Marks mit Classification(Class)-Marks Verknüpfen so dass am
	 * ende etwas entsteht wie:
	 * 
	 * Testcase 1 -> numeric: true; uppercase: true; length: P4ssword
	 */
	public ArrayList<CTTestCase> getTestData() {

		/**
		 * <testcaseName, marks>
		 */
		Map<String, Integer[]> testcaseMap = new HashMap<String, Integer[]>();

		/**
		 * <classificationName, ids>
		 */
		Map<Integer, Integer[]> classificationMap = new HashMap<Integer, Integer[]>();

		for (CteObject element : cteObjectTree.values()) {
			if (element.getClass().equals(CteTestCase.class)) {
				testcaseMap.put(element.getName(),
						((CteTestCase) element).getMarks());
			} else if (element.getClass().equals(Classification.class)) {
				classificationMap.put(element.getId(),
						((Classification) element).getTestDataIds());
			}
		}

		for (Map.Entry<Integer, Integer[]> entrycl : classificationMap
				.entrySet()) {
			for (int i = 0; i < entrycl.getValue().length; i++) {
				for (Map.Entry<String, Integer[]> entrytc : testcaseMap
						.entrySet()) {
					for (int j = 0; j < entrytc.getValue().length; j++) {
						if (entrycl.getValue()[i].equals(entrytc.getValue()[j])) {
							for (Iterator<CTTestCase> iterator = tcList.iterator(); iterator
									.hasNext();) {
								CTTestCase type = iterator.next();
								if (type.getName().equals(entrytc.getKey())) {
									String val = ((Classification) cteObjectTree
											.get(entrycl.getKey()))
											.getTestData()[i];
									if (val.equals("true")) {
										if (cteObjectTree.get(entrycl.getKey())
												.getName()
												.equalsIgnoreCase("upper case")) {
											type.setUppercase(true);
										} else {
											type.setNumeric(true);
										}
									} else if (val.equals("false")) {
										if (cteObjectTree.get(entrycl.getKey())
												.getName()
												.equalsIgnoreCase("upper case")) {
											type.setUppercase(false);
										} else {
											type.setNumeric(false);
										}
									} else if (val.equals("p4ss")) {
										type.setLength(val);
									} else if (val.equals("P4ssw0rd")) {
										type.setLength(val);
									} else {
										System.out.println("SHOULD NOT HAPPEN");
									}
								}
							}
						}
					}
				}
			}
		}
		return tcList;
	}

	public TreeMap<Integer, CteObject> getCteObjects() {
		return cteObjectTree;
	}

	public void saveTestCasesToFile() {
		// serialize the Queue
		System.out.println("serializing theQueue");
		try {
			FileOutputStream fout = new FileOutputStream("TEST_TC.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(tcList);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void tearDownStream() throws IOException {
		// Close the input stream
		in.close();
	}

}
