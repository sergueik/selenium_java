package example.iterators;

import java.util.Iterator;

public abstract class PeekableIterator<T> implements Iterator<T>{
	abstract public T peek();
	public void remove() {}
}