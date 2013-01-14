package de.haw_hamburg.ti.cte;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import de.haw_hamburg.ti.cte.xmlObjects.Classification;
import de.haw_hamburg.ti.cte.xmlObjects.CteObject;
import de.haw_hamburg.ti.cte.xmlObjects.CteTestCase;

public class CTE {

    private TreeMap<Integer, CteObject> cteObjectTree  = new TreeMap<Integer, CteObject>();
    private ArrayList<CteTestCase>      tcList         = new ArrayList<CteTestCase>();
    private final static String         COMPOSITION    = "Composition";
    private final static String         CLASSIFICATION = "Classification";
    private final static String         TESTCASE       = "TestCase";
    private CTEParser                   ctep;

    /**
     * Specified .cte File is Parsed and serialized written to a File.
     * 
     * @param cteFile
     *            - the File to be parsed and saved in serialized format
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public void saveTestCasesToFile() {
        try {
            FileOutputStream fout = new FileOutputStream("TEST_TC.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(tcList);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<CteTestCase> getTestData(File cteFile)
            throws ParserConfigurationException, SAXException, IOException {
        getCteTree(cteFile);
        /**
         * <testcaseName, marks>
         */
        Map<CteTestCase, Integer[]> testcaseMap = new HashMap<CteTestCase, Integer[]>();

        /**
         * <classificationName, ids>
         */
        Map<Classification, Integer[]> classificationMap = new HashMap<Classification, Integer[]>();

        for (CteObject element : cteObjectTree.values()) {
            if (element.getClass().equals(CteTestCase.class)) {
                testcaseMap.put((CteTestCase) element,
                        ((CteTestCase) element).getMarks());
            } else if (element.getClass().equals(Classification.class)) {
                classificationMap.put((Classification) element,
                        ((Classification) element).getTestDataIds());
            }
        }

        for (Entry<CteTestCase, Integer[]> entrytc : testcaseMap.entrySet()) {
            for (int i = 0; i < entrytc.getValue().length; i++) {
                for (Entry<Classification, Integer[]> entrycl : classificationMap
                        .entrySet()) {
                    for (int j = 0; j < entrycl.getValue().length; j++) {
                        if (entrytc.getValue()[i].equals(entrycl.getKey()
                                .getTestDataIds()[j])) {
                            entrytc.getKey().setValueOfMark(
                                    entrytc.getValue()[i],
                                    entrycl.getKey().getTestData()[j]);
                            entrytc.getKey().setCompositionOfMark(
                                    entrytc.getValue()[i],
                                    entrycl.getKey().getName());
                        }
                    }
                }
            }
            tcList.add(entrytc.getKey());
        }
        return tcList;
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
    // public ArrayList<CTTestCase> getTestData() {
    // // TODO: THIS DOES NOT WORK GENERICALLY, NEEDS RIGOROUS UPDATE
    // /**
    // * <testcaseName, marks>
    // */
    // Map<String, Integer[]> testcaseMap = new HashMap<String, Integer[]>();
    //
    // /**
    // * <classificationName, ids>
    // */
    // Map<Integer, Integer[]> classificationMap = new HashMap<Integer,
    // Integer[]>();
    //
    // for (CteObject element : cteObjectTree.values()) {
    // if (element.getClass().equals(CteTestCase.class)) {
    // testcaseMap.put(element.getName(),
    // ((CteTestCase) element).getMarks());
    // } else if (element.getClass().equals(Classification.class)) {
    // classificationMap.put(element.getId(),
    // ((Classification) element).getTestDataIds());
    // }
    // }
    //
    // for (Map.Entry<Integer, Integer[]> entrycl : classificationMap
    // .entrySet()) {
    // for (int i = 0; i < entrycl.getValue().length; i++) {
    // for (Map.Entry<String, Integer[]> entrytc : testcaseMap
    // .entrySet()) {
    // for (int j = 0; j < entrytc.getValue().length; j++) {
    // if (entrycl.getValue()[i]
    // .equals(entrytc.getValue()[j])) {
    // for (Iterator<CTTestCase> iterator = tcList
    // .iterator(); iterator.hasNext();) {
    // CTTestCase type = iterator.next();
    // if (type.getName().equals(entrytc.getKey())) {
    // String val = ((Classification) cteObjectTree
    // .get(entrycl.getKey()))
    // .getTestData()[i];
    // System.out.println(val);
    // if (val.equals("true")) {
    // if (cteObjectTree
    // .get(entrycl.getKey())
    // .getName()
    // .equalsIgnoreCase(
    // "upper case")) {
    // type.setUppercase(true);
    // } else {
    // type.setNumeric(true);
    // }
    // } else if (val.equals("false")) {
    // if (cteObjectTree
    // .get(entrycl.getKey())
    // .getName()
    // .equalsIgnoreCase(
    // "upper case")) {
    // type.setUppercase(false);
    // } else {
    // type.setNumeric(false);
    // }
    // } else if (val.equals("p4ss")) {
    // type.setLength(val);
    // } else if (val.equals("P4ssw0rd")) {
    // type.setLength(val);
    // } else {
    // System.out
    // .println("SHOULD NOT HAPPEN");
    // }
    // }
    // }
    // }
    // }
    // }
    // }
    // }
    // return tcList;
    // }

    private void getCteTree(File cteFile)
            throws ParserConfigurationException, SAXException, IOException {
        ctep = new CTEParser(cteFile);
        ctep.getCteObjectByName(COMPOSITION);
        ctep.getCteObjectByName(CLASSIFICATION);
        ctep.getCteObjectByName(TESTCASE);
        // tcList = ctep.getTCList();
        cteObjectTree = ctep.getCteTree();
    }

    @SuppressWarnings("unused")
    private void printData() {
        for (Integer elem : cteObjectTree.keySet())
            System.out.println(elem + " - " + cteObjectTree.get(elem));

    }

}
