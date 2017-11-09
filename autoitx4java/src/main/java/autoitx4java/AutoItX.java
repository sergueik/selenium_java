/**
 * Copyright (c) 2011 Richard Kanavati
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package autoitx4java;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.SafeArray;
import com.jacob.com.Variant;

/**
 * A Java AutoItX3 Bridge.
 * @author Richard Kanavati
 */

public class AutoItX {

	protected ActiveXComponent autoItX;
	public final static int SW_MAXIMIZE = 3;
	public final static int SW_HIDE = 0;
	public final static int SW_RESTORE = 9;
	public final static int SW_SHOW = 5;
	public final static int SW_SHOWDEFAULT = 10;
	public final static int SW_SHOWMAXIMIZED = 3;
	public final static int SW_SHOWMINIMIZED = 2;
	public final static int SW_SHOWMINNOACTIVE = 7;
	public final static int SW_SHOWNA = 8;
	public final static int SW_SHOWNOACTIVATE = 4;
	public final static int SW_SHOWNORMAL = 1;

	public final static String OPT_CARET_COORD_MODE = "CaretCoordMode";
	public final static String OPT_MOUSE_CLICK_DELAY = "MouseClickDelay";
	public final static String OPT_MOUSE_CLICK_DOWN_DELAY = "MouseClickDownDelay";
	public final static String OPT_MOUSE_CLICK_DRAG_DELAY = "MouseClickDragDelay";
	public final static String OPT_MOUSE_COORD_MODE = "MouseCoordMode";
	public final static String OPT_PIXEL_COORD_MODE = "PixelCoordMode";
	public final static String OPT_SEND_ATTACH_MODE = "SendAttachMode";
	public final static String OPT_SEND_CAPSLOCK_MODE = "SendCapslockMode";
	public final static String OPT_SEND_KEY_DELAY = "SendKeyDelay";
	public final static String OPT_SEND_KEY_DOWN_DELAY = "SendKeyDownDelay";
	public final static String OPT_WIN_DETECT_HIDDEN_TEXT = "WinDetectHiddenText";
	public final static String OPT_WIN_SEARCH_CHILDREN = "WinSearchChildren";
	public final static String OPT_WIN_TEXT_MATCH_MODE = "WinTextMatchMode";
	public final static String OPT_WIN_TITLE_MATCH_MODE = "WinTitleMatchMode";
	public final static String OPT_WIN_WAIT_DELAY = "WinWaitDelay";

	public AutoItX() {
		autoItX = new ActiveXComponent("AutoItX3.Control");
	}

	public String getVersion() {
		return autoItX.getProperty("version").getString();
	}

	public int getError() {
		Variant error = autoItX.invoke("error");
		return error.getInt();
	}

	public String clipGet() {
		return autoItX.invoke("ClipGet").getString();
	}

	public void clipPut(String value) {
		autoItX.invoke("ClipPut", new Variant(value));
	}

	public boolean driveMapAdd(String device, String remote) {
		return driveMapAdd(device, remote, 0, "", "");
	}

	public boolean driveMapAdd(String device, String remote, int flags,
			String username, String password) {
		Variant vDevice = new Variant(device);
		Variant vRemote = new Variant(remote);
		Variant vFlags = new Variant(flags);
		Variant vUsername = new Variant(username);
		Variant vPassword = new Variant(password);
		Variant[] params = new Variant[] { vDevice, vRemote, vFlags, vUsername,
				vPassword };
		Variant result = autoItX.invoke("DriveMapAdd", params);
		if (result.getvt() == Variant.VariantString) {
			return oneToTrue(Integer.parseInt(result.getString()));
		}

		return oneToTrue(result.getInt());
	}

	public boolean driveMapAdd(String device, String remote, int flags) {
		Variant vDevice = new Variant(device);
		Variant vRemote = new Variant(remote);
		Variant vFlags = new Variant(flags);
		Variant[] params = new Variant[] { vDevice, vRemote, vFlags };
		Variant result = autoItX.invoke("DriveMapAdd", params);
		if (result.getvt() == Variant.VariantString) {
			return oneToTrue(Integer.parseInt(result.getString()));
		}

		return oneToTrue(result.getInt());
	}

	public boolean driveMapDelete(String device) {
		Variant result = autoItX.invoke("DriveMapDel", device);
		return oneToTrue(result);
	}

	public String driveMapGet(String device) {
		Variant result = autoItX.invoke("DriveMapGet", device);
		return result.getString();
	}

	public boolean iniDelete(String filename, String section, String key) {
		Variant vFilename = new Variant(filename);
		Variant vSection = new Variant(section);
		Variant vKey = new Variant(key);
		Variant[] params = new Variant[] { vFilename, vSection, vKey };
		Variant result = autoItX.invoke("IniDelete", params);
		return oneToTrue(result);
	}

	public boolean iniDelete(String filename, String section) {
		return iniDelete(filename, section, "");
	}

	public String iniRead(String filename, String section, String key,
			String defaultVal) {
		Variant vFilename = new Variant(filename);
		Variant vSection = new Variant(section);
		Variant vKey = new Variant(key);
		Variant vDefault = new Variant(defaultVal);
		Variant[] params = new Variant[] { vFilename, vSection, vKey, vDefault };
		Variant result = autoItX.invoke("IniRead", params);
		return result.getString();
	}

	public Boolean iniWrite(String filename, String section, String key,
			String value) {
		Variant vFilename = new Variant(filename);
		Variant vSection = new Variant(section);
		Variant vKey = new Variant(key);
		Variant vValue = new Variant(value);
		Variant[] params = new Variant[] { vFilename, vSection, vKey, vValue };
		Variant result = autoItX.invoke("IniWrite", params);
		return oneToTrue(result);
	}

	public double pixelChecksum(int left, int top, int right, int bottom,
			int step) {
		Variant vLeft = new Variant(left);
		Variant vTop = new Variant(top);
		Variant vRight = new Variant(right);
		Variant vBottom = new Variant(bottom);
		Variant vStep = new Variant(step);
		Variant[] params = new Variant[] { vLeft, vTop, vRight, vBottom, vStep };
		Variant result = autoItX.invoke("PixelChecksum", params);
		return result.getDouble();
	}

