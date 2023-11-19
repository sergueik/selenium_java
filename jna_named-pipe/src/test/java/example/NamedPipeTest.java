package example;

// based on: https://github.com/java-native-access/jna/blob/master/contrib/platform/test/com/sun/jna/platform/win32/Kernel32NamedPipeTest.java

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
import java.util.Random;

public class NamedPipeTest {

	private String pipeName;
	private String name = "demo";
	private final int MAX_BUFFER_SIZE = 1024;

	// https://github.com/java-native-access/jna/blob/master/contrib/platform/src/com/sun/jna/platform/win32/Kernel32.java#L2045
	// see also ConnectNamedPipe. NOTE: needs HANDLE
	// https://github.com/java-native-access/jna/blob/master/contrib/platform/src/com/sun/jna/platform/win32/Kernel32.java#L2015
	@Test
	public void test2() {
		WinBase.OVERLAPPED lpOverlapped;
		Random random = new Random();
		pipeName = "\\\\.\\pipe\\" + name + random.nextInt();
		HANDLE hNamedPipe = Kernel32.INSTANCE.CreateNamedPipe(pipeName,
				WinBase.PIPE_ACCESS_DUPLEX, // dwOpenMode
				WinBase.PIPE_TYPE_BYTE | WinBase.PIPE_READMODE_BYTE | WinBase.PIPE_WAIT, // dwPipeMode
				1, // nMaxInstances,
				Byte.MAX_VALUE, // nOutBufferSize,
				Byte.MAX_VALUE, // nInBufferSize,
				1000, // nDefaultTimeOut,
				null); // lpSecurityAttributes
		assertThat(hNamedPipe, is(not(equalTo(WinBase.INVALID_HANDLE_VALUE))));
		// NOTE: for existing pipe
		// Expected: is not <const@0xffffffff>
		// but: was <const@0xffffffff>

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

			String expMessage = Thread.currentThread().getName() + " says test2";
			byte[] expData = expMessage.getBytes();
			IntByReference lpNumberOfBytesWritten = new IntByReference(0);
			Kernel32.INSTANCE.WriteFile(hNamedPipe, expData, expData.length,
					lpNumberOfBytesWritten, null);
			// assertEquals("Mismatched write buffer size", expData.length,
			// lpNumberOfBytesWritten.getValue());
			byte[] readBuffer = new byte[MAX_BUFFER_SIZE];
			IntByReference lpNumberOfBytesRead = new IntByReference(0);
			Kernel32.INSTANCE.ReadFile(hNamedPipe, readBuffer, readBuffer.length,
					lpNumberOfBytesRead, null);

			int readSize = lpNumberOfBytesRead.getValue();
			assertThat(readSize, greaterThan(0));

			String actMessage = new String(readBuffer, 0, readSize);
			assertThat("Mismatched server data", actMessage, is(expMessage));
			// https://github.com/java-native-access/jna/blob/master/contrib/platform/src/com/sun/jna/platform/win32/Kernel32.java#L2056
			Kernel32.INSTANCE.DisconnectNamedPipe(hNamedPipe);
		} finally {
			Kernel32.INSTANCE.CloseHandle(hNamedPipe);
		}
	}

	// CallNamedPipe
	// https://github.com/java-native-access/jna/blob/master/contrib/platform/src/com/sun/jna/platform/win32/Kernel32.java#L1994
	@Test
	public void test1() {
		pipeName = "\\\\.\\pipe\\" + name;
		String message = Thread.currentThread().getName() + " says test1";
		// The data to be written to the pipe
		byte[] readBuffer = message.getBytes();
		byte[] outBuffer = new byte[MAX_BUFFER_SIZE];
		IntByReference lpNumberOfBytesRead = new IntByReference(0);
		boolean status = Kernel32.INSTANCE.CallNamedPipe(pipeName, readBuffer,
				readBuffer.length, // nInBufferSize
				outBuffer, MAX_BUFFER_SIZE, // nOutBufferSize
				lpNumberOfBytesRead, 1000 // nTimeOut
		);
		assertThat("Bad status", status, is(true));
		int readSize = lpNumberOfBytesRead.getValue();
		// assertThat(readSize, is(0));
		assertThat(readSize, greaterThan(0));

		String message2 = new String(readBuffer, 0, readSize);
		System.err.println(message2);

	}

}
