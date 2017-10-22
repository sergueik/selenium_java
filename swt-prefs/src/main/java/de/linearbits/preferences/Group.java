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
 * A separator
 * @author Fabian Prasser
 */
class Group extends Preference<String> {
	public Group(String label) {
		super(label);
	}

	@Override
	protected String getValue() {
		return null;
	}

	@Override
	protected void setValue(Object t) {
		// Empty by design
	}

	@Override
	Editor<String> getEditor() {
		return null;
	}

	@Override
	Validator<String> getValidator() {
		return null;
	}
}
