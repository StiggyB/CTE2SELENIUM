package de.haw_hamburg.ti.tools;

public class StringMatch {

    private String s;
    
    public StringMatch(String s) {
        this.s = s;
    }

    public boolean containsStringElements(String searchStrings) {
        if (searchStrings == null) {
            return false;
        }
        String ss[] = searchStrings.split(" ");
        return containsStrings(ss);
    }

    private boolean containsStrings(String[] searchStrings) {
        if (s == null || s.length() == 0 || searchStrings == null
                || searchStrings.length == 0) {
            return false;
        }
        int cnt = 0;
        for (int i = 0; i < searchStrings.length; i++) {
            if (s.contains(searchStrings[i])) {
                cnt++;
                if (cnt == searchStrings.length) {
                    return true;
                }
            }
        }
        return false;
    }

}
