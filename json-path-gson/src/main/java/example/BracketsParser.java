package example;


import static example.JsonPath2.*;

import java.util.Date;
import java.util.LinkedList;

import example.nodetypes.ArrayIndexPathNode;
import example.nodetypes.CSVIndexPathNode;
import example.nodetypes.NamedPropertyPathNode;
import example.nodetypes.PathNode;
import example.nodetypes.SlicePathNode;
import example.nodetypes.WildcardPathNode;
public class BracketsParser {
	// @formatter:off
	enum BracketsState {
		inBegining,
		inPlaneString,
		inBracketsAreNumber,
		inEnd,
		afterNumber,
		afterNumberAfterComma,
		inNumberRangeAfterFirstColon,
		inNumberRangeSecondNumber,
		inNumberRangeAfterSecondNumber,
		inNumberRangeAfterSecondColon,
		inNumberRangeThirdNumber,
		inNumberRangeAfterThirdNumber,
	}
	// @formatter:on
	private StringBuilder buf = new StringBuilder();
	private PathNode result;
	BracketsState state = BracketsState.inBegining;
	private boolean escapeInString = false;
	
	private int position;
	private boolean allowReturn = false;
	
	/**
	 * Used as storage both for es4 slice operator and for CSV.
	 */
	private class NumberStorage extends LinkedList<Integer>{
		private static final long serialVersionUID = 1L;
		
		private boolean firstWasSetted = false;
		private Integer first = null;
		private Integer to = null;
		private Integer step = null;
		
		@Override
		public boolean add(Integer e) {
			if(!firstWasSetted){
				first = e;
				firstWasSetted = true;
			}
			return super.add(e);				
		}
		
		@Override
		public Integer getFirst() {
			return first;
		}
		
		public void setFirst(Integer first) {
			this.first = first;
			if(!firstWasSetted){
				firstWasSetted = true;
			}
			if(size()==0){
				add(first);
			}
		}
		
		public void setTo(Integer to){
			if(size()>1){
				// TODO:  ad test for this
				// [2,3:1]
				throw new JsonPathException("");
			}
			this.to = to;
		}
		
		public Integer getTo() {
			return to;
		}
		
		public Integer getStep() {
			return step;
		}
		
		public void setStep(Integer step) {
			this.step = step;
		}
		
	}
	private NumberStorage storedNumbers = new NumberStorage();
	/*
if(Character.isWhitespace(ss)){
				// skip
			} else if(isArabicDigitChar(ss) || ss == '-'){
				buf = new StringBuilder();
				buf.append(ss); // keep this first digit
				state = BracketsState.inBracketsAreNumberOfToIndex;
			} else if(ss == ':'){
				state = BracketsState.inBracketsAreNumberRangeWithStep;
			} else {
				throw new JsonPathException("invalid content at:"+position+" position: symbol:["+ss+"]");
			}
			break;
	 */
	public BracketsParser(int startPosition) {
		this.position = startPosition;
	}