	public double pixelChecksum(int left, int top, int right, int bottom) {
		return pixelChecksum(left, top, right, bottom, 0);
	}

	public float pixelGetColor(int x, int y) {
		Variant vX = new Variant(x);
		Variant vY = new Variant(y);
		Variant[] params = new Variant[] { vX, vY };
		Variant result = autoItX.invoke("PixelGetColor", params);
		return result.getInt();
	}

	public long[] pixelSearch(int left, int top, int right, int bottom, int color,
			int shadeVariation, int step) {
		Variant vLeft = new Variant(left);
		Variant vTop = new Variant(top);
		Variant vRight = new Variant(right);
		Variant vBottom = new Variant(bottom);
		Variant vColor = new Variant(color);
		Variant vShadeVariation = new Variant(shadeVariation);
		Variant vStep = new Variant(step);
		Variant[] params = new Variant[] { vLeft, vTop, vRight, vBottom, vColor,
				vShadeVariation, vStep };
		Variant result = autoItX.invoke("PixelSearch", params);
		long[] l = new long[2];
		if (result.getvt() == 8204) {
			l[0] = result.toSafeArray().getLong(0);
			l[1] = result.toSafeArray().getLong(1);
		}

		return l;
	}

	public long[] pixelSearch(int left, int top, int right, int bottom,
			int color) {
		return pixelSearch(left, top, right, bottom, color, 0, 1);
	}

	public void send(String keys, boolean isRaw) {
		Variant vKeys = new Variant(keys);
		Variant vFlag = new Variant(isRaw ? 1 : 0);
		Variant[] params = new Variant[] { vKeys, vFlag };
		autoItX.invoke("Send", params);
	}

	public void send(String keys) {
		send(keys, true);
	}

	public void toolTip(String text, int x, int y) {
		Variant vText = new Variant(text);
		Variant vX = new Variant(x);
		Variant vY = new Variant(y);
		Variant[] params = new Variant[] { vText, vX, vY };
		autoItX.invoke("ToolTip", params);
	}

	public void toolTip(String text) {
		autoItX.invoke("ToolTip", text);
	}

	public void blockInput(boolean disableInput) {
		autoItX.invoke("BlockInput", (disableInput ? 1 : 0));
	}

	public boolean cdTray(String drive, String status) {
		Variant vDrive = new Variant(drive);
		Variant vStatus = new Variant(status);
		Variant[] params = new Variant[] { vDrive, vStatus };
		Variant result = autoItX.invoke("CDTray", params);
		return oneToTrue(result);
	}

	public boolean isAdmin() {
		return oneToTrue(autoItX.invoke("IsAdmin"));
	}

	public String autoItSetOption(String option, String param) {
		Variant vOption = new Variant(option);
		Variant vParam = new Variant(param);
		Variant[] params = new Variant[] { vOption, vParam };
		Variant result = autoItX.invoke("AutoItSetOption", params);
		if (result.getvt() == Variant.VariantInt) {
			return String.valueOf(result.getInt());
		}

		return result.getString();
	}

	public String setOption(String option, String param) {
		return autoItSetOption(option, param);
	}

	public void mouseClick(String button, int x, int y, int clicks, int speed) {
		Variant vButton = new Variant(button);
		Variant vX = new Variant(x);
		Variant vY = new Variant(y);
		Variant vClicks = new Variant(clicks);
		Variant vSpeed = new Variant(speed);
		Variant[] params = new Variant[] { vButton, vX, vY, vClicks, vSpeed };
		autoItX.invoke("MouseClick", params);
	}

	public void mouseClick(String button, int clicks, int speed) {
		Variant vButton = new Variant(button);
		Variant vClicks = new Variant(clicks);
		Variant vSpeed = new Variant(speed);
		Variant[] params = new Variant[] { vButton, vClicks, vSpeed };
		autoItX.invoke("MouseClick", params);
	}

	public void mouseClickDrag(String button, int x, int y, int x2, int y2,
			int speed) {
		Variant vButton = new Variant(button);
		Variant vX = new Variant(x);
		Variant vY = new Variant(y);
		Variant vX2 = new Variant(x2);
		Variant vY2 = new Variant(y2);
		Variant vSpeed = new Variant(speed);
		Variant[] params = new Variant[] { vButton, vX, vY, vX2, vY2, vSpeed };
		autoItX.invoke("MouseClickDrag", params);
	}

	public void mouseClickDrag(String button, int x, int y, int x2, int y2) {
		mouseClickDrag(button, x, y, x2, y2, 10);
	}

	public void mouseDown(String button) {
		autoItX.invoke("MouseDown", button);
	}

	/**
	 * Returns a cursor ID Number of the current Mouse Cursor.
	 * @return  0 = UNKNOWN (this includes pointing and grabbing hand icons)
	 * <br/> 1 = APPSTARTING
	 * <br/> 2 = ARROW
	 * <br/> 3 = CROSS
	 * <br/> 4 = HELP
	 * <br/> 5 = IBEAM
	 * <br/> 6 = ICON
	 * <br/> 7 = NO
	 * <br/> 8 = SIZE
	 * <br/> 9 = SIZEALL
	 * <br/> 10 = SIZENESW
	 * <br/> 11 = SIZENS
	 * <br/> 12 = SIZENWSE
	 * <br/> 13 = SIZEWE
	 * <br/> 14 = UPARROW
	 * <br/> 15 = WAIT
	 */
	public int mouseGetCursor() {
		return autoItX.invoke("MouseGetCursor").getInt();
	}

	public int mouseGetPosX() {
		return autoItX.invoke("MouseGetPosX").getInt();
	}

	public int mouseGetPosY() {
		return autoItX.invoke("MouseGetPosY").getInt();
	}

	public boolean mouseMove(int x, int y, int speed) {
		Variant vX = new Variant(x);
		Variant vY = new Variant(y);
		Variant vSpeed = new Variant(speed);
		Variant[] params = new Variant[] { vX, vY, vSpeed };
		return oneToTrue(autoItX.invoke("MouseMove", params).getInt());
	}

	public boolean mouseMove(int x, int y) {
		return mouseMove(x, y, 10);
	}

