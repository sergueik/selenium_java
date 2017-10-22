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
package examples;

import de.linearbits.preferences.PreferenceBoolean;
import de.linearbits.preferences.PreferenceDouble;
import de.linearbits.preferences.PreferenceInteger;
import de.linearbits.preferences.PreferenceSelection;
import de.linearbits.preferences.PreferencesDialog;

/**
 * This class implements a dialog for editing project properties.
 *
 * @author Fabian Prasser
 */
public class PreferencesExample {

	public static void main(String[] args) {

		// Create dialog
		PreferencesDialog dialog = new PreferencesDialog(null, "Example",
				"Example");
		createTab(dialog);
		dialog.open();
	}

	/**
	 * Create a tab
	 * @param window
	 */
	private static void createTab(PreferencesDialog window) {

		window.addCategory("Category");

		window.addGroup("Group1");

		window.addPreference(new PreferenceInteger("IntegerPreference", 1, 10, 10) {
			protected Integer getValue() {
				return 1;
			}

			protected void setValue(Object t) {
				/*(Integer)t)*/ }
		});

		window.addPreference(new PreferenceSelection("SelectionPreference",
				new String[] { "Option1", "Option2" }) {
			protected String getValue() {
				return "Option1";
			}

			protected void setValue(Object arg0) {
				/*(String)arg0*/ }
		});

		window.addGroup("Group2");

		window.addPreference(
				new PreferenceDouble("DoublePreference", 1.0e-12, 1d, 1.0e-6) {
					protected Double getValue() {
						return 1d;
					}

					protected void setValue(Object t) {
						/*(Double)t*/ }
				});

		window.addPreference(new PreferenceInteger("IntegerPreference", 1, 10, 10) {
			protected Integer getValue() {
				return 1;
			}

			protected void setValue(Object t) {
				/*(Integer)t)*/ }
		});
		window.addPreference(new PreferenceInteger("IntegerPreference", 1, 10, 10) {
			protected Integer getValue() {
				return 1;
			}

			protected void setValue(Object t) {
				/*(Integer)t)*/ }
		});

		window.addPreference(new PreferenceInteger("IntegerPreference", 1, 10, 10) {
			protected Integer getValue() {
				return 1;
			}

			protected void setValue(Object t) {
				/*(Integer)t)*/ }
		});

		window.addPreference(new PreferenceInteger("IntegerPreference", 1, 10, 10) {
			protected Integer getValue() {
				return 1;
			}

			protected void setValue(Object t) {
				/*(Integer)t)*/ }
		});

		window.addPreference(new PreferenceBoolean("BooleanPreference", false) {
			protected Boolean getValue() {
				return true;
			}

			protected void setValue(Object t) {
				/*(Boolean)t*/}
		});

	}
}
