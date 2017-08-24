/*
 * PosDataInputStream.java    August 8, 2007, 12:48 PM
 *
 * Copyright  2007, FreeInternals.org. All rights reserved.
 * Use is subject to license terms.
 */
package org.freeinternals.format.classfile;

import java.io.DataInputStream;

/**
 *
 * @author Amos Shi
 * @since JDK 6.0
 */
class PosDataInputStream extends DataInputStream {

    /** Creates a new instance of PosDataInputStream
     * @param in 
     */
    public PosDataInputStream(final PosByteArrayInputStream in) {
        super(in);
    }

    /**
     *
     * @return  The index of the next character to read from the input stream
     *          buffer, or <code>-1</code> if there is internal error, the 
     *          input stream is not <code>PosByteArrayInputStream</code>.
     */
    public int getPos() {
        int pos = -1;
        if (this.in instanceof PosByteArrayInputStream) {
            pos = ((PosByteArrayInputStream) this.in).getPos();
        }

        return pos;
    }
}
