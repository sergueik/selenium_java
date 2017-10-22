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
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * Editor for string variables (single line)
 * @author Fabian Prasser
 */
class EditorString extends Editor<String> {

	private Text text;

	public EditorString(PreferencesDialog dialog, String _default) {
		super(dialog, null, _default);
	}

	public EditorString(PreferencesDialog dialog, Validator<String> validator,
			String _default) {
		super(dialog, validator, _default);
	}

	@Override
	void createControl(final Composite parent) {

		final GridData ldata = GridDataFactory.swtDefaults().grab(true, false)
				.indent(0, 0).align(SWT.FILL, SWT.FILL).create();
		text = new Text(parent, SWT.SINGLE | SWT.BORDER);
		ldata.minimumWidth = getDialog().getConfiguration().getMinimalTextWidth();
		text.setText("");
		text.setLayoutData(ldata);

		final Color black = new Color(text.getDisplay(), 0, 0, 0);
		final Color red = new Color(text.getDisplay(), 255, 0, 0);

		text.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent arg0) {
				if (accepts(text.getText())) {
					text.setForeground(black);
					setValid(true);
					update();
				} else {
					text.setForeground(red);
					setValid(false);
					update();
				}
			}
		});

		text.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				black.dispose();
				red.dispose();
			}
		});

		super.createUndoButton(parent);
		super.createDefaultButton(parent);
		super.update();
	}

	@Override
	String format(String d) {
		return d;
	}

	@Override
	String getValue() {
		return text.getText();
	}

	@Override
	String parse(final String s) {
		return s;
	}

	@Override
	void setValue(Object t) {
		this.setInitialValue((String) t);
		this.text.setText((String) t);
		super.update();
	}
}
