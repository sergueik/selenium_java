package org.examples;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.ChromeDriverManager;

public class FullScreenshotTest {

	private WebDriver driver;

	@BeforeClass
	public static void setupClass() {
		ChromeDriverManager.getInstance().setup();
	}

	@Before
	public void setupTest() {
		driver = new ChromeDriver();
	}

	@After
	public void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	public void test() throws Exception {
		driver.get("https://en.wikipedia.org/wiki/Main_Page");

		Robot robot = new Robot();
		Rectangle captureSize = new Rectangle(
				Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage capture = robot.createScreenCapture(captureSize);

		ImageIO.write(capture, "jpg", new File("full-screenshot.jpg"));
	}

}