	public void mouseUp(String button) {
		autoItX.invoke("MouseUp", button);
	}

	public void mouseWheel(String direction, int clicks) {
		Variant vDirection = new Variant(direction);
		Variant vClicks = new Variant(clicks);
		Variant[] params = new Variant[] { vDirection, vClicks };
		autoItX.invoke("MouseWheel", params);
	}

	public void mouseWheel(String direction) {
		mouseWheel(direction, 1);
	}

	public void processClose(String process) {
		autoItX.invoke("ProcessClose", process);
	}

	public int processExists(String process) {
		return autoItX.invoke("ProcessExists", process).getInt();
	}

	public boolean processSetPriority(String process, int priority) {
		Variant vProcess = new Variant(process);
		Variant vPriority = new Variant(priority);
		Variant[] params = new Variant[] { vProcess, vPriority };
		Variant result = autoItX.invoke("ProcessSetPriority", params);
		return oneToTrue(result.getInt());
	}

	public boolean processWait(String process, int timeout) {
		Variant vProcess = new Variant(process);
		Variant vTimeout = new Variant(timeout);
		Variant[] params = new Variant[] { vProcess, vTimeout };
		Variant result = autoItX.invoke("ProcessWait", params);
		return oneToTrue(result.getInt());
	}

	public boolean processWait(String process) {
		Variant result = autoItX.invoke("ProcessWait", process);
		return oneToTrue(result.getInt());
	}

	public boolean processWaitClose(String process, int timeout) {
		Variant vProcess = new Variant(process);
		Variant vTimeout = new Variant(timeout);
		Variant[] params = new Variant[] { vProcess, vTimeout };
		Variant result = autoItX.invoke("ProcessWaitClose", params);
		return oneToTrue(result.getInt());
	}

	public boolean processWaitClose(String process) {
		Variant result = autoItX.invoke("ProcessWaitClose", process);
		return oneToTrue(result.getInt());
	}

	public int run(String filename, String workingDirectory, int flag) {
		Variant vFilename = new Variant(filename);
		Variant vWorkingDirectory = new Variant(workingDirectory);
		Variant vFlag = new Variant(flag);
		Variant[] params = new Variant[] { vFilename, vWorkingDirectory, vFlag };
		return autoItX.invoke("Run", params).getInt();
	}

	public int run(String filename, String workingDirectory) {
		Variant vFilename = new Variant(filename);
		Variant vWorkingDirectory = new Variant(workingDirectory);
		Variant[] params = new Variant[] { vFilename, vWorkingDirectory };
		return autoItX.invoke("Run", params).getInt();
	}

	public int run(String filename) {
		return autoItX.invoke("Run", filename).getInt();
	}

	public int runAsSet(String username, String domain, String password,
			int options) {
		Variant vUsername = new Variant(username);
		Variant vDomain = new Variant(domain);
		Variant vPassword = new Variant(password);
		Variant vOptions = new Variant(options);
		Variant[] params = new Variant[] { vUsername, vDomain, vPassword,
				vOptions };
		return autoItX.invoke("RunAsSet", params).getInt();
	}

	public int runAsSet(String username, String domain, String password) {
		return runAsSet(username, domain, password, 1);
	}

	public int runWait(String filename, String workingDirectory, int flag) {
		Variant vFilename = new Variant(filename);
		Variant vWorkingDirectory = new Variant(workingDirectory);
		Variant vFlag = new Variant(flag);
		Variant[] params = new Variant[] { vFilename, vWorkingDirectory, vFlag };
		return autoItX.invoke("RunWait", params).getInt();
	}

	public int runWait(String filename, String workingDirectory) {
		Variant vFilename = new Variant(filename);
		Variant vWorkingDirectory = new Variant(workingDirectory);
		Variant[] params = new Variant[] { vFilename, vWorkingDirectory };
		return autoItX.invoke("RunWait", params).getInt();
	}

	public int runWait(String filename) {
		return autoItX.invoke("RunWait", filename).getInt();
	}

	public boolean shutdown(int code) {
		return oneToTrue(autoItX.invoke("Shutdown", new Variant(code)).getInt());
	}

	public int regDeleteKey(String keyname) {
		return autoItX.invoke("RegDeleteKey", keyname).getInt();
	}

	public int regDeleteVal(String keyname) {
		return autoItX.invoke("RegDeleteVal", keyname).getInt();
	}

	public String regEnumKey(String keyname, int instance) {
		Variant vKeyname = new Variant(keyname);
		Variant vInstance = new Variant(instance);
		Variant[] params = new Variant[] { vKeyname, vInstance };
		return autoItX.invoke("RegEnumKey", params).getString();
	}

	public String regEnumVal(String keyname, int instance) {
		Variant vKeyname = new Variant(keyname);
		Variant vInstance = new Variant(instance);
		Variant[] params = new Variant[] { vKeyname, vInstance };
		return autoItX.invoke("RegEnumVal", params).getString();
	}

	public String regRead(String keyname, String valueName) {
		Variant vKeyname = new Variant(keyname);
		Variant vValueName = new Variant(valueName);
		Variant[] params = new Variant[] { vKeyname, vValueName };
		Variant result = autoItX.invoke("RegRead", params);
		if (result.getvt() == Variant.VariantInt) {
			return String.valueOf(result.getInt());
		}

		if (result.getvt() == Variant.VariantString) {
			return result.getString();
		}

		return "";
	}

	public boolean regWrite(String keyname, String valueName, String type,
			String value) {
		Variant vKeyname = new Variant(keyname);
		Variant vValueName = new Variant(valueName);
		Variant vType = new Variant(type);
		Variant vValue = new Variant(value);
		Variant[] params = new Variant[] { vKeyname, vValueName, vType, vValue };
		Variant result = autoItX.invoke("RegWrite", params);
		return oneToTrue(result.getInt());
	}

	public void sleep(int delay) {
		autoItX.invoke("sleep", delay);
	}

