package de.haw_hamburg.ti.test.web;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Test2profile {
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();
	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://www.fcmuckefuck.de/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void test2profile() throws Exception {
		driver.get(baseUrl + "/");
		driver.findElement(By.linkText("Weiter zur Homepage...")).click();
		driver.findElement(By.cssSelector("span")).click();
		driver.findElement(By.id("loginUsername")).clear();
		driver.findElement(By.id("loginUsername")).sendKeys("Benny");
		driver.findElement(By.id("loginPassword")).clear();
		driver.findElement(By.id("loginPassword")).sendKeys("wirzer");
		driver.findElement(By.name("useCookies")).click();
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.linkText("Falls die automatische Weiterleitung nicht funktioniert, klicken Sie bitte hier!")).click();
		driver.findElement(By.cssSelector("#userMenuProfileEdit > a > span")).click();
		driver.findElement(By.id("aboutMe")).clear();
		driver.findElement(By.id("aboutMe")).sendKeys("testtest");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.cssSelector("span")).click();
		// ERROR: Caught exception [ERROR: Unsupported command [getAllFields |  | ]]
		// Warning: verifyTextPresent may require manual changes
		try {
			assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Sie sind nicht angemeldet[\\s\\S]*$"));
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
		driver.findElement(By.cssSelector("span")).click();
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	@SuppressWarnings("unused")
    private boolean isElementPresent(By by) throws NoSuchElementException {
		driver.findElement(by);
		return true;
	}
}
