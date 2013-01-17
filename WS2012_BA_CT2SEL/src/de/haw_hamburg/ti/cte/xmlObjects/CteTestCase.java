package de.haw_hamburg.ti.cte.xmlObjects;

import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CteTestCase extends CteObject {

    private static final long        serialVersionUID      = 3127651923678251484L;
    private String[]                 marks;
    private Pattern                  idPattern             = Pattern
                                                                   .compile("\\d+");
    private HashMap<Integer, String> markClassMap               = new HashMap<>();
    private HashMap<Integer, String> markClassificationMap = new HashMap<>();
    private HashMap<Integer, String> markCompositionMap    = new HashMap<>();

    public CteTestCase(String name, String id, String marks) {
        super(name, id);
        this.setMarks(splitMarks(marks));
    }

    private String[] splitMarks(String marks) {
        String[] markarr = marks.split(" ");
        return markarr;
    }

    public Integer[] getMarks() {
        Integer[] intMarks = new Integer[marks.length];
        for (int i = 0; i < marks.length; i++) {
            Matcher idMatcher = idPattern.matcher(marks[i]);
            idMatcher.find();
            intMarks[i] = Integer.parseInt(idMatcher.group());
        }
        return intMarks;
    }

    public void setClassOfMark(int mark, String clazz) {
        markClassMap.put(mark, clazz);
    }

    public String getClassOfMark(int mark) {
        return markClassMap.get(mark);
    }

    public void setClassificationOfMark(int mark, String classification) {
        markClassificationMap.put(mark, classification);
    }

    public String getClassificationOfMark(int mark) {
        return markClassificationMap.get(mark);
    }

    public void setCompositionOfMark(int mark, String composition) {
        markCompositionMap.put(mark, composition);
    }

    public String getCompositionOfMark(int mark) {
        return markCompositionMap.get(mark);
    }

    private void setMarks(String[] marks) {
        this.marks = marks;
    }

    public Object[] asArray() {
        Object arr[] = new Object[this.getClass().getDeclaredFields().length];
        arr[0] = this.getId();
        arr[1] = this.getName();
        arr[2] = this.getMarks();
        arr[3] = this.markClassMap;
        arr[4] = this.markClassificationMap;
        arr[5] = this.markCompositionMap;
        return arr;
    }

    @Override
    public String toString() {
        return "CteTestCase [marks=" + Arrays.toString(getMarks())
                + ", getId()=" + getId() + ", getName()=" + getName() + "]";
    }

}
