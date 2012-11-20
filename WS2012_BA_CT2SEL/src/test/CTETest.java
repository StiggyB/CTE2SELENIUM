package test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CTETest {
	WebDriver driver = new FirefoxDriver(new FirefoxBinary(new File("S:/Mozilla/Firefox/firefox.exe")), new FirefoxProfile());

	@Before
	public void setUp() throws Exception {
		driver.get("http://www.fcmuckefuck.de");
	}
	
//	Get Method Name in assert message
//	public void testSetUpFile() throws AssertionError, NoSuchMethodException, SecurityException {
//			assertTrue(new Object(){}.getClass().getEnclosingMethod().getName(), true);
//	}

	@Test
	public void testFirstLink() {
		driver.findElement(By.partialLinkText("Homepage")).click();
		assertEquals("Startseite - FC Muckefuck Bergedorf ..::Freizeitfuﬂball::..", driver.getTitle());
	}
	
	@Test
	public void testLogin() throws Exception {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.partialLinkText("Homepage")).click();
		driver.findElement(By.id("loginButton")).click();
		driver.findElement(By.name("loginUsername")).sendKeys("Benny");
		driver.findElement(By.name("loginPassword")).sendKeys("wirzer");
//		driver.findElement(By.name("useCookies")).click();
		driver.findElement(By.name("loginPassword")).submit();
		System.out.println("Page title is: " + driver.getTitle());

		new WebDriverWait(driver, 10).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.getTitle().equalsIgnoreCase("Startseite - FC Muckefuck Bergedorf ..::Freizeitfuﬂball::..");
			}
		});
		assertEquals("Angemeldet als Benny.", driver.findElement(By.id("userNote")).getText());
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
