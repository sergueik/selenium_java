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
 * A preference for char variables
 * @author Fabian Prasser
 */
public abstract class PreferenceCharacter extends Preference<String> {

	public PreferenceCharacter(String label, char _default) {
		super(label, String.valueOf(_default));
	}

	public PreferenceCharacter(String label) {
		super(label);
	}

	@Override
	protected Editor<String> getEditor() {
		return new EditorString(getDialog(), getValidator(), getDefault());
	}

	@Override
	protected Validator<String> getValidator() {
		return new ValidatorCharacter();
	}
}
