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

import static example.Constants.DLL;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.WString;
import com.sun.jna.platform.win32.WTypes.LPWSTR;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.RECT;
import com.sun.jna.Pointer;
import com.sun.jna.WString;
import com.sun.jna.platform.win32.WTypes.LPWSTR;

/**
 *
 * @author midorlo
 * @author sergueik
 */
public class AutoItX implements AutoItXLibrary {

	final AutoItXLibrary LIB;
	private static AutoItX INSTANCE;
	private static String resultString = (new StringBuilder(1024)).toString();
	private static int bufSize = resultString.length() - 1;

	AutoItX() {
		LIB = (AutoItXLibrary) Native.loadLibrary(DLL, AutoItXLibrary.class);
		LIB.AU3_Init();
		INSTANCE = this;
	}

	public static AutoItX getInstance() {
		return (null == INSTANCE) ? new AutoItX() : INSTANCE;
	}

	@Override
	public int AU3_ControlClick(WString arg0, WString arg1, WString arg2,
			WString arg3, int arg4, int arg5) {
		return LIB.AU3_ControlClick(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public int AU3_ControlClick(WString arg0, WString arg1, WString arg2,
			WString arg3, int arg4) {
		return LIB.AU3_ControlClick(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public int AU3_ControlClick(WString arg0, WString arg1, WString arg2,
			WString arg3, int arg4, int arg5, int arg6) {
		return LIB.AU3_ControlClick(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
	}

	// https://www.autoitscript.com/autoit3/docs/functions/Run.htm
	@Override
	public int AU3_Run(WString program, WString workingdir) {
		return LIB.AU3_Run(program, workingdir);
	}

	public boolean Run(String program, String workingdir) {
		return (LIB.AU3_Run(new WString(program),
				new WString(workingdir)) == Constants.AU3_SUCCESS);
	}

	@Override
	public int AU3_Run(WString program, WString workingdir, int showFlag) {
		return LIB.AU3_Run(program, workingdir, showFlag);
	}

	public boolean Run(String program, String workingdir, int showFlag) {
		// SW_HIDE, SW_MINIMIZE, SW_MAXIMIZE
		return (LIB.AU3_Run(new WString(program), new WString(workingdir),
				showFlag) == Constants.AU3_SUCCESS);
	}

	// https://www.autoitscript.com/autoit3/docs/functions/ControlSend.htm
	@Override
	public int AU3_ControlSend(WString title, WString text, WString controlID,
			WString data) {
		return LIB.AU3_ControlSend(title, text, controlID, data);
	}

	public boolean ControlSend(String title, String text, String controlID,
			String data) {
		return (LIB.AU3_ControlSend(new WString(title), new WString(text),
				new WString(controlID), new WString(data)) == Constants.AU3_SUCCESS);
	}

	@Override
	public int AU3_ControlSend(WString title, WString text, WString controlID,
			WString data, int flag) {
		return LIB.AU3_ControlSend(title, text, controlID, data, flag);
	}

	public boolean ControlSend(String title, String text, String controlID,
			String data, int flag) {
		return (LIB.AU3_ControlSend(new WString(title), new WString(text),
				new WString(controlID), new WString(data),
				flag) == Constants.AU3_SUCCESS);
	}

	@Override
	public void AU3_Init() {
		LIB.AU3_Init();
	}

	@Override
	public void AU3_ClipPut(WString arg0) {
		LIB.AU3_ClipPut(arg0);
	}

	@Override
	public void AU3_Sleep(int arg0) {
		LIB.AU3_Sleep(arg0);
	}

	@Override
	public HWND AU3_WinGetHandle(WString arg0, WString arg1) {
		return LIB.AU3_WinGetHandle(arg0, arg1);
	}

	@Override
	public void AU3_DriveMapGet(WString arg0, LPWSTR arg1, int arg2) {
		LIB.AU3_DriveMapGet(arg0, arg1, arg2);
	}

	@Override
	public int AU3_WinGetState(WString arg0, WString arg1) {
		return LIB.AU3_WinGetState(arg0, arg1);
	}

	@Override
	public int AU3_WinSetTrans(WString arg0, WString arg1, int arg2) {
		return LIB.AU3_WinSetTrans(arg0, arg1, arg2);
	}

	// https://www.autoitscript.com/autoit3/docs/functions/WinExists.htm
	@Override
	public int AU3_WinExists(WString title, WString text) {
		return LIB.AU3_WinExists(title, text);
	}

	public boolean WinExists(String title, String text) {
		return (LIB.AU3_WinExists(new WString(title),
				new WString(text)) == Constants.AU3_SUCCESS);
	}

	@Override
	public int AU3_ProcessClose(WString arg0) {
		return LIB.AU3_ProcessClose(arg0);
	}

	@Override
	public int AU3_DriveMapDel(WString arg0) {
		return LIB.AU3_DriveMapDel(arg0);
	}

	@Override
	public void AU3_DriveMapAdd(WString arg0, WString arg1, int arg2,
			WString arg3, WString arg4, LPWSTR arg5, int arg6) {
		LIB.AU3_DriveMapAdd(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
	}

	@Override
	public int AU3_WinSetOnTop(WString arg0, WString arg1, int arg2) {
		return LIB.AU3_WinSetOnTop(arg0, arg1, arg2);
	}

	// https://www.autoitscript.com/autoit3/docs/functions/WinClose.htm
	@Override
	public int AU3_WinClose(WString title, WString text) {
		return LIB.AU3_WinClose(title, text);
	}

	public boolean WinClose(String title, String text) {
		return (LIB.AU3_WinClose(new WString(title),
				new WString(text)) == Constants.AU3_SUCCESS);
	}

	@Override
	public int AU3_WinSetState(WString arg0, WString arg1, int arg2) {
		return LIB.AU3_WinSetState(arg0, arg1, arg2);
	}

	// https://www.autoitscript.com/autoit3/docs/functions/WinKill.htm
	@Override
	public int AU3_WinKill(WString title, WString text) {
		return LIB.AU3_WinKill(title, text);
	}

	public boolean WinKill(String title, String text) {
		return (LIB.AU3_WinKill(new WString(title),
				new WString(text)) == Constants.AU3_SUCCESS);
	}

	@Override
	public int AU3_ControlHide(WString arg0, WString arg1, WString arg2) {
		return LIB.AU3_ControlHide(arg0, arg1, arg2);
	}

	// https://www.autoitscript.com/autoit3/docs/functions/WinActive.htm
	@Override
	public int AU3_WinActive(WString title, WString text) {
		return LIB.AU3_WinActive(title, text);
	}

	public boolean WinActive(String title, String text) {
		return (LIB.AU3_WinActive(new WString(title),
				new WString(text)) == Constants.AU3_SUCCESS);
	}

	@Override
	public int AU3_ControlFocus(WString arg0, WString arg1, WString arg2) {
		return LIB.AU3_ControlFocus(arg0, arg1, arg2);
	}

	// https://www.autoitscript.com/autoit3/docs/functions/WinGetTitle.htm
	@Override
	public void AU3_WinGetTitle(WString title, WString text, LPWSTR resultPointer,
			int bufSize) {
		LIB.AU3_WinGetTitle(title, text, resultPointer, bufSize);
	}

	public String WinGetTitle(String title, String text) {
		resultString = (new StringBuilder(1024)).toString();
		bufSize = resultString.length() - 1;
		LPWSTR resultPtr = new LPWSTR(resultString);
		AU3_WinGetTitle(new WString(title), new WString(text), resultPtr, bufSize);
		resultString = resultPtr.getValue();
		return resultString.trim();
	}

	@Override
	public int AU3_ControlShow(WString arg0, WString arg1, WString arg2) {
		return LIB.AU3_ControlShow(arg0, arg1, arg2);
	}

	@Override
	public void AU3_ClipGet(LPWSTR arg0, int arg1) {
		LIB.AU3_ClipGet(arg0, arg1);
	}

	@Override
	public int AU3_Opt(WString arg0, int arg1) {
		return LIB.AU3_Opt(arg0, arg1);
	}

	@Override
	public void AU3_MouseWheel(WString arg0, int arg1) {
		LIB.AU3_MouseWheel(arg0, arg1);
	}

	@Override
	public void AU3_MouseUp(WString arg0) {
		LIB.AU3_MouseUp(arg0);
	}

	@Override
	public int AU3_error() {
		return LIB.AU3_error();
	}

	@Override
	public void AU3_MouseDown(WString arg0) {
		LIB.AU3_MouseDown(arg0);
	}

	@Override
	public int AU3_Shutdown(int arg0) {
		return LIB.AU3_Shutdown(arg0);
	}

	@Override
	public int AU3_WinGetPos(WString arg0, WString arg1, RECT arg2) {
		return LIB.AU3_WinGetPos(arg0, arg1, arg2);
	}

	@Override
	public void AU3_WinGetText(WString title, WString text, LPWSTR resultPointer,
			int bufSize) {
		LIB.AU3_WinGetText(title, text, resultPointer, bufSize);
	}

	public String WinGetText(String title, String text) {

		// https://www.programcreek.com/java-api-examples/index.php?api=com.sun.jna.platform.win32.WTypes
		LPWSTR resultPtr = new LPWSTR(resultString);
		LIB.AU3_WinGetText(new WString(title), new WString(text), resultPtr,
				bufSize);
		resultString = resultPtr.getValue();
		return resultString.trim();
	}

	@Override
	public int AU3_WinSetTitle(WString arg0, WString arg1, WString arg2,
			WString arg3) {
		return LIB.AU3_WinSetTitle(arg0, arg1, arg2, arg3);
	}

	@Override
	public int AU3_WinSetTitle(WString arg0, WString arg1, WString arg2) {
		return LIB.AU3_WinSetTitle(arg0, arg1, arg2);
	}

	// https://www.autoitscript.com/autoit3/docs/functions/WinActivate.htm
	@Override
	public int AU3_WinActivate(WString arg0, WString arg1) {
		return LIB.AU3_WinActivate(arg0, arg1);
	}

	public boolean WinActivate(String title, String text) {
		return (LIB.AU3_WinActivate(new WString(title),
				new WString(text)) == Constants.AU3_SUCCESS);
	}

	@Override
	public int AU3_IsAdmin() {
		return LIB.AU3_IsAdmin();
	}

	@Override
	public void AU3_PixelSearch(RECT arg0, int arg1, int arg2, int arg3,
			Pointer arg4) {
		LIB.AU3_PixelSearch(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public void AU3_ControlCommandByHandle(HWND arg0, HWND arg1, WString arg2,
			WString arg3, LPWSTR arg4, int arg5) {
		LIB.AU3_ControlCommandByHandle(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public int AU3_MouseGetCursor() {
		return LIB.AU3_MouseGetCursor();
	}

	@Override
	public int AU3_ControlEnable(WString arg0, WString arg1, WString arg2) {
		return LIB.AU3_ControlEnable(arg0, arg1, arg2);
	}

	@Override
	public int AU3_ControlShowByHandle(HWND arg0, HWND arg1) {
		return LIB.AU3_ControlShowByHandle(arg0, arg1);
	}

	// https://www.autoitscript.com/autoit3/docs/functions/AutoItSetOption.htm
	public int AutoItSetOption(String option, int param) {
		return LIB.AU3_AutoItSetOption(new WString(option), param);
	}

	@Override
	public int AU3_AutoItSetOption(WString option, int param) {
		return LIB.AU3_AutoItSetOption(option, param);
	}

	@Override
	public int AU3_ControlEnableByHandle(HWND arg0, HWND arg1) {
		return LIB.AU3_ControlEnableByHandle(arg0, arg1);
	}

	// https://www.autoitscript.com/autoit3/docs/functions/ControlCommand.htm
	@Override
	public void AU3_ControlCommand(WString title, WString text, WString arg2,
			WString arg3, WString arg4, LPWSTR arg5, int arg6) {
		LIB.AU3_ControlCommand(title, text, arg2, arg3, arg4, arg5, arg6);
		/*	
		Command, Option	Return Value
		"IsVisible", ""	Returns 1 if Control is visible, 0 otherwise
		"IsEnabled", ""	Returns 1 if Control is enabled, 0 otherwise
		"ShowDropDown", ""	Displays the ComboBox dropdown
		"HideDropDown", ""	Hides the ComboBox dropdown
		"AddString", 'string'	Adds a string to the end in a ListBox or ComboBox
		"DelString", occurrence	Deletes a string according to occurrence in a ListBox or ComboBox
		"FindString", 'string'	Returns occurrence ref of the exact string in a ListBox or ComboBox
		"SetCurrentSelection", occurrence	Sets selection to occurrence ref in a ListBox or ComboBox
		"SelectString", 'string'	Sets selection according to string in a ListBox or ComboBox
		"IsChecked", ""	Returns 1 if Button is checked, 0 otherwise
		"Check", ""	Checks radio or check Button
		"UnCheck", ""	Unchecks radio or check Button
		"GetCurrentLine", ""	Returns the line # where the caret is in an Edit
		"GetCurrentCol", ""	Returns the column # where the caret is in an Edit
		"GetCurrentSelection", ""	Returns name of the currently selected item in a ListBox or ComboBox
		"GetLineCount", ""	Returns # of lines in an Edit
		"GetLine", line#	Returns text at line # passed of an Edit
		"GetSelected", ""	Returns selected text of an Edit
		"EditPaste", 'string'	Pastes the 'string' at the Edit's caret position
		"CurrentTab", ""	Returns the current Tab shown of a SysTabControl32
		"TabRight", ""	Moves to the next tab to the right of a SysTabControl32
		"TabLeft", ""	Moves to the next tab to the left of a SysTabControl32
		"SendCommandID", Command ID	Simulates the WM_COMMAND message. Usually used for ToolbarWindow32 controls - use the ToolBar tab of Au3Info to get the Command ID.
		 */
	}

	@Override
	public int AU3_ControlDisable(WString arg0, WString arg1, WString arg2) {
		return LIB.AU3_ControlDisable(arg0, arg1, arg2);
	}

	@Override
	public int AU3_ControlDisableByHandle(HWND arg0, HWND arg1) {
		return LIB.AU3_ControlDisableByHandle(arg0, arg1);
	}

	@Override
	public int AU3_ControlSetTextByHandle(HWND arg0, HWND arg1, WString arg2) {
		return LIB.AU3_ControlSetTextByHandle(arg0, arg1, arg2);
	}

	@Override
	public void AU3_ControlTreeViewByHandle(HWND arg0, HWND arg1, WString arg2,
			WString arg3, WString arg4, LPWSTR arg5, int arg6) {
		LIB.AU3_ControlTreeViewByHandle(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
	}

	@Override
	public int AU3_ControlHideByHandle(HWND arg0, HWND arg1) {
		return LIB.AU3_ControlHideByHandle(arg0, arg1);
	}

	@Override
	public int AU3_ControlFocusByHandle(HWND arg0, HWND arg1) {
		return LIB.AU3_ControlFocusByHandle(arg0, arg1);
	}

	@Override
	public void AU3_ControlGetFocus(WString arg0, WString arg1, LPWSTR arg2,
			int arg3) {
		LIB.AU3_ControlGetFocus(arg0, arg1, arg2, arg3);
	}

	@Override
	public void AU3_ControlListView(WString arg0, WString arg1, WString arg2,
			WString arg3, WString arg4, WString arg5, LPWSTR arg6, int arg7) {
		LIB.AU3_ControlListView(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7);
	}

	@Override
	public int AU3_ProcessExists(WString arg0) {
		return LIB.AU3_ProcessExists(arg0);
	}

	@Override
	public int AU3_WinSetStateByHandle(HWND arg0, int arg1) {
		return LIB.AU3_WinSetStateByHandle(arg0, arg1);
	}

	@Override
	public int AU3_ProcessSetPriority(WString arg0, int arg1) {
		return LIB.AU3_ProcessSetPriority(arg0, arg1);
	}

	@Override
	public int AU3_PixelGetColor(int arg0, int arg1) {
		return LIB.AU3_PixelGetColor(arg0, arg1);
	}

	@Override
	public void AU3_ControlTreeView(WString arg0, WString arg1, WString arg2,
			WString arg3, WString arg4, WString arg5, LPWSTR arg6, int arg7) {
		LIB.AU3_ControlTreeView(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7);
	}

	@Override
	public String AU3_WinMinimizeAll() {
		return LIB.AU3_WinMinimizeAll();
	}

	@Override
	public void AU3_ControlGetTextByHandle(HWND arg0, HWND arg1, LPWSTR arg2,
			int arg3) {
		LIB.AU3_ControlGetTextByHandle(arg0, arg1, arg2, arg3);
	}

	@Override
	public int AU3_ControlSetText(WString arg0, WString arg1, WString arg2,
			WString arg3) {
		return LIB.AU3_ControlSetText(arg0, arg1, arg2, arg3);
	}

	@Override
	public HWND AU3_ControlGetHandle(HWND arg0, WString arg1) {
		return LIB.AU3_ControlGetHandle(arg0, arg1);
	}

	@Override
	public void AU3_ControlGetHandleAsText(WString arg0, WString arg1,
			WString arg2, LPWSTR arg3, int arg4) {
		LIB.AU3_ControlGetHandleAsText(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public int AU3_ControlGetPos(WString arg0, WString arg1, WString arg2,
			RECT arg3) {
		return LIB.AU3_ControlGetPos(arg0, arg1, arg2, arg3);
	}

	@Override
	public void AU3_ControlGetText(WString arg0, WString arg1, WString arg2,
			LPWSTR arg3, int arg4) {
		LIB.AU3_ControlGetText(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public void AU3_ControlGetFocusByHandle(HWND arg0, LPWSTR arg1, int arg2) {
		LIB.AU3_ControlGetFocusByHandle(arg0, arg1, arg2);
	}

	@Override
	public int AU3_ControlGetPosByHandle(HWND arg0, HWND arg1, RECT arg2) {
		return LIB.AU3_ControlGetPosByHandle(arg0, arg1, arg2);
	}

	@Override
	public void AU3_ControlListViewByHandle(HWND arg0, HWND arg1, WString arg2,
			WString arg3, WString arg4, LPWSTR arg5, int arg6) {
		LIB.AU3_ControlListViewByHandle(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
	}

	@Override
	public int AU3_WinCloseByHandle(HWND arg0) {
		return LIB.AU3_WinCloseByHandle(arg0);
	}

	@Override
	public void AU3_WinMinimizeAllUndo() {
		LIB.AU3_WinMinimizeAllUndo();
	}

	@Override
	public int AU3_ControlSendByHandle(HWND arg0, HWND arg1, WString arg2) {
		return LIB.AU3_ControlSendByHandle(arg0, arg1, arg2);
	}

	@Override
	public int AU3_ControlSendByHandle(HWND arg0, HWND arg1, WString arg2,
			int arg3) {
		return LIB.AU3_ControlSendByHandle(arg0, arg1, arg2, arg3);
	}

	@Override
	public int AU3_WinWaitActiveByHandle(HWND arg0, int arg1) {
		return LIB.AU3_WinWaitActiveByHandle(arg0, arg1);
	}

	@Override
	public int AU3_WinMoveByHandle(HWND arg0, int arg1, int arg2, int arg3) {
		return LIB.AU3_WinMoveByHandle(arg0, arg1, arg2, arg3);
	}

	@Override
	public int AU3_WinMoveByHandle(HWND arg0, int arg1, int arg2, int arg3,
			int arg4) {
		return LIB.AU3_WinMoveByHandle(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public int AU3_StatusbarGetTextByHandle(HWND arg0, int arg1, LPWSTR arg2,
			int arg3) {
		return LIB.AU3_StatusbarGetTextByHandle(arg0, arg1, arg2, arg3);
	}

	@Override
	public void AU3_WinGetClassList(WString arg0, WString arg1, LPWSTR arg2,
			int arg3) {
		LIB.AU3_WinGetClassList(arg0, arg1, arg2, arg3);
	}

	@Override
	public int AU3_WinExistsByHandle(HWND arg0) {
		return LIB.AU3_WinExistsByHandle(arg0);
	}

	@Override
	public int AU3_MouseClickDrag(WString arg0, int arg1, int arg2, int arg3,
			int arg4, int arg5) {
		return LIB.AU3_MouseClickDrag(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public int AU3_MouseClickDrag(WString arg0, int arg1, int arg2, int arg3,
			int arg4) {
		return LIB.AU3_MouseClickDrag(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public int AU3_WinWaitByHandle(HWND arg0, int arg1) {
		return LIB.AU3_WinWaitByHandle(arg0, arg1);
	}

	@Override
	public int AU3_ControlClickByHandle(HWND arg0, HWND arg1, WString arg2,
			int arg3, int arg4, int arg5) {
		return LIB.AU3_ControlClickByHandle(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public int AU3_ControlClickByHandle(HWND arg0, HWND arg1, WString arg2,
			int arg3) {
		return LIB.AU3_ControlClickByHandle(arg0, arg1, arg2, arg3);
	}

	@Override
	public int AU3_WinActivateByHandle(HWND arg0) {
		return LIB.AU3_WinActivateByHandle(arg0);
	}

	@Override
	public int AU3_ControlMoveByHandle(HWND arg0, HWND arg1, int arg2, int arg3) {
		return LIB.AU3_ControlMoveByHandle(arg0, arg1, arg2, arg3);
	}

	@Override
	public int AU3_ControlMoveByHandle(HWND arg0, HWND arg1, int arg2, int arg3,
			int arg4, int arg5) {
		return LIB.AU3_ControlMoveByHandle(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public int AU3_ProcessWaitClose(WString arg0) {
		return LIB.AU3_ProcessWaitClose(arg0);
	}

	@Override
	public int AU3_ProcessWaitClose(WString arg0, int arg1) {
		return LIB.AU3_ProcessWaitClose(arg0, arg1);
	}

	@Override
	public UnsignedInt AU3_PixelChecksum(RECT arg0) {
		return LIB.AU3_PixelChecksum(arg0);
	}

	@Override
	public UnsignedInt AU3_PixelChecksum(RECT arg0, int arg1) {
		return LIB.AU3_PixelChecksum(arg0, arg1);
	}

	@Override
	public void AU3_WinGetHandleAsText(WString arg0, WString arg1, LPWSTR arg2,
			int arg3) {
		LIB.AU3_WinGetHandleAsText(arg0, arg1, arg2, arg3);
	}

	@Override
	public int AU3_WinGetCaretPos(Pointer arg0) {
		return LIB.AU3_WinGetCaretPos(arg0);
	}

	// https://www.autoitscript.com/autoit3/docs/functions/WinWaitActive.htm
	public int WinWaitActive(String title, String text, int timeout) {
		return LIB.AU3_WinWaitActive(new WString(title), new WString(text),
				timeout);
	}

	public int WinWaitActive(String title, String text) {
		return LIB.AU3_WinWaitActive(new WString(title), new WString(text));
	}

	@Override
	public int AU3_WinWaitActive(WString arg0, WString arg1, int arg2) {
		return LIB.AU3_WinWaitActive(arg0, arg1, arg2);
	}

	@Override
	public int AU3_WinWaitActive(WString arg0, WString arg1) {
		return LIB.AU3_WinWaitActive(arg0, arg1);
	}

	@Override
	public int AU3_WinGetStateByHandle(HWND arg0) {
		return LIB.AU3_WinGetStateByHandle(arg0);
	}

	@Override
	public int AU3_WinActiveByHandle(HWND arg0) {
		return LIB.AU3_WinActiveByHandle(arg0);
	}

	@Override
	public int AU3_WinSetOnTopByHandle(HWND arg0, int arg1) {
		return LIB.AU3_WinSetOnTopByHandle(arg0, arg1);
	}

	@Override
	public int AU3_WinSetTitleByHandle(HWND arg0, WString arg1) {
		return LIB.AU3_WinSetTitleByHandle(arg0, arg1);
	}

	@Override
	public DWORD AU3_WinGetProcessByHandle(HWND arg0) {
		return LIB.AU3_WinGetProcessByHandle(arg0);
	}

	@Override
	public int AU3_WinGetClientSize(WString arg0, WString arg1, RECT arg2) {
		return LIB.AU3_WinGetClientSize(arg0, arg1, arg2);
	}

	@Override
	public void AU3_WinGetTextByHandle(HWND arg0, LPWSTR arg1, int arg2) {
		LIB.AU3_WinGetTextByHandle(arg0, arg1, arg2);
	}

	@Override
	public int AU3_WinGetPosByHandle(HWND arg0, RECT arg1) {
		return LIB.AU3_WinGetPosByHandle(arg0, arg1);
	}

	@Override
	public void AU3_WinGetTitleByHandle(HWND arg0, LPWSTR arg1, int arg2) {
		LIB.AU3_WinGetTitleByHandle(arg0, arg1, arg2);
	}

	@Override
	public int AU3_StatusbarGetText(WString arg0, WString arg1, int arg2,
			LPWSTR arg3, int arg4) {
		return LIB.AU3_StatusbarGetText(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public void AU3_WinGetClassListByHandle(HWND arg0, LPWSTR arg1, int arg2) {
		LIB.AU3_WinGetClassListByHandle(arg0, arg1, arg2);
	}

	@Override
	public DWORD AU3_WinGetProcess(WString arg0, WString arg1) {
		return LIB.AU3_WinGetProcess(arg0, arg1);
	}

	@Override
	public int AU3_WinGetClientSizeByHandle(HWND arg0, RECT arg1) {
		return LIB.AU3_WinGetClientSizeByHandle(arg0, arg1);
	}

	@Override
	public int AU3_WinWaitCloseByHandle(HWND arg0, int arg1) {
		return LIB.AU3_WinWaitCloseByHandle(arg0, arg1);
	}

	@Override
	public int AU3_WinMenuSelectItemByHandle(HWND arg0, WString arg1,
			WString arg2, WString arg3, WString arg4, WString arg5, WString arg6,
			WString arg7, WString arg8) {
		return LIB.AU3_WinMenuSelectItemByHandle(arg0, arg1, arg2, arg3, arg4, arg5,
				arg6, arg7, arg8);
	}

	@Override
	public int AU3_WinWaitNotActive(WString arg0, WString arg1, int arg2) {
		return LIB.AU3_WinWaitNotActive(arg0, arg1, arg2);
	}

	@Override
	public int AU3_WinMenuSelectItem(WString arg0, WString arg1, WString arg2,
			WString arg3, WString arg4, WString arg5, WString arg6, WString arg7,
			WString arg8, WString arg9) {
		return LIB.AU3_WinMenuSelectItem(arg0, arg1, arg2, arg3, arg4, arg5, arg6,
				arg7, arg8, arg9);
	}

	@Override
	public int AU3_WinSetTransByHandle(HWND arg0, int arg1) {
		return LIB.AU3_WinSetTransByHandle(arg0, arg1);
	}

	@Override
	public int AU3_WinKillByHandle(HWND arg0) {
		return LIB.AU3_WinKillByHandle(arg0);
	}

	@Override
	public int AU3_WinWaitClose(WString arg0, WString arg1) {
		return LIB.AU3_WinWaitClose(arg0, arg1);
	}

	@Override
	public int AU3_WinWaitClose(WString arg0, WString arg1, int arg2) {
		return LIB.AU3_WinWaitClose(arg0, arg1, arg2);
	}

	@Override
	public int AU3_WinMove(WString arg0, WString arg1, int arg2, int arg3) {
		return LIB.AU3_WinMove(arg0, arg1, arg2, arg3);
	}

	@Override
	public int AU3_WinMove(WString arg0, WString arg1, int arg2, int arg3,
			int arg4) {
		return LIB.AU3_WinMove(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public int AU3_WinMove(WString arg0, WString arg1, int arg2, int arg3,
			int arg4, int arg5) {
		return LIB.AU3_WinMove(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public int AU3_WinWait(WString arg0, WString arg1, int arg2) {
		return LIB.AU3_WinWait(arg0, arg1, arg2);
	}

	@Override
	public int AU3_WinWait(WString arg0, WString arg1) {
		return LIB.AU3_WinWait(arg0, arg1);
	}

	@Override
	public void AU3_MouseGetPos(Pointer arg0) {
		LIB.AU3_MouseGetPos(arg0);
	}

	@Override
	public int AU3_MouseClick(WString arg0, int arg1, int arg2, int arg3,
			int arg4) {
		return LIB.AU3_MouseClick(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public int AU3_MouseClick(WString arg0) {
		return LIB.AU3_MouseClick(arg0);
	}

	@Override
	public int AU3_ControlMove(WString arg0, WString arg1, WString arg2, int arg3,
			int arg4) {
		return LIB.AU3_ControlMove(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public int AU3_ControlMove(WString arg0, WString arg1, WString arg2, int arg3,
			int arg4, int arg5, int arg6) {
		return LIB.AU3_ControlMove(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
	}

	@Override
	public int AU3_RunAsWait(WString arg0, WString arg1, WString arg2, int arg3,
			WString arg4, WString arg5, int arg6) {
		return LIB.AU3_RunAsWait(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
	}

	@Override
	public int AU3_RunAsWait(WString arg0, WString arg1, WString arg2, int arg3,
			WString arg4, WString arg5) {
		return LIB.AU3_RunAsWait(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public void AU3_ToolTip(WString arg0, int arg1) {
		LIB.AU3_ToolTip(arg0, arg1);
	}

	@Override
	public void AU3_ToolTip(WString arg0) {
		LIB.AU3_ToolTip(arg0);
	}

	@Override
	public void AU3_ToolTip(WString arg0, int arg1, int arg2) {
		LIB.AU3_ToolTip(arg0, arg1, arg2);
	}

	// https://www.autoitscript.com/autoit3/docs/functions/Send.htm
	public void Send(String keys) {
		LIB.AU3_Send(new WString(keys));
	}

	public void Send(String keys, Boolean flag) {
		LIB.AU3_Send(new WString(keys), flag ? 0 : 1);
	}

	@Override
	public void AU3_Send(WString arg0) {
		LIB.AU3_Send(arg0);
	}

	@Override
	public void AU3_Send(WString arg0, int arg1) {
		LIB.AU3_Send(arg0, arg1);
	}

	@Override
	public int AU3_MouseMove(int arg0, int arg1, int arg2) {
		return LIB.AU3_MouseMove(arg0, arg1, arg2);
	}

	@Override
	public int AU3_MouseMove(int arg0, int arg1) {
		return LIB.AU3_MouseMove(arg0, arg1);
	}

	@Override
	public int AU3_ProcessWait(WString arg0) {
		return LIB.AU3_ProcessWait(arg0);
	}

	@Override
	public int AU3_ProcessWait(WString arg0, int arg1) {
		return LIB.AU3_ProcessWait(arg0, arg1);
	}

	@Override
	public int AU3_RunWait(WString arg0, WString arg1, int arg2) {
		return LIB.AU3_RunWait(arg0, arg1, arg2);
	}

	@Override
	public int AU3_RunWait(WString arg0, WString arg1) {
		return LIB.AU3_RunWait(arg0, arg1);
	}

	@Override
	public int AU3_RunAs(WString arg0, WString arg1, WString arg2, int arg3,
			WString arg4, WString arg5) {
		return LIB.AU3_RunAs(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public int AU3_RunAs(WString arg0, WString arg1, WString arg2, int arg3,
			WString arg4, WString arg5, int arg6) {
		return LIB.AU3_RunAs(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
	}

}
