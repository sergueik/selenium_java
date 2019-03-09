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

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import com.sun.jna.WString;

/**
 * @authors midorlo, sergueik
 */
public class AutoItTest {

	private String title = null;
	private String text = "";
	private String result = null;
	private AutoItX instance = null;

	@BeforeMethod
	public void beforeMethod() {
		instance = AutoItX.getInstance();
	}

	@AfterMethod
	public void afterMethod() {
		// cleanup of instance ?
	}

	@Test(enabled = true)
	public void testCloseOpenFileDialog() {
		System.err.println("Close File Dialog");
		title = "Open"; // title will become "Save"
		assertFalse(instance.WinClose(title, text));
	}

	@Test(enabled = true)
	public void testZoomFirefoxBrowser() {
		System.err.println("Close Mozilla Firefox Browser");
		title = "Mozilla Firefox Start Page";
		instance.AutoItSetOption("SendKeyDownDelay", 30);
		instance.AutoItSetOption("SendKeyDelay", 10);
		// zoom out four times
		for (int cnt = 0; cnt != 4; cnt++) {
			instance.Send("^-", true);
			sleep(1000);
		}
		// zoom 100 %
		instance.Send("^0", true);
		sleep(1000);
		// CTLR + is a bit tricky since the '+' itself has a special meaning
		// zoom in 2 times
		instance.Send("^{+}^{+}", true);
		sleep(1000);
		instance.WinClose(title, text);
	}

	@Test(enabled = true)
	public void testProvidePathToOpenFile() {
		System.err.println("Provide Path to Open File");
		title = "Open";
		// customized to exercise shift modifier
		String filePath = "D:\\AutoIT-commands\\TestingV+i+d+e+o.mp4";
		instance.WinWaitActive(title, text);
		// will successfully send a shift key
		instance.Send(filePath, true);
		sleep(100);
		instance.Send("\n"); // "{ENTER}"
		result = instance.WinGetText(title, text);
		assertThat(result, notNullValue());
		// TODO: get actual dialog text
		// currently receiving the text of the button having the focus ("OK")
		System.err.println("Result is: " + result);
		sleep(100);
		// close the "file not found" sub-dialog
		instance.WinClose(title, text);
		sleep(100);
		// close the file open dialog
		assertTrue(instance.WinClose(title, text));
	}

	@Test(enabled = true)
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

	public void sleep(Integer milliSeconds) {
		try {
			Thread.sleep((long) milliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
