/*
 * Tool.java    August 15, 2007, 10:56 AM
 *
 * Copyright 2007, FreeInternals.org. All rights reserved.
 * Use is subject to license terms.
 */
package org.freeinternals.javaclassviewer.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Utility class.
 * <p>
 * The code in this file may be refactored later.
 *
 * @author Amos Shi
 * @since JDK 6.0
 */
public final class Tool {

    private Tool() {
    }

    /**
     * Read class byte array from {@code zipFile} of entry {@code zipEntry}
     *
     * @param zipFile The {@code jar} or {@code zip} file
     * @param zipEntry The entry to be read
     * @return Byte array of the class file, or {@code null} if error happened.
     * @throws java.io.IOException Error happened when reading the zip file
     */
    public static byte[] readClassFile(final ZipFile zipFile, final ZipEntry zipEntry) throws IOException {
        if (zipFile == null) {
            throw new IllegalArgumentException("Parameter 'zipFile' is null.");
        }
        if (zipEntry == null) {
            throw new IllegalArgumentException("Parameter 'zipEntry' is null.");
        }

        final long fileSize = zipEntry.getSize();
        final byte contents[] = new byte[(int) fileSize];
        ByteBuffer byteBuf = ByteBuffer.allocate(contents.length);
        InputStream is = null;
        int bytesRead = 0;
        int bytesAll = 0;

        try {
            is = zipFile.getInputStream(zipEntry);
            while(true){
                bytesRead = is.read(contents);
                if (bytesRead != -1){
                    byteBuf.put(contents, 0, bytesRead);
                    bytesAll += bytesRead;
                }else{
                    break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Tool.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }

        if (bytesAll == fileSize){
            return byteBuf.array();
        } else {
            throw new IOException(String.format(
                    "File read error: expected = %d bytes, result = %d bytes.\nzipFile = %s\nzipEntry = %s",
                    fileSize,
                    byteBuf.array().length,
                    zipFile.getName(),
                    zipEntry.getName()));
        }
    }

    /**
     * Read class byte array from the {@code class} file {@code file}
     *
     * @param file The class file
     * @return Byte array of the class file, or {@code null} if error happened.
     */
    public static byte[] readClassFile(final File file) {
        byte[] contents = null;

        FileInputStream input = null;

        final long fileLength = file.length();
        final int fileLengthInt = (int) (fileLength % Integer.MAX_VALUE);

        contents = new byte[fileLengthInt];
        final ByteBuffer byteBuf = ByteBuffer.allocate(contents.length);
        int bytesRead;
        int bytesAll = 0;
        

        try {
            input = new FileInputStream(file);

            while(true){
                bytesRead = input.read(contents);
                if (bytesRead != -1){
                    byteBuf.put(contents, 0, bytesRead);
                    bytesAll += bytesRead;
                }else{
                    break;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Tool.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Tool.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Tool.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (bytesAll == fileLengthInt){
            return byteBuf.array();
        }else{
            return null;
        }
    }

    /**
     * Get a string for the {@code hex} view of byte array {@code data}.
     *
     * @param data Byte array
     * @return A string representing the {@code hex} version of {@code data}
     */
    public static String getByteDataHexView(final byte[] data) {
        if (data == null) {
            return "";
        }
        if (data.length < 1) {
            return "";
        }

        final StringBuilder sb = new StringBuilder(data.length * 5);
        final int length = data.length;
        int i;
        int lineBreakCounter = 0;
        for (i = 0; i < length; i++) {
            sb.append(String.format(" %02X", data[i]));
            lineBreakCounter++;
            if (lineBreakCounter == 16) {
                sb.append('\n');
                lineBreakCounter = 0;
            }
        }
        sb.append('\n');

        return sb.toString();
    }
}
