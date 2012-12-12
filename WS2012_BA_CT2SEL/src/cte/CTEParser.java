package cte;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import c2s.CTTestCase;

import cte.xmlObjects.Classification;
import cte.xmlObjects.Composition;
import cte.xmlObjects.CteObject;
import cte.xmlObjects.CteTestCase;

import tools.XMLParser;

public class CTEParser {

    private File cteFile;
    private TreeMap<Integer, CteObject> cteObjectTree = new TreeMap<Integer, CteObject>();
    private ArrayList<CTTestCase> tcList = new ArrayList<CTTestCase>();
    private final static String composition = "Composition";
    private final static String classification = "Classification";
    private final static String testcase = "TestCase";
    
    public CTEParser(File cteFile) {
        this.cteFile = cteFile;
    }

    public CteObject[] getCteObjects(String name) throws ParserConfigurationException, SAXException, IOException {
        Element rootElement = XMLParser.parse(cteFile);
        NodeList currentObj_nl = rootElement.getElementsByTagName(name);
        getAllNodes(currentObj_nl);
        return null;
    }
    public boolean parseDocument() throws ParserConfigurationException, SAXException, IOException {
        // get the root elememt
        Element rootElement = XMLParser.parse(cteFile);

        // get a nodelist of all elements
        NodeList comp_nl = rootElement.getElementsByTagName(composition);
        NodeList classi_nl = rootElement.getElementsByTagName(classification);
        NodeList class_nl = rootElement.getElementsByTagName("Class");
        NodeList tc_nl = rootElement.getElementsByTagName(testcase);

        getAllNodes(comp_nl);
        getAllNodes(classi_nl);
        getAllNodes(class_nl);
        getAllNodes(tc_nl);
        return rootElement.equals(null);
    }
    
    private void getAllNodes(NodeList nl) {
        if (nl != null && nl.getLength() > 0) {
            for (int i = 0; i < nl.getLength(); i++) {

                // get the cte element
                Element el = (Element) nl.item(i);
                // get the cte object
                System.out.println(el.getNodeName());
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
}
