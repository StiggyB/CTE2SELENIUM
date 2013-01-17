package de.haw_hamburg.ti.cte;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.haw_hamburg.ti.cte.xmlObjects.Classification;
import de.haw_hamburg.ti.cte.xmlObjects.Composition;
import de.haw_hamburg.ti.cte.xmlObjects.CteObject;
import de.haw_hamburg.ti.cte.xmlObjects.CteTestCase;
import de.haw_hamburg.ti.tools.Cast;
import de.haw_hamburg.ti.tools.XMLParser;

public class CTEParser {

    private TreeMap<Integer, CteObject> cteObjectTree  = new TreeMap<Integer, CteObject>();
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
            if (rootElement.getElementsByTagName(composition).item(0)
                    .getAttributes().item(1).getNodeValue()
                    .equals("Sizing_Type_and_Medium_Section")) {
            }
        } else if (el.getNodeName().equals(classification)) {
            String[][] cteClass = getClassValue(el, "Class");
            cteObj = new Classification(name, id, cteClass);
        } else if (el.getNodeName().equals(composition)) {
            cteObj = new Composition(name, id);
            NodeList nl = el.getElementsByTagName(classification);
            String[][] clarr = new String[nl.getLength()][2];
            for (int i = 0; i < nl.getLength(); i++) {
//                classification = new Classification(
//                        ((Element) nl.item(i)).getAttribute("name"),
//                        ((Element) nl.item(i)).getAttribute("id"),
//                        getClassValue(((Element) nl.item(i)), "Class"));
//                clarr[i] = classification;
                clarr[i][0] = ((Element) nl.item(i)).getAttribute("id");
                clarr[i][1] = ((Element) nl.item(i)).getAttribute("name");
            }
            ((Composition) cteObj).setClassifications(clarr);
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
