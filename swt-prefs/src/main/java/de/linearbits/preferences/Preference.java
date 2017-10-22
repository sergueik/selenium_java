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
 * An abstract base class for preferences
 * @author Fabian Prasser
 *
 * @param <T>
 */
abstract class Preference<T> {

	private String label;
	private PreferencesDialog dialog;
	private T _default = null;

	Preference(String label, T _default) {
		this(label);
		this._default = _default;
	}

	Preference(String label) {
		this.label = label;
		if (label == null) {
			throw new NullPointerException("Label must not be null");
		}
	}

	protected abstract T getValue();

	protected abstract void setValue(Object t);

	PreferencesDialog getDialog() {
		return dialog;
	}

	abstract Editor<T> getEditor();

	String getLabel() {
		return label;
	}

	abstract Validator<T> getValidator();

	void setDialog(PreferencesDialog dialog) {
		this.dialog = dialog;
	}

	T getDefault() {
		return _default;
	}
}
