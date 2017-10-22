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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

// https://help.eclipse.org/luna/index.jsp?topic=%2Forg.eclipse.platform.doc.isv%2Freference%2Fapi%2Forg%2Feclipse%2Fjface%2Fdialogs%2FTitleAreaDialog.html

public class PreferencesDialog extends TitleAreaDialog {

	private PreferencesDialogConfiguration config = new PreferencesDialogConfiguration();
	private List<String> categories = new ArrayList<>();
	private Map<String, List<Preference<?>>> preferences = new HashMap<>();
	private Map<Preference<?>, Editor<?>> editors = new HashMap<>();
	private String category;
	private Map<String, Image> images = new HashMap<>();
	private String title;
	private String description;

	private Shell shell;
	private Button ok;
	private TabFolder folder;

	public PreferencesDialog(Shell shell, String title, String description) {
		this(shell, title, description, false);
	}

	public PreferencesDialog(Shell shell, String title, String description,
			boolean resizable) {
		super(shell);
		if (resizable) {
			this.setShellStyle(this.getShellStyle() | SWT.RESIZE);
		}
		this.title = title;
		this.description = description;
		this.shell = shell;
	}

	public void addCategory(String label) {
		addCategory(label, null);
	}

	public void addCategory(String label, Image image) {
		if (label == null) {
			throw new NullPointerException("Label must not be null");
		}
		this.preferences.put(label, new ArrayList<Preference<?>>());
		this.category = label;
		this.images.put(label, image);
		this.categories.add(label);
	}

	public void addGroup(String text) {
		if (category == null) {
			throw new IllegalStateException("Please create a category first");
		}
		this.preferences.get(category).add(new Group(text));
	}

	public void addPreference(Preference<?> preference) {
		if (category == null) {
			throw new IllegalStateException("Please create a category first");
		}
		this.preferences.get(category).add(preference);
		preference.setDialog(this);
	}

	@Override
	public void create() {
		super.create();
		setTitle(title);
		setMessage(description);
	}

	public PreferencesDialogConfiguration getConfiguration() {
		return config;
	}

	private Composite createCategory(final TabFolder folder, String category,
			List<Preference<?>> preferences) {
		final Composite c = new Composite(folder, SWT.NONE);
		c.setLayout(new GridLayout(4, false));

		final List<Label> labels = new ArrayList<Label>();

		org.eclipse.swt.widgets.Group current = null;
		for (final Preference<?> e : preferences) {
			if (e instanceof Group) {
				current = new org.eclipse.swt.widgets.Group(c, SWT.SHADOW_ETCHED_IN);
				current.setText(e.getLabel());
				current.setLayoutData(GridDataFactory.fillDefaults().grab(true, false)
						.span(4, 1).create());
				current.setLayout(new GridLayout(4, false));
			} else {
				final Label l = new Label(current != null ? current : c, SWT.NONE);
				l.setText(e.getLabel() + ":"); //$NON-NLS-1$
				labels.add(l);
				editors.put(e, e.getEditor());
				editors.get(e).createControl(current != null ? current : c);
				editors.get(e).setValue(e.getValue());
			}
		}

		// Set equal width on labels
		ControlListener listener = new ControlAdapter() {

			int count = 0;
			int maxWidth = 0;

			@Override
			public void controlResized(ControlEvent arg0) {
				maxWidth = Math.max(((Label) arg0.widget).getSize().x, maxWidth);
				if (++count == labels.size()) {
					GridData gridData = new GridData();
					gridData.widthHint = maxWidth;
					for (Label label : labels) {
						label.setLayoutData(gridData);
					}
					c.layout(true, true);
				}
			}
		};

		// Attach listener
		for (Label label : labels) {
			label.addControlListener(listener);
		}

		// Return base
		return c;
	}

	private boolean isDirty() {
		boolean dirty = false;
		for (Entry<String, List<Preference<?>>> entry : preferences.entrySet()) {
			for (Preference<?> preference : entry.getValue()) {
				Editor<?> editor = editors.get(preference);
				if (editor != null) {
					if (!editor.isValid()) {
						return false;
					}
					dirty |= editor.isDirty();
				}
			}
		}
		return dirty;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		if (shell != null) {
			newShell.setImages(shell.getImages());
		}
	}

	@Override
	protected void createButtonsForButtonBar(final Composite parent) {
		parent.setLayoutData(GridDataFactory.swtDefaults().grab(true, true)
				.indent(0, 0).align(SWT.FILL, SWT.FILL).create());
		ok = createButton(parent, -5, config.getStringOK(), true);
		ok.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {

				for (Entry<Preference<?>, Editor<?>> entry : editors.entrySet()) {
					if (entry.getValue().isDirty()) {
						entry.getKey().setValue(entry.getValue().getValue());
					}
				}

				setReturnCode(Window.OK);
				close();
			}
		});
		ok.setEnabled(isDirty());
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		folder = new TabFolder(parent, SWT.NONE);
		folder.setLayoutData(GridDataFactory.swtDefaults().grab(true, true)
				.indent(0, 0).align(SWT.FILL, SWT.FILL).create());

		// Build tabs
		for (final String category : categories) {

			// Create the tab folder
			final TabItem tab = new TabItem(folder, SWT.NONE);
			tab.setText(category);
			if (images.get(category) != null) {
				tab.setImage(images.get(category));
			}
			final Composite tabC = createCategory(folder, category,
					preferences.get(category));
			tab.setControl(tabC);
		}

		// Ugly hack that seems to be needed to achieve a correct layout on
		// Linux/GTK
		folder.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent arg0) {
				folder.layout(true, true);
				folder.removePaintListener(this);
			}
		});

		return parent;
	}

	@Override
	protected ShellListener getShellListener() {
		return new ShellAdapter() {
			@Override
			public void shellClosed(final ShellEvent event) {
				setReturnCode(Window.CANCEL);
			}
		};
	}

	@Override
	protected boolean isResizable() {
		return false;
	}

	void update() {
		if (ok != null) {
			ok.setEnabled(isDirty());
		}
	}
}