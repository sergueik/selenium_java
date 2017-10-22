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
 * This class provides basic configuration options for the dialog
 * @author Fabian Prasser
 *
 */
public class PreferencesDialogConfiguration {

	private int minimalTextWidth = 60;
	private int minimalTextHeight = 60;
	private String yes = "Yes";
	private String no = "No";
	private String ok = "OK";
	private String undo = "Undo changes";
	private String _default = "Set to default";

	public int getMinimalTextHeight() {
		return minimalTextHeight;
	}

	public int getMinimalTextWidth() {
		return minimalTextWidth;
	}

	public String getStringNo() {
		return this.no;
	}

	public String getStringOK() {
		return this.ok;
	}

	public String getStringYes() {
		return this.yes;
	}

	public String getStringDefault() {
		return this._default;
	}

	public String getStringUndo() {
		return this.undo;
	}

	public void setMinimalTextHeight(int minimalTextHeight) {
		checkPositive(minimalTextHeight);
		this.minimalTextHeight = minimalTextHeight;
	}

	public void setMinimalTextWidth(int minimalTextWidth) {
		checkPositive(minimalTextWidth);
		this.minimalTextWidth = minimalTextWidth;
	}

	public void setStringNo(String no) {
		checkNull(no);
		this.no = no;
	}

	public void setStringOK(String ok) {
		checkNull(ok);
		this.ok = ok;
	}

	public void setStringYes(String yes) {
		checkNull(yes);
		this.yes = yes;
	}

	public void setStringDefault(String _default) {
		checkNull(_default);
		this._default = _default;
	}

	public void setStringUndo(String undo) {
		checkNull(undo);
		this.undo = undo;
	}

	private void checkNull(Object arg) {
		if (arg == null) {
			throw new NullPointerException("Argument must not be null");
		}
	}

	private void checkPositive(int arg) {
		if (arg < 0) {
			throw new IllegalArgumentException("Argument must be a positive integer");
		}
	}
}
