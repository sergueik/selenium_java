/*
 * AttributeLineNumberTable.java    5:28 AM, August 5, 2007
 *
 * Copyright  2007, FreeInternals.org. All rights reserved.
 * Use is subject to license terms.
 */
package org.freeinternals.format.classfile;

import java.io.IOException;
import org.freeinternals.format.FileFormatException;

/**
 * The class for the {@code LineNumberTable} attribute.
 * The {@code LineNumberTable} attribute has the following format:
 *
 * <pre>
 *    LineNumberTable_attribute {
 *        u2 attribute_name_index;
 *        u4 attribute_length;
 *        u2 line_number_table_length;
 *        {  u2 start_pc;
 *           u2 line_number;
 *        } line_number_table[line_number_table_length];
 *    }
 * </pre>
 *
 * @author Amos Shi
 * @since JDK 6.0
 * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#22856">
 * VM Spec: The LineNumberTable Attribute
 * </a>
 */
public class AttributeLineNumberTable extends AttributeInfo {

    private transient final u2 line_number_table_length;
    private transient LineNumberTable[] lineNumberTable;

    AttributeLineNumberTable(final u2 nameIndex, final String type, final PosDataInputStream posDataInputStream)
            throws IOException, FileFormatException {
        super(nameIndex, type, posDataInputStream);

        this.line_number_table_length = new u2();
        this.line_number_table_length.value = posDataInputStream.readUnsignedShort();
        if (this.line_number_table_length.value > 0) {
            this.lineNumberTable = new LineNumberTable[this.line_number_table_length.value];
            for (int i = 0; i < this.line_number_table_length.value; i++) {
                this.lineNumberTable[i] = new LineNumberTable(posDataInputStream);
            }
        }

        super.checkSize(posDataInputStream.getPos());
    }

    /**
     * Get the value of {@code line_number_table_length}.
     *
     * @return The value of {@code line_number_table_length}
     */
    public int getLineNumberTableLength() {
        return this.line_number_table_length.value;
    }

    /**
     * Get the value of {@code line_number_table}[{@code index}].
     *
     * @param index Index of the line number table
     * @return The value of {@code line_number_table}[{@code index}]
     */
    public LineNumberTable getLineNumberTable(final int index) {
        LineNumberTable lnt = null;
        if (this.lineNumberTable != null) {
            lnt = this.lineNumberTable[index];
        }

        return lnt;
    }

    /**
     * The {@code line_number_table} structure in {@code LineNumberTable} attribute.
     *
     * @author Amos Shi
     * @since JDK 6.0
     * @see AttributeLineNumberTable
     */
    public final class LineNumberTable extends ClassComponent {

        private transient final u2 start_pc;
        private transient final u2 line_number;

        private LineNumberTable(final PosDataInputStream posDataInputStream)
                throws IOException {
            this.startPos = posDataInputStream.getPos();
            this.length = 4;

            this.start_pc = new u2();
            this.start_pc.value = posDataInputStream.readUnsignedShort();
            this.line_number = new u2();
            this.line_number.value = posDataInputStream.readUnsignedShort();
        }

        /**
         * Get the value of {@code start_pc}.
         *
         * @return The value of {@code start_pc}
         */
        public int getStartPc() {
            return this.start_pc.value;
        }

        /**
         * Get the value of {@code line_number}.
         *
         * @return The value of {@code line_number}
         */
        public int getLineNumber() {
            return this.line_number.value;
        }
    }
}
