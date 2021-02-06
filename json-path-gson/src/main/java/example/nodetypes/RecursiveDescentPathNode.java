package example.nodetypes;

import com.google.gson.JsonElement;

import example.iterators.PeekableIterator;
import example.iterators.WildcardIterator;

public class RecursiveDescentPathNode implements PathNode {

	private static class DescentPathIterator extends PeekableIterator<JsonElement> {

		private WildcardIterator iterator;
		private boolean wasFirst = false;
		private JsonElement parent;
		private DescentPathIterator stored;
		private boolean nextWasTaken = false;
		private JsonElement next;
		private boolean needIncludeRoot;

		public DescentPathIterator(JsonElement parent, boolean needIncludeRoot) {
			this.needIncludeRoot = needIncludeRoot;
			this.parent = parent;
			this.iterator = new WildcardIterator(parent);
		}

		public boolean hasNext() {
			if (!nextWasTaken) {
				this.next = takeNext();
				nextWasTaken = true;
			}
			return this.next != null;
		}

		public JsonElement next() {
			if (!nextWasTaken) {
				this.next = takeNext();
			} else {
				nextWasTaken = false;
			}
			return this.next;
		}

		public JsonElement takeNext() {
			if (stored != null) {
				if (stored.hasNext()) {
					JsonElement nextItem = stored.next();
					if (!stored.hasNext()) {
						stored = null;
					}
					return nextItem;
				} else {
					stored = null;
				}
			}

			if (!wasFirst && needIncludeRoot) {
				wasFirst = true;
				return parent;
			} else {
				while (iterator.hasNext()) {
					JsonElement nextItem = iterator.next();
					if (nextItem.isJsonArray() || nextItem.isJsonObject()) {
						DescentPathIterator current = new DescentPathIterator(nextItem, true);
						if (current.hasNext()) {
							JsonElement res = current.next();
							if (current.hasNext()) {
								this.stored = current;
							}
							return res;
						}
					} else {
						return nextItem;
					}
				}
			}
			return null;
		}

		@Override
		public JsonElement peek() {
			if (!nextWasTaken) {
				this.next = takeNext();
				nextWasTaken = true;
			}
			return this.next;
		}

	}

	public PeekableIterator<JsonElement> filter(JsonElement parent) {
		return new DescentPathIterator(parent, false);
	}

	@Override
	public String toString() {
		return "..";
	}
}