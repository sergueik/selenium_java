package example;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import example.JsonPath2.Expression;
import example.nodetypes.NamedPropertyPathNode;
import example.nodetypes.PathNode;
import example.nodetypes.RecursiveDescentPathNode;

public class Parser {

	enum ReaderStates{
		inSquareBrackets, rightAfterSquareBrackets
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
		
		int sequentialDotsCount = 0;
		
		if(str.charAt(0) != '$'){
			throw new JsonPathException("input string should start from '$' sign"); // i don't like this, but i left this for compatibility with sta 
		}
		if(str.length()<2){
			throw new JsonPathException("input string is to short"); 
		}
		
		BracketsParser brParser = null;
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
						}
						sequentialDotsCount++;
					}
				} else if('[' == ss){
					
					if(i != 0 && i != 1 && buf.length()!=0){
						// avoid first empty-string property
						// theoretically it can be in start
						// {'':[null]} - '[0]' 
						// [null] - '[0]'
						res.add(new NamedPropertyPathNode(buf.toString())); 
					}
					buf = new StringBuilder();
					stack.add(ReaderStates.inSquareBrackets);
					brParser = new BracketsParser(i);
				} else if(Character.isWhitespace(ss)){
					throw new JsonPathException("invalid expression syntax: unexpected  whitespace at:"+i+" position)");
				} else {
					buf.append(ss);
					if(i == length){ // last char
						res.add(new NamedPropertyPathNode(buf.toString()));
						buf = new StringBuilder();
					}
				}
			} else {
				if(stack.peek() == ReaderStates.inSquareBrackets){
					brParser.consume(ss);
					PathNode result = brParser.getResult();
					if(result!=null){
						res.add(result);
						stack.pop();
						stack.add(ReaderStates.rightAfterSquareBrackets);
					}
					
				} else if (stack.peek() == ReaderStates.rightAfterSquareBrackets) {
					if('.' == ss){
						// all fine
						stack.pop();
					} else if('[' == ss){
						stack.pop();
						stack.add(ReaderStates.inSquareBrackets);
						brParser = new BracketsParser(i);
					} else {
						throw new JsonPathException("invalid expression syntax");
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


}
