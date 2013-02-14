package de.haw_hamburg.ti.runTimeTests;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertThat;

import de.haw_hamburg.ti.c2s.com.valvestar.MediumSelectionPage;
import de.haw_hamburg.ti.c2s.com.valvestar.ServiceConditionPage;

public class MediumSelectionTest {

    private static MediumSelectionPage msp;

    @Before
    public void setUp() throws Exception {
        System.out.println(this.getClass().getSimpleName() + "-> setting up");
        msp.clickSelectButton();
        System.out.println(this.getClass().getSimpleName()
                + "-> clicked select");
    }

    @Test
    public void testMSP() {
        System.out.print(this.getClass().getSimpleName()
                + "-> assert right page");
        assertThat(msp, isA(MediumSelectionPage.class));
        System.out.println("-> did work");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println(this.getClass().getSimpleName() + "-> tear down");
        ServiceConditionPage scp = msp.clickNextButton();
        scp.setMedium(msp.getMedium());
        System.out.println(this.getClass().getSimpleName()
                + "-> clicked next");
        org.junit.runner.JUnitCore jc = new JUnitCore();
        Result r = new Result();
        r = jc.run(ServiceCondtitionTest.suite(scp));
        if (r.getFailureCount() > 0) {
            System.out.println(this.getClass().getSimpleName() + "-> nof: "
                    + r.getFailureCount() + " nor: " + r.getRunCount()
                    + " time: " + r.getRunTime() + " failuredescr.: "
                    + r.getFailures().get(0).getTrace() + " message: "
                    + r.getFailures().get(0).getMessage());
        }
        msp = (MediumSelectionPage) scp.clickBackButton();
        System.out.println(this.getClass().getSimpleName()
                + "-> clicked back");
    }

    public static junit.framework.Test suite(MediumSelectionPage msp) {
        System.out.println(MediumSelectionTest.class.getSimpleName()
                + "-> suite up");
        MediumSelectionTest.msp = msp;
        return new JUnit4TestAdapter(MediumSelectionTest.class);
    }

}