	public boolean controlClick(String title, String text, String controlID,
			String button, int clicks, int x, int y) {
		Variant vTitle = new Variant(title);
		Variant vText = new Variant(text);
		Variant vControlID = new Variant(controlID);
		Variant vButton = new Variant(button);
		Variant vClicks = new Variant(clicks);
		Variant vX = new Variant(x);
		Variant vY = new Variant(y);
		Variant[] params = new Variant[] { vTitle, vText, vControlID, vButton,
				vClicks, vX, vY };
		Variant result = autoItX.invoke("ControlClick", params);
		return oneToTrue(result.getInt());
	}

	public boolean controlClick(String title, String text, String controlID,
			String button, int clicks) {
		Variant vTitle = new Variant(title);
		Variant vText = new Variant(text);
		Variant vControlID = new Variant(controlID);
		Variant vButton = new Variant(button);
		Variant vClicks = new Variant(clicks);
		Variant[] params = new Variant[] { vTitle, vText, vControlID, vButton,
				vClicks };
		Variant result = autoItX.invoke("ControlClick", params);
		return oneToTrue(result.getInt());
	}

	public boolean controlClick(String title, String text, String controlID,
			String button) {
		return controlClick(title, text, controlID, button, 1);
	}

	public boolean controlClick(String title, String text, String controlID) {
		return controlClick(title, text, controlID, "left", 1);
	}

	private String controlCommandString(String title, String text, String control,
			String command, String option) {
		Variant result = controlCommandVariant(title, text, control, command,
				option);
		return result.getString();
	}

	private void controlCommandVoid(String title, String text, String control,
			String command, String option) {
		controlCommandVariant(title, text, control, command, option);

	}

	private Variant controlCommandVariant(String title, String text,
			String control, String command, String option) {
		Variant vTitle = new Variant(title);
		Variant vText = new Variant(text);
		Variant vControl = new Variant(control);
		Variant vCommand = new Variant(command);
		Variant vOption = new Variant(option);
		Variant[] params = new Variant[] { vTitle, vText, vControl, vCommand,
				vOption };
		return autoItX.invoke("ControlCommand", params);
	}

	public void controlCommandShowDropdown(String title, String text,
			String control) {
		controlCommandVoid(title, text, control, "ShowDropDown", "");
	}

	public void controlCommandHideDropDown(String title, String text,
			String control) {
		controlCommandVoid(title, text, control, "HideDropDown", "");
	}

	public void controlCommandCheck(String title, String text, String control) {
		controlCommandVoid(title, text, control, "Check", "");
	}

	public void controlCommandUncheck(String title, String text, String control) {
		controlCommandVoid(title, text, control, "UnCheck", "");
	}

	public void controlCommandAddString(String title, String text, String control,
			String string) {
		controlCommandVoid(title, text, control, "AddString", string);
	}

	public void controlCommandDeleteString(String title, String text,
			String control, String occurrance) {
		controlCommandVoid(title, text, control, "DelString", occurrance);
	}

	public void controlCommandEditPaste(String title, String text, String control,
			String string) {
		controlCommandVoid(title, text, control, "EditPaste", string);
	}

	public void controlCommandSetCurrentSelection(String title, String text,
			String control, String occurrance) {
		controlCommandVoid(title, text, control, "SetCurrentSelection", occurrance);
	}

	public void controlCommandSelectString(String title, String text,
			String control, String string) {
		controlCommandVoid(title, text, control, "SelectString", string);
	}

	private boolean controlCommandBoolean(String title, String text,
			String control, String command, String option) {
		return oneToTrue(controlCommandInts(title, text, control, command, option));
	}

	public boolean controlCommandIsVisible(String title, String text,
			String control) {
		return controlCommandBoolean(title, text, control, "IsVisible", "");
	}

	public boolean controlCommandIsChecked(String title, String text,
			String control) {
		return controlCommandBoolean(title, text, control, "IsChecked", "");
	}

	public boolean controlCommandIsEnabled(String title, String text,
			String control) {
		return controlCommandBoolean(title, text, control, "IsEnabled", "");
	}

	public int controlCommandFindString(String title, String text, String control,
			String string) {
		return controlCommandInts(title, text, control, "FindString", string);
	}

	private int controlCommandInts(String title, String text, String control,
			String command, String option) {
		Variant result = controlCommandVariant(title, text, control, command,
				option);
		return result.getvt() == Variant.VariantString
				? Integer.parseInt(result.getString()) : 0;
	}

	public int controlCommandGetCurrentLine(String title, String text,
			String control) {
		return controlCommandInts(title, text, control, "GetCurrentLine", "");
	}

	public int controlCommandGetCurrentCol(String title, String text,
			String control) {
		return controlCommandInts(title, text, control, "GetCurrentCol", "");
	}

	public int controlCommandGetLineCount(String title, String text,
			String control) {
		return controlCommandInts(title, text, control, "GetLineCount", "");
	}

	public String controlCommandGetCurrentSelection(String title, String text,
			String control, int charLength) {
		return controlCommandString(title, text, control, "GetCurrentSelection",
				"");
	}

	public String controlCommandGetSelected(String title, String text,
			String control, int charLength) {
		return controlCommandString(title, text, control, "GetSelected", "");
	}

	public void controlCommandTabLeft(String title, String text, String control) {
		controlCommandVoid(title, text, control, "TabLeft", "");
	}

	public void controlCommandTabRight(String title, String text,
			String control) {
		controlCommandVoid(title, text, control, "TabRight", "");
	}

	public String controlCommandCurrentTab(String title, String text,
			String control) {
		return controlCommandString(title, text, control, "CurrentTab", "");
	}

	public boolean controlDisable(String title, String text, String control) {
		Variant vTitle = new Variant(title);
		Variant vText = new Variant(text);
		Variant vControl = new Variant(control);
		Variant[] params = new Variant[] { vTitle, vText, vControl };
		Variant result = autoItX.invoke("ControlDisable", params);
		return oneToTrue(result.getInt());
	}

	public boolean controlEnable(String title, String text, String control) {
		Variant vTitle = new Variant(title);
		Variant vText = new Variant(text);
		Variant vControl = new Variant(control);
		Variant[] params = new Variant[] { vTitle, vText, vControl };
		Variant result = autoItX.invoke("ControlEnable", params);
		return oneToTrue(result.getInt());
	}

