package example.iterators;

public class OneItemIterator<T> extends PeekableIterator<T>{

	private T element;
	boolean isTaken = false;

	public OneItemIterator(T element) {
		this.element = element;
	}

	public boolean hasNext() {
		return !isTaken;
	}

	public T next() {
		if(!isTaken){
			isTaken = true;
			return element;	
		} else {
			return null;
		}
	}

	@Override
	public T peek() {
		if(!isTaken){
			return element;	
		} else {
			return null;
		}
	}
	
}