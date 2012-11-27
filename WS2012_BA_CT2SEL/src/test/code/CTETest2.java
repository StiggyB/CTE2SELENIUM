package test.code;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import cte.CTE;

public class CTETest2 {

	private CTE cte = new CTE();
	
	@Before
	public void setUp() throws Exception {
		File dummyFile = new File("/password.cte");
		cte.setUpFile(dummyFile);
		while (cte.readCTEfileByLine() != null);
		cte.getNodes();
	}

	@Ignore
	public void testSetUpFile() throws IOException {
		File dummyFile = new File("/password.cte");
		assertTrue(cte.setUpFile(dummyFile));
	}
	
	@Ignore
	public void testReadCTEfileByLine() throws IOException {
		File dummyFile = new File("/password.cte");
		cte.setUpFile(dummyFile);
		assertNotNull(cte.readCTEfileByLine());
	}
	
	@Ignore
	public void testGetNodes() {
		assertFalse(cte.getNodes());
	}
	
	@Ignore
	public void testGetCteObject() {
		assertFalse(cte.getCteObjects().isEmpty());
	}
	
	@Test
	public void testGetTestData() {
		cte.getTestData();
	}
	

	@After
	public void tearDown() throws Exception {
		
	}

}