	public boolean controlFocus(String title, String text, String control) {
		return controlBool(title, text, control, "ControlFocus");
	}

	public String controlGetFocus(String title, String text) {
		Variant vTitle = new Variant(title);
		Variant vText = new Variant(text);
		Variant[] params = new Variant[] { vTitle, vText };
		return autoItX.invoke("ControlGetFocus", params).getString();
	}

	public String controlGetFocus(String title) {
		return autoItX.invoke("ControlGetFocus", title).getString();
	}

	public String controlGetHandle(String title, String text, String controlID) {
		return controlString(title, text, controlID, "ControlGetHandle");
	}

	public int controlGetPosX(String title, String text, String controlID) {
		return controlInt(title, text, controlID, "ControlGetPosX");
	}

	public int controlGetPosY(String title, String text, String controlID) {
		return controlInt(title, text, controlID, "ControlGetPosY");
	}

	public int controlGetPosWidth(String title, String text, String controlID) {
		return controlInt(title, text, controlID, "ControlGetPosWidth");
	}

	public int controlGetPosHeight(String title, String text, String controlID) {
		return controlInt(title, text, controlID, "ControlGetPosHeight");
	}

	public String controlGetText(String title, String text, String controlID) {
		return controlString(title, text, controlID, "ControlGetText");
	}

	public boolean controlHide(String title, String text, String controlID) {
		return controlBool(title, text, controlID, "ControlHide");
	}

	public int controlListViewFindItem(String title, String text, String control,
			String string, String subitem) {
		return controlListViewInt(title, text, control, "FindItem", string,
				subitem);
	}

	private int controlListViewInt(String title, String text, String control,
			String command, String option, String option2) {
		return controlView(title, text, control, command, option, option2,
				"ControlListView").getInt();
	}

	private String controlListViewString(String title, String text,
			String control, String command, String option, String option2) {
		return controlView(title, text, control, command, option, option2,
				"ControlListView").getString();
	}

	private Variant controlView(String title, String text, String control,
			String command, String option, String option2, String function) {
		Variant vTitle = new Variant(title);
		Variant vText = new Variant(text);
		Variant vControl = new Variant(control);
		Variant vCommand = new Variant(command);
		Variant vOption = new Variant(option);
		Variant vOption2 = new Variant(option2);
		Variant[] params = new Variant[] { vTitle, vText, vControl, vCommand,
				vOption, vOption2 };
		return autoItX.invoke(function, params);
	}

	public int controlListViewGetItemCount(String title, String text,
			String control) {
		return controlListViewInt(title, text, control, "GetItemCount", "", "");

	}

	public int controlListViewGetSelectedCount(String title, String text,
			String control) {
		return controlListViewInt(title, text, control, "GetSelectedCount", "", "");
	}

	public int controlListViewGetSubItemCount(String title, String text,
			String control) {
		return controlListViewInt(title, text, control, "GetSubItemCount", "", "");
	}

	public String controlListViewGetText(String title, String text,
			String control, String item, String subitem) {
		return controlListViewString(title, text, control, "GetText", item,
				subitem);
	}

	public boolean controlListViewIsSelected(String title, String text,
			String control, String item) {
		return oneToTrue(
				controlListViewInt(title, text, control, "IsSelected", item, ""));
	}

	public String controlListViewGetSelected(String title, String text,
			String control) {
		return controlListViewString(title, text, control, "GetSelected", "", "");
	}

	public String[] controlListViewGetSelectedArray(String title, String text,
			String control) {
		SafeArray safeArr = controlView(title, text, control, "GetSelected", "", "",
				"ControlListView").toSafeArray();
		return safeArr.toStringArray();
	}

	public void controlListViewSelect(String title, String text, String control,
			String from, String to) {
		controlView(title, text, control, "Select", from, to, "ControlListView");
	}

	public void controlListViewSelectAll(String title, String text,
			String control, String from, String to) {
		controlView(title, text, control, "SelectAll", from, to, "ControlListView");
	}

	public void controlListViewSelectAll(String title, String text,
			String control, String from) {
		Variant vTitle = new Variant(title);
		Variant vText = new Variant(text);
		Variant vControl = new Variant(control);
		Variant vCommand = new Variant("SelectAll");
		Variant vFrom = new Variant(from);
		Variant[] params = new Variant[] { vTitle, vText, vControl, vCommand,
				vFrom };
		autoItX.invoke("ControlListView", params);
	}

	public void controlListViewSelectClear(String title, String text,
			String control) {
		controlView(title, text, control, "SelectClear", "", "", "ControlListView");
	}

	public void controlListViewSelectInvert(String title, String text,
			String control) {
		controlView(title, text, control, "SelectInvert", "", "",
				"ControlListView");
	}

	public void controlListViewSelectViewChange(String title, String text,
			String control, String view) {
		controlView(title, text, control, "ViewChnage", view, "",
				"ControlListView");
	}

	protected Variant controlVariant(String title, String text, String control,
			String function) {
		Variant vTitle = new Variant(title);
		Variant vText = new Variant(text);
		Variant vControl = new Variant(control);
		Variant[] params = new Variant[] { vTitle, vText, vControl };
		return autoItX.invoke(function, params);
	}

	protected boolean controlBool(String title, String text, String control,
			String function) {
		Variant result = controlVariant(title, text, control, function);
		return oneToTrue(result.getInt());
	}

	protected int controlInt(String title, String text, String control,
			String function) {
		Variant result = controlVariant(title, text, control, function);
		return result.getInt();
	}

	protected String controlString(String title, String text, String control,
			String function) {
		Variant result = controlVariant(title, text, control, function);
		if (result.getvt() == Variant.VariantString) {
			return result.getString();
		}

		if (result.getvt() == Variant.VariantInt) {
			return String.valueOf(result.getInt());
		}

		return "";
	}

	public boolean controlMove(String title, String text, String control, int x,
			int y, int width, int height) {
		Variant vTitle = new Variant(title);
		Variant vText = new Variant(text);
		Variant vControl = new Variant(control);
		Variant vX = new Variant(x);
		Variant vY = new Variant(y);
		Variant vWidth = new Variant(width);
		Variant vHeight = new Variant(height);
		Variant[] params = new Variant[] { vTitle, vText, vControl, vX, vY, vWidth,
				vHeight };
		Variant result = autoItX.invoke("ControlMove", params);
		return oneToTrue(result.getInt());
	}

