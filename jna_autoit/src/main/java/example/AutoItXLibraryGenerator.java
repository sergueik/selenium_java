/*
 * Copyright 2018 midorlo.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Util Class to regenerate the interfaces.
 * @author midorlo
 */
class AutoItXLibraryGenerator {

	protected static void printJavaMethods() {
		Method[] methods = new AutoItX().LIB.getClass().getMethods();
		for (Method method : methods) {
			System.out
					.println("public " + method.getReturnType() + " " + method.getName()
							+ "(" + prefixPrintJavaMethods(method.getParameters()) + ") {"
							+ System.lineSeparator()
							+ ((method.getReturnType().getSimpleName().equals("void")) ? ""
									: "return ")
							+ " LIB." + method.getName() + "("
							+ paramsPrintJavaMethods(method.getParameters()) + ");"
							+ System.lineSeparator() + "}");
		}
	}

	private static String prefixPrintJavaMethods(Parameter[] xx) {
		String r = "";
		for (Parameter parameter : xx) {
			r += parameter.getType().getSimpleName() + " " + parameter.getName()
					+ ", ";
		}
		if (r.length() > 0) {
			r = r.substring(0, r.length() - 2);
		}
		return r;
	}

	private static String paramsPrintJavaMethods(Parameter[] xx) {
		String r = "";
		for (Parameter parameter : xx) {
			r += parameter.getName() + ", ";
		}
		if (r.length() > 0) {
			r = r.substring(0, r.length() - 2);
		}
		return r;
	}

	public static void main(String[] args) {
		String libraryPath = System.getProperty("user.dir");
		System.setProperty("java.library.path", libraryPath);
		printJavaMethods();
	}
}
