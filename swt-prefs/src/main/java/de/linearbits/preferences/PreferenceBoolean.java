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
 * A preference for boolean variables
 * @author Fabian Prasser
 */
public abstract class PreferenceBoolean extends Preference<Boolean> {

	public PreferenceBoolean(String label, Boolean _default) {
		super(label, _default);
	}

	public PreferenceBoolean(String label) {
		super(label);
	}

	@Override
	protected Editor<Boolean> getEditor() {
		return new EditorBoolean(getDialog(), getDefault());
	}

	@Override
	protected Validator<Boolean> getValidator() {
		return null;
	}
}
