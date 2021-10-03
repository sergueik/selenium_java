package example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// based on: https://docs.oracle.com/javase/6/docs/api/java/io/RandomAccessFile.html
// see also: http://www.java2s.com/Tutorial/Java/0180__File/ReadingaFileintoaByteArrayreadstheentirecontentsofafileintoabytearray.htm
public class Tailer {

	private boolean debug = true;
	private String filePath = null;

	public void setFilePath(String value) {
		filePath = value;
	}

	private int offset = -1;
	private int length = -1;

	public int getLength() {
		return length;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int value) {
		offset = value;
	}

	private byte[] bytes;
	private String data;
	private RandomAccessFile file;

	public void setFile(RandomAccessFile value) {
		file = value;
	}

	public static void main(String[] args) {
		if (args.length >= 2) {
			Tailer tailer = new Tailer();
			tailer.setFilePath(args[0]);
			tailer.setOffset(Integer.parseInt(args[1]));
			tailer.tail();
			System.out.println(tailer.data);
			// System.out.println(tailer.data.length());
			// System.out.println(tailer.bytes.length);
			int length = tailer.getLength();
			System.out.println("length: " + length);
			System.out.println(
					String.format("rpm: %d /%d ", tailer.rpm(), tailer.rpm(false)));
			String fragment = args.length == 2 ? "version>" : args[2];
			System.out.println(
					String.format("rpm	(%s): %d", fragment, tailer.rpm(fragment)));
			tailer.setOffset(length);
			tailer.tail();
			System.out.println(tailer.data);
			// System.out.println(tailer.data.length());
			// System.out.println(tailer.bytes.length);
			System.out.println(
					String.format("RPM(1): %d /%d ", tailer.rpm(), tailer.rpm(false)));

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
		if (debug)
			System.err.println(String.format("data: \"%s\"", data));
		int result = (data.isEmpty()) ? 0 : data.split("\r?\n").length;
		if (debug)
			System.err.println(String.format("result: \"%d\"", result));
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
			if (file == null) {
				file = new RandomAccessFile(filePath, "r");
			}
			length = (int) file.length();
			if (debug) {
				System.err.println(String.format("offset %d lenth %d", offset, length));
			}

			if (offset < length) {
				file.seek(offset);
				int numBytesToRead = length - offset;
				if (length > Integer.MAX_VALUE) {
					System.out.println("File is too large");
				}
				if (debug)
					System.err
							.println(String.format("Allocate %d bytes", numBytesToRead));
				bytes = new byte[(int) numBytesToRead];

				int readOffset = 0;
				int numRead = 0;
				// numBytesToRead = 1000;
				// int totalRead = 0;
				while (numRead >= 0) {
					if (debug)
						System.err.println(String.format("Reading %d %d",
								offset + readOffset, numBytesToRead));
					// java.lang.IndexOutOfBoundsException
					numRead = file.read(bytes, readOffset, numBytesToRead);
					if (numRead == 0)
						break;
					readOffset += numRead;
					if (numBytesToRead > length - (offset + readOffset)) {
						if (debug)
							System.err.println(String.format("Remaining %d bytes",
									length - (offset + readOffset)));
						System.err.println(String
								.format("Read %d bytes in the buffer already", readOffset));
						numBytesToRead = length - (offset + readOffset);
					}
				}

				if (readOffset < bytes.length) {
					throw new IOException("Could not completely read file " + filePath);
				}
			} else {
				if (debug)
					System.err.println("clearing bytes and data");
				bytes = new byte[] {};
				data = null;
			}
			file.close();
			file = null;
			data = new String(bytes);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
