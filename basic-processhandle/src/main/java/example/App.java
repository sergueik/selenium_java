package example;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.lang.reflect.Field;
import java.util.Optional;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

// https://en.wikipedia.org/wiki/Java_Native_Access
import com.sun.jna.Library;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.NativeMapped;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
// https://github.com/java-native-access/jna/blob/master/contrib/platform/src/com/sun/jna/platform/win32/Advapi32Util.java
import com.sun.jna.platform.win32.Advapi32Util;

import com.sun.jna.platform.win32.VerRsrc.VS_FIXEDFILEINFO;
// https://github.com/java-native-access/jna/blob/master/contrib/platform/src/com/sun/jna/platform/win32/WinReg.java
import com.sun.jna.platform.win32.WinReg;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.win32.W32APIFunctionMapper;
import com.sun.jna.win32.W32APITypeMapper;

/**
 * based on Baeldung's example at https://www.baeldung.com/java-9-process-api
 */

public class App {
	private static Logger logger = LogManager.getLogger(App.class.getName());
	private static CommandLineParser commandLineparser = new DefaultParser();
	private static CommandLine commandLine = null;
	private final static Options options = new Options();
	private final static List<List<Float>> data = new ArrayList<>();

	public static void help() {
		System.exit(1);
	}

	public static void main(String[] args) {
		options.addOption("h", "help", false, "Help");
		options.addOption("d", "debug", false, "Debug");
		options.addOption("a", "action", true, "Action");
		options.addOption("p", "pid", true, "Pid");
		try {
			commandLine = commandLineparser.parse(options, args);
			if (commandLine.hasOption("h")) {
				help();
			}
			String action = commandLine.getOptionValue("action");
			if (action == null) {
				System.err.println("Missing required argument: action");

			}
			if (action.equals("list")) {
				infoOfLiveProcesses();
			}
			/*
			 * if (action.equals("current")) { Process process =
			 * Process.getCurrentProcess(); getWindowsProcessId(process,
			 * logger); logger.info(process.toHandle().pid()); }
			 */
			if (action.equals("check")) {
				String resource = commandLine.getOptionValue("pid");
				if (resource == null) {
					System.err.println("Missing required argument: pid");
				} else {
					Integer pid = Integer.parseInt(resource);

					Optional<ProcessHandle> result = ProcessHandle.of((long) pid);
					ProcessHandle processHandle = (result.isPresent()) ? result.get() : null;
					logger.info(processHandle);
					Boolean status = (processHandle == null) ? false : processHandle.isAlive();
					logger.info("Process pid: " + pid + " is: "
							+ (status ? "alive" + "(" + processHandle.info().command().get() + ")" : "not alive"));
				}
			}
		} catch (ParseException e) {
		}
	}

	private static void infoOfLiveProcesses() {
		Stream<ProcessHandle> liveProcesses = ProcessHandle.allProcesses();
		liveProcesses.filter(ProcessHandle::isAlive).forEach(ph -> {
			logger.info("PID: " + ph.pid());
			logger.info("Instance: " + ph.info().startInstant());
			logger.info("User: " + ph.info().user());
		});
	}

	// https://docs.oracle.com/javase/9/docs/api/java/lang/ProcessHandle.html
	// https://docs.oracle.com/javase/9/docs/api/java/lang/ProcessHandle.Info.html
	// https://www.tabnine.com/code/java/methods/com.sun.jna.platform.win32.Kernel32/GetProcessId

	// https://www.tabnine.com/code/java/methods/com.sun.jna.platform.win32.Kernel32/GetProcessId
	private static Long getWindowsProcessId(final Process process, final Logger logger) {
		/* determine the pid on windows plattforms */
		try {
			Field f = process.getClass().getDeclaredField("handle");
			f.setAccessible(true);
			long handl = f.getLong(process);
			Kernel32 kernel = Kernel32.INSTANCE;
			HANDLE handle = new HANDLE();
			handle.setPointer(Pointer.createConstant(handl));
			int ret = kernel.GetProcessId(handle);
			logger.debug("Detected pid: {}", ret);
			return Long.valueOf(ret);
		} catch (final IllegalAccessException | NoSuchFieldException nsfe) {
			logger.debug("Could not find PID for child process due to {}", nsfe);
		}
		return null;
	}

}