	public boolean controlMove(String title, String text, String control, int x,
			int y) {
		Variant vTitle = new Variant(title);
		Variant vText = new Variant(text);
		Variant vControl = new Variant(control);
		Variant vX = new Variant(x);
		Variant vY = new Variant(y);
		Variant[] params = new Variant[] { vTitle, vText, vControl, vX, vY };
		Variant result = autoItX.invoke("ControlMove", params);
		return oneToTrue(result.getInt());
	}

	public boolean controlSend(String title, String text, String control,
			String string, boolean sendRawKeys) {
		Variant vTitle = new Variant(title);
		Variant vText = new Variant(text);
		Variant vControl = new Variant(control);
		Variant vString = new Variant(string);
		int flag = (sendRawKeys) ? 1 : 0;
		Variant vFlag = new Variant(flag);
		Variant[] params = new Variant[] { vTitle, vText, vControl, vString,
				vFlag };
		Variant result = autoItX.invoke("ControlSend", params);
		return oneToTrue(result.getInt());
	}

	public boolean controlSend(String title, String text, String control,
			String string) {
		return controlSend(title, text, control, string, false);
	}

	public boolean ControlSetText(String title, String text, String control,
			String string) {
		Variant vTitle = new Variant(title);
		Variant vText = new Variant(text);
		Variant vControl = new Variant(control);
		Variant vString = new Variant(string);
		Variant[] params = new Variant[] { vTitle, vText, vControl, vString };
		Variant result = autoItX.invoke("ControlSetText", params);
		return oneToTrue(result.getInt());
	}

	public boolean controlShow(String title, String text, String control) {
		return controlBool(title, text, control, "ControlShow");
	}

	private String controlTreeViewString(String title, String text,
			String control, String command, String option, String option2) {
		return controlView(title, text, control, command, option, option2,
				"ControlTreeView").getString();
	}

	private int controlTreeViewInt(String title, String text, String control,
			String command, String option, String option2) {
		return controlView(title, text, control, command, option, option2,
				"ControlTreeView").getInt();
	}

	public boolean controlTreeViewBoolean(String title, String text,
			String control, String command, String option, String option2) {
		Variant result = controlView(title, text, control, command, option, option2,
				"ControlTreeView");
		return oneToTrue(result.getInt());
	}

	public void controlTreeViewCheck(String title, String text, String control,
			String item) {
		controlView(title, text, control, "Check", item, "", "ControlTreeView");
	}

	public void controlTreeViewCollapse(String title, String text, String control,
			String item) {
		controlView(title, text, control, "Collapse", item, "", "ControlTreeView");
	}

	public Boolean controlTreeViewExists(String title, String text,
			String control, String item) {
		return controlTreeViewBoolean(title, text, control, "Exists", item, "");
	}

	public void controlTreeViewExpand(String title, String text, String control,
			String item) {
		controlView(title, text, control, "Expand", item, "", "ControlTreeView");
	}

	public int controlTreeViewGetItemCount(String title, String text,
			String control, String item) {
		return controlTreeViewInt(title, text, control, "GetItemCount", item, "");
	}

	public int controlTreeViewGetSelectedItemIndex(String title, String text,
			String control) {
		Variant vTitle = new Variant(title);
		Variant vText = new Variant(text);
		Variant vControl = new Variant(control);
		Variant vCommand = new Variant("GetSelected");
		Variant vIndex = new Variant(1);
		Variant[] params = new Variant[] { vTitle, vText, vControl, vCommand,
				vIndex };
		return autoItX.invoke("ControlTreeView", params).getInt();
	}

	public String controlTreeViewGetSelectedItemText(String title, String text,
			String control) {
		Variant vTitle = new Variant(title);
		Variant vText = new Variant(text);
		Variant vControl = new Variant(control);
		Variant vCommand = new Variant("GetSelected");
		Variant vIndex = new Variant(0);
		Variant[] params = new Variant[] { vTitle, vText, vControl, vCommand,
				vIndex };
		return autoItX.invoke("ControlTreeView", params).getString();
	}

	public String controlTreeViewGetText(String title, String text,
			String control, String item) {
		return controlTreeViewString(title, text, control, "GetText", item, "");
	}

	public int controlTreeViewIsChecked(String title, String text,
			String control) {
		return controlView(title, text, control, "IsChecked", "", "",
				"ControlTreeView").getInt();
	}

	public void controlTreeViewSelect(String title, String text, String control,
			String item) {
		controlView(title, text, control, "Select", item, "", "ControlTreeView");
	}

	public void controlTreeViewUncheck(String title, String text, String control,
			String item) {
		controlView(title, text, control, "Uncheck", item, "", "ControlTreeView");
	}

	public String statusbarGetText(String title, String text, int part) {
		Variant vTitle = new Variant(title);
		Variant vText = new Variant(text);
		Variant vPart = new Variant(part);
		Variant[] params = new Variant[] { vTitle, vText, vPart };
		return autoItX.invoke("StatusbarGetText", params).getString();
	}

	public String StatusbarGetText(String title, String text) {
		return winVariant(title, text, "StatusbarGetText").getString();

	}

	private Variant winVariant(String title, String text, String function) {
		Variant vTitle = new Variant(title);
		Variant vText = new Variant(text);
		Variant[] params = new Variant[] { vTitle, vText };
		return autoItX.invoke(function, params);
	}

	private Variant winVariant(String title, String function) {
		Variant vTitle = new Variant(title);
		Variant[] params = new Variant[] { vTitle };
		return autoItX.invoke(function, params);
	}

	public void winActivate(String title, String text) {
		winVariant(title, text, "WinActivate");
	}

	public void winActivate(String title) {
		winVariant(title, "WinActivate");
	}

	public void winActive(String title, String text) {
		winVariant(title, text, "WinActive");
	}

	public void winActive(String title) {
		winVariant(title, "WinActive");
	}

	public void winClose(String title, String text) {
		winVariant(title, text, "WinClose");
	}

