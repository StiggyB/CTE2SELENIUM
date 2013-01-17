package de.haw_hamburg.ti.cte.xmlObjects;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Composition extends CteObject {

    private static final long serialVersionUID = -8300766669210613807L;
    private String[][]  classifications;
    private Pattern idPattern = Pattern.compile("\\d+");
    private Matcher idMatcher;

    public Composition(String name, String id) {
        super(name, id);
    }

    public void setClassifications(String[][] clarr) {
        this.classifications = clarr;
    }

//    public String[] getClassificationIds() {
//        String[] ids = new String[classifications.length];
//        for (int i = 0, j = 0; i < classifications.length; i++) {
//            ids[i] = classifications[i][j];
//        }
//        return ids;
//    }
    
    public Integer[] getClassificationIds() {
        Integer[] intId = new Integer[classifications.length];
        for (int i = 0, j = 0; i < intId.length; i++) {
            idMatcher = idPattern.matcher(classifications[i][j]);
            idMatcher.find();
            intId[i] = Integer.parseInt(idMatcher.group());
        }
        return intId;
    }
    
    public String[] getClassificationNames() {
        String[] names = new String[classifications.length];
        for (int i = 0, j = 1; i < classifications.length; i++) {
            names[i] = classifications[i][j];
        }
        return names;
    }
    
    @Override
    public String toString() {
        return "Composition [getId()=" + getId() + ", getName()=" + getName()
                + ", classifications=[Ids=" + Arrays.toString(getClassificationIds())
                + ", names=" + Arrays.toString(getClassificationNames()) + "]";
    }

}
