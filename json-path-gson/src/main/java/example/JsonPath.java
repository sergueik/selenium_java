package example;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonPath {
	
	private boolean itWasElementInBrackets;
	private boolean escapeInString;
	
	public static abstract interface PathNode{
		List<JsonElement> EMPTY_LIST = Collections.<JsonElement>emptyList();
		abstract List<JsonElement> filter(JsonElement parent);
	}
	
	public static class NamedPropertyPathNode implements PathNode{
		private String name;

		public NamedPropertyPathNode(String name) {
			this.name = name;
		}

		public List<JsonElement> filter(JsonElement parent) {
			if(parent.isJsonObject()){
				JsonObject parentObj = parent.getAsJsonObject();
				if(parentObj.has(name)){
					JsonElement element = parentObj.get(name);
					List <JsonElement> res = new ArrayList<JsonElement>();
					res.add(element);
					return res;
				}
			}
			return EMPTY_LIST;
		}
		
		@Override
		public String toString() {
			return "\""+name+"\"";
		}
	}
	
	public static class ArrayIndexPathNode implements PathNode{

		private int index;

		public ArrayIndexPathNode(int index) {
			this.index = index;
		}

		public List<JsonElement> filter(JsonElement parent) {
			if(parent.isJsonArray()){
				JsonArray parentArr = parent.getAsJsonArray();
				if(index>=0 && index<parentArr.size()){
					JsonElement element = parentArr.get(index);
					List <JsonElement> res = new ArrayList<JsonElement>();
					res.add(element);
					return res;
				}
			}
			return EMPTY_LIST;
		}
		
		@Override
		public String toString() {
			return ""+index;
		}
	}
	
	
	public static class WildcardPathNode implements PathNode{

		public List<JsonElement> filter(JsonElement parent) {
			
			if(parent.isJsonArray()){
				JsonArray parentArr = parent.getAsJsonArray();
				List<JsonElement> arrayList = new ArrayList<JsonElement>();
				int len = parentArr.size();
				for (int i = 0; i < len; i++) {
					arrayList.add(parentArr.get(i));
				}
				return arrayList;
			} else if(parent.isJsonObject()){
				JsonObject parentObj = parent.getAsJsonObject();
				List<JsonElement> arrayList = new ArrayList<JsonElement>();
				for (Iterator<Entry<String, JsonElement>> it = parentObj.entrySet().iterator(); it.hasNext();) {
					arrayList.add(it.next().getValue());
				}
				return arrayList;
			}
			return EMPTY_LIST;
		}
		
		@Override
		public String toString() {
			return "*";
		}
	}
	
	
	public static class RecursiveDescentPathNode implements PathNode{

		public List<JsonElement> filter(JsonElement parent) {
			List<JsonElement> res = getAllNodes(parent);
			return res;
		}
		
		// TODO: replace list with iterator - save memory a lot
		private List<JsonElement> getAllNodes(JsonElement parent) {
			List<JsonElement> res = new ArrayList<JsonElement>();
			if(parent.isJsonArray()){
				JsonArray array = parent.getAsJsonArray();
				for (JsonElement e : array) {
					res.add(e);
					res.addAll(getAllNodes(e));
				}
			} else if (parent.isJsonObject()){
				JsonObject object = parent.getAsJsonObject();
				// shuld be parent in list?
				Set<Entry<String,JsonElement>> set = object.entrySet();
				for (Entry<String, JsonElement> e : set) {
					res.add(e.getValue());
					res.addAll(getAllNodes(e.getValue()));
				}
			}
			return res;
		}

		@Override
		public String toString() {
			return "..";
		}
	}
	
	
	
	public static class JsonPathException extends RuntimeException{
		private static final long serialVersionUID = 1L;
		public JsonPathException(String message) {
			super(message);
		}
	}
	
	enum ReaderStates{
		inSquareBrackets, rightAfterSquareBrackets, inPlaneString, inBracketsAreNumber
	}
	
	static public class Expression{
		private final List <PathNode> nodes;
		public Expression(List <PathNode> nodes) {
			this.nodes = nodes;
		}
		public List<PathNode> getNodes() {
			return nodes;
		}
		
		List<JsonElement> exec(String strJson){
			return exec(new JsonParser().parse(strJson));
		}
		
		List<JsonElement> exec(JsonElement obj){
			ArrayList<JsonElement> list = new ArrayList<JsonElement>();
			list.add(obj);
			int filterPosition = 0;
			List<JsonElement> res = exec(list, filterPosition);
			return res;
		}
		
		private List<JsonElement> exec(List<JsonElement> in, int filterPosition){
			List <JsonElement> res = new ArrayList<JsonElement>();
			
			if(filterPosition>=nodes.size()){
				res.addAll(in);
				return in;
			} else {
				for (JsonElement next : in) {
					PathNode pathNode = nodes.get(filterPosition);
					List<JsonElement> execed = exec(pathNode.filter(next), filterPosition+1);// return only first matches
					res.addAll(execed);
				}
			}
			return res;
		}
		
	} 

	private void onNewNodeStart() {
		itWasElementInBrackets = false;
	}
	
	Expression parseExpression(String str) throws JsonPathException{

		if(str==null){
			throw new JsonPathException("null input");
		}
		if(str.length()==0){
			throw new JsonPathException("empty string");
		}
		if(Character.isWhitespace(str.charAt(0)) || Character.isWhitespace(str.charAt(str.length()-1))){
			throw new JsonPathException("input string should be trimmed");
			// trimming inside will break offsets and information about other errors will be incorrect
		}

		// create a new scanner with the specified String Object
		char ss;
		StringBuilder buf = new StringBuilder();
		// List <String> res = new ArrayList<String>();
		List <PathNode> res = new ArrayList<PathNode>();
		
		int length = str.length()-1;
		Stack<ReaderStates> stack = new Stack<ReaderStates>();
		
		escapeInString = false;
		itWasElementInBrackets = false;
		int sequentialDotsCount = 0;
		
		if(str.charAt(0) != '$'){
			throw new JsonPathException("input string should start from '$' sign"); // i don't like this, but i left this for compatibility with sta 
		}
		if(str.length()<2){
			throw new JsonPathException("input string is to short"); 
		}
		
		for (int i = 1; i <= length; i++) {
			
			ss = str.charAt(i);
			
			if(sequentialDotsCount>0 && '.' != ss){
				sequentialDotsCount = 0;
			}
			
			if(stack.isEmpty()){ // not in string, not in brackets  
				if('.' == ss){
					if(sequentialDotsCount==1){
						res.add(new RecursiveDescentPathNode());
						sequentialDotsCount++;
					} else if(sequentialDotsCount>1){ 
						throw new JsonPathException("invalid expression syntax: no than two dots in a row at:"+i+" position)");
					} else {
						if(buf.toString().length()>0){// avoid empty property on start
							res.add(new NamedPropertyPathNode(buf.toString()));
							buf = new StringBuilder();
							onNewNodeStart();
						}
						sequentialDotsCount++;
					}
				} else if('[' == ss){
					if(i != 0 && i != 1){
						// avoid first empty-string property
						// theoretically it can be in start
						// {'':[null]} - '[0]' 
						// [null] - '[0]'
						res.add(new NamedPropertyPathNode(buf.toString())); 
					}
					buf = new StringBuilder();
					onNewNodeStart();
					stack.add(ReaderStates.inSquareBrackets);
				} else if(Character.isWhitespace(ss)){
					throw new JsonPathException("invalid expression syntax: unexpected  whitespace at:"+i+" position)");
				} else {
					buf.append(ss);
					if(i == length){ // last char
						res.add(new NamedPropertyPathNode(buf.toString()));
						buf = new StringBuilder();
						onNewNodeStart();
					}
				}
			} else {
				if(stack.peek() == ReaderStates.inSquareBrackets){
					if(']' == ss){
						// string and numbers are collected already, but this behavior can be changed in the future
						stack.pop();
						stack.add(ReaderStates.rightAfterSquareBrackets);
						itWasElementInBrackets = false;

					} else if('\'' == ss){
						if(itWasElementInBrackets){
							throw new JsonPathException("invalid expression syntax: there is allowed only one element in square brackets(new unexpected string start at:"+i+" position)");
						} else {
							buf = new StringBuilder();
							onNewNodeStart();
							stack.add(ReaderStates.inPlaneString);							
						}
		 			} else if(isArabicDigitChar(ss)){
		 				if(itWasElementInBrackets){
							throw new JsonPathException("invalid expression syntax: there is allowed only one element in square brackets(new unexpected digit start at:"+i+" position)");
						} else {
							buf = new StringBuilder();
							buf.append(ss); // keep this first digit
							onNewNodeStart();
							stack.add(ReaderStates.inBracketsAreNumber);							
						}
		 			} else if(Character.isWhitespace(ss)){
		 				// ignore whitespace in brackets that is not in plain string 
		 			} else if('*' == ss){
		 				if(itWasElementInBrackets){
							throw new JsonPathException("invalid expression syntax: there is allowed only one element in square brackets(new unexpected wildcard start at:"+i+" position)");
						} else {
							itWasElementInBrackets = true;
							res.add(new WildcardPathNode());
						}
					} else {
						// currently i decide to do all as strict as possible, 
						// so far in this implementation inside of square brackets 
						// ONLY plain strings, numbers and white spaces around its is supported
						throw new UnsupportedOperationException();
					}
				} else if (stack.peek() == ReaderStates.rightAfterSquareBrackets) {
					if('.' == ss){
						// all fine
						stack.pop();
					} else if('[' == ss){
						stack.pop();
						stack.add(ReaderStates.inSquareBrackets);
					} else {
						throw new JsonPathException("invalid expression syntax");
					}
				} else if (stack.peek() == ReaderStates.inPlaneString){
					
					if(escapeInString){
						// TODO: how it works in other parsers?
						// test data : "c['asas[2].hg'][0]['1\\'s\\\\ds\\d''s']" 
						if('\\' != ss && '\'' != ss){
							buf.append('\\');
						}
						buf.append(ss);
						escapeInString = false;
					} else {
						if('\'' == ss){
							itWasElementInBrackets = true;
							res.add(new NamedPropertyPathNode(buf.toString()));
							buf = new StringBuilder();
							stack.pop();
						} else if ('\\' == ss){
							escapeInString = true;
						} else {
							buf.append(ss);
						}
					}
				} else if (stack.peek() == ReaderStates.inBracketsAreNumber){
					if(isArabicDigitChar(ss)){
						buf.append(ss);
					} else {
						// even there is only numbers, but exception still can be in case of too big number
						int number = Integer.parseInt(buf.toString()); 
						buf = new StringBuilder();
						res.add(new ArrayIndexPathNode(number));
						itWasElementInBrackets = true;
						stack.pop();
						
						// it could be even exit from brackets
						if (']' == ss){
							buf = new StringBuilder();
							onNewNodeStart();
							stack.pop();
							stack.add(ReaderStates.rightAfterSquareBrackets);
						}
					}
				} else {
					throw new JsonPathException("invalid expression syntax(this cause internal error with unknow state to peek)");
				}
				
			}
		}
		
		if(!stack.isEmpty() && stack.peek() == ReaderStates.rightAfterSquareBrackets){
			stack.pop();
		}
		
		if(!stack.isEmpty()){
			throw new JsonPathException("invalid expression syntax");
		}
		// System.out.println(">>>");
		return new Expression(res);
	}
		
	private boolean isArabicDigitChar(char ss) {
		return (ss>=48 && ss<=57); 
	}

	public static void main(String[] args) throws JsonPathException {
		
		String json = "{'c':[{'v':5},{'v':51},{'v':52},{'v':{'d': 777}}]}";
		RecursiveDescentPathNode node = new  RecursiveDescentPathNode();
		List<JsonElement> list = node.filter(new JsonParser().parse(json));
		
		assertEquals(10, list.size());
		
		assertEquals(5, list.get(2).getAsInt());
		assertEquals(777, list.get(9).getAsInt());
	}

	private static void assertEquals(Object a, Object b) {
		if((a==null && b!=null) ||  !a.equals(b) ){
			throw new RuntimeException("<"+a+"> is not same as <"+b+">");
		}
	}
}
