package de.haw_hamburg.ti.cte;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.TreeWalker;

import de.haw_hamburg.ti.cte.xmlObjects.Classification;
import de.haw_hamburg.ti.cte.xmlObjects.Composition;
import de.haw_hamburg.ti.cte.xmlObjects.CteClass;
import de.haw_hamburg.ti.cte.xmlObjects.CteObject;
import de.haw_hamburg.ti.cte.xmlObjects.CteTestCase;
import de.haw_hamburg.ti.tools.Cast;
import de.haw_hamburg.ti.tools.XMLParser;
import de.haw_hamburg.ti.tools.tree.Tree;

public class CTEParser {

    private TreeMap<Integer, CteObject> cteObjectTree  = new TreeMap<Integer, CteObject>();
    private final static String         composition    = "Composition";
    private final static String         classification = "Classification";
    private final static String         testcase       = "TestCase";
    private Element                     rootElement;
    private String                      actualCteObject;
    private Tree<CteObject>             cteTree;
    private ArrayList<CteTestCase>      tcList         = new ArrayList<>();

    // CONSTRUCTOR
    public CTEParser(File cteFile) throws IOException {
        rootElement = XMLParser.parse(cteFile);
    }

    // GETTER & SETTER
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

    public Tree<CteObject> getTree() {
        createTree(XMLParser.getDom());
        return cteTree;
    }

    /**
     * @return the tcList
     */
    protected ArrayList<CteTestCase> getTcList() {
        return tcList;
    }

    // METHODS
    private void createTree(Document dom) {
        DocumentTraversal traversal = (DocumentTraversal) dom;

        CteObjectsInDocument cteObjectsInDoc = new CteObjectsInDocument();
        TreeWalker walker = traversal.createTreeWalker(dom
                .getDocumentElement(), NodeFilter.SHOW_ELEMENT,
                cteObjectsInDoc, true);

        traverseLevel(walker, "", 0);
    }

    private void traverseLevel(TreeWalker walker, String indent, int i) {

        // describe current node:
        Node parent = walker.getCurrentNode();
//        System.out.println(indent + "- " + ((Element) parent).getTagName()
//                + "(" + ((Element) parent).getAttribute("name") + ")");

        addToCteTree(parent, i);

        // traverse children:
        for (Node n = walker.firstChild(); n != null; n = walker
                .nextSibling()) {

            traverseLevel(walker, indent + '\t', i + 1);
        }

        // return position to the current (level up):
        walker.setCurrentNode(parent);
    }

    private void addToCteTree(Node n, int i) {
        for (CteObj obj : CteObj.values()) {
            if (obj.name().equalsIgnoreCase((n.getNodeName()))) {
                CteObject c = getCteObj((Element) n, obj);
                if (i == 1 && !(c instanceof CteTestCase)) {
                    cteTree = new Tree<CteObject>(c, i);
                } else if (i == 2) {
                    cteTree.getRootNode().addChild(c, i);
                } else if (i > 2) {
                    cteTree.getLastNodeByLevel(i - 1).addChild(c, i);
                } else if (c instanceof CteTestCase) {
                    tcList.add((CteTestCase) c);
                } else {
                    // nothing
                }
            }
        }
    }

    private CteObject getCteObj(Element e, CteObj obj) {
        switch (obj) {
            case Composition:
                return new Composition(getElementName(e), getElementId(e));
            case Classification:
                return new Classification(getElementName(e), getElementId(e));
            case Class:
                return new CteClass(getElementName(e), getElementId(e));
            case TestCase:
                return new CteTestCase(getElementName(e), getElementId(e),
                        getValue(e, "Marks"));
            default:
                return null;
        }
    }

    private String getElementName(Element e) {
        return e.getAttribute("name");
    }

    private String getElementId(Element e) {
        return e.getAttribute("id");
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
                // cteObjectTree2.put(Integer.parseInt(el.getAttribute("id")
                // .replace("c", "")), el.getAttribute("name"));

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
        } else if (el.getNodeName().equals(classification)) {
            String[][] cteClass = getClassValue(el, "Class");
            cteObj = new Classification(name, id, cteClass);
        } else if (el.getNodeName().equals(composition)) {
            cteObj = new Composition(name, id);
            NodeList nl = el.getChildNodes();
            ArrayList<Element> eles = new ArrayList<>();
            for (int i = 0; i < nl.getLength(); i++) {
                if (nl.item(i).getNodeType() == Node.ELEMENT_NODE
                        && !el.getChildNodes().item(i).getNodeName()
                                .equalsIgnoreCase("Tag")) {
                    eles.add((Element) nl.item(i));
                }
            }
            String[][] clarr = new String[eles.size()][2];
            for (int i = 0; i < eles.size(); i++) {
                clarr[i][0] = eles.get(i).getAttribute("id");
                clarr[i][1] = eles.get(i).getAttribute("name");
            }
            ((Composition) cteObj).setClassifications(clarr);
        } else {
            return null;
            // throw new IllegalArgumentException("Unsupported object: "
            // + el.getNodeName());
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
        ArrayList<String[]> val = new ArrayList<>();
        int dim1 = 0;
        if (nl != null && nl.getLength() > 0) {
            for (int i = 0; i < nl.getLength(); i++) {
                Element el = (Element) nl.item(i);
                if (el.getParentNode().equals(ele)) {
                    val.add(new String[] { el.getAttribute("id"),
                            el.getAttribute("name") });
                }
            }
        }
        String[][] textVal1 = new String[val.size()][2];
        for (Iterator<String[]> iterator = val.iterator(); iterator.hasNext();) {
            String[] strings = iterator.next();
            textVal1[dim1] = strings;
            dim1++;
        }
        return textVal1;
    }

}
