/*
 * ClassComponent.java    8:51 PM, August 7, 2007
 *
 * Copyright  2007, FreeInternals.org. All rights reserved.
 * Use is subject to license terms.
 */
package org.freeinternals.format.classfile;

/**
 * Super class for all components in a class file. This class has two fields:
 * {@code startPos} indecates the start index in the class file byte array
 * of current component; {@code length} is
 *
 *
 * @author Amos Shi
 * @since JDK 6.0
 */
public class ClassComponent {

    /**
     * Start position in the class file byte array of current component.
     */
    transient protected int startPos;
    /**
     * Length of current component.
     */
    transient protected int length;

    ClassComponent() {
        this.startPos = 0;
        this.length = 0;
    }

    /**
     * Get the start position of current component.
     *
     * @return The start position
     */
    public int getStartPos() {
        return this.startPos;
    }

    /**
     * Get the length of current component.
     *
     * @return The length
     */
    public int getLength() {
        return this.length;
    }
}
