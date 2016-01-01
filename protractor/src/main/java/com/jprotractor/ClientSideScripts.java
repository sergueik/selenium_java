package com.jprotractor;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

public class ClientSideScripts {

	protected static String getScriptContent(String fileName) {
		try {
				InputStream is = ClientSideScripts.class.getClassLoader().getResourceAsStream(fileName);
				byte[] bytes = new byte[is.available()];
				is.read(bytes);
				return new String(bytes, "UTF-8");
		} catch ( IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static final String WaitForAngular = getScriptContent("waitForAngular.js") ;
	public static final String TestForAngular = getScriptContent("testForAngular.js") ; 
    public static final String ResumeAngularBootstrap =  getScriptContent("resumeAngularBootstrap.js");
	public static final String GetLocationAbsUrl = getScriptContent("getLocationAbsUrl.js"); 
	public static final String Evaluate = getScriptContent("evaluate.js");
	public static final String FindButtonText = getScriptContent("buttonText.js");
	public static final String PartialButtonText = getScriptContent("partialButtonText.js");
	public static final String FindBindings = getScriptContent("binding.js");
	public static final String FindModel = getScriptContent("model.js");
	public static final String FindOptions = getScriptContent("options.js");
	public static final String FindSelectedOption = getScriptContent("selectedOption.js"); 
	public static final String FindAllRepeaterRows = getScriptContent("repeater.js"); 
	public static final String FindRepeaterColumn = getScriptContent("repeaterColumn.js"); 
}
