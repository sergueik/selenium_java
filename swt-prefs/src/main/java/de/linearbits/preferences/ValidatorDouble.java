/* ******************************************************************************
 * Copyright (c) 2014 - 2016 Fabian Prasser.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Fabian Prasser - initial API and implementation
 * ****************************************************************************
 */

package de.linearbits.preferences;

/**
 * Validates double input.
 *
 * @author Fabian Prasser
 */
class ValidatorDouble implements Validator<Double> {

    /**  Bound */
    private final double min;

    /**  Bound */
    private final double max;

    /**
     * Creates a new instance.
     *
     * @param min
     * @param max
     */
    public ValidatorDouble(final double min, final double max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean isValid(final Double s) {
        return (s >= min) && (s <= max);
    }
}
