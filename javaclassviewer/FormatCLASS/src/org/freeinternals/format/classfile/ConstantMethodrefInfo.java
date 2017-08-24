/*
 * ConstantMethodrefInfo.java    4:34 AM, August 5, 2007
 *
 * Copyright  2007, FreeInternals.org. All rights reserved.
 * Use is subject to license terms.
 */
package org.freeinternals.format.classfile;

import java.io.IOException;

/**
 * The class for the {@code CONSTANT_Methodref_info} structure in constant pool.
 * The {@code CONSTANT_Methodref_info} structure has the following format:
 *
 * <pre>
 *    CONSTANT_Methodref_info {
 *        u1 tag;
 *        u2 class_index;
 *        u2 name_and_type_index;
 *    }
 * </pre>
 *
 * @author Amos Shi
 * @since JDK 6.0
 * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#42041">
 * VM Spec:  The CONSTANT_Methodref_info Structure
 * </a>
 */
public class ConstantMethodrefInfo extends AbstractCPInfo {

    private final u2 class_index;
    private final u2 name_and_type_index;

    ConstantMethodrefInfo(final PosDataInputStream posDataInputStream)
            throws IOException {
        super();
        this.tag.value = AbstractCPInfo.CONSTANT_Methodref;

        this.startPos = posDataInputStream.getPos() - 1;
        this.length = 5;

        this.class_index = new u2();
        this.class_index.value = posDataInputStream.readUnsignedShort();
        this.name_and_type_index = new u2();
        this.name_and_type_index.value = posDataInputStream.readUnsignedShort();
    }

    @Override
    public String getName() {
        return "Methodref";
    }

    @Override
    public String getDescription() {
        return String.format("ConstantMethodrefInfo: Start Position: [%d], length: [%d], value: class_index=[%d], name_and_type_index=[%d].", this.startPos, this.length, this.class_index.value, this.name_and_type_index.value);
    }

    /**
     * Get the value of {@code class_index}.
     *
     * @return The value of {@code class_index}
     */
    public int getClassIndex() {
        return this.class_index.value;
    }

    /**
     * Get the value of {@code name_and_type_index}.
     *
     * @return The value of {@code name_and_type_index}
     */
    public int getNameAndTypeIndex() {
        return this.name_and_type_index.value;
    }
}
