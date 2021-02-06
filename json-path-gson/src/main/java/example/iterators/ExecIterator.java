package example.iterators;

import com.google.gson.JsonElement;

import example.JsonPath2;
import example.JsonPath2.Expression;
import example.nodetypes.PathNode;

/**
 * This iterator recursively iterate over JSON tree and evaluate appropriate expression part for each node.
 * All nodes, that match to whole expression is accessible via iterator.
 * This class is a part of GsonPath library(<a href="https://github.com/msangel/JsonPath-Gson">https://github.com/msangel/JsonPath-Gson</a>)<br/>.
 * License:  Apache License 2.0 
 * @author msangel &lt;h6.msangel@gmail.com&gt; 
 *
 */
public class ExecIterator extends PeekableIterator<JsonElement> {

	private final JsonPath2.Expression expression;
	private final PeekableIterator<JsonElement> in;
	private final int filterPosition;
	private PeekableIterator<JsonElement> current;
	private JsonElement next = null;
	private boolean isNextTaken = false;

	public ExecIterator(JsonPath2.Expression expression, PeekableIterator<JsonElement> in, int filterPosition) {
		this.expression = expression;
		this.in = in;
		this.filterPosition = filterPosition;
	}

	public boolean hasNext() {
		if(current!=null){ // if have current iterator - delegate checking to it
			return current.hasNext();
		}
		if(!isNextTaken){
			this.next = takeNext();
			isNextTaken = true;
		}
		return !(next==null);
	}
	

	public JsonElement next(){
		if(isNextTaken){
			isNextTaken = false;
			return next;
		} else {
			return takeNext();
		}
	}

	/** 
	 * This function return only next or null. This function should not change {@link #isNextTaken} and {@link #next} fields.
	 * @return next item in iteration.
	 */
	private JsonElement takeNext(){
		if(current!=null){ // if here - current has least one item
			if(current.hasNext()){
				JsonElement next = current.next();
				if(!current.hasNext()){
					current = null;
				}
				return next;
			} else {
				current = null;
			}
		}
		
		if(filterPosition>=this.expression.getNodes().size()){
			if(in.hasNext()){
				return in.next();
			} else {
				return null;
			}
		}
		
		
		PathNode pathNode = expression.getNodes().get(filterPosition);
		
		while (in.hasNext()){
			JsonElement next = in.next();
			PeekableIterator<JsonElement> filtered = pathNode.filter(next); // current element children
			if(filtered.hasNext()){
				// cases: 
				// 1) no items - skip this case and trying to get item from next iteration  
				// 2) one item - return it
				// 3) few items - save 'current' iterator for accession other items in this iterator for the next time.
				ExecIterator iter = new ExecIterator(this.expression, filtered, filterPosition+1);
				if(iter.hasNext()){
					JsonElement returned = iter.next();
					if(iter.hasNext()){ // few items
						current = iter;
					}
					return returned; 
				} 
			} // no items - else in.next() and once again till not get result or all list is ended
			//  move this all to 'hasNext' for keeping logic correct
		}
		return null; // no items at all
	}
	
	@Override
	public JsonElement peek() {
		if(current!=null){
			return current.peek();
		}
		if(!isNextTaken){
			this.next = takeNext();
		}
		return next;
	}
}