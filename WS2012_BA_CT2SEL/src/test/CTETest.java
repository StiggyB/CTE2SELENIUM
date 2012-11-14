package test;

import java.io.File;

import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.*;

public class CTETest {
	WebDriver driver = new FirefoxDriver(new FirefoxBinary(new File("S:/Mozilla/Firefox/firefox.exe")), new FirefoxProfile());

	@Before
	public void setUp() throws Exception {
		driver.get("http://www.google.com");
	}
	
//	public void testSetUpFile() throws AssertionError, NoSuchMethodException, SecurityException {
//			assertTrue(new Object(){}.getClass().getEnclosingMethod().getName(), true);
//	}

	@Test
	public void testAdvancedSearch() throws Exception {
		driver.findElement(By.name("q")).sendKeys("Selenium 2.0 WebDriver");
		driver.findElement(By.name("q")).submit();
		System.out.println("Page title is: " + driver.getTitle());
	}
	
	@After
	public void tearDown() {
		driver.quit();
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
