package com.github.sergueik.example;

/*
 * Copyright (c) Ian F. Darwin, http://www.darwinsys.com/, 1996-2002.
 * All rights reserved. Software written by Ian F. Darwin and others.
 * $Id: LICENSE,v 1.8 2004/02/09 03:33:38 ian Exp $
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS''
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * Java, the Duke mascot, and all variants of Sun's Java "steaming coffee
 * cup" logo are trademarks of Sun Microsystems. Sun's, and James Gosling's,
 * pioneering role in inventing and promulgating (and standardizing) the Java
 * language and environment is gratefully acknowledged.
 *
 * The pioneering role of Dennis Ritchie and Bjarne Stroustrup, of AT&T, for
 * inventing predecessor languages C and C++ is also gratefully acknowledged.
 */
// Diff -- text file difference utility.
// See full docu-comment at beginning of Diff class.

// $Id: Diff.java,v 1.6 2004/02/09 03:34:05 ian Exp $

// origin: http://www.java2s.com/Code/Java/File-Input-Output/Difftextfiledifferenceutility.htm
// stripped some comments, and rearranged and renamed the classes to meet the filename to match the first class name  requirements
import java.io.*;
import static java.lang.System.err;

public class Diff {

	final int UNREAL = Integer.MAX_VALUE;

	FileInfo oldinfo, newinfo;
	int blocklen[];

	public static void main(String argstrings[]) {
		if (argstrings.length != 2) {
			System.err.println("Usage: diff oldfile newfile");
			System.exit(1);
		}
		Diff d = new Diff();
		d.doDiff(argstrings[0], argstrings[1]);
		return;
	}

	/** Construct a Diff object. */
	Diff() {
	}

	private static class Node { /* the tree is made up of these nodes */
		Node pleft, pright;
		int linenum;

		static final int freshnode = 0, oldonce = 1, newonce = 2, bothonce = 3,
				other = 4;

		int /* enum linestates */ linestate;
		String line;

		static Node panchor = null; /* symtab is a tree hung from this */

		Node(String pline) {
			pleft = pright = null;
			linestate = freshnode;
			/* linenum field is not always valid */
			line = pline;
		}

		static Node matchsymbol(String pline) {
			int comparison;
			Node pnode = panchor;
			if (panchor == null)
				return panchor = new Node(pline);
			for (;;) {
				comparison = pnode.line.compareTo(pline);
				if (comparison == 0)
					return pnode; /* found */

				if (comparison < 0) {
					if (pnode.pleft == null) {
						pnode.pleft = new Node(pline);
						return pnode.pleft;
					}
					pnode = pnode.pleft;
				}
				if (comparison > 0) {
					if (pnode.pright == null) {
						pnode.pright = new Node(pline);
						return pnode.pright;
					}
					pnode = pnode.pright;
				}
			}
			/* NOTE: There are return stmts, so control does not get here. */
		}

		static Node addSymbol(String pline, boolean inoldfile, int linenum) {
			Node pnode;
			pnode = matchsymbol(pline); /* find the node in the tree */
			if (pnode.linestate == freshnode) {
				pnode.linestate = inoldfile ? oldonce : newonce;
			} else {
				if ((pnode.linestate == oldonce && !inoldfile)
						|| (pnode.linestate == newonce && inoldfile))
					pnode.linestate = bothonce;
				else
					pnode.linestate = other;
			}
			if (inoldfile)
				pnode.linenum = linenum;
			return pnode;
		}

		boolean symbolIsUnique() {
			return (linestate == bothonce);
		}

		void showSymbol() {
			System.out.println(line);
		}
	}

	private static class FileInfo {

		static final int MAXLINECOUNT = 20000;

		DataInputStream file; /* File handle that is open for read.  */
		public int maxLine;
		Node symbol[];
		int other[];

		FileInfo(String filename) {
			symbol = new Node[MAXLINECOUNT + 2];
			other = null; // allocated later!
			try {
				file = new DataInputStream(new FileInputStream(filename));
			} catch (IOException e) {
				System.err.println("Diff can't read file " + filename);
				System.err.println("Error Exception was:" + e);
				System.exit(1);
			}
		}

		void alloc() {
			other = new int[symbol.length + 2];
		}
	};

