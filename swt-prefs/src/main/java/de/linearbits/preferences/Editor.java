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

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * Abstract base class for an editor for a given data type.
 *
 * @author Fabian Prasser
 * @param <T>
 */
abstract class Editor<T> {

	private Validator<T> validator = null;
	private T _default = null;
	private PreferencesDialog dialog = null;
	private T initialValue = null;
	private boolean valid = true;
	private Button buttonUndo = null;
	private Button buttonDefault = null;

	public Editor(PreferencesDialog dialog, Validator<T> validator, T _default) {
		this.validator = validator;
		this.dialog = dialog;
		this._default = _default;
	}

	boolean accepts(String s) {
		try {
			T t = parse(s);
			if (t == null)
				return false;
			else if (validator != null && !validator.isValid(t))
				return false;
			else
				return true;
		} catch (Exception e) {
			return false;
		}
	}

	abstract void createControl(Composite parent);

	abstract String format(T t);

	PreferencesDialog getDialog() {
		return dialog;
	}

	T getInitialValue() {
		return initialValue;
	}

	abstract T getValue();

	boolean isDirty() {
		if (getInitialValue() == null)
			return false;
		else if (!isValid())
			return true;
		else
			return !getInitialValue().equals(getValue());
	}

	boolean isValid() {
		return this.valid;
	}

	abstract T parse(String s);

	void setInitialValue(T t) {
		if (this.initialValue == null) {
			this.initialValue = t;
		}
	}

	void setValid(boolean valid) {
		this.valid = valid;
	}

	abstract void setValue(Object t);

	void update() {
		dialog.update();
		buttonUndo.setEnabled(isDirty() && getInitialValue() != null);
		try {
			buttonDefault
					.setEnabled(_default != null && !getValue().equals(_default));
		} catch (Exception e) {
			buttonDefault.setEnabled(false);
		}
	}

	void createUndoButton(Composite parent) {
		buttonUndo = new Button(parent, SWT.PUSH);
		buttonUndo.setImage(Resources.getImageUndo());
		buttonUndo.setLayoutData(
				GridDataFactory.swtDefaults().align(SWT.LEFT, SWT.TOP).create());
		buttonUndo.setToolTipText(dialog.getConfiguration().getStringUndo());
		buttonUndo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setValue(getInitialValue());
			}
		});
	}

	void createDefaultButton(Composite parent) {
		buttonDefault = new Button(parent, SWT.PUSH);
		buttonDefault.setImage(Resources.getImageDefault());
		buttonDefault.setLayoutData(
				GridDataFactory.swtDefaults().align(SWT.LEFT, SWT.TOP).create());
		buttonDefault.setToolTipText(dialog.getConfiguration().getStringDefault());
		buttonDefault.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setValue(_default);
			}
		});
	}
}
