/*
 * Tool.java    August 21, 2010, 23:07 AM
 *
 * Copyright 2007, FreeInternals.org. All rights reserved.
 * Use is subject to license terms.
 */
package org.freeinternals.commonlib.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility Class.
 *
 * @author Amos Shi
 * @since JDK 6.0
 */
public final class Tool {

    private Tool() {
    }

    /**
     * Returns byte array from the {@code file}
     *
     * @param file The file
     * @return Byte array of the {@code file}, or {@code null} if error happened.
     */
    public static byte[] readFileAsBytes(final File file) {
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

            while (true) {
                bytesRead = input.read(contents);
                if (bytesRead != -1) {
                    byteBuf.put(contents, 0, bytesRead);
                    bytesAll += bytesRead;
                } else {
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

        if (bytesAll == fileLengthInt) {
            return byteBuf.array();
        } else {
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

    /**
     * Compares if the contents of two byte array are the same.
     * <p>
     * When either <code>bin1</code> or <code>bin2</code> is <code>null</code>,
     * <code>false</code> will be returned.
     * When either <code>bin1</code> or <code>bin2</code> is empty,
     * <code>false</code> will be returned.
     * </p>
     *
     * @param bin1  The first byte array
     * @param  bin2 The second byte array
     * @return  <code>true</code> if the content are the same, else false
     */
    public static boolean isByteArraySame(byte[] bin1, byte[] bin2) {
        if (bin1 == null || bin2 == null) {
            return false;
        }
        if (bin1.length == 0 || bin2.length == 0) {
            return false;
        }
        if (bin1.length != bin2.length) {
            return false;
        }

        boolean same = true;
        for (int i = 0; i < bin1.length; i++) {
            if (bin1[i] != bin2[i]) {
                same = false;
            }
        }

        return same;
    }

    /**
     * Checks if the byte array <code>bigBin</code> starts from 
     * <code>start</code> is the same as <code>sampleBin</code>.
     */
    public static boolean isByteArraySame(byte[] sampleBin, byte[] bigBin, int start) {
        if (sampleBin == null || bigBin == null) {
            return false;
        }
        if (sampleBin.length == 0 || bigBin.length == 0) {
            return false;
        }
        if (start < 0) {
            return false;
        }
        if (start + sampleBin.length > bigBin.length) {
            return false;
        }

        boolean same = true;
        for (int i = 0; i < sampleBin.length; i++) {
            if (sampleBin[i] != bigBin[start + i]) {
                same = false;
            }
        }

        return same;
    }
}
