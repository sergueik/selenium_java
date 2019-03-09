/*
 * Copyright 2018 midorlo.
 * Updated 2019 by sergueik
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

/**
 * @via https://www.autoitscript.com/forum/topic/72905-c-use-of-the-dll-some-idears-for-you/
 * @authors midorlo, sergueik
 */
public class Constants {

	public static final int AU3_INTDEFAULT = -2147483647; // "Default" value for
																												// _some_ final int
																												// parameters (largest
																												// negative number)
	public static final int ERROR = 1;
	public static final int SW_HIDE = 2;
	public static final int SW_MAXIMIZE = 3;
	public static final int SW_MINIMIZE = 4;
	public static final int SW_RESTORE = 5;
	public static final int SW_SHOW = 6;
	public static final int SW_SHOWDEFAULT = 7;
	public static final int SW_SHOWMAXIMIZED = 8;
	public static final int SW_SHOWMINIMIZED = 9;
	public static final int SW_SHOWMINNOACTIVE = 10;
	public static final int SW_SHOWNA = 11;
	public static final int SW_SHOWNOACTIVATE = 12;
	public static final int SW_SHOWNORMAL = 13;
	public static final int VERSION = 109;

	// The AutoIt v3 functions return values are unusual
	// for a java program
	// e.g.
	// https://www.autoitscript.com/autoit3/docs/functions/WinClose.htm
	// https://www.autoitscript.com/autoit3/docs/functions/WinKill.htm
	// etc. illustrate the pattern:
	// Success: 1.
	// Failure: 0 if the window is not found ,
	// cannot be activated (gives focus
	// to) for WinActivate
	// some functions only return the success value of 1.

	// https://www.autoitscript.com/autoit3/docs/appendix/AutoItConstants.htm
	// https://github.com/310ken1/AutoItSciTEj/blob/master/language/au3/Include/AutoItConstants.au3
	// TODO: rename constants used in this class
	public static final int AU3_SUCCESS = 1;
	public static final int AU3_FAILURE = 0;
	public static final int AU3_SEND_DEFAULT = 0;
	public static final int AU3_SEND_RAW = 1;
	public static final String DLL = (System.getProperty("os.arch")
			.equals("amd64") ? "AutoItX3_x64.dll" : "AutoItX3.dll");
}
