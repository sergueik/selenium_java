/*
 * JTreeField.java    August 15, 2007, 6:00 PM
 *
 * Copyright  2007, FreeInternals.org. All rights reserved.
 * Use is subject to license terms.
 */
package org.freeinternals.javaclassviewer.ui;

import javax.swing.tree.DefaultMutableTreeNode;
import org.freeinternals.format.classfile.AttributeInfo;
import org.freeinternals.format.classfile.ClassFile;
import org.freeinternals.format.classfile.FieldInfo;

/**
 *
 * @author Amos Shi
 * @since JDK 6.0
 */
final class JTreeField {

    private JTreeField() {
    }

    public static void generateTreeNode(
            final DefaultMutableTreeNode rootNode,
            final FieldInfo field_info,
            final ClassFile classFile)
            throws InvalidTreeNodeException {
        if (field_info == null) {
            return;
        }

        final int startPos = field_info.getStartPos();
        final int attributesCount = field_info.getAttributesCount();

        rootNode.add(new DefaultMutableTreeNode(new JTreeNodeClassComponent(
                startPos,
                2,
                "access_flags: " + field_info.getAccessFlags() + ", " + field_info.getModifiers())));
        rootNode.add(new DefaultMutableTreeNode(new JTreeNodeClassComponent(
                startPos + 2,
                2,
                "name_index: " + field_info.getNameIndex())));
        rootNode.add(new DefaultMutableTreeNode(new JTreeNodeClassComponent(
                startPos + 4,
                2,
                "descriptor_index: " + field_info.getDescriptorIndex())));
        rootNode.add(new DefaultMutableTreeNode(new JTreeNodeClassComponent(
                startPos + 6,
                2,
                "attributes_count: " + attributesCount)));

        if (attributesCount > 0) {
            final AttributeInfo lastAttr = field_info.getAttribute(attributesCount - 1);
            final DefaultMutableTreeNode treeNodeAttr = new DefaultMutableTreeNode(new JTreeNodeClassComponent(
                    startPos + 8,
                    lastAttr.getStartPos() + lastAttr.getLength() - startPos - 8,
                    "attributes"));

            DefaultMutableTreeNode treeNodeAttrItem;
            AttributeInfo attr;
            for (int i = 0; i < attributesCount; i++) {
                attr = field_info.getAttribute(i);

                treeNodeAttrItem = new DefaultMutableTreeNode(new JTreeNodeClassComponent(
                        attr.getStartPos(),
                        attr.getLength(),
                        String.format("%d. %s", i + 1, attr.getName())));
                JTreeAttribute.generateTreeNode(treeNodeAttrItem, attr, classFile);
                treeNodeAttr.add(treeNodeAttrItem);
                treeNodeAttrItem = null;
            }
            rootNode.add(treeNodeAttr);
        }
    }
}
