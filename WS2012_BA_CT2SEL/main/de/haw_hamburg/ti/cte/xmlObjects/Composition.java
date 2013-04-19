package de.haw_hamburg.ti.cte.xmlObjects;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Composition extends CteObject {

    private static final long serialVersionUID = -8300766669210613807L;
    private String[][]  childs;
    private Pattern idPattern = Pattern.compile("\\d+");
    private Matcher idMatcher;

    public Composition(String name, String id) {
        super(name, id);
    }

    public void setClassifications(String[][] clarr) {
        this.childs = clarr;
    }
    
    public Integer[] getChildIds() {
        Integer[] intId = new Integer[childs.length];
        for (int i = 0, j = 0; i < intId.length; i++) {
            idMatcher = idPattern.matcher(childs[i][j]);
            idMatcher.find();
            intId[i] = Integer.parseInt(idMatcher.group());
        }
        return intId;
    }
    
    public String[] getChildNames() {
        String[] names = new String[childs.length];
        for (int i = 0, j = 1; i < childs.length; i++) {
            names[i] = childs[i][j];
        }
        return names;
    }
    
    @Override
    public String toString() {
        return "Composition [getId()=" + getId() + ", getName()=" + getName();
//                + ", childs=[Ids=" + Arrays.toString(getChildIds())
//                + ", names=" + Arrays.toString(getChildNames()) + "]";
    }

}
