package com.mycompany.app;

import org.bytedeco.javacpp.*;


import static org.bytedeco.javacpp.lept.*;
import static org.bytedeco.javacpp.tesseract.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.experimental.categories.Category;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;


public class AppTest {
	// use leptonica library
	private PIX image;
	private static TessBaseAPI api;
	@BeforeClass
	public static void setUp() {
		api = new TessBaseAPI();
		// Initialize tesseract-ocr with English, with default settings
		if (api.Init(".", "ENG") != 0) {
			System.err.println("Could not initialize tesseract.");
			System.exit(1);
		}
	}
	
	@Test
	public void RecognizeText() throws Exception {
		
		// Open input image
		image = pixRead("test.png");
		api.SetImage(image);
		// Get OCR result
		BytePointer outText = api.GetUTF8Text();
		String string = outText.getString();
		assertTrue(!string.isEmpty());
		System.err.println("OCR output:\n" + string);
		outText.deallocate();
		pixDestroy(image);
	}
	
	@AfterClass
	public static void tearDown() {
		api.End();
	}
}