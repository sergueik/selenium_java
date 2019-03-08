/*
 * Copyright 2018 midorlo
 * Updated 2019 by sergueik
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example;

import org.testng.Assert;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.fail;

// import org.openqa.selenium.Keys;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.containsString;

import com.sun.jna.WString;

/**
 * @authors midorlo, sergueik
 */
public class AutoItV3JnaTest {

	private String title = null;
	private String text = "";
	private final int expResult = Constants.AU3_SUCCESS;

	@Test(enabled = false)
	public void testGetInstance() {
		System.err.println("Get Singleton Instance");
		AutoItX expResult = AutoItX.getInstance();
		AutoItX result = AutoItX.getInstance();
		assertEquals(result, expResult);
	}

	@Test(enabled = false)
	public void testInitAU3() {
		System.err.println("Init");
		AutoItX instance = new AutoItX();
		instance.AU3_Init();
	}

	@Test(enabled = false)
	public void testCloseOpenFileDialog() {
		System.err.println("Close File Dialog");
		title = "Open"; // will switch to "Save"
		text = "";
		AutoItX instance = new AutoItX();
		int result = instance.AU3_WinClose(new WString(title), new WString(text));
		assertEquals(result, expResult);
	}

	@Test(enabled = true)
	public void testCloseOpenFileDialog2() {
		System.err.println("Close File Dialog");
		title = "Open"; // will switch to "Save"
		AutoItX instance = new AutoItX();
		assertFalse(instance.WinClose(title, text));
	}

	@Test(enabled = false)
	public void testCloseChromeBrowser() {
		System.err.println("Close Chrome Browser");
		title = "New Tab"; // chrome
		AutoItX instance = new AutoItX();
		assertEquals(instance.AU3_WinClose(new WString(title), new WString(text)),
				expResult);
	}

	@Test(enabled = false)
	public void testCloseFirefoxBrowser() {
		System.err.println("Close Mozilla Firefox Browser");
		title = "Mozilla Firefox Start Page"; // firefox
		AutoItX instance = new AutoItX();
		assertEquals(instance.AU3_WinClose(new WString(title), new WString(text)),
				expResult);
		// TODO: send the jna equivalent of Keys.chord(Keys.CONTROL, Keys.ADD)
		// e.g. to zoom
	}

	@Test(enabled = false)
	public void testProvidePathToOpenFile() {
		System.err.println("Provide Path to Open File");
		title = "Open";
		AutoItX instance = new AutoItX();
		instance.AU3_WinWaitActive(new WString(title), new WString(text));
		instance.AU3_Send(new WString("D:\\AutoIT-commands\\TestingVideo.mp4"));
		instance.AU3_Send(new WString("\n")); // "{ENTER}"
		// close the file not found sub-dialog
		instance.AU3_WinClose(new WString(title), new WString(text));
		// close the file open dialog
		assertEquals(instance.AU3_WinClose(new WString(title), new WString(text)),
				0);
	}

	@Test(enabled = true)
	public void testProvidePathToOpenFile2() {
		System.err.println("Provide Path to Open File");
		title = "Open";
		AutoItX instance = new AutoItX();
		instance.WinWaitActive(title, text);
		instance.Send("D:\\AutoIT-commands\\TestingVideo.mp4");
		instance.Send("\n"); // "{ENTER}"
		// close the file not found sub-dialog
		instance.WinClose(title, text);
		// close the file open dialog
		assertFalse(instance.WinClose(title, text));
	}

	@Test(enabled = false)
	public void testEnvironment() {
		System.err
				.println("Test OS Environment on " + System.getProperty("os.name"));
		String expResult = null;
		String result = System.getProperty("os.name");
		Assert.assertTrue((expResult = "Windows 10").equals(result)
				|| (expResult = "Windows 8").equals(result)
				|| (expResult = "Windows 8.1").equals(result)
				|| (expResult = "Windows 7").equals(result)
				|| (expResult = "Windows Server 2012 R2").equals(result));

		// NOTE: anyOf(containsString(expResult1),...) does not work with testng ?
		// see also:
		// https://www.programcreek.com/java-api-examples/index.php?api=org.hamcrest.core.AnyOf
	}

	// @Test(enabled = false)
	// public void testAU3_WinActive() {
	// System.err.println("AU3_WinActive");
	// WString arg0 = null;
	// WString arg1 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = Constants.AU3_SUCCESS;;
	// int result = instance.AU3_WinActive(arg0, arg1);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }

	// @Test(enabled = false)
	// public void testAU3_WinExists() {
	// System.err.println("AU3_WinExists");
	// WString arg0 = null;
	// WString arg1 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = Constants.AU3_SUCCESS;;
	// int result = instance.AU3_WinExists(arg0, arg1);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }

	// @Test(enabled = false)
	// public void testAU3_WinGetTitle() {
	// System.err.println("AU3_WinGetTitle");
	// WString arg0 = null;
	// WString arg1 = null;
	// WTypes.LPWSTR arg2 = null;
	// int arg3 = 0;
	// AutoItX instance = new AutoItX();
	// instance.AU3_WinGetTitle(arg0, arg1, arg2, arg3);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }

	public void sleep(Integer milliSeconds) {
		try {
			Thread.sleep((long) milliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
