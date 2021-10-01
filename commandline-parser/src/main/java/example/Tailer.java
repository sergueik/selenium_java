package example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

// based on: http://www.java2s.com/Tutorial/Java/0180__File/ReadingaFileintoaByteArrayreadstheentirecontentsofafileintoabytearray.htm
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
			System.out.println(tailer.data.length());
			System.out.println(tailer.bytes.length);
		}
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
