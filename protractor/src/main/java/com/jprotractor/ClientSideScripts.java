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

	public static final String ResumeAngularBootstrap =  "angular.resumeBootstrap(arguments[0].length ? arguments[0].split(',') : []);";
	
	public static final String GetLocationAbsUrl = "var el = document.querySelector(arguments[0]);	return angular.element(el).injector().get('$location').absUrl();";
	public static final String Evaluate = getScriptContent("evaluate.js");
	public static final String FindButtonText = getScriptContent("buttonText.js");
	public static final String FindBindings = getScriptContent("binding.js");
	public static final String FindModel = getScriptContent("model.js");
	public static final String FindOptions = getScriptContent("options.js");
	public static final String FindSelectedOption = getScriptContent("selectedOption.js"); 
	public static final String FindAllRepeaterRows = getScriptContent("repeater.js"); 
	//public static final String FindAllRepeaterRows = "var using = arguments[0] || document; var repeater = arguments[1]; var rows = []; var prefixes = ['ng-', 'ng_', 'data-ng-', 'x-ng-', 'ng\\:']; for (var p = 0; p < prefixes.length; ++p) { var attr = prefixes[p] + 'repeat'; var repeatElems = using.querySelectorAll('[' + attr + ']'); attr = attr.replace(/\\/, ''); for (var i = 0; i < repeatElems.length; ++i) { if (repeatElems[i].getAttribute(attr).indexOf(repeater) != -1) { rows.push(repeatElems[i]); } } } for (var p = 0; p < prefixes.length; ++p) { var attr = prefixes[p] + 'repeat-start'; var repeatElems = using.querySelectorAll('[' + attr + ']'); attr = attr.replace(/\\/, ''); for (var i = 0; i < repeatElems.length; ++i) { if (repeatElems[i].getAttribute(attr).indexOf(repeater) != -1) { var elem = repeatElems[i]; while (elem.nodeType != 8 || !(elem.nodeValue.indexOf(repeater) != -1)) { if (elem.nodeType == 1) { rows.push(elem); } elem = elem.nextSibling; } } } } return rows;";
	

}