	/** Do one file comparison. Called with both filenames. */
	public void doDiff(String oldFile, String newFile) {
		println(">>>> Difference of file \"" + oldFile + "\" and file \"" + newFile
				+ "\".\n");
		oldinfo = new FileInfo(oldFile);
		newinfo = new FileInfo(newFile);
		/* we don't process until we know both files really do exist. */
		try {
			inputscan(oldinfo);
			inputscan(newinfo);
		} catch (IOException e) {
			System.err.println("Read error: " + e);
		}

		/* Now that we've read all the lines, allocate some arrays.
		 */
		blocklen = new int[(oldinfo.maxLine > newinfo.maxLine ? oldinfo.maxLine
				: newinfo.maxLine) + 2];
		oldinfo.alloc();
		newinfo.alloc();

		/* Now do the work, and print the results. */
		transform();
		printout();
	}

	/**
	 * inputscan    Reads the file specified by pinfo.file.
	 * ---------    Places the lines of that file in the symbol table.
	 *              Sets pinfo.maxLine to the number of lines found.
	 */
	void inputscan(FileInfo pinfo) throws IOException {
		String linebuffer;

		pinfo.maxLine = 0;
		while ((linebuffer = pinfo.file.readLine()) != null) {
			storeline(linebuffer, pinfo);
		}
	}

	/**
	 * storeline    Places line into symbol table.
	 * ---------    Expects pinfo.maxLine initted: increments.
	 *              Places symbol table handle in pinfo.ymbol.
	 *              Expects pinfo is either oldinfo or newinfo.
	 */
	void storeline(String linebuffer, FileInfo pinfo) {
		int linenum = ++pinfo.maxLine; /* note, no line zero */
		if (linenum > FileInfo.MAXLINECOUNT) {
			System.err.println("MAXLINECOUNT exceeded, must stop.");
			System.exit(1);
		}
		pinfo.symbol[linenum] = Node.addSymbol(linebuffer, pinfo == oldinfo,
				linenum);
	}

	void transform() {
		int oldline, newline;
		int oldmax = oldinfo.maxLine + 2; /* Count pseudolines at  */
		int newmax = newinfo.maxLine + 2; /* ..front and rear of file */

		for (oldline = 0; oldline < oldmax; oldline++)
			oldinfo.other[oldline] = -1;
		for (newline = 0; newline < newmax; newline++)
			newinfo.other[newline] = -1;

		scanunique();
		scanafter();
		scanbefore();
		scanblocks();
	}

	void scanunique() {
		int oldline, newline;
		Node psymbol;

		for (newline = 1; newline <= newinfo.maxLine; newline++) {
			psymbol = newinfo.symbol[newline];
			if (psymbol.symbolIsUnique()) {
				oldline = psymbol.linenum;
				newinfo.other[newline] = oldline;
				oldinfo.other[oldline] = newline;
			}
		}
		newinfo.other[0] = 0;
		oldinfo.other[0] = 0;
		newinfo.other[newinfo.maxLine + 1] = oldinfo.maxLine + 1;
		oldinfo.other[oldinfo.maxLine + 1] = newinfo.maxLine + 1;
	}

	void scanafter() {
		int oldline, newline;

		for (newline = 0; newline <= newinfo.maxLine; newline++) {
			oldline = newinfo.other[newline];
			if (oldline >= 0) {
				for (;;) {
					if (++oldline > oldinfo.maxLine)
						break;
					if (oldinfo.other[oldline] >= 0)
						break;
					if (++newline > newinfo.maxLine)
						break;
					if (newinfo.other[newline] >= 0)
						break;

					if (newinfo.symbol[newline] != oldinfo.symbol[oldline])
						break;

					newinfo.other[newline] = oldline;
					oldinfo.other[oldline] = newline;
				}
			}
		}
	}

	void scanbefore() {
		int oldline, newline;

		for (newline = newinfo.maxLine + 1; newline > 0; newline--) {
			oldline = newinfo.other[newline];
			if (oldline >= 0) { /* unique in each */
				for (;;) {
					if (--oldline <= 0)
						break;
					if (oldinfo.other[oldline] >= 0)
						break;
					if (--newline <= 0)
						break;
					if (newinfo.other[newline] >= 0)
						break;

					/* oldline and newline exist,
					and aren't marked yet */

					if (newinfo.symbol[newline] != oldinfo.symbol[oldline])
						break; // not same

					newinfo.other[newline] = oldline;
					oldinfo.other[oldline] = newline;
				}
			}
		}
	}