	public void consume(char ss) throws JsonPathException{
		position++;
		
		switch (state) {
		case inBegining:
			if(']' == ss){
				if(result==null){
					throw new JsonPathException("invalid content at:"+position+" position");
				}
				// string and numbers are collected already, but this behavior can be changed in the future
				allowReturn  = true;
			} else if('\'' == ss){
				buf = new StringBuilder();
				state = BracketsState.inPlaneString;
 			} else if(isArabicDigitChar(ss) || ss == '-'){
 				buf = new StringBuilder();
				buf.append(ss); // keep this first digit
				state = BracketsState.inBracketsAreNumber;
 			} else if(Character.isWhitespace(ss)){
 				// ignore whitespace in brackets that is not in plain string 
 			} else if('*' == ss){
 				result = new WildcardPathNode();
 			} else if(':' == ss){
 				storedNumbers.setFirst(null);
 				state = BracketsState.inNumberRangeAfterFirstColon;
			} else {
				throw new UnsupportedOperationException();
			}
			break;
		case inBracketsAreNumber:
			if(isArabicDigitChar(ss)){
				buf.append(ss);
			} else {
				// even there is only numbers, but exception still can be in case of too big number
				int number = Integer.parseInt(buf.toString());
				
				if(Character.isWhitespace(ss)){
					state = BracketsState.afterNumber;
					storeNumber(number);
				} else if (ss == ','){
					state = BracketsState.afterNumberAfterComma;
					storeNumber(number);
				} else if(ss == ':'){
					if(storedNumbers.size()==0){ // first number
						storedNumbers.setFirst(number);
						state = BracketsState.inNumberRangeAfterFirstColon;
					} else {
						throw new JsonPathException("invalid content at:"+position+" position: symbol:["+ss+"]");
					}
				} else if(ss == ']'){
					storeNumber(number);
					result = getFromStoredNumbres();
					allowReturn = true;
				} else {
					throw new JsonPathException("invalid content at:"+position+" position");
				}
			}
			break;
		case afterNumber:
			if(']' == ss){
				result = getFromStoredNumbres();
				allowReturn = true;
			} else if(Character.isWhitespace(ss)){
				// skip
			} else if (ss == ','){
				state = BracketsState.afterNumberAfterComma;
			} else if (ss == ':'){
				if(storedNumbers.size()==1){ // first number 
					state = BracketsState.inNumberRangeAfterFirstColon;
				} else {
					throw new JsonPathException("invalid content at:"+position+" position: symbol:["+ss+"]");
				}
			} else {
				throw new JsonPathException("invalid content at:"+position+" position: symbol:["+ss+"]");
			}
			break;
		case afterNumberAfterComma:
			if(Character.isWhitespace(ss)){
				// skip
			} else if(isArabicDigitChar(ss)){
				buf = new StringBuilder();
				buf.append(ss); // keep this first digit
				state = BracketsState.inBracketsAreNumber;
			} else {
				throw new JsonPathException("invalid content at:"+position+" position: symbol:["+ss+"]");
			}
			break;
		case inNumberRangeAfterFirstColon:
			if(Character.isWhitespace(ss)){
				// skip
			} else if(isArabicDigitChar(ss) || ss == '-'){
				buf = new StringBuilder();
				buf.append(ss);
				state = BracketsState.inNumberRangeSecondNumber; 
			} else if(ss == ':'){
				storedNumbers.setTo(null);
				state = BracketsState.inNumberRangeAfterSecondColon; 
			} else if(ss == ']'){
				result = new SlicePathNode(storedNumbers.getFirst(), storedNumbers.getTo(), storedNumbers.getStep());
				allowReturn = true;
			} else {
				throw new JsonPathException("invalid content at:"+position+" position: symbol:["+ss+"]");
			}
			break;
		case inNumberRangeSecondNumber:
			if(isArabicDigitChar(ss)){
				buf.append(ss);
			} else {
				// even there is only numbers, but exception still can be in case of too big number
				int number = Integer.parseInt(buf.toString());

				storedNumbers.setTo(number);
				
				if(Character.isWhitespace(ss)){
					state = BracketsState.inNumberRangeAfterSecondNumber;
				} else if(ss == ':'){
					state = BracketsState.inNumberRangeAfterSecondColon; 
				} else if(ss == ']'){
					result = new SlicePathNode(storedNumbers.getFirst(), storedNumbers.getTo(), storedNumbers.getStep());
					allowReturn = true;
				} else {
					throw new JsonPathException("invalid content at:"+position+" position: symbol:["+ss+"]");
				}
			}
			break;
		case inNumberRangeAfterSecondNumber:
			if(Character.isWhitespace(ss)){
				// skip
			} else if(ss == ':'){
				state = BracketsState.inNumberRangeAfterSecondColon; 
			} else if(ss == ']'){
				result = new SlicePathNode(storedNumbers.getFirst(), storedNumbers.getTo(), storedNumbers.getStep());
				allowReturn = true;
			} else {
				throw new JsonPathException("invalid content at:"+position+" position: symbol:["+ss+"]");
			}
			break;
		case inNumberRangeAfterSecondColon:
			if(Character.isWhitespace(ss)){
				// skip
			} else if(isArabicDigitChar(ss) || ss == '-'){
				buf = new StringBuilder();
				buf.append(ss);
				state = BracketsState.inNumberRangeThirdNumber; 
			} else if(ss == ']'){
				result = new SlicePathNode(storedNumbers.getFirst(), storedNumbers.getTo(), null);
				allowReturn = true;
			} else {
				throw new JsonPathException("invalid content at:"+position+" position: symbol:["+ss+"]");
			}
			break;
		case inNumberRangeThirdNumber:
			if(isArabicDigitChar(ss)){
				buf.append(ss);
			} else {
				int number = Integer.parseInt(buf.toString());
				storedNumbers.setStep(number);
				
				if(Character.isWhitespace(ss)){
					state = BracketsState.inNumberRangeAfterThirdNumber;
				} else if(ss == ']'){
					result = new SlicePathNode(storedNumbers.getFirst(), storedNumbers.getTo(), storedNumbers.getStep());
					allowReturn = true;
				} else {
					throw new JsonPathException("invalid content at:"+position+" position: symbol:["+ss+"]");
				}
			}
			break;
		case inNumberRangeAfterThirdNumber:
			if(Character.isWhitespace(ss)){
				// skip
			} else if(ss == ']'){
				result = new SlicePathNode(storedNumbers.getFirst(), storedNumbers.getTo(), storedNumbers.getStep());
				allowReturn = true;
			} else {
				throw new JsonPathException("invalid content at:"+position+" position: symbol:["+ss+"]");
			}
			break;
		case inEnd:
			if(']' == ss){
				if(result==null){
					throw new JsonPathException("invalid content at:"+position+" position: symbol:["+ss+"]");
				}
				// string and numbers are collected already, but this behavior can be changed in the future
				allowReturn  = true;
			} else if(Character.isWhitespace(ss)){
 				// ignore whitespace in brackets that is not in plain string 
			} else {
				throw new UnsupportedOperationException("invalid content at:"+position+" position: symbol:["+ss+"]");
			}
			break;
		case inPlaneString:

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
					result = new NamedPropertyPathNode(buf.toString());
					buf = new StringBuilder();
					state = BracketsState.inEnd;
				} else if ('\\' == ss){
					escapeInString = true;
				} else {
					buf.append(ss);
				}
			}
			break;
		default:
			break;
		
		}
	}

	private PathNode getFromStoredNumbres() {
		if(storedNumbers.size()==0){
			throw new JsonPathException("in java SDK shuld be ImpossibleException (as RuntimeException)");
		} else if(storedNumbers.size()==1){
			Integer index = storedNumbers.get(0);
			return new ArrayIndexPathNode(index);
		} else {
			return new CSVIndexPathNode(storedNumbers);
		}
	}

	private void storeNumber(int number) {
		storedNumbers.add(number);
	}

	public void consumeAll(String s) {
		for (char e : s.toCharArray()) {
			consume(e);
		}
	}
	
	private boolean isArabicDigitChar(char ss) {
		return (ss>=48 && ss<=57); 
	}

	
	public PathNode getResult(){
		if(allowReturn){
			return result;	
		}
		return null;
	}
	
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
/*
 * 
ref:

http://goessner.net/articles/JsonPath/
http://wiki.ecmascript.org/doku.php?id=proposals:slice_syntax&s=array+slice 

cases:


[(@.length-1)]
[-1:]

[?(@.isbn)]
[?(@.price<10)]
['a', 'b'] 

$..* - all

?:

$..
$.price..


*/
		System.out.println(new Date().getHours()+":"+new Date().getMinutes());
	
		BracketsParser a = new BracketsParser(0);
		SlicePathNode casted;

		a.consumeAll(" : :  4  ]"); // same as 0:(size):4
		assertTrue(SlicePathNode.class.isInstance(a.getResult()));
		casted = SlicePathNode.class.cast(a.getResult());
		assertTrue(casted.getStep() == 4);
		
		System.out.println("ok");
	}
}
