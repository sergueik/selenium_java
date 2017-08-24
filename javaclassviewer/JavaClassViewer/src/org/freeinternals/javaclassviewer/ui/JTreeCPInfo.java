/*
 * JTreeCPInfo.java    August 15, 2007, 4:12 PM
 *
 * Copyright  2007, FreeInternals.org. All rights reserved.
 * Use is subject to license terms.
 */
package org.freeinternals.javaclassviewer.ui;

import javax.swing.tree.DefaultMutableTreeNode;
import org.freeinternals.format.classfile.AbstractCPInfo;
import org.freeinternals.format.classfile.ConstantClassInfo;
import org.freeinternals.format.classfile.ConstantDoubleInfo;
import org.freeinternals.format.classfile.ConstantFieldrefInfo;
import org.freeinternals.format.classfile.ConstantFloatInfo;
import org.freeinternals.format.classfile.ConstantIntegerInfo;
import org.freeinternals.format.classfile.ConstantInterfaceMethodrefInfo;
import org.freeinternals.format.classfile.ConstantLongInfo;
import org.freeinternals.format.classfile.ConstantMethodrefInfo;
import org.freeinternals.format.classfile.ConstantNameAndTypeInfo;
import org.freeinternals.format.classfile.ConstantStringInfo;
import org.freeinternals.format.classfile.ConstantUtf8Info;

/**
 *
 * @author Amos Shi
 * @since JDK 6.0
 */
final class JTreeCPInfo {

    private JTreeCPInfo(){
    }

    public static void generateTreeNode(final DefaultMutableTreeNode rootNode, final AbstractCPInfo cp_info)
            throws InvalidTreeNodeException {
        if (cp_info == null) {
            return;
        }

        final short tag = cp_info.getTag();
        rootNode.add(new DefaultMutableTreeNode(new JTreeNodeClassComponent(
                cp_info.getStartPos(),
                1,
                "tag: " + tag)));

        switch (tag) {
            case AbstractCPInfo.CONSTANT_Utf8:
                JTreeCPInfo.generateTreeNode(rootNode, (ConstantUtf8Info) cp_info);
                break;

            case AbstractCPInfo.CONSTANT_Integer:
                JTreeCPInfo.generateTreeNode(rootNode, (ConstantIntegerInfo) cp_info);
                break;

            case AbstractCPInfo.CONSTANT_Float:
                JTreeCPInfo.generateTreeNode(rootNode, (ConstantFloatInfo) cp_info);
                break;

            case AbstractCPInfo.CONSTANT_Long:
                JTreeCPInfo.generateTreeNode(rootNode, (ConstantLongInfo) cp_info);
                break;

            case AbstractCPInfo.CONSTANT_Double:
                JTreeCPInfo.generateTreeNode(rootNode, (ConstantDoubleInfo) cp_info);
                break;

            case AbstractCPInfo.CONSTANT_Class:
                JTreeCPInfo.generateTreeNode(rootNode, (ConstantClassInfo) cp_info);
                break;

            case AbstractCPInfo.CONSTANT_String:
                JTreeCPInfo.generateTreeNode(rootNode, (ConstantStringInfo) cp_info);
                break;

            case AbstractCPInfo.CONSTANT_Fieldref:
                JTreeCPInfo.generateTreeNode(rootNode, (ConstantFieldrefInfo) cp_info);
                break;

            case AbstractCPInfo.CONSTANT_Methodref:
                JTreeCPInfo.generateTreeNode(rootNode, (ConstantMethodrefInfo) cp_info);
                break;

            case AbstractCPInfo.CONSTANT_InterfaceMethodref:
                JTreeCPInfo.generateTreeNode(rootNode, (ConstantInterfaceMethodrefInfo) cp_info);
                break;

            case AbstractCPInfo.CONSTANT_NameAndType:
                JTreeCPInfo.generateTreeNode(rootNode, (ConstantNameAndTypeInfo) cp_info);
                break;

            default:
                // TODO: Add exception
                break;
        }
    }

    private static void generateTreeNode(final DefaultMutableTreeNode rootNode, final ConstantUtf8Info utf8Info)
            throws InvalidTreeNodeException {
        final int startPos = utf8Info.getStartPos();
        final int bytesLength = utf8Info.getBytesLength();

        rootNode.add(new DefaultMutableTreeNode(new JTreeNodeClassComponent(
                startPos + 1,
                2,
                "length: " + bytesLength)));
        rootNode.add(new DefaultMutableTreeNode(new JTreeNodeClassComponent(
                startPos + 3,
                bytesLength,
                "bytes: " + utf8Info.getValue())));
    }

    private static void generateTreeNode(final DefaultMutableTreeNode rootNode, final ConstantIntegerInfo integerInfo)
            throws InvalidTreeNodeException {
        rootNode.add(new DefaultMutableTreeNode(new JTreeNodeClassComponent(
                integerInfo.getStartPos() + 1,
                4,
                "bytes: " + integerInfo.getValue())));
    }

