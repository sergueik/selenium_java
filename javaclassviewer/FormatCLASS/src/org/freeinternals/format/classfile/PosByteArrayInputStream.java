/*
 * PosByteArrayInputStream.java    August 8, 2007, 12:44 PM
 *
 * Copyright  2007, FreeInternals.org. All rights reserved.
 * Use is subject to license terms.
 */

package org.freeinternals.format.classfile;

import java.io.ByteArrayInputStream;

/**
 *
 * @author Amos Shi
 * @since JDK 6.0
 */
class PosByteArrayInputStream extends ByteArrayInputStream
{
    /**
     * Creates a new instance of PosByteArrayInputStream
     * @param buf 
     */
    public PosByteArrayInputStream(final byte[] buf)
    {
        super(buf);
    }
    
    int getPos()
    {
        return this.pos;
    }
}
