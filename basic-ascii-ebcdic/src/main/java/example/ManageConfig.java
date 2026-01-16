package example;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Map;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import java.nio.charset.CharacterCodingException;

import java.nio.charset.Charset;

import java.nio.charset.CharsetDecoder;

import java.nio.charset.CharsetEncoder;

import example.CommandLineParser;

public class ManageConfig {
	private static boolean debug = false;
	private static CommandLineParser commandLineParser;

	public static void main(String[] args) throws IOException {

		commandLineParser = new CommandLineParser();

		commandLineParser.saveFlagValue("inputfile");
		commandLineParser.saveFlagValue("outputfile");
		commandLineParser.saveFlagValue("operation");

		commandLineParser.parse(args);

		if (commandLineParser.hasFlag("debug")) {
			debug = true;
		}
		String outFile = commandLineParser.getFlagValue("outputfile");
		if (outFile == null) {
			System.err.println("Missing required argument: outputfile");
			return;
		}

		String inputfile = commandLineParser.getFlagValue("inputfile");
		if (inputfile == null) {
			System.err.println("Missing required argument: inputfile");
			return;
		}
		/*
		 * if (commandLineParser.getFlagValue("inputfile") == null) {
		 * System.err.println("Missing required argument: inputfile"); return; }
		 */
		String operation = commandLineParser.getFlagValue("operation");
		if (operation == null) {
			System.err.println("Missing required argument: operation");
			return;
		}
		if (operation.equalsIgnoreCase("encode")) {
			System.out.println(Convert("test", "ASCII", "CP1047"));
			// MergeDocumentFragments.main(args);
		}
		if (operation.equalsIgnoreCase("decode")) {
			FileInputStream fis = new FileInputStream(inputfile);
			byte[] ebcdicData = fis.readAllBytes();
			fis.close();
			String unicodeText = new String(ebcdicData, Charset.forName("Cp037"));
			byte[] asciiData = unicodeText.getBytes(StandardCharsets.US_ASCII);
			System.err.println(new String(asciiData, StandardCharsets.US_ASCII));
		}
		if (debug) {
			System.err.println("Done: " + operation);
		}
	}

	public static String Convert (String strToConvert,String in, String out){
	       try {
		        System.out.println("Original String is: " + strToConvert);

	        Charset charset_in = Charset.forName(out);
	        System.out.println("charset_in is: " + charset_in.displayName());
	        Charset charset_out = Charset.forName(in);
	        System.out.println("charset_out is: " + charset_out.displayName());
	        CharsetDecoder decoder = charset_out.newDecoder();

	        CharsetEncoder encoder = charset_in.newEncoder();

	        CharBuffer uCharBuffer = CharBuffer.wrap(strToConvert);
	        System.out.println("uCharBuffer is : " + uCharBuffer.length());

	        ByteBuffer bbuf = encoder.encode(uCharBuffer);
	        System.out.println("bbuf is : " + bbuf.array().length);
	        CharBuffer cbuf = decoder.decode(bbuf);
	        System.out.println("cbuf is : " + cbuf.array().length);

	        String s = cbuf.toString();

	        System.out.println("Original String is: " + s);
	        return s;

	    } catch (CharacterCodingException e) {

	        System.out.println("Character Coding Error: " + e.getMessage());
	        return "";

	    }
	}
}
