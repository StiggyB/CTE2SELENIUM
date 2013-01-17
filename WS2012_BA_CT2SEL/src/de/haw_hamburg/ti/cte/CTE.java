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
            FileOutputStream fout = new FileOutputStream(cteObjectTree
                    .firstEntry().getValue().getName()
                    + ".cttc");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(tcList);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Binds TestCase Marks with Classification(Class)-Marks and saves them in
     * the respective <tt>CteTestCase</tt>
     * 
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
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
        
        System.out.println(classificationMap);
        System.out.println(testcaseMap);

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

    private void getCteTree(File cteFile)
            throws ParserConfigurationException, SAXException, IOException {
        ctep = new CTEParser(cteFile);
        ctep.getCteObjectByName(COMPOSITION);
        ctep.getCteObjectByName(CLASSIFICATION);
        ctep.getCteObjectByName(TESTCASE);
        // tcList = ctep.getTCList();
        cteObjectTree = ctep.getCteTree();
        System.out.println(ctep.getCteTree());
    }

    @SuppressWarnings("unused")
    private void printData() {
        for (Integer elem : cteObjectTree.keySet())
            System.out.println(elem + " - " + cteObjectTree.get(elem));

    }

}
