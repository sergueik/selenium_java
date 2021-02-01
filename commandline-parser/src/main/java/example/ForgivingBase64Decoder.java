package example;
/**
 * Copyright 2021 Serguei Kouzmine
 */

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Base64;

public class ForgivingBase64Decoder {

	private boolean debug = false;

	public static void main(String[] args)
			throws IOException, URISyntaxException {
		byte[] decodedBytes = Base64.getDecoder().decode(args[0]);
		String decodedString = new String(decodedBytes);
		System.err.println("Result: " + decodedString);
	}
}
