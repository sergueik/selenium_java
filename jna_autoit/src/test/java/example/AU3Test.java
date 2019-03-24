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

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.sun.jna.WString;
import com.sun.jna.platform.win32.WTypes.LPWSTR;

/**
 * @authors midorlo, sergueik
 */
public class AU3Test {

	private String title = null;
	private String text = "";
	private final int au3Success = Constants.AU3_SUCCESS;
	private final int au3Failure = Constants.AU3_FAILURE;
	AutoItX instance = null;

	@BeforeMethod
	public void beforeMethod(Method method) {
		instance = AutoItX.getInstance();
	}

	@AfterMethod
	public void afterMethod() {
		// cleanup of instance ?
	}

	@Test(enabled = false)
	public void testGetSingletonInstance() {
		System.err.println("Get Singleton Instances");
		AutoItX instance2 = AutoItX.getInstance();
		assertEquals(instance, instance2);
	}

	@Test(enabled = false)
	public void testInit() {
		System.err.println("Init");
		instance.AU3_Init();
	}

	@Test(enabled = false)
	public void testCloseOpenFileDialog() {
		System.err.println("Close File Dialog");
		title = "Open"; // will switch to "Save"
		text = "";
		int result = instance.AU3_WinClose(new WString(title), new WString(text));
		assertEquals(result, au3Success);
	}

	@Test(enabled = false)
	public void testCloseChromeBrowser() {
		System.err.println("Close Chrome Browser");
		title = "New Tab";
		assertEquals(instance.AU3_WinClose(new WString(title), new WString(text)),
				au3Success);
	}

	@Test(enabled = false)
	public void testCloseFirefoxBrowser() {
		System.err.println("Close Mozilla Firefox Browser");
		title = "Mozilla Firefox Start Page";
		assertEquals(instance.AU3_WinClose(new WString(title), new WString(text)),
				au3Success);
	}

	@Test(enabled = false)
	public void testProvidePathToOpenFile() {
		System.err.println("Provide Path to Open File");
		title = "Open";
		instance.AU3_WinWaitActive(new WString(title), new WString(text));
		instance.AU3_Send(new WString("D:\\AutoIT-commands\\TestingVideo.mp4"));
		instance.AU3_Send(new WString("\n")); // "{ENTER}"
		// close the file not found sub-dialog
		instance.AU3_WinClose(new WString(title), new WString(text));
		// close the file open dialog
		// TODO: confirm the status
		assertEquals(instance.AU3_WinClose(new WString(title), new WString(text)),
				au3Failure);
	}

	@Test(enabled = true)
	public void testClipboard() {
		System.err.println("Put and get data using AU3 Clipboard API");
		String dataPut = "example";
		int dataPutLength = dataPut.length();
		instance.AU3_ClipPut(new WString(dataPut));
		try {
			StringBuilder sb = new StringBuilder(dataPutLength);
			while (sb.length() < dataPutLength) {
				sb.append("*");
			}
			String dataGet = sb.toString();
			// TODO: explain
			int bufSize = dataGet.length() + 1;
			LPWSTR resultPtr = new LPWSTR(dataGet);
			instance.AU3_ClipGet(resultPtr, bufSize);
			dataGet = resultPtr.getValue().toString();
			dataGet.trim();
			assertThat(dataGet, notNullValue());
			System.err.println("Got data: " + dataGet);
			Assert.assertTrue(dataGet.equals(dataPut));
		} catch (Exception e) {
			// Corrupted stdin stream in forked JVM 1. Stream '#' - solved.
			System.err.println("Exception " + e.toString());
		}
	}

	// @Test(enabled = false)
	// public void testAU3_WinActive() {
	// System.err.println("AU3_WinActive");
	// WString arg0 = null;
	// WString arg1 = null;
	// int result = instance.AU3_WinActive(arg0, arg1);
	// assertEquals(result, au3Success);
	// }

	// @Test(enabled = false)
	// public void testAU3_WinExists() {
	// System.err.println("AU3_WinExists");
	// WString arg0 = null;
	// WString arg1 = null;
	// int result = instance.AU3_WinExists(arg0, arg1);
	// assertEquals(result, au3Success);
	// }

	// @Test(enabled = false)
	// public void testAU3_WinGetTitle() {
	// System.err.println("AU3_WinGetTitle");
	// WString arg0 = null;
	// WString arg1 = null;
	// WTypes.LPWSTR arg2 = null;
	// int arg3 = 0;
	// instance.AU3_WinGetTitle(arg0, arg1, arg2, arg3);
	// }

}
