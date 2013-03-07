package de.haw_hamburg.ti.runTimeTests;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class NewPageRule extends TestWatcher {

    private static boolean failed;
    private static int     succeededTestCases = 0;
    private static int     noOfTestcases      = 7; ;

    public NewPageRule(int noT) {
        if (noOfTestcases >= noT) {
            failed = false;
            noOfTestcases = 0;
            succeededTestCases = 0;
        } else {
            noOfTestcases++;
        }
        System.out.println("#NPRCONST# failed?->" + isFailed());
    }

    @Override
    protected void failed(Throwable e, Description description) {
        failed = true;
        succeededTestCases = 0;
    }

    @Override
    protected void succeeded(Description description) {
        succeededTestCases++;
    }

    protected boolean isFailed() {
        return failed;
    }

    public int getSucceededTestCases() {
        return succeededTestCases;
    }
}
