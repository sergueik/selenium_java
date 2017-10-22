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
 * Validates char input.
 *
 * @author Fabian Prasser
 */
class ValidatorCharacter implements Validator<String> {

	@Override
	public boolean isValid(final String s) {
		return s != null && s.length() == 1;
	}
}
