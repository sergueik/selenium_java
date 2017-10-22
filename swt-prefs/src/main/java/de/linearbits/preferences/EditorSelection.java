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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

/**
 * Editor for set-valued variables
 * @author Fabian Prasser
 */
class EditorSelection extends Editor<String> {

	private final String[] elems;
	private Combo combo;

	public EditorSelection(PreferencesDialog dialog, String[] elems,
			String _default) {
		super(dialog, null, _default);
		this.elems = elems;
	}

	private int indexOf(final String value) {
		for (int i = 0; i < elems.length; i++) {
			if (elems[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	void createControl(final Composite parent) {
		combo = new Combo(parent, SWT.READ_ONLY);
		combo.setItems(elems);
		combo.setLayoutData(GridDataFactory.swtDefaults().grab(true, false)
				.indent(0, 0).align(SWT.FILL, SWT.FILL).create());
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent arg0) {
				if (combo.getSelectionIndex() >= 0) {
					setValid(true);
				} else {
					setValid(false);
				}
				update();
			}
		});

		super.createUndoButton(parent);
		super.createDefaultButton(parent);
		super.update();
	}

	@Override
	String format(String t) {
		return t;
	}

	@Override
	String getValue() {
		return elems[combo.getSelectionIndex()];
	}

	@Override
	String parse(String s) {
		return s;
	}

	@Override
	void setValue(Object t) {
		this.setInitialValue((String) t);
		combo.select(indexOf((String) t));
		super.update();
	}
}
