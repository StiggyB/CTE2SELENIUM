package de.haw_hamburg.ti.tools;

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
     * @return the dom
     */
    public static Document getDom() {
        return dom;
    }
    
    /**
     * Parses the given Document(DOM) and returns the root Element e.g. null if
     * no root is found.
     * 
     * @param chosenFile
     *            - File to parse
     * @return root Element
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public static Element parse(File chosenFile) throws IOException {
        parseXmlFile(chosenFile);
        return dom.getDocumentElement();
    }

    private static void parseXmlFile(File chosenFile) throws IOException {
        // get the factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        // Using factory get an instance of document builder
        DocumentBuilder db;
        try {
            db = dbf.newDocumentBuilder();
            // parse using builder to get DOM representation of the XML file
            dom = db.parse(chosenFile);

        } catch (ParserConfigurationException e) {
            System.err.println("Configuration error");
            e.printStackTrace();
        } catch (SAXException e) {
            System.err.println("Parse error");
            e.printStackTrace();
        }

        dom.normalize();
    }

 
}
