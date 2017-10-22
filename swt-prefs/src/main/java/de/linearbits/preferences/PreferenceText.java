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

public abstract class PreferenceText extends Preference<String> {

	public PreferenceText(String label) {
		super(label);
	}

	public PreferenceText(String label, String _default) {
		super(label, _default);
	}

	@Override
	protected Editor<String> getEditor() {
		return new EditorText(getDialog(), getDefault());
	}

	@Override
	protected Validator<String> getValidator() {
		return null;
	}
}
