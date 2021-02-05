package example.nodetypes;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import example.iterators.PeekableIterator;

public class CSVIndexPathNode implements PathNode {

	private static class CSVIndexIterator extends PeekableIterator<JsonElement> {

		private Iterator<Integer> iterator;
		private JsonArray parent;
		private boolean nextIsTaken = false;
		private JsonElement next = null;

		public CSVIndexIterator(Iterator<Integer> iterator, JsonArray parent) {
			this.iterator = iterator;
			this.parent = parent;
		}

		public boolean hasNext() {
			if (!nextIsTaken) {
				next = takeNext();
				nextIsTaken = true;
			}
			return next != null;
		}

		public JsonElement next() {
			if (!nextIsTaken) {
				next = takeNext();
			} else {
				nextIsTaken = false;
			}
			return next;
		}

		@Override
		public JsonElement peek() {
			if (!nextIsTaken) {
				next = takeNext();
				nextIsTaken = true;
			}
			return next;
		}

		private JsonElement takeNext() {
			while (iterator.hasNext()) {
				Integer index = iterator.next();
				ArrayIndexPathNode el = new ArrayIndexPathNode(index);
				PeekableIterator<JsonElement> iter = el.filter(parent);
				// this iterator can contain none elements or only one
				if (iter.hasNext()) {
					return iter.next();
				} else {
					// in sequense CAN be invalid array indexes (to big or to low)
					// so, this value can not have element and we should iterate further
					continue;
				}
			}
			return null;
		}

	}

	private Iterator<Integer> iterator;
	private LinkedList<Integer> indexes;

	public CSVIndexPathNode(LinkedList<Integer> indexes) {
		this.indexes = indexes;
		iterator = indexes.iterator();
	}

	public PeekableIterator<JsonElement> filter(JsonElement parent) {
		if (parent.isJsonArray()) {
			JsonArray array = parent.getAsJsonArray();
			return new CSVIndexIterator(iterator, array);
		} else {
			return EMPTY_ITERATOR;
		}
	}

	public List<Integer> getIndexes() {
		return indexes;
	}
}
