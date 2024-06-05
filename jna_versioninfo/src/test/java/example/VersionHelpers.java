package example;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinDef.WORD;
import com.sun.jna.platform.win32.WinNT.OSVERSIONINFOEX;

// origin: https://github.com/java-native-access/jna/blob/master/contrib/platform/src/com/sun/jna/platform/win32/VersionHelpers.java
// NOTE: the origin code is not published anywhere
public class VersionHelpers {

	public static boolean IsWindowsVersionOrGreater(int wMajorVersion,
			int wMinorVersion, int wServicePackMajor) {
		OSVERSIONINFOEX osvi = new OSVERSIONINFOEX();
		osvi.dwOSVersionInfoSize = new DWORD(osvi.size());
		osvi.dwMajorVersion = new DWORD(wMajorVersion);
		osvi.dwMinorVersion = new DWORD(wMinorVersion);
		osvi.wServicePackMajor = new WORD(wServicePackMajor);

		long dwlConditionMask = 0;
		dwlConditionMask = Kernel32.INSTANCE.VerSetConditionMask(dwlConditionMask,
				WinNT.VER_MAJORVERSION, (byte) WinNT.VER_GREATER_EQUAL);
		dwlConditionMask = Kernel32.INSTANCE.VerSetConditionMask(dwlConditionMask,
				WinNT.VER_MINORVERSION, (byte) WinNT.VER_GREATER_EQUAL);
		dwlConditionMask = Kernel32.INSTANCE.VerSetConditionMask(dwlConditionMask,
				WinNT.VER_SERVICEPACKMAJOR, (byte) WinNT.VER_GREATER_EQUAL);

		return Kernel32.INSTANCE.VerifyVersionInfoW(osvi, WinNT.VER_MAJORVERSION
				| WinNT.VER_MINORVERSION | WinNT.VER_SERVICEPACKMAJOR,
				dwlConditionMask);
	}

	public static boolean IsWindowsXPOrGreater() {
		return IsWindowsVersionOrGreater((byte) (Kernel32.WIN32_WINNT_WINXP >>> 8),
				(byte) Kernel32.WIN32_WINNT_WINXP, 0);
	}

	public static boolean IsWindowsXPSP1OrGreater() {
		return IsWindowsVersionOrGreater((byte) (Kernel32.WIN32_WINNT_WINXP >>> 8),
				(byte) Kernel32.WIN32_WINNT_WINXP, 1);
	}

	public static boolean IsWindowsXPSP2OrGreater() {
		return IsWindowsVersionOrGreater((byte) (Kernel32.WIN32_WINNT_WINXP >>> 8),
				(byte) Kernel32.WIN32_WINNT_WINXP, 2);
	}

	/**
	 * @return true if the current OS version matches, or is greater than, the
	 *         Windows XP with Service Pack 3 (SP3) version.
	 */
	public static boolean IsWindowsXPSP3OrGreater() {
		return IsWindowsVersionOrGreater((byte) (Kernel32.WIN32_WINNT_WINXP >>> 8),
				(byte) Kernel32.WIN32_WINNT_WINXP, 3);
	}

	/**
	 * @return true if the current OS version matches, or is greater than, the
	 *         Windows Vista version.
	 */
	public static boolean IsWindowsVistaOrGreater() {
		return IsWindowsVersionOrGreater((byte) (Kernel32.WIN32_WINNT_VISTA >>> 8),
				(byte) Kernel32.WIN32_WINNT_VISTA, 0);
	}

	/**
	 * @return true if the current OS version matches, or is greater than, the
	 *         Windows Vista with Service Pack 1 (SP1) version.
	 */
	public static boolean IsWindowsVistaSP1OrGreater() {
		return IsWindowsVersionOrGreater((byte) (Kernel32.WIN32_WINNT_VISTA >>> 8),
				(byte) Kernel32.WIN32_WINNT_VISTA, 1);
	}

	/**
	 * @return true if the current OS version matches, or is greater than, the
	 *         Windows Vista with Service Pack 2 (SP2) version.
	 */
	public static boolean IsWindowsVistaSP2OrGreater() {
		return IsWindowsVersionOrGreater((byte) (Kernel32.WIN32_WINNT_VISTA >>> 8),
				(byte) Kernel32.WIN32_WINNT_VISTA, 2);
	}

	/**
	 * @return true if the current OS version matches, or is greater than, the
	 *         Windows 7 version.
	 */
	public static boolean IsWindows7OrGreater() {
		return IsWindowsVersionOrGreater((byte) (Kernel32.WIN32_WINNT_WIN7 >>> 8),
				(byte) Kernel32.WIN32_WINNT_WIN7, 0);
	}

	/**
	 * @return true if the current OS version matches, or is greater than, the
	 *         Windows 7 with Service Pack 1 (SP1) version.
	 */
	public static boolean IsWindows7SP1OrGreater() {
		return IsWindowsVersionOrGreater((byte) (Kernel32.WIN32_WINNT_WIN7 >>> 8),
				(byte) Kernel32.WIN32_WINNT_WIN7, 1);
	}

	/**
	 * @return true if the current OS version matches, or is greater than, the
	 *         Windows 8 version.
	 */
	public static boolean IsWindows8OrGreater() {
		return IsWindowsVersionOrGreater((byte) (Kernel32.WIN32_WINNT_WIN8 >>> 8),
				(byte) Kernel32.WIN32_WINNT_WIN8, 0);
	}

	/**
	 * @return true if the current OS version matches, or is greater than, the
	 *         Windows 8.1 version. For Windows 8.1 and/or Windows 10,
	 *         {@link #IsWindows8Point1OrGreater} returns false unless the
	 *         application contains a manifest that includes a compatibility
	 *         section that contains the GUIDs that designate Windows 8.1 and/or
	 *         Windows 10.
	 */
	public static boolean IsWindows8Point1OrGreater() {
		return IsWindowsVersionOrGreater(
				(byte) (Kernel32.WIN32_WINNT_WINBLUE >>> 8),
				(byte) Kernel32.WIN32_WINNT_WINBLUE, 0);
	}

	/**
	 * @return true if the current OS version matches, or is greater than, the
	 *         Windows 10 version. For Windows 10,
	 *         {@link #IsWindows8Point1OrGreater} returns false unless the
	 *         application contains a manifest that includes a compatibility
	 *         section that contains the GUID that designates Windows 10.
	 */
	public static boolean IsWindows10OrGreater() {
		return IsWindowsVersionOrGreater((byte) (Kernel32.WIN32_WINNT_WIN10 >>> 8),
				(byte) Kernel32.WIN32_WINNT_WIN10, 0);
	}

	/**
	 * Applications that need to distinguish between server and client versions
	 * of Windows should call this function.
	 *
	 * @return true if the current OS is a Windows Server release.
	 */
	public static boolean IsWindowsServer() {
		// This should properly be OSVERSIONINFOEXW which is not defined in JNA.
		// The OSVERSIONINFOEX structure in JNA is the (W) Unicode-compliant
		// version.
		OSVERSIONINFOEX osvi = new OSVERSIONINFOEX();
		osvi.dwOSVersionInfoSize = new DWORD(osvi.size());
		osvi.wProductType = WinNT.VER_NT_WORKSTATION;

		long dwlConditionMask = Kernel32.INSTANCE.VerSetConditionMask(0,
				WinNT.VER_PRODUCT_TYPE, (byte) WinNT.VER_EQUAL);

		return !Kernel32.INSTANCE.VerifyVersionInfoW(osvi, WinNT.VER_PRODUCT_TYPE,
				dwlConditionMask);
	}
}
