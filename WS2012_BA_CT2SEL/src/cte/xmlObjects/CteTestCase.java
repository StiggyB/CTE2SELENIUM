package cte.xmlObjects;

import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CteTestCase extends CteObject {

    private static final long        serialVersionUID = 3127651923678251484L;
    private String[]                 marks;
    private Pattern                  idPattern        = Pattern
                                                              .compile("\\d+");
    private HashMap<Integer, String> markMap          = new HashMap<>();
    private HashMap<Integer, String> markCompMap      = new HashMap<>();

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

    public void setValueOfMark(int mark, String value) {
        markMap.put(mark, value);
    }

    public String getValueOfMark(int mark) {
        return markMap.get(mark);
    }

    public void setCompositionOfMark(int mark, String composition) {
        markCompMap.put(mark, composition);
    }

    public String getCompositionOfMark(int mark) {
        return markCompMap.get(mark);
    }

    private void setMarks(String[] marks) {
        this.marks = marks;
    }
    
    public Object[] asArray() {
        Object arr[] = new Object[this.getClass().getDeclaredFields().length];
        arr[0] = this.getId();
        arr[1] = this.getName();
        arr[2] = this.getMarks();
        arr[3] = this.markMap;
        arr[4] = this.markCompMap;
        return arr;
    }

    @Override
    public String toString() {
        return "CteTestCase [marks=" + Arrays.toString(getMarks())
                + ", getId()=" + getId() + ", getName()=" + getName() + "]";
    }

}
