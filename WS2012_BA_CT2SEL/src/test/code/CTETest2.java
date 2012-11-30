package test.code;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import c2s.TC;

import cte.CTE;

public class CTETest2 {

	private CTE cte = new CTE();
	
	@Before
	public void setUp() throws Exception {
		File dummyFile = new File("password.cte");
		cte.setUpFile(dummyFile);
		cte.getNodes();
	}

	@Ignore
	@Test
	public void testSetUpFile() throws IOException {
		File dummyFile = new File("/password.cte");
		assertTrue(cte.setUpFile(dummyFile));
	}
	
	@Ignore
	@Test
	public void testReadCTEfileByLine() throws IOException {
		File dummyFile = new File("/password.cte");
		cte.setUpFile(dummyFile);
	}
	
	@Ignore
	@Test
	public void testGetNodes() {
		assertFalse(cte.getNodes());
	}
	
	@Ignore
	@Test
	public void testGetCteObject() {
		assertFalse(cte.getCteObjects().isEmpty());
	}
	
	@Test
	public void testGetTestData() {
		ArrayList<TC> tcList = cte.getTestData();
		System.out.println(tcList.toString());
		for (TC tc : tcList) {
			assertEquals(TC.class, tc.getClass());
		} 
		cte.saveTestCasesToFile();
	}
	
	@After
	public void tearDown() throws Exception {
		
	}

}
