/*
 * Copyright 2018-2019 midorlo, sergueik
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import com.sun.jna.Pointer;
import com.sun.jna.WString;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.User32;

/**
 * @author midorlo
 * @author sergueik
 */
public class AutoItTest {

	private String title = null;
	private String text = "";
	private String result = null;
	private AutoItX instance = null;
	private static final boolean debug = true;

	@BeforeMethod
	public void beforeMethod() {
		instance = AutoItX.getInstance();
	}

	@AfterMethod
	public void afterMethod() {
		// cleanup of instance ?
	}

	@Test(enabled = false)
	public void testCloseOpenFileDialog() {
		System.err.println("Close File Dialog");
		title = "Open"; // title will become "Save"
		assertFalse(instance.WinClose(title, text));
	}

	@Test(enabled = true)
	public void testClipboard() {
		System.err.println("Put and get data using Clipboard");
		String dataPut = "example";
		try {
			instance.ClipPut(dataPut);
			String dataGet = instance.ClipGet();
			Assert.assertTrue(dataGet.equals(dataPut));
		} catch (Exception e) {
			// Corrupted stdin stream in forked JVM 1. Stream '#' - solved.
			System.err.println("Exception " + e.toString());
		}
	}

	@Test(enabled = true)
	public void testActiveWindowClassList() {
		System.err.println("Get active window class List information");
		title = "[ACTIVE]";
		try {
			List<String> classList = instance.WinGetClassList(title, text);
			assertThat(classList, notNullValue());
			assertThat(classList.get(0), notNullValue());
			for (String className : classList) {
				System.err.println(String.format("Class name: \"%s\"", className));
			}
			// e.g.for Chrome browser: Chrome_RenderWidgetHostHWND, "Intermediate
			// Software Window"
			// for Internet Explorer: BrowserFrameGripperClass, "Client Caption",
			// WorkerW, ReBarWindow32 etc.
			// for Firefox: nothing
		} catch (Exception e) {
			// Corrupted stdin stream in forked JVM 1. Stream '#' - solved.
			System.err.println("Exception " + e.toString());
		}
	}

	@Test(enabled = true)
	public void testActiveWindowState() {
		System.err.println("Get active window state information");
		title = "[ACTIVE]";
		String state = instance.WinGetState(title, text);
		assertThat(state, notNullValue());
		List<String> stateList = new ArrayList<>();
		stateList = Arrays.asList(state.split("\\n"));

		assertThat(stateList.get(0), notNullValue());
		for (String stateName : stateList) {
			System.err.println(String.format("Window state: \"%s\"", stateName));
		}

	}

	@Test(enabled = true)
	public void testActiveWindowTitle() {
		System.err.println("Get active window title");
		title = "[ACTIVE]";
		String windowTitle = instance.WinGetTitle(title, text);
		assertThat(windowTitle, notNullValue());
		System.err.println(
				String.format("The active window title is \"%s\"", windowTitle));
	}

	@Test(enabled = true)
	public void testActiveWindowHandle() {
		System.err.println("Get active window handle as string");
		title = "[ACTIVE]";
		String windowTitle = instance.WinGetTitle(title, text);
		assertThat(windowTitle, notNullValue());
		String windowHandle = instance.WinGetHandleAsText(title, text);
		assertThat(windowHandle, notNullValue());

		// https://stackoverflow.com/questions/33705230/how-can-i-get-hwnd-from-string

		HWND hwnd = new HWND();
		hwnd.setPointer(new Pointer(Long.decode(windowHandle)));
		assertTrue(User32.INSTANCE.IsWindow(hwnd));
		System.err.println(String.format(
				"Window \"%s\" handle is %s. It is a valid window handle.", windowTitle,
				windowHandle));
	}

	@Test(enabled = true)
	public void testActiveWindowGetTextByHandle() {
		System.err.println("Get active window text by handle (as string)");
		title = "[ACTIVE]";
		String windowTitle = instance.WinGetTitle(title, text);
		assertThat(windowTitle, notNullValue());
		String windowHandle = instance.WinGetHandleAsText(title, text);
		assertThat(windowHandle, notNullValue());
		String windowText = instance.WinGetTextByHandle(windowHandle);
		assertThat(windowText, notNullValue());
		System.err.println(String.format("Window \"%s\" text is \"%s\".",
				windowTitle, windowText));
	}

	@Test(enabled = false)
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

	@Test(enabled = false)
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

	public void sleep(Integer milliSeconds) {
		try {
			Thread.sleep((long) milliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
