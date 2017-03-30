package org.swet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.text.MessageFormat;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.Vector;

import org.eclipse.swt.SWT;

import org.eclipse.swt.custom.LineStyleEvent;
import org.eclipse.swt.custom.LineStyleListener;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

/**
 * Token scanner for Selenium Webdriver Elementor Tool
 * 
 * @author Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

// origin:
// http://www.java2s.com/Code/Java/SWT-JFace-Eclipse/JavaSourcecodeViewer.htm

public class JavaScanner {

	public static final int EOF = -1;
	public static final int EOL = 10;
	public static final int WORD = 0;
	public static final int WHITE = 1;
	public static final int KEY = 2;
	public static final int COMMENT = 3;
	public static final int STRING = 5;
	public static final int OTHER = 6;
	public static final int NUMBER = 7;
	public static final int MAXIMUM_TOKEN = 8;

	protected Hashtable fgKeys = null;
	protected StringBuffer fBuffer = new StringBuffer();
	protected String fDoc;
	protected int fPos;
	protected int fEnd;
	protected int fStartToken;
	protected boolean fEofSeen = false;

	private String[] fgKeywords = { "abstract", "boolean", "break", "byte",
			"case", "catch", "char", "class", "continue", "default", "do", "double",
			"else", "extends", "false", "final", "finally", "float", "for", "if",
			"implements", "import", "instanceof", "int", "interface", "long",
			"native", "new", "null", "package", "private", "protected", "public",
			"return", "short", "static", "super", "switch", "synchronized", "this",
			"throw", "throws", "transient", "true", "try", "void", "volatile",
			"while" };

	public JavaScanner() {
		initialize();
	}

	/**
	 * Returns the ending location of the current token in the document.
	 */
	public final int getLength() {
		return fPos - fStartToken;
	}

	/**
	 * Initialize the lookup table.
	 */
	void initialize() {
		fgKeys = new Hashtable();
		Integer k = new Integer(KEY);
		for (int i = 0; i < fgKeywords.length; i++)
			fgKeys.put(fgKeywords[i], k);
	}

	/**
	 * Returns the starting location of the current token in the document.
	 */
	public final int getStartOffset() {
		return fStartToken;
	}

	/**
	 * Returns the next lexical token in the document.
	 */
	public int nextToken() {
		int c;
		fStartToken = fPos;
		while (true) {
			switch (c = read()) {
			case EOF:
				return EOF;
			case '/': // comment
				c = read();
				if (c == '/') {
					while (true) {
						c = read();
						if ((c == EOF) || (c == EOL)) {
							unread(c);
							return COMMENT;
						}
					}
				} else {
					unread(c);
				}
				return OTHER;
			case '\'': // char const
				character: for (;;) {
					c = read();
					switch (c) {
					case '\'':
						return STRING;
					case EOF:
						unread(c);
						return STRING;
					case '\\':
						c = read();
						break;
					}
				}

			case '"': // string
				string: for (;;) {
					c = read();
					switch (c) {
					case '"':
						return STRING;
					case EOF:
						unread(c);
						return STRING;
					case '\\':
						c = read();
						break;
					}
				}

			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				do {
					c = read();
				} while (Character.isDigit((char) c));
				unread(c);
				return NUMBER;
			default:
				if (Character.isWhitespace((char) c)) {
					do {
						c = read();
					} while (Character.isWhitespace((char) c));
					unread(c);
					return WHITE;
				}
				if (Character.isJavaIdentifierStart((char) c)) {
					fBuffer.setLength(0);
					do {
						fBuffer.append((char) c);
						c = read();
					} while (Character.isJavaIdentifierPart((char) c));
					unread(c);
					Integer i = (Integer) fgKeys.get(fBuffer.toString());
					if (i != null)
						return i.intValue();
					return WORD;
				}
				return OTHER;
			}
		}
	}

	/**
	 * Returns next character.
	 */
	protected int read() {
		if (fPos <= fEnd) {
			return fDoc.charAt(fPos++);
		}
		return EOF;
	}

	public void setRange(String text) {
		fDoc = text;
		fPos = 0;
		fEnd = fDoc.length() - 1;
	}

	protected void unread(int c) {
		if (c != EOF)
			fPos--;
	}
}
