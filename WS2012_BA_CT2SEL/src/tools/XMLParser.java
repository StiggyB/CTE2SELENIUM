package tools;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class XMLParser {

	private static Document dom;

	private XMLParser() {
		super();
	}

	/**
	 * Parses the given Document(DOM) and returns the root Element e.g. null if no
	 * root is found.
	 * 
	 * @param chosenFile
	 *            - File to parse
	 * @return root Element
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public static Element parse(File chosenFile) throws ParserConfigurationException, SAXException, IOException {
		parseXmlFile(chosenFile);
		return dom.getDocumentElement();
	}

	private static void parseXmlFile(File chosenFile)
			throws ParserConfigurationException, SAXException, IOException {
		// get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		// Using factory get an instance of document builder
		DocumentBuilder db = dbf.newDocumentBuilder();

		// parse using builder to get DOM representation of the XML file
		dom = db.parse(chosenFile);
		dom.normalize();
	}

}
