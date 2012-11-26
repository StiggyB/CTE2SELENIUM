package test.code;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.testng.internal.MapList;

import cte.CTE;
import cte.Composition;

public class CTETest2 {

	private CTE cte = new CTE();
	
	@Before
	public void setUp() throws Exception {
		File dummyFile = new File("/password.cte");
		cte.setUpFile(dummyFile);
		while (cte.readCTEfileByLine() != null);
	}

	@After
	public void tearDown() throws Exception {
		
	}

//	@Test
//	public void testReadCTEfileByLine() {
//		
//	}
	
	@Test
	public void testExample() {
		cte.runExample();
	}
	
	@Test
	public void testGetCteObject() {
		
	}


}
