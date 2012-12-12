package test.code;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.SAXException;

import c2s.CTTestCase;

import cte.CTE;

public class CTETest2 {

	private CTE cte = new CTE();
	
	@Before
	public void setUp() throws Exception {
		File dummyFile = new File("password.cte");
		cte.setUpFile(dummyFile);
		cte.getNodes(dummyFile);
	}

	@Ignore
	@Test
	public void testSetUpFile() throws IOException {
		File dummyFile = new File("/password.cte");
		assertTrue(cte.setUpFile(dummyFile));
	}
	
	@Ignore
	@Test
	public void testGetNodes() {
		File dummyFile = new File("password.cte");
		assertFalse(cte.getNodes(dummyFile));
	}
	
	@Ignore
	@Test
	public void testGetCteObject() {
		assertFalse(cte.getCteObjects().isEmpty());
	}
	
	@Ignore
	@Test
	public void testGetTestData() {
		ArrayList<CTTestCase> tcList = cte.getTestData();
		System.out.println(tcList.toString());
		for (CTTestCase tc : tcList) {
			assertEquals(CTTestCase.class, tc.getClass());
		} 
		cte.saveTestCasesToFile();
	}
	
	@Test
	public void testSchema() throws SAXException, IOException {
		cte.testSchema(new File("password.cte"));
	}
	
	@After
	public void tearDown() throws Exception {
		
	}

}
