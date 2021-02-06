package example.iterators;

import static example.JsonPath2.getStackOffset;

import java.util.ArrayList;
import java.util.ListIterator;

public class ArrayListPeekableIterator<T> extends PeekableIterator<T>{

	private ListIterator<T> iter;

	public ArrayListPeekableIterator(ArrayList<T> in) {
		this.iter = in.listIterator();
	}
	
	public boolean hasNext() {
		boolean hasNext = iter.hasNext();
		return hasNext;
	}

	public T next() {
		return iter.next();
	}

	public void remove() {
		iter.remove();
	}

	/**
	 * Take next item but not shift position.
	 */
	public T peek() {
		if(hasNext()){
			T res = iter.next();
			iter.previous();// return back
			return res;
		} else {
			return null;
		}
	}
}