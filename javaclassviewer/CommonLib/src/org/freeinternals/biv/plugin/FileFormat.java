/*
 * FileFormat.java    Apr 12, 2011, 13:02
 *
 * Copyright 2011, FreeInternals.org. All rights reserved.
 * Use is subject to license terms.
 */
package org.freeinternals.biv.plugin;

import java.io.File;
import java.io.IOException;
import javax.swing.tree.DefaultMutableTreeNode;
import org.freeinternals.commonlib.util.Tool;
import org.freeinternals.format.FileFormatException;

/**
 *
 * @author Amos Shi
 */
public abstract class FileFormat {

    public final String fileName;
    public final byte[] fileByteArray;

    public FileFormat(final File file) throws IOException, FileFormatException {
        this.fileName = file.getName();

        if (file.length() == 0) {
            throw new FileFormatException(
                    String.format("The file content is empty.\nname = %s", file.getPath()));
        }
        this.fileByteArray = Tool.readFileAsBytes(file);
    }

    public abstract String getContentTabName();

    public abstract void generateTreeNode(final DefaultMutableTreeNode parentNode);
}