	public void winClose(String title) {
		winVariant(title, "WinClose");
	}

	public boolean winExists(String title, String text) {
		Variant result = winVariant(title, text, "WinExists");
		return oneToTrue(result.getInt());
	}

	public boolean winExists(String title) {
		Variant result = winVariant(title, "WinExists");
		return oneToTrue(result.getInt());
	}

	public int winGetCaretPosX() {
		return autoItX.invoke("WinGetCaretPosX").getInt();
	}

	public int winGetCaretPosY() {
		return autoItX.invoke("WinGetCaretPosY").getInt();
	}

	public String winGetClassList(String title, String text) {
		Variant vTitle = new Variant(title);
		Variant vText = new Variant(text);
		Variant[] params = new Variant[] { vTitle, vText };
		Variant result = autoItX.invoke("WinGetClassList", params);
		return safeString(result);
	}

	public int winGetClientSizeWidth(String title, String text) {
		Variant result = winVariant(title, text, "WinGetClientSizeWidth");
		return result.getInt();
	}

	public int winGetClientSizeHeight(String title, String text) {
		Variant result = winVariant(title, text, "WinGetClientSizeHeight");
		return result.getInt();
	}

	public int winGetClientSizeWidth(String title) {
		Variant result = winVariant(title, "WinGetClientSizeWidth");
		return result.getInt();
	}

	public int winGetClientSizeHeight(String title) {
		Variant result = winVariant(title, "WinGetClientSizeHeight");
		return result.getInt();
	}

	private String safeString(Variant v) {
		return v.getvt() == Variant.VariantString ? v.getString() : "";
	}

	public String winGetHandle(String title, String text) {
		Variant result = winVariant(title, text, "WinGetHandle");
		return result.getString();
	}

	public String winGetHandle(String title) {
		Variant result = winVariant(title, "WinGetHandle");
		return result.getString();
	}

	public int winGetPosX(String title, String text) {
		return winVariant(title, text, "WinGetPosX").getInt();
	}

	public int winGetPosX(String title) {
		return winVariant(title, "WinGetPosX").getInt();
	}

	public int winGetPosY(String title, String text) {
		Variant result = winVariant(title, text, "WinGetPosY");
		return result.getInt();
	}

	public int winGetPosY(String title) {
		Variant result = winVariant(title, "WinGetPosY");
		return result.getInt();
	}

	public int winGetPosWidth(String title, String text) {
		Variant result = winVariant(title, text, "WinGetPosWidth");
		return result.getInt();
	}

	public int winGetPosWidth(String title) {
		Variant result = winVariant(title, "WinGetPosWidth");
		return result.getInt();
	}

	public int winGetPosHeight(String title, String text) {
		Variant result = winVariant(title, text, "WinGetPosHeight");
		return result.getInt();
	}

	public int winGetPosHeight(String title) {
		Variant result = winVariant(title, "WinGetPosHeight");
		return result.getInt();
	}

	public String winGetProcess(String title, String text) {
		Variant v = winVariant(title, text, "WinGetProcess");
		return v.getString();
	}

	public String winGetProcess(String title) {
		Variant v = winVariant(title, "WinGetProcess");
		return v.getString();
	}

	public int winGetState(String title, String text) {
		Variant result = winVariant(title, text, "WinGetState");
		return result.getInt();
	}

	public int winGetState(String title) {
		Variant result = winVariant(title, "WinGetState");
		return result.getInt();
	}

	public String winGetText(String title, String text) {
		Variant result = winVariant(title, text, "WinGetText");
		return result.getString();
	}

	public String winGetText(String title) {
		Variant result = winVariant(title, "WinGetText");
		return result.getString();
	}

	public String winGetTitle(String title, String text) {
		Variant result = winVariant(title, text, "WinGetTitle");
		if (result.getvt() == Variant.VariantString) {
			return result.getString();
		}
		return "";
	}

	public String winGetTitle(String title) {
		Variant result = winVariant(title, "WinGetTitle");
		if (result.getvt() == Variant.VariantString) {
			return result.getString();
		}
		return "";
	}

	public void winKill(String title, String text) {
		winVariant(title, text, "WinKill");
	}

	public void winKill(String title) {
		winVariant(title, "WinKill");
	}

	public String[][] winList(String title, String text) {
		Variant result = winVariant(title, text, "WinList");
		SafeArray arr = result.toSafeArray();
		int entries = arr.getInt(0, 0);
		String[][] resultArr = new String[2][entries + 1];
		for (int i = 0; i <= entries; i++) {
			resultArr[0][i] = arr.getString(0, i);
			resultArr[1][i] = arr.getString(1, i);
		}
		return resultArr;
	}

	public String[][] winList(String title) {
		Variant result = winVariant(title, "WinList");
		SafeArray arr = result.toSafeArray();
		int entries = arr.getInt(0, 0);
		String[][] resultArr = new String[2][entries + 1];
		for (int i = 0; i <= entries; i++) {
			resultArr[0][i] = arr.getString(0, i);
			resultArr[1][i] = arr.getString(1, i);
		}

		return resultArr;
	}

	public boolean winMenuSelectItem(String title, String text, String item) {
		Variant vTitle = new Variant(title);
		Variant vText = new Variant(text);
		Variant vItem = new Variant(item);
		Variant[] params = new Variant[] { vTitle, vText, vItem };
		Variant result = autoItX.invoke("WinMenuSelectItem", params);
		return oneToTrue(result.getInt());
	}

	public boolean winMenuSelectItem(String title, String text, String item,
			String item2) {
		Variant vTitle = new Variant(title);
		Variant vText = new Variant(text);
		Variant vItem = new Variant(item);
		Variant vItem2 = new Variant(item2);
		Variant[] params = new Variant[] { vTitle, vText, vItem, vItem2 };
		Variant result = autoItX.invoke("WinMenuSelectItem", params);
		return oneToTrue(result.getInt());
	}

	public boolean winMenuSelectItem(String title, String text, String item,
			String item2, String item3) {
		Variant vTitle = new Variant(title);
		Variant vText = new Variant(text);
		Variant vItem = new Variant(item);
		Variant vItem2 = new Variant(item2);
		Variant vItem3 = new Variant(item3);
		Variant[] params = new Variant[] { vTitle, vText, vItem, vItem2, vItem3 };
		Variant result = autoItX.invoke("WinMenuSelectItem", params);
		return oneToTrue(result.getInt());
	}

