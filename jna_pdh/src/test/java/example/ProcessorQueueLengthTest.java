package example;
/**
 * Copyright 2022,2024 Serguei Kouzmine
 */

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import java.io.PrintStream;

import org.junit.Test;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.Pdh;
import com.sun.jna.platform.win32.Pdh.PDH_COUNTER_PATH_ELEMENTS;
import com.sun.jna.platform.win32.Pdh.PDH_RAW_COUNTER;
import com.sun.jna.platform.win32.PdhMsg;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinDef.DWORDByReference;
import com.sun.jna.platform.win32.WinError;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.platform.win32.WinNT.HANDLEByReference;
import com.sun.jna.platform.win32.AbstractWin32TestSupport;

@SuppressWarnings({ "deprecation", "unused" })
public class ProcessorQueueLengthTest extends AbstractWin32TestSupport {
	private static final Pdh pdh = Pdh.INSTANCE;

	private static void showRawCounterData(PrintStream out, String counterName, PDH_RAW_COUNTER rawCounter) {
		out.append('\t').append(counterName).append(" ").append(String.valueOf(rawCounter.TimeStamp.toDate()))
				.append(" 1st=").append(String.valueOf(rawCounter.FirstValue)).append(" 2nd=")
				.append(String.valueOf(rawCounter.SecondValue)).append(" multi=")
				.append(String.valueOf(rawCounter.MultiCount)).println();
	}

	private static String makeCounterPath(Pdh pdh, PDH_COUNTER_PATH_ELEMENTS pathElements) {
		DWORDByReference pcchBufferSize = new DWORDByReference();
		int status = pdh.PdhMakeCounterPath(pathElements, null, pcchBufferSize, 0);
		assertThat("Unexpected status code: 0x" + Integer.toHexString(status), status, is(PdhMsg.PDH_MORE_DATA));

		DWORD bufSize = pcchBufferSize.getValue();
		int numChars = bufSize.intValue();
		assertThat("Bad required buffer size: " + numChars, numChars, greaterThan(0));

		char[] szFullPathBuffer = new char[numChars + 1 /* the \0 */];
		pcchBufferSize.setValue(new DWORD(szFullPathBuffer.length));
		assertThat("Error in PdhMakeCounterPath",
				pdh.PdhMakeCounterPath(pathElements, szFullPathBuffer, pcchBufferSize, 0), is(WinError.ERROR_SUCCESS));
		return Native.toString(szFullPathBuffer);
	}

	@Test
	public void test() {
		PDH_COUNTER_PATH_ELEMENTS elems = new PDH_COUNTER_PATH_ELEMENTS();

		elems.szObjectName = "System";
		elems.szInstanceName = null;
		elems.szCounterName = "Processor Queue Length";
		String counterName = makeCounterPath(pdh, elems);

		HANDLEByReference ref = new HANDLEByReference();
		assertThat("Error in PdhOpenQuery", pdh.PdhOpenQuery(null, null, ref), is(WinError.ERROR_SUCCESS));

		HANDLE hQuery = ref.getValue();
		try {
			ref.setValue(null);
			assertThat("Error in PdhAddEnglishCounter", pdh.PdhAddEnglishCounter(hQuery, counterName, null, ref),
					is(WinError.ERROR_SUCCESS));

			HANDLE hCounter = ref.getValue();
			try {

				assertThat("Error in PdhCollectQueryData call", pdh.PdhCollectQueryData(hQuery),
						is(WinError.ERROR_SUCCESS));
				DWORDByReference lpdwType = new DWORDByReference();
				PDH_RAW_COUNTER rawCounter = new PDH_RAW_COUNTER();
				assertThat("Error in PdhGetRawCounterValue", pdh.PdhGetRawCounterValue(hCounter, lpdwType, rawCounter),
						is(WinError.ERROR_SUCCESS));
				assertThat("unexected Counter data status", rawCounter.CStatus, is(WinError.ERROR_SUCCESS));
				showRawCounterData(System.out, counterName, rawCounter);
			} finally {
				assertThat("Error in PdhRemoveCounter", pdh.PdhRemoveCounter(hCounter), is(WinError.ERROR_SUCCESS));
			}
		} finally {
			assertThat("Error in PdhCloseQuery", pdh.PdhCloseQuery(hQuery), is(WinError.ERROR_SUCCESS));
		}
	}
}
