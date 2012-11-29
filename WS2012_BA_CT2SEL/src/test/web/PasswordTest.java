package test.web;

import static org.junit.Assert.*;


import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import cte.CTE;
public class PasswordTest {

	WebDriver driver;
	
	
	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		driver.get("http://www.fcmuckefuck.de");
	}

	@After
	public void tearDown() throws Exception {
	}

	public void testPassword(boolean numeric, boolean uppercase, String length) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.partialLinkText("Homepage")).click();
		driver.findElement(By.id("loginButton")).click();
		if (numeric && uppercase && length.length() >= 8) {
			driver.findElement(By.name("loginUsername")).sendKeys("Benny");
			driver.findElement(By.name("loginPassword")).sendKeys("wirzer");
			driver.findElement(By.name("loginPassword")).submit();

			new WebDriverWait(driver, 10).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {
					return d.getTitle().equalsIgnoreCase("Startseite - FC Muckefuck Bergedorf ..::Freizeitfuﬂball::..");
				}
			});
			assertEquals("Angemeldet als Benny.", driver.findElement(By.id("userNote")).getText());
		} else {
			driver.findElement(By.name("loginUsername")).sendKeys("Benny");
			driver.findElement(By.name("loginPassword")).sendKeys("bla");
			driver.findElement(By.name("loginPassword")).submit();
			
			new WebDriverWait(driver, 10).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {
					return d.getTitle().equalsIgnoreCase("Startseite - FC Muckefuck Bergedorf ..::Freizeitfuﬂball::..");
				}
			});
			assertEquals("Sie sind nicht angemeldet.", driver.findElement(By.id("userNote")).getText());
		}
	}

}
