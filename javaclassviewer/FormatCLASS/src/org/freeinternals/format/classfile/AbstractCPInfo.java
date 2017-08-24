/*
 * AbstractCPInfo.java    3:44 AM, August 5, 2007
 *
 * Copyright  2007, FreeInternals.org. All rights reserved.
 * Use is subject to license terms.
 */
package org.freeinternals.format.classfile;

/**
 * The super class for constant pool items in class file. All constant pool
 * items have the following format:
 *
 * <pre>
 *    cp_info {
 *        u1 tag;
 *        u1 info[];
 *    }
 * </pre>
 *
 * The contents in {@code info} is determined by {@code tag}.
 *
 * @author Amos Shi
 * @since JDK 6.0
 * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#20080">
 * VM Spec: The Constant Pool
 * </a>
 */
public abstract class AbstractCPInfo extends ClassComponent {

    /**
     * The value for constant type {@code CONSTANT_Utf8}.
     *
     * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#7963">
     * VM Spec: The CONSTANT_Utf8_info Structure
     * </a>
     */
    public static final short CONSTANT_Utf8 = 1;
    /**
     * The value for constant type {@code CONSTANT_Integer}.
     *
     * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#21942">
     * VM Spec: The CONSTANT_Integer Structure
     * </a>
     */
    public static final short CONSTANT_Integer = 3;
    /**
     * The value for constant type {@code CONSTANT_Float}.
     *
     * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#21942">
     * VM Spec: The CONSTANT_Float Structure
     * </a>
     */
    public static final short CONSTANT_Float = 4;
    /**
     * The value for constant type {@code CONSTANT_Long}.
     *
     * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#1348">
     * VM Spec: The CONSTANT_Long Structure
     * </a>
     */
    public static final short CONSTANT_Long = 5;
    /**
     * The value for constant type {@code CONSTANT_Double}.
     *
     * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#1348">
     * VM Spec: The CONSTANT_Double Structure
     * </a>
     */
    public static final short CONSTANT_Double = 6;
    /**
     * The value for constant type {@code CONSTANT_Class}.
     *
     * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#1221">
     * VM Spec: The CONSTANT_Class Structure
     * </a>
     */
    public static final short CONSTANT_Class = 7;
    /**
     * The value for constant type {@code CONSTANT_String}.
     *
     * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#29297">
     * VM Spec: The CONSTANT_String Structure
     * </a>
     */
    public static final short CONSTANT_String = 8;
    /**
     * The value for constant type {@code CONSTANT_Fieldref}.
     *
     * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#42041">
     * VM Spec: The CONSTANT_Utf8_info CONSTANT_Fieldref
     * </a>
     */
    public static final short CONSTANT_Fieldref = 9;
    /**
     * The value for constant type {@code CONSTANT_Methodref}.
     *
     * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#42041">
     * VM Spec: The CONSTANT_Methodref Structure
     * </a>
     */
    public static final short CONSTANT_Methodref = 10;
    /**
     * The value for constant type {@code CONSTANT_InterfaceMethodref}.
     *
     * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#42041">
     * VM Spec: The CONSTANT_InterfaceMethodref Structure
     * </a>
     */
    public static final short CONSTANT_InterfaceMethodref = 11;
    /**
     * The value for constant type {@code CONSTANT_NameAndType}.
     *
     * @see <a href="http://www.freeinternals.org/mirror/java.sun.com/vmspec.2nded/ClassFile.doc.html#1327">
     * VM Spec: The CONSTANT_NameAndType Structure
     * </a>
     */
    public static final short CONSTANT_NameAndType = 12;
    transient u1 tag;

    AbstractCPInfo() {
        this.tag = new u1();
    }

    /**
     * Get the value of {@code tag}.
     *
     * @return The value of {@code tag}
     */
    public short getTag() {
        return this.tag.value;
    }

    /**
     * Get the name of current constant pool item.
     *
     * @return Name of the constant pool item
     */
    public abstract String getName();

    /**
     * Get a detailed, technical description of the constant pool item.
     *
     * @return Detailed, technical description of the item
     */
    public abstract String getDescription();
}
