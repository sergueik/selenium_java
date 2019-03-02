/*
 * Copyright 2018 midorlo.
 *
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

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author midorlo
 */
public class AutoItXNGTest {

	public AutoItXNGTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@BeforeMethod
	public void setUpMethod() throws Exception {
	}

	@AfterMethod
	public void tearDownMethod() throws Exception {
	}

	/**
	 * Test of getInstance method, of class AutoItX.
	 */
	@Test
	public void testGetInstance() {
		System.out.println("getInstance");
		AutoItX expResult = AutoItX.getInstance();
		AutoItX result = AutoItX.getInstance();
		assertEquals(result, expResult);
	}

	// /**
	// * Test of AU3_ProcessSetPriority method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_ProcessSetPriority() {
	// System.out.println("AU3_ProcessSetPriority");
	// WString arg0 = null;
	// int arg1 = 0;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_ProcessSetPriority(arg0, arg1);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }

	/**
	 * Test of AU3_Init method, of class AutoItX.
	 */
	@Test
	public void testAU3_Init() {
		System.out.println("AU3_Init");
		AutoItX instance = new AutoItX();
		instance.AU3_Init();
	}

	// /**
	// * Test of AU3_IsAdmin method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_IsAdmin() {
	// System.out.println("AU3_IsAdmin");
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_IsAdmin();
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }

	// /**
	// * Test of AU3_ControlGetFocus method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_ControlGetFocus() {
	// System.out.println("AU3_ControlGetFocus");
	// WString arg0 = null;
	// WString arg1 = null;
	// WTypes.LPWSTR arg2 = null;
	// int arg3 = 0;
	// AutoItX instance = new AutoItX();
	// instance.AU3_ControlGetFocus(arg0, arg1, arg2, arg3);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_MouseGetCursor method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_MouseGetCursor() {
	// System.out.println("AU3_MouseGetCursor");
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_MouseGetCursor();
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_PixelGetColor method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_PixelGetColor() {
	// System.out.println("AU3_PixelGetColor");
	// int arg0 = 0;
	// int arg1 = 0;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_PixelGetColor(arg0, arg1);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinClose method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinClose() {
	// System.out.println("AU3_WinClose");
	// String szTitle = "";
	// String szText = "";
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_WinClose(szTitle, szText);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_ProcessExists method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_ProcessExists() {
	// System.out.println("AU3_ProcessExists");
	// WString arg0 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_ProcessExists(arg0);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_ControlListViewByHandle method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_ControlListViewByHandle() {
	// System.out.println("AU3_ControlListViewByHandle");
	// WinDef.HWND arg0 = null;
	// WinDef.HWND arg1 = null;
	// WString arg2 = null;
	// WString arg3 = null;
	// WString arg4 = null;
	// WTypes.LPWSTR arg5 = null;
	// int arg6 = 0;
	// AutoItX instance = new AutoItX();
	// instance.AU3_ControlListViewByHandle(arg0, arg1, arg2, arg3, arg4, arg5,
	// arg6);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_ControlHideByHandle method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_ControlHideByHandle() {
	// System.out.println("AU3_ControlHideByHandle");
	// WinDef.HWND arg0 = null;
	// WinDef.HWND arg1 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_ControlHideByHandle(arg0, arg1);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_AutoItSetOption method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_AutoItSetOption() {
	// System.out.println("AU3_AutoItSetOption");
	// WString arg0 = null;
	// int arg1 = 0;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_AutoItSetOption(arg0, arg1);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_ControlEnable method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_ControlEnable() {
	// System.out.println("AU3_ControlEnable");
	// WString arg0 = null;
	// WString arg1 = null;
	// WString arg2 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_ControlEnable(arg0, arg1, arg2);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_ControlSetText method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_ControlSetText() {
	// System.out.println("AU3_ControlSetText");
	// WString arg0 = null;
	// WString arg1 = null;
	// WString arg2 = null;
	// WString arg3 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_ControlSetText(arg0, arg1, arg2, arg3);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_ControlEnableByHandle method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_ControlEnableByHandle() {
	// System.out.println("AU3_ControlEnableByHandle");
	// WinDef.HWND arg0 = null;
	// WinDef.HWND arg1 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_ControlEnableByHandle(arg0, arg1);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_ControlGetFocusByHandle method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_ControlGetFocusByHandle() {
	// System.out.println("AU3_ControlGetFocusByHandle");
	// WinDef.HWND arg0 = null;
	// WTypes.LPWSTR arg1 = null;
	// int arg2 = 0;
	// AutoItX instance = new AutoItX();
	// instance.AU3_ControlGetFocusByHandle(arg0, arg1, arg2);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_ControlShowByHandle method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_ControlShowByHandle() {
	// System.out.println("AU3_ControlShowByHandle");
	// WinDef.HWND arg0 = null;
	// WinDef.HWND arg1 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_ControlShowByHandle(arg0, arg1);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_ControlTreeView method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_ControlTreeView() {
	// System.out.println("AU3_ControlTreeView");
	// WString arg0 = null;
	// WString arg1 = null;
	// WString arg2 = null;
	// WString arg3 = null;
	// WString arg4 = null;
	// WString arg5 = null;
	// WTypes.LPWSTR arg6 = null;
	// int arg7 = 0;
	// AutoItX instance = new AutoItX();
	// instance.AU3_ControlTreeView(arg0, arg1, arg2, arg3, arg4, arg5, arg6,
	// arg7);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinMinimizeAll method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinMinimizeAll() {
	// System.out.println("AU3_WinMinimizeAll");
	// AutoItX instance = new AutoItX();
	// String expResult = "";
	// String result = instance.AU3_WinMinimizeAll();
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_ControlFocusByHandle method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_ControlFocusByHandle() {
	// System.out.println("AU3_ControlFocusByHandle");
	// WinDef.HWND arg0 = null;
	// WinDef.HWND arg1 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_ControlFocusByHandle(arg0, arg1);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_ControlCommandByHandle method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_ControlCommandByHandle() {
	// System.out.println("AU3_ControlCommandByHandle");
	// WinDef.HWND arg0 = null;
	// WinDef.HWND arg1 = null;
	// WString arg2 = null;
	// WString arg3 = null;
	// WTypes.LPWSTR arg4 = null;
	// int arg5 = 0;
	// AutoItX instance = new AutoItX();
	// instance.AU3_ControlCommandByHandle(arg0, arg1, arg2, arg3, arg4, arg5);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_ControlGetTextByHandle method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_ControlGetTextByHandle() {
	// System.out.println("AU3_ControlGetTextByHandle");
	// WinDef.HWND arg0 = null;
	// WinDef.HWND arg1 = null;
	// WTypes.LPWSTR arg2 = null;
	// int arg3 = 0;
	// AutoItX instance = new AutoItX();
	// instance.AU3_ControlGetTextByHandle(arg0, arg1, arg2, arg3);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_ControlGetHandleAsText method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_ControlGetHandleAsText() {
	// System.out.println("AU3_ControlGetHandleAsText");
	// WString arg0 = null;
	// WString arg1 = null;
	// WString arg2 = null;
	// WTypes.LPWSTR arg3 = null;
	// int arg4 = 0;
	// AutoItX instance = new AutoItX();
	// instance.AU3_ControlGetHandleAsText(arg0, arg1, arg2, arg3, arg4);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_ControlGetPos method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_ControlGetPos() {
	// System.out.println("AU3_ControlGetPos");
	// WString arg0 = null;
	// WString arg1 = null;
	// WString arg2 = null;
	// WinDef.RECT arg3 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_ControlGetPos(arg0, arg1, arg2, arg3);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_ControlListView method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_ControlListView() {
	// System.out.println("AU3_ControlListView");
	// WString arg0 = null;
	// WString arg1 = null;
	// WString arg2 = null;
	// WString arg3 = null;
	// WString arg4 = null;
	// WString arg5 = null;
	// WTypes.LPWSTR arg6 = null;
	// int arg7 = 0;
	// AutoItX instance = new AutoItX();
	// instance.AU3_ControlListView(arg0, arg1, arg2, arg3, arg4, arg5, arg6,
	// arg7);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_ControlCommand method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_ControlCommand() {
	// System.out.println("AU3_ControlCommand");
	// WString arg0 = null;
	// WString arg1 = null;
	// WString arg2 = null;
	// WString arg3 = null;
	// WString arg4 = null;
	// WTypes.LPWSTR arg5 = null;
	// int arg6 = 0;
	// AutoItX instance = new AutoItX();
	// instance.AU3_ControlCommand(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_ControlGetHandle method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_ControlGetHandle() {
	// System.out.println("AU3_ControlGetHandle");
	// WinDef.HWND arg0 = null;
	// WString arg1 = null;
	// AutoItX instance = new AutoItX();
	// WinDef.HWND expResult = null;
	// WinDef.HWND result = instance.AU3_ControlGetHandle(arg0, arg1);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_ControlDisable method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_ControlDisable() {
	// System.out.println("AU3_ControlDisable");
	// WString arg0 = null;
	// WString arg1 = null;
	// WString arg2 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_ControlDisable(arg0, arg1, arg2);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_ControlGetPosByHandle method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_ControlGetPosByHandle() {
	// System.out.println("AU3_ControlGetPosByHandle");
	// WinDef.HWND arg0 = null;
	// WinDef.HWND arg1 = null;
	// WinDef.RECT arg2 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_ControlGetPosByHandle(arg0, arg1, arg2);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_ControlGetText method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_ControlGetText() {
	// System.out.println("AU3_ControlGetText");
	// WString arg0 = null;
	// WString arg1 = null;
	// WString arg2 = null;
	// WTypes.LPWSTR arg3 = null;
	// int arg4 = 0;
	// AutoItX instance = new AutoItX();
	// instance.AU3_ControlGetText(arg0, arg1, arg2, arg3, arg4);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_ControlSetTextByHandle method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_ControlSetTextByHandle() {
	// System.out.println("AU3_ControlSetTextByHandle");
	// WinDef.HWND arg0 = null;
	// WinDef.HWND arg1 = null;
	// WString arg2 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_ControlSetTextByHandle(arg0, arg1, arg2);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_ControlDisableByHandle method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_ControlDisableByHandle() {
	// System.out.println("AU3_ControlDisableByHandle");
	// WinDef.HWND arg0 = null;
	// WinDef.HWND arg1 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_ControlDisableByHandle(arg0, arg1);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_ControlTreeViewByHandle method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_ControlTreeViewByHandle() {
	// System.out.println("AU3_ControlTreeViewByHandle");
	// WinDef.HWND arg0 = null;
	// WinDef.HWND arg1 = null;
	// WString arg2 = null;
	// WString arg3 = null;
	// WString arg4 = null;
	// WTypes.LPWSTR arg5 = null;
	// int arg6 = 0;
	// AutoItX instance = new AutoItX();
	// instance.AU3_ControlTreeViewByHandle(arg0, arg1, arg2, arg3, arg4, arg5,
	// arg6);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_error method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_error() {
	// System.out.println("AU3_error");
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_error();
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_MouseWheel method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_MouseWheel() {
	// System.out.println("AU3_MouseWheel");
	// WString arg0 = null;
	// int arg1 = 0;
	// AutoItX instance = new AutoItX();
	// instance.AU3_MouseWheel(arg0, arg1);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_DriveMapGet method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_DriveMapGet() {
	// System.out.println("AU3_DriveMapGet");
	// WString arg0 = null;
	// WTypes.LPWSTR arg1 = null;
	// int arg2 = 0;
	// AutoItX instance = new AutoItX();
	// instance.AU3_DriveMapGet(arg0, arg1, arg2);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_DriveMapDel method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_DriveMapDel() {
	// System.out.println("AU3_DriveMapDel");
	// WString arg0 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_DriveMapDel(arg0);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinActive method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinActive() {
	// System.out.println("AU3_WinActive");
	// WString arg0 = null;
	// WString arg1 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_WinActive(arg0, arg1);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinExists method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinExists() {
	// System.out.println("AU3_WinExists");
	// WString arg0 = null;
	// WString arg1 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_WinExists(arg0, arg1);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinGetHandle method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinGetHandle() {
	// System.out.println("AU3_WinGetHandle");
	// WString arg0 = null;
	// WString arg1 = null;
	// AutoItX instance = new AutoItX();
	// WinDef.HWND expResult = null;
	// WinDef.HWND result = instance.AU3_WinGetHandle(arg0, arg1);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_ClipGet method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_ClipGet() {
	// System.out.println("AU3_ClipGet");
	// WTypes.LPWSTR arg0 = null;
	// int arg1 = 0;
	// AutoItX instance = new AutoItX();
	// instance.AU3_ClipGet(arg0, arg1);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_MouseDown method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_MouseDown() {
	// System.out.println("AU3_MouseDown");
	// WString arg0 = null;
	// AutoItX instance = new AutoItX();
	// instance.AU3_MouseDown(arg0);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_ControlHide method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_ControlHide() {
	// System.out.println("AU3_ControlHide");
	// WString arg0 = null;
	// WString arg1 = null;
	// WString arg2 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_ControlHide(arg0, arg1, arg2);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_ControlFocus method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_ControlFocus() {
	// System.out.println("AU3_ControlFocus");
	// WString arg0 = null;
	// WString arg1 = null;
	// WString arg2 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_ControlFocus(arg0, arg1, arg2);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_DriveMapAdd method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_DriveMapAdd() {
	// System.out.println("AU3_DriveMapAdd");
	// WString arg0 = null;
	// WString arg1 = null;
	// int arg2 = 0;
	// WString arg3 = null;
	// WString arg4 = null;
	// WTypes.LPWSTR arg5 = null;
	// int arg6 = 0;
	// AutoItX instance = new AutoItX();
	// instance.AU3_DriveMapAdd(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_Opt method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_Opt() {
	// System.out.println("AU3_Opt");
	// WString arg0 = null;
	// int arg1 = 0;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_Opt(arg0, arg1);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_MouseUp method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_MouseUp() {
	// System.out.println("AU3_MouseUp");
	// WString arg0 = null;
	// AutoItX instance = new AutoItX();
	// instance.AU3_MouseUp(arg0);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_PixelSearch method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_PixelSearch() {
	// System.out.println("AU3_PixelSearch");
	// WinDef.RECT arg0 = null;
	// int arg1 = 0;
	// int arg2 = 0;
	// int arg3 = 0;
	// Pointer arg4 = null;
	// AutoItX instance = new AutoItX();
	// instance.AU3_PixelSearch(arg0, arg1, arg2, arg3, arg4);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_ClipPut method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_ClipPut() {
	// System.out.println("AU3_ClipPut");
	// WString arg0 = null;
	// AutoItX instance = new AutoItX();
	// instance.AU3_ClipPut(arg0);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_ProcessClose method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_ProcessClose() {
	// System.out.println("AU3_ProcessClose");
	// WString arg0 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_ProcessClose(arg0);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_Shutdown method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_Shutdown() {
	// System.out.println("AU3_Shutdown");
	// int arg0 = 0;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_Shutdown(arg0);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_Sleep method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_Sleep() {
	// System.out.println("AU3_Sleep");
	// int arg0 = 0;
	// AutoItX instance = new AutoItX();
	// instance.AU3_Sleep(arg0);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinActivate method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinActivate() {
	// System.out.println("AU3_WinActivate");
	// WString arg0 = null;
	// WString arg1 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_WinActivate(arg0, arg1);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_ControlShow method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_ControlShow() {
	// System.out.println("AU3_ControlShow");
	// WString arg0 = null;
	// WString arg1 = null;
	// WString arg2 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_ControlShow(arg0, arg1, arg2);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinSetStateByHandle method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinSetStateByHandle() {
	// System.out.println("AU3_WinSetStateByHandle");
	// WinDef.HWND arg0 = null;
	// int arg1 = 0;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_WinSetStateByHandle(arg0, arg1);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinMenuSelectItemByHandle method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinMenuSelectItemByHandle() {
	// System.out.println("AU3_WinMenuSelectItemByHandle");
	// WinDef.HWND arg0 = null;
	// WString arg1 = null;
	// WString arg2 = null;
	// WString arg3 = null;
	// WString arg4 = null;
	// WString arg5 = null;
	// WString arg6 = null;
	// WString arg7 = null;
	// WString arg8 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_WinMenuSelectItemByHandle(arg0, arg1, arg2, arg3,
	// arg4, arg5, arg6, arg7, arg8);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinMenuSelectItem method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinMenuSelectItem() {
	// System.out.println("AU3_WinMenuSelectItem");
	// WString arg0 = null;
	// WString arg1 = null;
	// WString arg2 = null;
	// WString arg3 = null;
	// WString arg4 = null;
	// WString arg5 = null;
	// WString arg6 = null;
	// WString arg7 = null;
	// WString arg8 = null;
	// WString arg9 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_WinMenuSelectItem(arg0, arg1, arg2, arg3, arg4,
	// arg5, arg6, arg7, arg8, arg9);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinSetOnTopByHandle method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinSetOnTopByHandle() {
	// System.out.println("AU3_WinSetOnTopByHandle");
	// WinDef.HWND arg0 = null;
	// int arg1 = 0;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_WinSetOnTopByHandle(arg0, arg1);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinWaitNotActive method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinWaitNotActive() {
	// System.out.println("AU3_WinWaitNotActive");
	// WString arg0 = null;
	// WString arg1 = null;
	// int arg2 = 0;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_WinWaitNotActive(arg0, arg1, arg2);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinWaitCloseByHandle method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinWaitCloseByHandle() {
	// System.out.println("AU3_WinWaitCloseByHandle");
	// WinDef.HWND arg0 = null;
	// int arg1 = 0;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_WinWaitCloseByHandle(arg0, arg1);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinSetTransByHandle method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinSetTransByHandle() {
	// System.out.println("AU3_WinSetTransByHandle");
	// WinDef.HWND arg0 = null;
	// int arg1 = 0;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_WinSetTransByHandle(arg0, arg1);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinKillByHandle method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinKillByHandle() {
	// System.out.println("AU3_WinKillByHandle");
	// WinDef.HWND arg0 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_WinKillByHandle(arg0);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinSetTitleByHandle method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinSetTitleByHandle() {
	// System.out.println("AU3_WinSetTitleByHandle");
	// WinDef.HWND arg0 = null;
	// WString arg1 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_WinSetTitleByHandle(arg0, arg1);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinGetClientSize method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinGetClientSize() {
	// System.out.println("AU3_WinGetClientSize");
	// WString arg0 = null;
	// WString arg1 = null;
	// WinDef.RECT arg2 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_WinGetClientSize(arg0, arg1, arg2);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinGetPosByHandle method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinGetPosByHandle() {
	// System.out.println("AU3_WinGetPosByHandle");
	// WinDef.HWND arg0 = null;
	// WinDef.RECT arg1 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_WinGetPosByHandle(arg0, arg1);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinGetProcessByHandle method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinGetProcessByHandle() {
	// System.out.println("AU3_WinGetProcessByHandle");
	// WinDef.HWND arg0 = null;
	// AutoItX instance = new AutoItX();
	// WinDef.DWORD expResult = null;
	// WinDef.DWORD result = instance.AU3_WinGetProcessByHandle(arg0);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinGetTextByHandle method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinGetTextByHandle() {
	// System.out.println("AU3_WinGetTextByHandle");
	// WinDef.HWND arg0 = null;
	// WTypes.LPWSTR arg1 = null;
	// int arg2 = 0;
	// AutoItX instance = new AutoItX();
	// instance.AU3_WinGetTextByHandle(arg0, arg1, arg2);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinGetStateByHandle method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinGetStateByHandle() {
	// System.out.println("AU3_WinGetStateByHandle");
	// WinDef.HWND arg0 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_WinGetStateByHandle(arg0);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinGetTitleByHandle method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinGetTitleByHandle() {
	// System.out.println("AU3_WinGetTitleByHandle");
	// WinDef.HWND arg0 = null;
	// WTypes.LPWSTR arg1 = null;
	// int arg2 = 0;
	// AutoItX instance = new AutoItX();
	// instance.AU3_WinGetTitleByHandle(arg0, arg1, arg2);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinActiveByHandle method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinActiveByHandle() {
	// System.out.println("AU3_WinActiveByHandle");
	// WinDef.HWND arg0 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_WinActiveByHandle(arg0);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_StatusbarGetText method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_StatusbarGetText() {
	// System.out.println("AU3_StatusbarGetText");
	// WString arg0 = null;
	// WString arg1 = null;
	// int arg2 = 0;
	// WTypes.LPWSTR arg3 = null;
	// int arg4 = 0;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_StatusbarGetText(arg0, arg1, arg2, arg3, arg4);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinGetClassListByHandle method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinGetClassListByHandle() {
	// System.out.println("AU3_WinGetClassListByHandle");
	// WinDef.HWND arg0 = null;
	// WTypes.LPWSTR arg1 = null;
	// int arg2 = 0;
	// AutoItX instance = new AutoItX();
	// instance.AU3_WinGetClassListByHandle(arg0, arg1, arg2);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinGetProcess method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinGetProcess() {
	// System.out.println("AU3_WinGetProcess");
	// WString arg0 = null;
	// WString arg1 = null;
	// AutoItX instance = new AutoItX();
	// WinDef.DWORD expResult = null;
	// WinDef.DWORD result = instance.AU3_WinGetProcess(arg0, arg1);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinGetClientSizeByHandle method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinGetClientSizeByHandle() {
	// System.out.println("AU3_WinGetClientSizeByHandle");
	// WinDef.HWND arg0 = null;
	// WinDef.RECT arg1 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_WinGetClientSizeByHandle(arg0, arg1);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinGetCaretPos method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinGetCaretPos() {
	// System.out.println("AU3_WinGetCaretPos");
	// Pointer arg0 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_WinGetCaretPos(arg0);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinActivateByHandle method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinActivateByHandle() {
	// System.out.println("AU3_WinActivateByHandle");
	// WinDef.HWND arg0 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_WinActivateByHandle(arg0);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinExistsByHandle method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinExistsByHandle() {
	// System.out.println("AU3_WinExistsByHandle");
	// WinDef.HWND arg0 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_WinExistsByHandle(arg0);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinGetHandleAsText method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinGetHandleAsText() {
	// System.out.println("AU3_WinGetHandleAsText");
	// WString arg0 = null;
	// WString arg1 = null;
	// WTypes.LPWSTR arg2 = null;
	// int arg3 = 0;
	// AutoItX instance = new AutoItX();
	// instance.AU3_WinGetHandleAsText(arg0, arg1, arg2, arg3);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_StatusbarGetTextByHandle method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_StatusbarGetTextByHandle() {
	// System.out.println("AU3_StatusbarGetTextByHandle");
	// WinDef.HWND arg0 = null;
	// int arg1 = 0;
	// WTypes.LPWSTR arg2 = null;
	// int arg3 = 0;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_StatusbarGetTextByHandle(arg0, arg1, arg2, arg3);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinCloseByHandle method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinCloseByHandle() {
	// System.out.println("AU3_WinCloseByHandle");
	// WinDef.HWND arg0 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_WinCloseByHandle(arg0);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinGetClassList method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinGetClassList() {
	// System.out.println("AU3_WinGetClassList");
	// WString arg0 = null;
	// WString arg1 = null;
	// WTypes.LPWSTR arg2 = null;
	// int arg3 = 0;
	// AutoItX instance = new AutoItX();
	// instance.AU3_WinGetClassList(arg0, arg1, arg2, arg3);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinSetState method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinSetState() {
	// System.out.println("AU3_WinSetState");
	// WString arg0 = null;
	// WString arg1 = null;
	// int arg2 = 0;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_WinSetState(arg0, arg1, arg2);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinGetState method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinGetState() {
	// System.out.println("AU3_WinGetState");
	// WString arg0 = null;
	// WString arg1 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_WinGetState(arg0, arg1);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinGetText method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinGetText() {
	// System.out.println("AU3_WinGetText");
	// WString arg0 = null;
	// WString arg1 = null;
	// WTypes.LPWSTR arg2 = null;
	// int arg3 = 0;
	// AutoItX instance = new AutoItX();
	// instance.AU3_WinGetText(arg0, arg1, arg2, arg3);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinSetTitle method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinSetTitle_3args() {
	// System.out.println("AU3_WinSetTitle");
	// WString arg0 = null;
	// WString arg1 = null;
	// WString arg2 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_WinSetTitle(arg0, arg1, arg2);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinSetTitle method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinSetTitle_4args() {
	// System.out.println("AU3_WinSetTitle");
	// WString arg0 = null;
	// WString arg1 = null;
	// WString arg2 = null;
	// WString arg3 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_WinSetTitle(arg0, arg1, arg2, arg3);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinSetTrans method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinSetTrans() {
	// System.out.println("AU3_WinSetTrans");
	// WString arg0 = null;
	// WString arg1 = null;
	// int arg2 = 0;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_WinSetTrans(arg0, arg1, arg2);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinSetOnTop method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinSetOnTop() {
	// System.out.println("AU3_WinSetOnTop");
	// WString arg0 = null;
	// WString arg1 = null;
	// int arg2 = 0;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_WinSetOnTop(arg0, arg1, arg2);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinKill method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinKill() {
	// System.out.println("AU3_WinKill");
	// WString arg0 = null;
	// WString arg1 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_WinKill(arg0, arg1);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinGetPos method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinGetPos() {
	// System.out.println("AU3_WinGetPos");
	// WString arg0 = null;
	// WString arg1 = null;
	// WinDef.RECT arg2 = null;
	// AutoItX instance = new AutoItX();
	// int expResult = 0;
	// int result = instance.AU3_WinGetPos(arg0, arg1, arg2);
	// assertEquals(result, expResult);
	// // TODO review the generated test code and remove the default call to fail.
	// fail("The test case is a prototype.");
	// }
	//
	// /**
	// * Test of AU3_WinGetTitle method, of class AutoItX.
	// */
	// @Test
	// public void testAU3_WinGetTitle() {
	// System.out.println("AU3_WinGetTitle");
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
