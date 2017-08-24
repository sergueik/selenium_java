/*
 * AttributeExceptions.java    5:18 AM, August 5, 2007
 *
 * Copyright  2007, FreeInternals.org. All rights reserved.
 * Use is subject to license terms.
 */
package org.freeinternals.format.classfile;

import java.io.IOException;
import org.freeinternals.format.FileFormatException;

/**
 * The class for the {@code Exceptions} attribute.
 * The {@code Exceptions} attribute has the following format:
 *
 * <pre>
 *    Exceptions_attribute {
 *        u2 attribute_name_index;
 *        u4 attribute_length;
 *        u2 number_of_exceptions;
 *        u2 exception_index_table[number_of_exceptions];
 *    }
 * </pre>
 *
 * @author Amos Shi
 * @since JDK 6.0
 * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#3129">
 * VM Spec: The Exceptions Attribute
 * </a>
 */
public class AttributeExceptions extends AttributeInfo {

    private transient final u2 number_of_exceptions;
    private transient u2[] exception_index_table;

    AttributeExceptions(final u2 nameIndex, final String type, final PosDataInputStream posDataInputStream)
            throws IOException, FileFormatException {
        super(nameIndex, type, posDataInputStream);

        this.number_of_exceptions = new u2();
        this.number_of_exceptions.value = posDataInputStream.readUnsignedShort();
        final int excpCount = this.number_of_exceptions.value;
        if (excpCount > 0) {
            this.exception_index_table = new u2[excpCount];
            for (int i = 0; i < excpCount; i++) {
                this.exception_index_table[i] = new u2();
                this.exception_index_table[i].value = posDataInputStream.readUnsignedShort();
            }
        }

        super.checkSize(posDataInputStream.getPos());
    }

    /**
     * Get the value of {@code number_of_exceptions}.
     *
     * @return The value of {@code number_of_exceptions}
     */
    public int getNumberOfExceptions() {
        return this.number_of_exceptions.value;
    }

    /**
     * Get the value of {@code exception_index_table}[{@code index}].
     *
     * @param index Index of the exception table
     * @return The value of {@code exception_index_table}[{@code index}]
     */
    public int getExceptionIndexTableItem(final int index) {
        //TODO: Add the check: index < number_of_exceptions
        int i;
        if (this.exception_index_table == null) {
            i = -1;
        } else {
            i = this.exception_index_table[index].value;
        }

        return i;
    }
}
