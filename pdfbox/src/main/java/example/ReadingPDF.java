package example;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.nio.charset.StandardCharsets;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class ReadingPDF {

	public static void main(String argv[]) throws IOException {
		PDDocument document = null;
		PDFTextStripper pdfStripper = null;
		final List<String> characters = new ArrayList<>();
		final String pdfFilepath = argv[0];
		final String outputFilepath = argv[1];

		final String outputFilepath2 = (argv.length > 2) ? argv[2] : null;
		try {
			File file = new File(pdfFilepath);
			document = PDDocument.load(file);

			if (!document.isEncrypted()) {
				pdfStripper = new PDFTextStripper();
				pdfStripper.setStartPage(1);
				pdfStripper.setEndPage(1);
				String rawText = pdfStripper.getText(document);
				// System.out.println("TRANSLIT: " + Translit.toAscii(rawText));
				for (int cnt = 0; cnt != rawText.length(); cnt++) {
					int codePoint = Character.codePointAt(rawText, cnt);
					// 'CYRILLIC SMALL LETTER A' (U+0430)
					// https://www.fileformat.info/info/unicode/char/430/index.htm
					// 'CYRILLIC SMALL LETTER YA' (U+044F)
					// https://www.fileformat.info/info/unicode/char/044f/index.htm
					characters.add(String.format("\\u%04x", codePoint));
				}
				// System.out.println("ENCODED: " + characters);
				// System.out.println("TRANSLIT: " + Translit.toCyrillic(characters));

				// String parsedText = rawText.replaceAll("[^A-Za-z0-9. ]+", "");
				// System.out.println(parsedText);

				String encoding = "UTF-8";

				// https://stackoverflow.com/questions/4389005/how-to-add-a-utf-8-bom-in-java
				File textFile = new File(outputFilepath);
				Writer outputStreamWriter = new OutputStreamWriter(
						new FileOutputStream(textFile),
						StandardCharsets.UTF_8 /* encoding s*/ );
				// no need to write BOM explicitly with OutputStreamWriter
				outputStreamWriter.write(rawText);
				outputStreamWriter.flush();
				outputStreamWriter.close();

				// NOTE: using the PrintStream with BOM does not work
				if (outputFilepath2 != null) {
					textFile = new File(outputFilepath2);
					Writer printWriter = new PrintWriter(new FileOutputStream(textFile));

					byte[] BOM = { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };
					// '' for (byte b : BOM) {
					// printWriter.append((char) b);
					// }
					// writer.print(0xef);
					printWriter.write(new String(BOM) + rawText);
					printWriter.flush();
					printWriter.close();
				}

			} else {
				System.out.println("Document is encrypted: " + pdfFilepath);
			}
		} catch (IOException e) {
			System.err.println("Exception:" + e.toString());
		} finally {
			if (document != null) {
				document.close();
			}

		}
	}
	// based on
	// https://stackoverflow.com/questions/5806690/is-there-an-iconv-with-translit-equivalent-in-java
	// and
	// http://tocrva.blogspot.com/2015/03/java-transliterate-cyrillic-to-latin.html

	public static class Translit {

		private static final Charset UTF8 = Charset.forName("UTF-8");
		private static final char[] alphabetCyrillic = { ' ', 'а', 'б', 'в', 'г',
				'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р',
				'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю',
				'я', 'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л',
				'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ',
				'Ъ', 'Ы', 'Б', 'Э', 'Ю', 'Я', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
				'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
				'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
				'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
				'Y', 'Z', '<', '>', '"', ':', '&', '$', '(', ')', '=', '-', '.', '0',
				'1', '2', '3', '4', '5', '6', '7', '8', '9' };

		private static final String[] alphabetTranslit = { " ", "a", "b", "v", "g",
				"d", "e", "e", "zh", "z", "i", "y", "k", "l", "m", "n", "o", "p", "r",
				"s", "t", "u", "f", "h", "ts", "ch", "sh", "sch", "", "i", "", "e",
				"ju", "ja", "A", "B", "V", "G", "D", "E", "E", "Zh", "Z", "I", "Y", "K",
				"L", "M", "N", "O", "P", "R", "S", "T", "U", "F", "H", "Ts", "Ch", "Sh",
				"Sch", "", "I", "", "E", "Ju", "Ja", "a", "b", "c", "d", "e", "f", "g",
				"h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u",
				"v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I",
				"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
				"X", "Y", "Z", "<", ">", "\"", ":", "&", "$", "(", ")", "=", "-", ".",
				"0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

		// NOTE: does not handle some letters correctly

		public static final String[] alphabetResources = { " ", "\\u0430",
				"\\u0431", "\\u0432", "\\u0433", "\\u0434", "\\u0435", "\\u0451",
				"\\u0436", "\\u0437", "\\u0438", "\\u0439", "\\u043a", "\\u043b",
				"\\u043c", "\\u043d", "\\u043e", "\\u043f", "\\u0440", "\\u0441",
				"\\u0442", "\\u0443", "\\u0444", "\\u0445", "\\u0446", "\\u0447",
				"\\u0448", "\\u0449", "\\u044a", "\\u044b", "\\u044c", "\\u044d",
				"\\u044e", "\\u044f", "\\u0410", "\\u0411", "\\u0412", "\\u0413",
				"\\u0414", "\\u0415", "\\u0401", "\\u0416", "\\u0417", "\\u0418",
				"\\u0419", "\\u041a", "\\u041b", "\\u041c", "\\u041d", "\\u041e",
				"\\u041f", "\\u0420", "\\u0421", "\\u0422", "\\u0423", "\\u0424",
				"\\u0425", "\\u0426", "\\u0427", "\\u0428", "\\u0429", "\\u042a",
				"\\u042b", "\\u042c", "\\u042d", "\\u042e", "\\u042f", "a", "b", "c",
				"d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q",
				"r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E",
				"F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
				"T", "U", "V", "W", "X", "Y", "Z", "<", ">", "\"", ":", "&", "$", "(",
				")", "=", "-", ".", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

		public static String toCyrillic(final List<String> input) {
			return toCyrillic(input, alphabetTranslit);
		}

		public static String toCyrillic(final List<String> input,
				final String[] translationTable) {
			StringBuilder builder = new StringBuilder();

			for (int i = 0; i < input.size(); i++) {
				String letter = input.get(i);
				for (int x = 0; x < alphabetResources.length; x++) {
					if (letter.equals(alphabetResources[x])) {
						builder.append(translationTable[x]);
					}
				}
			}
			return builder.toString();
		}

		public static String toAscii(final String input) {
			return toAscii(input, alphabetTranslit);
		}

		public static String toAscii(final String input,
				final String[] translationTable) {
			final CharsetEncoder charsetEncoder = UTF8.newEncoder();
			final char[] decomposed = Normalizer
					.normalize(input, Normalizer.Form.NFKD).toCharArray();
			final StringBuilder sb = new StringBuilder(decomposed.length);

			// NOTE: evaluating the character charcount is unnecessary with Cyrillic
			for (int i = 0; i < decomposed.length;) {
				final int codePoint = Character.codePointAt(decomposed, i);
				final int charCount = Character.charCount(codePoint);

				if (charsetEncoder
						.canEncode(CharBuffer.wrap(decomposed, i, charCount))) {
					sb.append(decomposed, i, charCount);
				}

				i += charCount;
			}

			StringBuilder builder = new StringBuilder();

			for (int i = 0; i < sb.length(); i++) {
				for (int x = 0; x < alphabetCyrillic.length; x++)
					if (sb.charAt(i) == alphabetCyrillic[x]) {
						builder.append(translationTable[x]);
					}
			}
			return builder.toString();
		}
	}

}