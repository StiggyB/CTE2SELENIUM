package test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class CTETest {

	@Before
	public void setUp() throws Exception {
		testSetUpFile();
	}
	
	@Test
	public void testSetUpFile() throws AssertionError, NoSuchMethodException, SecurityException {
			assertTrue(new Object(){}.getClass().getEnclosingMethod().getName(), false);
	}

//	@Test
//	public void testReadCTEfileByLine() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testTearDownStream() {
//		fail("Not yet implemented");
//	}

}
