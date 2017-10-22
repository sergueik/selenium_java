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
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * Editor for text variables (multi-line)
 * @author Fabian Prasser
 */
class EditorText extends Editor<String> {

	private Text text;

	public EditorText(PreferencesDialog dialog, String _default) {
		super(dialog, null, _default);
	}

	@Override
	void createControl(final Composite parent) {

		final GridData ldata = GridDataFactory.swtDefaults().grab(true, false)
				.indent(0, 0).align(SWT.FILL, SWT.FILL).create();
		text = new Text(parent, SWT.MULTI | SWT.V_SCROLL | SWT.BORDER);
		ldata.minimumWidth = getDialog().getConfiguration().getMinimalTextWidth();
		ldata.heightHint = getDialog().getConfiguration().getMinimalTextHeight();
		text.setText("");
		text.setLayoutData(ldata);
		text.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent arg0) {
				setValid(true);
				update();
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