	void scanblocks() {
		int oldline, newline;
		int oldfront = 0;
		int newlast = -1;

		for (oldline = 1; oldline <= oldinfo.maxLine; oldline++)
			blocklen[oldline] = 0;
		blocklen[oldinfo.maxLine + 1] = UNREAL;

		for (oldline = 1; oldline <= oldinfo.maxLine; oldline++) {
			newline = oldinfo.other[oldline];
			if (newline < 0)
				oldfront = 0;
			else {
				if (oldfront == 0)
					oldfront = oldline;
				if (newline != (newlast + 1))
					oldfront = oldline;
				++blocklen[oldfront];
			}
			newlast = newline;
		}
	}

	public static final int idle = 0, delete = 1, insert = 2, movenew = 3,
			moveold = 4, same = 5, change = 6;
	int printstatus;
	boolean anyprinted;
	int printoldline, printnewline;

	void printout() {
		printstatus = idle;
		anyprinted = false;
		for (printoldline = printnewline = 1;;) {
			if (printoldline > oldinfo.maxLine) {
				newconsume();
				break;
			}
			if (printnewline > newinfo.maxLine) {
				oldconsume();
				break;
			}
			if (newinfo.other[printnewline] < 0) {
				if (oldinfo.other[printoldline] < 0)
					showchange();
				else
					showinsert();
			} else if (oldinfo.other[printoldline] < 0)
				showdelete();
			else if (blocklen[printoldline] < 0)
				skipold();
			else if (oldinfo.other[printoldline] == printnewline)
				showsame();
			else
				showmove();
		}
		if (anyprinted == true)
			println(">>>> End of differences.");
		else
			println(">>>> Files are identical.");
	}

	void newconsume() {
		for (;;) {
			if (printnewline > newinfo.maxLine)
				break; /* end of file */
			if (newinfo.other[printnewline] < 0)
				showinsert();
			else
				showmove();
		}
	}

	void oldconsume() {
		for (;;) {
			if (printoldline > oldinfo.maxLine)
				break; /* end of file */
			printnewline = oldinfo.other[printoldline];
			if (printnewline < 0)
				showdelete();
			else if (blocklen[printoldline] < 0)
				skipold();
			else
				showmove();
		}
	}

	void showdelete() {
		if (printstatus != delete)
			println(">>>> DELETE AT " + printoldline);
		printstatus = delete;
		oldinfo.symbol[printoldline].showSymbol();
		anyprinted = true;
		printoldline++;
	}

	void showinsert() {
		if (printstatus == change)
			println(">>>>     CHANGED TO");
		else if (printstatus != insert)
			println(">>>> INSERT BEFORE " + printoldline);
		printstatus = insert;
		newinfo.symbol[printnewline].showSymbol();
		anyprinted = true;
		printnewline++;
	}

	void showchange() {
		if (printstatus != change)
			println(">>>> " + printoldline + " CHANGED FROM");
		printstatus = change;
		oldinfo.symbol[printoldline].showSymbol();
		anyprinted = true;
		printoldline++;
	}

	void skipold() {
		printstatus = idle;
		for (;;) {
			if (++printoldline > oldinfo.maxLine)
				break;
			if (oldinfo.other[printoldline] < 0)
				break;
			if (blocklen[printoldline] != 0)
				break;
		}
	}

	void skipnew() {
		int oldline;
		printstatus = idle;
		for (;;) {
			if (++printnewline > newinfo.maxLine)
				break;
			oldline = newinfo.other[printnewline];
			if (oldline < 0)
				break;
			if (blocklen[oldline] != 0)
				break;
		}
	}

	void showsame() {
		int count;
		printstatus = idle;
		if (newinfo.other[printnewline] != printoldline) {
			System.err.println("BUG IN LINE REFERENCING");
			System.exit(1);
		}
		count = blocklen[printoldline];
		printoldline += count;
		printnewline += count;
	}

	void showmove() {
		int oldblock = blocklen[printoldline];
		int newother = newinfo.other[printnewline];
		int newblock = blocklen[newother];

		if (newblock < 0)
			skipnew();
		else if (oldblock >= newblock) {
			blocklen[newother] = -1;
			println(">>>> " + newother + " THRU " + (newother + newblock - 1)
					+ " MOVED TO BEFORE " + printoldline);
			for (; newblock > 0; newblock--, printnewline++)
				newinfo.symbol[printnewline].showSymbol();
			anyprinted = true;
			printstatus = idle;

		} else
			skipold();
	}

  // send the results to STDERR by default
	public void println(String s) {
		err.println(s);
	}
};
