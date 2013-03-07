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
        msp.clickSelectButton();
    }

    @Test
    public void testMSP() {
        assertThat(msp, isA(MediumSelectionPage.class));
    }

    @After
    public void tearDown() throws Exception {
        ServiceConditionPage scp = (ServiceConditionPage) msp.clickNextButton();
        scp.setMedium(msp.getMedium());
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
    }

    public static junit.framework.Test suite(MediumSelectionPage msp) {
        MediumSelectionTest.msp = msp;
        return new JUnit4TestAdapter(MediumSelectionTest.class);
    }

}
