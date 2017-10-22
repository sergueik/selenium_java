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
 * A preference for string variables (single line)
 * @author Fabian Prasser
 */
public abstract class PreferenceString extends Preference<String> {

	public PreferenceString(String label) {
		super(label);
	}

	public PreferenceString(String label, String _default) {
		super(label, _default);
	}

	@Override
	protected Editor<String> getEditor() {
		return new EditorString(getDialog(), getDefault());
	}

	@Override
	protected Validator<String> getValidator() {
		return null;
	}
}
