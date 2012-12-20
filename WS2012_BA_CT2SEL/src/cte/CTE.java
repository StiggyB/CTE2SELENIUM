package cte;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import c2s.CTTestCase;
import cte.xmlObjects.Classification;
import cte.xmlObjects.CteObject;
import cte.xmlObjects.CteTestCase;

public class CTE {

    private TreeMap<Integer, CteObject> cteObjectTree  = new TreeMap<Integer, CteObject>();
    private ArrayList<CTTestCase>       tcList         = new ArrayList<CTTestCase>();
    private final static String         composition    = "Composition";
    private final static String         classification = "Classification";
    private final static String         testcase       = "TestCase";

    /**
     * Specified .cte File is Parsed and serialized written to a File.
     * 
     * @param cteFile - the File to be parsed and saved in serialized format
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public void saveTestCasesToFile(File cteFile)
            throws ParserConfigurationException, SAXException, IOException {
        getCteTree(cteFile);
        try {
            FileOutputStream fout = new FileOutputStream("TEST_TC.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(tcList);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Soll TestCase Marks mit Classification(Class)-Marks Verknüpfen so dass am
     * ende etwas entsteht wie:
     * 
     * Testcase 1 -> numeric: true; uppercase: true; length: P4ssword
     * 
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public ArrayList<CTTestCase> getTestData() {
        //TODO: THIS DOES NOT WORK GENERICALLY, NEEDS RIGOROUS UPDATE
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
                        if (entrycl.getValue()[i]
                                .equals(entrytc.getValue()[j])) {
                            for (Iterator<CTTestCase> iterator = tcList
                                    .iterator(); iterator.hasNext();) {
                                CTTestCase type = iterator.next();
                                if (type.getName().equals(entrytc.getKey())) {
                                    String val = ((Classification) cteObjectTree
                                            .get(entrycl.getKey()))
                                            .getTestData()[i];
                                    System.out.println(val);
                                    if (val.equals("true")) {
                                        if (cteObjectTree
                                                .get(entrycl.getKey())
                                                .getName()
                                                .equalsIgnoreCase(
                                                        "upper case")) {
                                            type.setUppercase(true);
                                        } else {
                                            type.setNumeric(true);
                                        }
                                    } else if (val.equals("false")) {
                                        if (cteObjectTree
                                                .get(entrycl.getKey())
                                                .getName()
                                                .equalsIgnoreCase(
                                                        "upper case")) {
                                            type.setUppercase(false);
                                        } else {
                                            type.setNumeric(false);
                                        }
                                    } else if (val.equals("p4ss")) {
                                        type.setLength(val);
                                    } else if (val.equals("P4ssw0rd")) {
                                        type.setLength(val);
                                    } else {
                                        System.out
                                                .println("SHOULD NOT HAPPEN");
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

    private void getCteTree(File cteFile)
            throws ParserConfigurationException, SAXException, IOException {
        CTEParser ctep = new CTEParser(cteFile);
        ctep.getCteObjectByName(composition);
        ctep.getCteObjectByName(classification);
        ctep.getCteObjectByName(testcase);
        tcList = ctep.getTCList();
        cteObjectTree = ctep.getCteTree();
    }

    @SuppressWarnings("unused")
    private void printData() {
        for (Integer elem : cteObjectTree.keySet())
            System.out.println(elem + " - " + cteObjectTree.get(elem));

    }

}
