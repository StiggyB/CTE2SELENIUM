package cte;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import tools.Cast;
import tools.XMLParser;
import c2s.CTTestCase;
import cte.xmlObjects.Classification;
import cte.xmlObjects.Composition;
import cte.xmlObjects.CteObject;
import cte.xmlObjects.CteTestCase;

public class CTEParser {

    private TreeMap<Integer, CteObject> cteObjectTree  = new TreeMap<Integer, CteObject>();
    private ArrayList<CTTestCase>       tcList         = new ArrayList<CTTestCase>();
    private final static String         composition    = "Composition";
    private final static String         classification = "Classification";
    private final static String         testcase       = "TestCase";
    private Element                     rootElement;
    private String                      actualCteObject;

    public CTEParser(File cteFile) throws ParserConfigurationException,
            SAXException, IOException {
        rootElement = XMLParser.parse(cteFile);
    }

    /**
     * Searches for a given Object in the CTE-XML file by Name.
     * 
     * @param objname
     *            - desired CTE Object
     * @return an ArrayList of those Objects
     */
    public ArrayList<CteObject> getCteObjectByName(String objname) {
        this.actualCteObject = objname;
        NodeList nl = rootElement.getElementsByTagName(actualCteObject);
        if (nl.getLength() < 1)
            throw new IllegalArgumentException("Unsupported object: "
                    + actualCteObject);

        ArrayList<CteObject> compList = Cast.as((ArrayList.class),
                getNodes(nl));
        return compList;
    }
    
    public TreeMap<Integer, CteObject> getCteTree() {
        return cteObjectTree;
    }
    
    public ArrayList<CTTestCase> getTCList() {
        return tcList;
    }

    private ArrayList<? extends CteObject> getNodes(NodeList nl) {
        ArrayList<CteObject> cteList = new ArrayList<>();
        if (nl != null && nl.getLength() > 0) {
            for (int i = 0; i < nl.getLength(); i++) {
                // get the cte element
                Element el = (Element) nl.item(i);
                // get the cte object
                CteObject c = getCteObject(el);
                // add it to list
                cteList.add(c);
                cteObjectTree.put(c.getId(), c);
            }
        }
        return cteList;
    }

    private CteObject getFirstDetails(Element el) {
        String name = el.getAttribute("name");// getTextValue(compEl,"name");
        String id = el.getAttribute("id");// getTextValue(compEl,"id");
        return new Composition(name, id);
    }

    private CteObject getCteObject(Element el) {

        CteObject cteObj = getFirstDetails(el);
        String name = el.getAttribute("name");// getTextValue(compEl,"name");
        String id = el.getAttribute("id");// getTextValue(compEl,"id");

        if (el.getNodeName().equals(testcase)) {
            String marks = getValue(el, "Marks");
            cteObj = new CteTestCase(name, id, marks);
            tcList.add(new CTTestCase(name));
        } else if (el.getNodeName().equals(classification)) {
            String[][] cteClass = getClassValue(el, "Class");
            cteObj = new Classification(name, id, cteClass);
        } else if (el.getNodeName().equals(composition)) {
            cteObj = new Composition(name, id);
        } else {
            throw new IllegalArgumentException("Unsupported object: "
                    + el.getNodeName());
        }
        return cteObj;
    }

    private String getValue(Element ele, String tagName) {
        NodeList nl = ele.getElementsByTagName(tagName);
        String textVal = new String();
        Element el = (Element) nl.item(0); // 0 because always first el
        textVal = el.getAttributes().item(0).getNodeValue();
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
