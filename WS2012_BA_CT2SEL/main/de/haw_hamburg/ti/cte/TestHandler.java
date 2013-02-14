package de.haw_hamburg.ti.cte;

import java.util.ArrayList;

import de.haw_hamburg.ti.cte.xmlObjects.CteTestCase;

public final class TestHandler {

    private static ArrayList<CteTestCase> tests;
    
    private TestHandler() {}
    
    public static void testTests() {
        System.out.println(tests.get(0).getId() + ": " + tests.get(0).getName());
        System.out.println("-----------------------------------------------");
    }

    public static void addList(ArrayList<CteTestCase> ctes) {
        tests = ctes;
    }
    
    public static ArrayList<CteTestCase> getList() {
        return tests;
    }
}
