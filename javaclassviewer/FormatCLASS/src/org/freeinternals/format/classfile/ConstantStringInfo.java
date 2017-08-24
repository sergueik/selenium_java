/*
 * ConstantStringInfo.java    4:36 AM, August 5, 2007
 *
 * Copyright  2007, FreeInternals.org. All rights reserved.
 * Use is subject to license terms.
 */
package org.freeinternals.format.classfile;

import java.io.IOException;

/**
 * The class for the {@code CONSTANT_String_info} structure in constant pool.
 * The {@code CONSTANT_String_info} structure has the following format:
 *
 * <pre>
 *    CONSTANT_String_info {
 *        u1 tag;
 *        u2 string_index;
 *    }
 * </pre>
 *
 * @author Amos Shi
 * @since JDK 6.0
 * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#29297">
 * VM Spec: The CONSTANT_String_info Structure
 * </a>
 */
public class ConstantStringInfo extends AbstractCPInfo {

    private final u2 string_index;

    ConstantStringInfo(final PosDataInputStream posDataInputStream)
            throws IOException {
        super();
        this.tag.value = AbstractCPInfo.CONSTANT_String;

        this.startPos = posDataInputStream.getPos() - 1;
        this.length = 3;

        this.string_index = new u2();
        this.string_index.value = posDataInputStream.readUnsignedShort();
    }

    @Override
    public String getName() {
        return "String";
    }

    @Override
    public String getDescription() {
        return String.format("ConstantStringInfo: Start Position: [%d], length: [%d], value: string_index=[%d].", this.startPos, this.length, this.string_index.value);
    }

    /**
     * Get the value of {@code string_index}.
     *
     * @return The value of {@code string_index}
     */
    public int getStringIndex() {
        return this.string_index.value;
    }
}
