package com.jprotractor;

import org.openqa.selenium.By;
//import com.ostusa.JavaScriptBy;

public class NgBy 
{
	private NgBy() { }
	
	public static By binding(String binding){
		return new JavaScriptBy(ClientSideScripts.FindBindings, binding);
	}
	
	public static By buttonText(String text){
		return new JavaScriptBy(ClientSideScripts.FindButtonText, text);
	}
	
	public static By input(String input){
		return new JavaScriptBy(ClientSideScripts.FindModel, input);
	}

	public static By model(String model){
		return new JavaScriptBy(ClientSideScripts.FindModel, model);
	}

	public static By partialButtonText(String text){
		return new JavaScriptBy(ClientSideScripts.PartialButtonText, text);
	}
	
	public static By repeater(String repeat){
		return new JavaScriptBy(ClientSideScripts.FindAllRepeaterRows, repeat);
	}
	public static By repeaterColumn(String repeat, String binding){
		return new JavaScriptBy(ClientSideScripts.FindRepeaterColumn, repeat, binding);
	}

	public static By options(String options){
		return new JavaScriptBy(ClientSideScripts.FindOptions, options);
	}
	
	public static By selectedOption(String model){
		return new JavaScriptBy(ClientSideScripts.FindSelectedOption, model);
	}
}
