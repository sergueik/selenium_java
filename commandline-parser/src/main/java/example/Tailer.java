package example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// based on: https://docs.oracle.com/javase/6/docs/api/java/io/RandomAccessFile.html
//see also: http://www.java2s.com/Tutorial/Java/0180__File/ReadingaFileintoaByteArrayreadstheentirecontentsofafileintoabytearray.htm
public class Tailer {

	private String filePath = null;
	private int offset = -1;
	private byte[] bytes;
	private String data;
	private RandomAccessFile file;

	public static void main(String[] args) {
		if (args.length == 2) {
			Tailer tailer = new Tailer();
			tailer.filePath = args[0];
			tailer.offset = Integer.parseInt(args[1]);
			tailer.tail();
			System.out.println(tailer.data);
			// System.out.println(tailer.data.length());
			// System.out.println(tailer.bytes.length);
			System.out.println(
					String.format("RPM(1): %d /%d ", tailer.rpm(), tailer.rpm(false)));
			String fragment = "<version>";
			System.out.println(
					String.format("RPM(%s): %d", fragment, tailer.rpm(fragment)));

		}
	}

	public int rpm() {
		int result = 0;
		for (int cnt = 0; cnt != bytes.length; cnt++)
			if (bytes[cnt] == '\n')
				result++;
		return result;
	}

	// NOTE: flag is not used
	public int rpm(boolean flag) {
		int result = data.split("\r?\n").length;
		return result;
	}

	public int rpm(String fragment) {
		int result = 0;
		String[] lines = data.split("\r?\n");
		Pattern pattern = Pattern.compile(Pattern.quote(fragment),
				Pattern.CASE_INSENSITIVE);

		for (int cnt = 0; cnt != lines.length; cnt++)
			if (pattern.matcher(lines[cnt]).find())
				result++;
		return result;
	}

	public void tail() {
		try {
			file = new RandomAccessFile(filePath, "r");
			int length = (int) file.length();
			file.seek(offset);
			int numBytesToRead = length - offset;
			if (length > Integer.MAX_VALUE) {
				System.out.println("File is too large");
			}

			System.err.println(String.format("Allocate %d bytes", numBytesToRead));
			bytes = new byte[(int) numBytesToRead];

			int readOffset = 0;
			int numRead = 0;
			// numBytesToRead = 1000;
			// int totalRead = 0;
			while (numRead >= 0) {
				System.err.println(String.format("Reading %d %d", offset + readOffset,
						numBytesToRead));
				// java.lang.IndexOutOfBoundsException
				numRead = file.read(bytes, readOffset, numBytesToRead);
				if (numRead == 0)
					break;
				readOffset += numRead;
				if (numBytesToRead > length - (offset + readOffset)) {
					System.err.println(String.format("Remaining %d bytes",
							length - (offset + readOffset)));
					System.err.println(
							String.format("Read %d bytes in the buffer already", readOffset));
					numBytesToRead = length - (offset + readOffset);
				}
			}

			if (readOffset < bytes.length) {
				throw new IOException("Could not completely read file " + filePath);
			}
			file.close();
			data = new String(bytes);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
