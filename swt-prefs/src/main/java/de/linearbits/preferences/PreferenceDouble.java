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
 * A preference for double variables
 * @author Fabian Prasser
 */
public abstract class PreferenceDouble extends Preference<Double> {

	private Validator<Double> validator = null;

	public PreferenceDouble(String label) {
		super(label);
	}

	public PreferenceDouble(String label, double min, double max) {
		super(label);
		this.validator = new ValidatorDouble(min, max);
	}

	public PreferenceDouble(String label, double min, double max,
			double _default) {
		super(label, _default);
		this.validator = new ValidatorDouble(min, max);
	}

	@Override
	protected Editor<Double> getEditor() {
		return new EditorDouble(getDialog(), getValidator(), getDefault());
	}

	@Override
	Validator<Double> getValidator() {
		return validator;
	}

}