    private static void generateTreeNode(final DefaultMutableTreeNode rootNode, final ConstantFloatInfo floatInfo)
            throws InvalidTreeNodeException {
        rootNode.add(new DefaultMutableTreeNode(new JTreeNodeClassComponent(
                floatInfo.getStartPos() + 1,
                4,
                "bytes: " + floatInfo.getValue())));
    }

    private static void generateTreeNode(final DefaultMutableTreeNode rootNode, final ConstantLongInfo longInfo)
            throws InvalidTreeNodeException {
        final int startPos = longInfo.getStartPos();

        rootNode.add(new DefaultMutableTreeNode(new JTreeNodeClassComponent(
                startPos + 1,
                4,
                "high_bytes")));
        rootNode.add(new DefaultMutableTreeNode(new JTreeNodeClassComponent(
                startPos + 5,
                4,
                "low_bytes")));
    }

    private static void generateTreeNode(final DefaultMutableTreeNode rootNode, final ConstantDoubleInfo doubleInfo)
            throws InvalidTreeNodeException {
        final int startPos = doubleInfo.getStartPos();

        rootNode.add(new DefaultMutableTreeNode(new JTreeNodeClassComponent(
                startPos + 1,
                4,
                "high_bytes")));
        rootNode.add(new DefaultMutableTreeNode(new JTreeNodeClassComponent(
                startPos + 5,
                4,
                "low_bytes")));
    }

    private static void generateTreeNode(final DefaultMutableTreeNode rootNode, final ConstantClassInfo classInfo)
            throws InvalidTreeNodeException {
        rootNode.add(new DefaultMutableTreeNode(new JTreeNodeClassComponent(
                classInfo.getStartPos() + 1,
                2,
                "name_index: " + classInfo.getNameIndex())));
    }

    private static void generateTreeNode(final DefaultMutableTreeNode rootNode, final ConstantStringInfo stringInfo)
            throws InvalidTreeNodeException {
        rootNode.add(new DefaultMutableTreeNode(new JTreeNodeClassComponent(
                stringInfo.getStartPos() + 1,
                2,
                "string_index: " + stringInfo.getStringIndex())));
    }

    private static void generateTreeNode(final DefaultMutableTreeNode rootNode, final ConstantFieldrefInfo fieldrefInfo)
            throws InvalidTreeNodeException {
        final int startPos = fieldrefInfo.getStartPos();

        rootNode.add(new DefaultMutableTreeNode(new JTreeNodeClassComponent(
                startPos + 1,
                2,
                "class_index: " + fieldrefInfo.getClassIndex())));
        rootNode.add(new DefaultMutableTreeNode(new JTreeNodeClassComponent(
                startPos + 3,
                2,
                "name_and_type_index: " + fieldrefInfo.getNameAndTypeIndex())));
    }

    private static void generateTreeNode(final DefaultMutableTreeNode rootNode, final ConstantMethodrefInfo methodrefInfo)
            throws InvalidTreeNodeException {
        final int startPos = methodrefInfo.getStartPos();

        rootNode.add(new DefaultMutableTreeNode(new JTreeNodeClassComponent(
                startPos + 1,
                2,
                "class_index: " + methodrefInfo.getClassIndex())));
        rootNode.add(new DefaultMutableTreeNode(new JTreeNodeClassComponent(
                startPos + 3,
                2,
                "name_and_type_index: " + methodrefInfo.getNameAndTypeIndex())));
    }

    private static void generateTreeNode(final DefaultMutableTreeNode rootNode, final ConstantInterfaceMethodrefInfo interfaceMethodrefInfo)
            throws InvalidTreeNodeException {
        final int startPos = interfaceMethodrefInfo.getStartPos();

        rootNode.add(new DefaultMutableTreeNode(new JTreeNodeClassComponent(
                startPos + 1,
                2,
                "class_index: " + interfaceMethodrefInfo.getClassIndex())));
        rootNode.add(new DefaultMutableTreeNode(new JTreeNodeClassComponent(
                startPos + 3,
                2,
                "name_and_type_index: " + interfaceMethodrefInfo.getNameAndTypeIndex())));
    }

    private static void generateTreeNode(final DefaultMutableTreeNode rootNode, final ConstantNameAndTypeInfo nameAndTypeInfo)
            throws InvalidTreeNodeException {
        final int startPos = nameAndTypeInfo.getStartPos();

        rootNode.add(new DefaultMutableTreeNode(new JTreeNodeClassComponent(
                startPos + 1,
                2,
                "name_index: " + nameAndTypeInfo.getNameIndex())));
        rootNode.add(new DefaultMutableTreeNode(new JTreeNodeClassComponent(
                startPos + 3,
                2,
                "descriptor_index: " + nameAndTypeInfo.getDescriptorIndex())));
    }
}
