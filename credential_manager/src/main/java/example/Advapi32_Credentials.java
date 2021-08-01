package example;

import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.win32.StdCallLibrary;

public interface Advapi32_Credentials extends StdCallLibrary {
	@SuppressWarnings("deprecation")
	Advapi32_Credentials INSTANCE = Native.loadLibrary("advapi32", Advapi32_Credentials.class);

	/*
	 * BOOL CredEnumerate( _In_ LPCTSTR Filter, _In_ DWORD Flags, _Out_ DWORD
	 * *Count, _Out_ PCREDENTIAL **Credentials)
	 */
	boolean CredEnumerateW(String filter, int flags, IntByReference count, PointerByReference pref);
}
