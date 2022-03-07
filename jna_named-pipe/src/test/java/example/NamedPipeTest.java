package example;

//based on:
//Copyright (c) 2007 Timothy Wall, All Rights Reserved
//

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.junit.Test;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.platform.win32.WinDef.ULONGByReference;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.ptr.IntByReference;

/**
 * @author lgoldstein
 */
public class NamedPipeTest {

	private String name = "demo";

	@Test
	public void testNamedPipeServerAPI() {
		String pipeName = "\\\\.\\pipe\\" + name;
		HANDLE hNamedPipe = Kernel32.INSTANCE.CreateNamedPipe(pipeName,
				WinBase.PIPE_ACCESS_DUPLEX, // dwOpenMode
				WinBase.PIPE_TYPE_BYTE | WinBase.PIPE_READMODE_BYTE | WinBase.PIPE_WAIT, // dwPipeMode
				1, // nMaxInstances,
				Byte.MAX_VALUE, // nOutBufferSize,
				Byte.MAX_VALUE, // nInBufferSize,
				1000, // nDefaultTimeOut,
				null); // lpSecurityAttributes
		assertThat(hNamedPipe, is(not(equalTo(WinBase.INVALID_HANDLE_VALUE))));

		try {
			IntByReference lpFlags = new IntByReference(0);
			IntByReference lpOutBuffferSize = new IntByReference(0);
			IntByReference lpInBufferSize = new IntByReference(0);
			IntByReference lpMaxInstances = new IntByReference(0);

			Kernel32.INSTANCE.GetNamedPipeInfo(hNamedPipe, lpFlags, lpOutBuffferSize,
					lpInBufferSize, lpMaxInstances);

			ULONGByReference ServerProcessId = new ULONGByReference();
			Kernel32.INSTANCE.GetNamedPipeServerProcessId(hNamedPipe,
					ServerProcessId);

			ULONGByReference ServerSessionId = new ULONGByReference();
			Kernel32.INSTANCE.GetNamedPipeServerSessionId(hNamedPipe,
					ServerSessionId);

			String expMessage = Thread.currentThread().getName() + " says hello";
			byte[] expData = expMessage.getBytes();
			IntByReference lpNumberOfBytesWritten = new IntByReference(0);
			Kernel32.INSTANCE.WriteFile(hNamedPipe, expData, expData.length,
					lpNumberOfBytesWritten, null);
			// assertEquals("Mismatched write buffer size", expData.length,
			// lpNumberOfBytesWritten.getValue());
			final int MAX_BUFFER_SIZE = 1024;
			byte[] readBuffer = new byte[MAX_BUFFER_SIZE];
			IntByReference lpNumberOfBytesRead = new IntByReference(0);
			Kernel32.INSTANCE.ReadFile(hNamedPipe, readBuffer, readBuffer.length,
					lpNumberOfBytesRead, null);

			int readSize = lpNumberOfBytesRead.getValue();
			assertThat(readSize, greaterThan(0));

			String actMessage = new String(readBuffer, 0, readSize);
			assertThat("Mismatched server data", actMessage, is(expMessage));

			Kernel32.INSTANCE.DisconnectNamedPipe(hNamedPipe);
		} finally {
			Kernel32.INSTANCE.CloseHandle(hNamedPipe);
		}
	}

}
