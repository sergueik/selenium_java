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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

// import org.openqa.selenium.Keys;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.sun.jna.WString;

/**
 * @authors midorlo, sergueik
 */
public class AutoItXNGTest {

	public AutoItXNGTest() {
	}

	@Test
	public void testGetInstance() {
		System.err.println("getInstance");
		AutoItX expResult = AutoItX.getInstance();
		AutoItX result = AutoItX.getInstance();
		assertEquals(result, expResult);
	}

	@Test
	public void testAU3_Init() {
		System.err.println("Init");
		AutoItX instance = new AutoItX();
		instance.AU3_Init();
	}

	@Test
	public void testAU3_WinCloseFileDialog() {
		System.err.println("Close File Dialog");
		String szTitle = "Open"; // will switch to "Save"
		String szText = "";
		AutoItX instance = new AutoItX();
		int expResult = Constants.AU3_SUCCESS;
		// int result = instance.AU3_WinClose(szTitle, szText);
		int result = instance.AU3_WinClose(new WString(szTitle),
				new WString(szText));

		assertEquals(result, expResult);
	}

	@Test
	public void testAU3_WinCloseChromeBrowser() {
		System.err.println("Close Browser");
		String szTitle = "New Tab"; // chrome
		String szText = "";
		AutoItX instance = new AutoItX();
		int expResult = Constants.AU3_SUCCESS;
		int result = instance.AU3_WinClose(new WString(szTitle),
				new WString(szText));

		assertEquals(result, expResult);
	}

	@Test
	public void testAU3_WinCloseFirefoxBrowser() {
		System.err.println("Close Browser");
		String szTitle = "Mozilla Firefox Start Page"; // firefox
		String szText = "";
		AutoItX instance = new AutoItX();
		int expResult = Constants.AU3_SUCCESS;
		// int result = instance.AU3_WinClose(szTitle, szText);
		int result = instance.AU3_WinClose(new WString(szTitle),
				new WString(szText));

		assertEquals(result, expResult);
		// TODO: send the jna equivalent of Keys.chord(Keys.CONTROL, Keys.ADD)
		// e.g. to zoom
	}

	@Test
	public void testAU3_WinOpenFile() {
		System.err.println("Provide Path to Open File");
		String szTitle = "Open";
		String szText = "";
		AutoItX instance = new AutoItX();
		int expResult = Constants.AU3_SUCCESS;
		// int result = instance.AU3_WinClose(szTitle, szText);
		instance.AU3_WinWaitActive(new WString(szTitle), new WString(szText));
		instance.AU3_Send(new WString("D:\\AutoIT-commands\\TestingVideo.mp4"));
		instance.AU3_Send(new WString("\n")); // "{ENTER}"
		// INPUT.
		instance.AU3_WinClose(new WString(szTitle), new WString(szText));
		int result = instance.AU3_WinClose(new WString(szTitle),
				new WString(szText));

		assertEquals(result, 0);
	}

	// @Test
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
	//
	// @Test
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
	//
	// @Test
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
	//
}
