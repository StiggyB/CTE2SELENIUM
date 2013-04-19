package de.haw_hamburg.ti.runTimeTests;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class NewPageRule extends TestWatcher {

    /**
     * Thought of this is that tests can signal of they are done in the bool
     * array.
     * 
     * When a tests finishes all its playthroughs, next test !could! finish the
     * wizard
     */
    private enum pageNames {
        STAMS, SCP, IPC
    }

    private static boolean   failed;
    private static int       succeededTestCases = 0;
    private static int       noOfTestcases      = 0;
    private static boolean[] pageFinished;
    private int              noT;
    private PageTest         test;

    public NewPageRule(int noT, PageTest test) {
        this.noT = noT;
        this.test = test;
        System.out.println("-------------------------------------");
    }

    @Override
    protected void failed(Throwable e, Description description) {
        System.out.println(">>>>TC FAILED<<<<");
        failed = true;
        // succeededTestCases = 0;
    }

    @Override
    protected void succeeded(Description description) {
        System.out.println(">>>>TC SUCCEEDED<<<<");
        succeededTestCases++;
    }

    @Override
    protected void skipped(org.junit.internal.AssumptionViolatedException e,
            Description description) {
        System.out.println(">>>>TC SKIPPED<<<<");
    }

    @Override
    protected void finished(Description description) {
        System.out.println(">>>>TC FINISHED<<<<");
        System.out.println("Succeeded: " + getSucceededTestCases() + "("
                + getNoOfTestcases() + " of " + noT + ")[Failed?: "
                + isFailed() + "]");
        if (getNoOfTestcases() == noT) {
            if (getSucceededTestCases() == noT) {
                reset();
                test.evalNextPage();
            } else {
                reset();
            }
        }
    }

    protected boolean isFailed() {
        return failed;
    }

    public int getSucceededTestCases() {
        return succeededTestCases;
    }

    public int getNoOfTestcases() {
        return noOfTestcases;
    }

    public void reset() {
        succeededTestCases = 0;
        noOfTestcases = 0;
        failed = false;
    }

    public void updateFinishedTestcases() {
        noOfTestcases++;
    }
}
