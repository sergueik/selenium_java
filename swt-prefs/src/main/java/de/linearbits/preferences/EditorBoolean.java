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
 * Editor for boolean variables
 * @author Fabian Prasser
 */
class EditorBoolean extends Editor<Boolean> {
	private Button checkbox;

	public EditorBoolean(PreferencesDialog dialog, Boolean _default) {
		super(dialog, null, _default);
	}

	@Override
	void createControl(final Composite parent) {
		checkbox = new Button(parent, SWT.CHECK);
		checkbox.setSelection(false);
		checkbox.setText(this.getDialog().getConfiguration().getStringNo());
		checkbox.setLayoutData(GridDataFactory.swtDefaults().grab(true, false)
				.indent(0, 0).align(SWT.FILL, SWT.FILL).create());
		checkbox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setValid(true);
				update();
				if (checkbox.getSelection()) {
					checkbox.setText(getDialog().getConfiguration().getStringYes());
				} else {
					checkbox.setText(getDialog().getConfiguration().getStringNo());
				}
			}
		});

		super.createUndoButton(parent);
		super.createDefaultButton(parent);
		super.update();
	}

	@Override
	String format(Boolean b) {
		return b.toString();
	}

	@Override
	Boolean getValue() {
		return checkbox.getSelection();
	}

	@Override
	Boolean parse(final String s) {
		if (s.equals(Boolean.TRUE.toString())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	void setValue(Object t) {
		setInitialValue((Boolean) t);
		checkbox.setSelection((Boolean) t);
		checkbox.setText((Boolean) t ? getDialog().getConfiguration().getStringYes()
				: getDialog().getConfiguration().getStringNo());
		super.update();
	}
}