	public boolean winMenuSelectItem(String title, String text, String item,
			String item2, String item3, String item4, String item5, String item6,
			String item7) {
		Variant vTitle = new Variant(title);
		Variant vText = new Variant(text);
		Variant vItem = new Variant(item);
		Variant vItem2 = new Variant(item2);
		Variant vItem3 = new Variant(item3);
		Variant vItem4 = new Variant(item4);
		Variant vItem5 = new Variant(item5);
		Variant vItem6 = new Variant(item6);
		Variant vItem7 = new Variant(item7);
		Variant[] params = new Variant[] { vTitle, vText, vItem, vItem2, vItem3,
				vItem4, vItem5, vItem6, vItem7 };
		Variant result = autoItX.invoke("WinMenuSelectItem", params);
		return oneToTrue(result.getInt());
	}

	public void winMinimizeAll() {
		autoItX.invoke("WinMinimizeAll");
	}

	public void winMinimizeAllUndo() {
		autoItX.invoke("WinMinimizeAllUndo");
	}

	public void winMove(String title, String text, int x, int y, int width,
			int height) {
		Variant vTitle = new Variant(title);
		Variant vText = new Variant(text);
		Variant vX = new Variant(x);
		Variant vY = new Variant(y);
		Variant vWidth = new Variant(width);
		Variant vHeight = new Variant(height);
		Variant[] params = new Variant[] { vTitle, vText, vX, vY, vWidth, vHeight };
		autoItX.invoke("WinMove", params);
	}

	public void winMove(String title, String text, int x, int y) {
		Variant vTitle = new Variant(title);
		Variant vText = new Variant(text);
		Variant vX = new Variant(x);
		Variant vY = new Variant(y);
		Variant[] params = new Variant[] { vTitle, vText, vX, vY };
		autoItX.invoke("WinMove", params);
	}

	public void winSetOnTop(String title, String text, boolean isTopMost) {
		Variant vTitle = new Variant(title);
		Variant vText = new Variant(text);
		int flag = 0;
		if (isTopMost) {
			flag = 1;
		}
		Variant vFlag = new Variant(flag);
		Variant[] params = new Variant[] { vTitle, vText, vFlag };
		autoItX.invoke("WinSetOnTop", params);
	}

	public void winSetState(String title, String text, int flag) {
		Variant vTitle = new Variant(title);
		Variant vText = new Variant(text);
		Variant vFlag = new Variant(flag);
		Variant[] params = new Variant[] { vTitle, vText, vFlag };
		autoItX.invoke("WinSetState", params);
	}

	public void winSetTitle(String title, String text, String newtitle) {
		Variant vTitle = new Variant(title);
		Variant vText = new Variant(text);
		Variant vNewtitle = new Variant(newtitle);
		Variant[] params = new Variant[] { vTitle, vText, vNewtitle };
		autoItX.invoke("WinSetTitle", params);
	}

	public boolean winSetTrans(String title, String text, int transparency) {
		Variant vTitle = new Variant(title);
		Variant vText = new Variant(text);
		Variant vTransparency = new Variant(transparency);
		Variant[] params = new Variant[] { vTitle, vText, vTransparency };
		Variant result = autoItX.invoke("WinSetTrans", params);
		return result.getInt() != 0;
	}

	public boolean winWait(String title, String text, int timeout) {
		return winVariantBool(title, text, timeout, "WinWait");
	}

	public boolean winWait(String title) {
		return winVariantBool(title, "WinWait");
	}

	public boolean winWait(String title, String text) {
		return winVariantBool(title, text, "WinWait");
	}

	public boolean winWaitActive(String title, String text, int timeout) {
		return winVariantBool(title, text, timeout, "WinWaitActive");
	}

	public boolean winWaitActive(String title, String text) {
		return winVariantBool(title, text, "WinWaitActive");
	}

	public boolean winWaitActive(String title) {
		return winVariantBool(title, "WinWaitActive");
	}

	public boolean winWaitClose(String title, String text, int timeout) {
		return winVariantBool(title, text, timeout, "WinWaitClose");
	}

	public boolean winWaitClose(String title) {
		return winVariantBool(title, "WinWaitClose");
	}

	public boolean winWaitClose(String title, String text) {
		return winVariantBool(title, text, "WinWaitClose");
	}

	public boolean winWaitNoActive(String title, String text, int timeout) {
		return winVariantBool(title, text, timeout, "WinWaitNotActive");
	}

	public boolean winWaitNoActive(String title) {
		return winVariantBool(title, "WinWaitNotActive");
	}

	public boolean winWaitNoActive(String title, String text) {
		return winVariantBool(title, text, "WinWaitNotActive");
	}

	private boolean winVariantBool(String title, String text, int timeout,
			String function) {
		Variant vTitle = new Variant(title);
		Variant vText = new Variant(text);
		Variant vTimeout = new Variant(timeout);
		Variant[] params = new Variant[] { vTitle, vText, vTimeout };
		Variant result = autoItX.invoke(function, params);
		return oneToTrue(result.getInt());
	}

	private boolean winVariantBool(String title, String text, String function) {
		Variant vTitle = new Variant(title);
		Variant vText = new Variant(text);
		Variant[] params = new Variant[] { vTitle, vText };
		Variant result = autoItX.invoke(function, params);
		return oneToTrue(result.getInt());
	}

	private boolean winVariantBool(String title, String function) {
		Variant vTitle = new Variant(title);
		Variant[] params = new Variant[] { vTitle };
		Variant result = autoItX.invoke(function, params);
		return oneToTrue(result.getInt());
	}

	public String statusbarGetText(String title) {
		return autoItX.invoke("StatusbarGetText", title).getString();
	}

	private boolean oneToTrue(int i) {
		return i == 1;
	}

	private boolean oneToTrue(Variant v) {
		if (v.getvt() == Variant.VariantInt || v.getvt() == Variant.VariantShort) {
			return v.getInt() == 1;
		}

		return false;
	}
}
