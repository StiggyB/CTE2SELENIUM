package de.haw_hamburg.ti.cte;

import java.io.File;
import java.io.FileNotFoundException;
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
import de.haw_hamburg.ti.cte.xmlObjects.Composition;
import de.haw_hamburg.ti.cte.xmlObjects.CteObject;
import de.haw_hamburg.ti.cte.xmlObjects.CteTestCase;
import de.haw_hamburg.ti.tools.tree.Tree;

public class CTE {

    private TreeMap<Integer, CteObject> cteObjectTree  = new TreeMap<Integer, CteObject>();
    private ArrayList<CteTestCase>      tcList         = new ArrayList<CteTestCase>();
    private CTEParser                   ctep;
    private Tree<CteObject>             cteTree;

    /**
     * Specified .cte File is Parsed and serialized written to a File.
     * 
     * @param cteFile
     *            - the File to be parsed and saved in serialized format
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public void saveTestCasesToFile(File cteFile) throws IOException {
        getCteTree(cteFile);
        FileOutputStream fout = null;
        ObjectOutputStream oos = null;
        try {
            fout = new FileOutputStream(cteTree.getRootNode().getContent()
                    .getName()
                    + ".cttc");

            oos = new ObjectOutputStream(fout);
            oos.writeObject(tcList);
            oos.writeObject(cteTree);

        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Read/Write error.");
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                    fout.close();
                } catch (IOException e) {
                    System.err.println("Read/Write error.");
                    e.printStackTrace();
                }
            }
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
            throws IOException {
        getCteTree(cteFile);
        /**
         * <testcaseName, marks>
         */
        Map<CteTestCase, Integer[]> testcaseMap = new HashMap<CteTestCase, Integer[]>();

        /**
         * <classificationName, ids>
         */
        Map<Classification, Integer[]> classificationMap = new HashMap<Classification, Integer[]>();
        /**
         * <compositionName, ids>
         */
        Map<Composition, Integer[]> compositionMap = new HashMap<Composition, Integer[]>();

        fillMaps(testcaseMap, classificationMap, compositionMap);

        // System.out.println(compositionMap);
        // System.out.println(classificationMap);
        // System.out.println(testcaseMap);

        setMarkParents(testcaseMap, classificationMap, compositionMap);
        return tcList;
    }

    /**
     * @param testcaseMap
     * @param classificationMap
     * @param compositionMap
     */
    private void setMarkParents(Map<CteTestCase, Integer[]> testcaseMap,
            Map<Classification, Integer[]> classificationMap,
            Map<Composition, Integer[]> compositionMap) {
        for (Entry<CteTestCase, Integer[]> entrytc : testcaseMap.entrySet()) {
            for (int i = 0; i < entrytc.getValue().length; i++) {
                for (Entry<Classification, Integer[]> entrycl : classificationMap
                        .entrySet()) {
                    for (int j = 0; j < entrycl.getValue().length; j++) {
                        if (entrytc.getValue()[i].equals(entrycl.getKey()
                                .getTestDataIds()[j])) {
                            entrytc.getKey().setClassOfMark(
                                    entrytc.getValue()[i],
                                    entrycl.getKey().getTestData()[j]);
                            entrytc.getKey().setClassificationOfMark(
                                    entrytc.getValue()[i],
                                    entrycl.getKey().getName());
                        }
                    }
                }
                markCompositions(classificationMap, compositionMap, entrytc,
                        i);
            }
            // tcList.add(entrytc.getKey());
        }
    }

    /**
     * @param classificationMap
     * @param compositionMap
     * @param entrytc
     * @param i
     */
    private void markCompositions(
            Map<Classification, Integer[]> classificationMap,
            Map<Composition, Integer[]> compositionMap,
            Entry<CteTestCase, Integer[]> entrytc, int i) {
        for (Entry<Composition, Integer[]> entryco : compositionMap
                .entrySet()) {
            for (int k = 0; k < entryco.getValue().length; k++) {
                for (Entry<Classification, Integer[]> entrycl : classificationMap
                        .entrySet()) {
                    if (entrycl.getKey().getId() == (entryco.getValue()[k])) {
                        for (int j = 0; j < entrycl.getValue().length; j++) {
                            if (entrytc.getValue()[i].equals(entrycl
                                    .getValue()[j])) {
                                entrytc.getKey().setCompositionOfMark(
                                        entrycl.getValue()[j],
                                        entryco.getKey().getName());
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @param testcaseMap
     * @param classificationMap
     * @param compositionMap
     */
    private void fillMaps(Map<CteTestCase, Integer[]> testcaseMap,
            Map<Classification, Integer[]> classificationMap,
            Map<Composition, Integer[]> compositionMap) {
        for (CteObject element : cteObjectTree.values()) {
            if (element.getClass().equals(CteTestCase.class)) {
                testcaseMap.put((CteTestCase) element,
                        ((CteTestCase) element).getMarks());
            } else if (element.getClass().equals(Classification.class)) {
                classificationMap.put((Classification) element,
                        ((Classification) element).getTestDataIds());
            } else if (element.getClass().equals(Composition.class)) {
                compositionMap.put((Composition) element,
                        ((Composition) element).getChildIds());
            }
        }
    }

    /**
     * 
     * @param cteFile
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    private void getCteTree(File cteFile) throws IOException {
        ctep = new CTEParser(cteFile);
//        ctep.getCteObjectByName(COMPOSITION);
//        ctep.getCteObjectByName(CLASSIFICATION);
//        ctep.getCteObjectByName(TESTCASE);
        cteTree = ctep.getTree();
        tcList = ctep.getTcList();
//        cteObjectTree = ctep.getCteTree();
    }

    @SuppressWarnings("unused")
    private void printData() {
        for (Integer elem : cteObjectTree.keySet())
            System.out.println(elem + " - " + cteObjectTree.get(elem));

    }

}
