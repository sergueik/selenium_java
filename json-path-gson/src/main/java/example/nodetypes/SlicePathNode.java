package example.nodetypes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import example.JsonPathException;
import example.iterators.PeekableIterator;

public class SlicePathNode implements PathNode {
	// http://stackoverflow.com/a/509295/449553

	/**
	 * 
	 * Note: I wrote this class according to specs and testing all behave with
	 * python, but there is a lot that i don't like.
	 *
	 */
	private static class SliceIterator extends PeekableIterator<JsonElement> {

		private int startIndex;
		private int endIndex;
		private int position;
		private int step;
		private JsonArray arr;

		/**
		 * no requested indexes in arr - nothing to return;
		 */
		private boolean isOutOfRange = false;

		public SliceIterator(JsonArray arr, Integer from, Integer to, Integer step) {
			this.step = step;
			this.arr = arr;
			calculatePosition(from, to);
			validateRanges();
			this.position = this.startIndex - this.step;// before first element
		}

		public boolean hasNext() {
			if (isOutOfRange) {
				return false;
			}
			if (step > 0) {
				if (position + step < endIndex) {
					return true;
				} else {
					return false;
				}
			} else {
				if (position + step > endIndex) {
					return true;
				} else {
					return false;
				}
			}
		}

		public JsonElement next() {
			if (isOutOfRange) {
				return null; // TODO: or NoSuchElementException ?
			}
			position = position + step;
			JsonElement el = arr.get(position);
			return el;
		}

		@Override
		public JsonElement peek() {
			return arr.get(position);
		}

		// best documentation in this case is tests.
		// thats all is about ranges, inclusion and slice syntax standards(from python).
		// all that this code is doing - setting default values in ranges, if implicit
		// is not setted another.
		private void calculatePosition(Integer from, Integer to) {
			if (step > 0) {

				if (from != null) {

					if (from >= 0) {
						startIndex = from;
					} else {
						startIndex = arr.size() + from; // +(-x) == -x
					}
				} else {
					startIndex = 0;
				}

				if (to != null) {

					if (to >= 0) {
						endIndex = to;
					} else {
						endIndex = arr.size() + to; // +(-x) == -x
					}
				} else {
					endIndex = arr.size();
				}

			} else if (step < 0) {

				if (from != null) {
					if (from >= 0) {
						startIndex = from;
					} else {
						startIndex = arr.size() + from;
					}
				} else {
					startIndex = arr.size() - 1;
				}

				if (to != null) {
					if (to >= 0) {
						endIndex = to;
					} else {
						endIndex = arr.size() + to;
					}
				} else {
					endIndex = -1; // less then 0, excluding range
				}

			} // if 0 - constructor throw exception
		}

		private void validateRanges() {
			if (step > 0) {
				if (startIndex < 0) {
					startIndex = 0;
				} else if (startIndex > (arr.size() - 1)) {
					this.isOutOfRange = true; // start in indexes where array is ended
				}

				if (endIndex > arr.size()) {
					endIndex = arr.size(); // exclusion
				} else if (endIndex < 0) {
					this.isOutOfRange = true; // finish in indexes, where array is starting
				}

				if (!isOutOfRange && (startIndex > endIndex)) {
					this.isOutOfRange = true; // if still here but startIndex > endIndex
				}
			} else {

				if (startIndex > arr.size() - 1) {
					startIndex = arr.size() - 1;
				} else if (startIndex < 0) {
					this.isOutOfRange = true; // start in indexes, where array is not starting yet
				}
				if (endIndex < -1) { // exclusion
					endIndex = -1;
				} else if (endIndex > (arr.size() - 1)) {
					this.isOutOfRange = true; // start in indexes where array is ended
				}

				if (!isOutOfRange && (startIndex < endIndex)) {
					this.isOutOfRange = true; // in reverse direction startIndex should be greater of endIndex
				}

			}
		}
	}

	private final Integer from;
	private final Integer to;
	private final Integer step;
	private final WildcardPathNode wildcard;

	/*
	 * 
	 * http://wiki.ecmascript.org/doku.php?id=proposals:slice_syntax&s=array+slice
	 * https://mail.mozilla.org/pipermail/es-discuss/2007-August/004424.html
	 * 
	 * if from>to : The description seems to be clear here, the result should be an
	 * empty array.
	 * 
	 */
	public SlicePathNode(Integer from, Integer to, Integer step) {
		this.from = from;
		this.to = to;

		if (step != null) {
			if (step.intValue() == 0) {
				throw new JsonPathException("step can not be 0");
			}
			this.step = step;
		} else {
			this.step = 1;
		}

		// [::]
		// open question should objects use this as wildcard?
		// let it be for now
		if (from == null && to == null && this.step == 1) {
			this.wildcard = new WildcardPathNode();
		} else {
			this.wildcard = null;
		}

	}

	public PeekableIterator<JsonElement> filter(JsonElement parent) {
		if (wildcard != null) {
			return wildcard.filter(parent);
		} else {
			if (parent.isJsonArray()) {
				JsonArray arr = parent.getAsJsonArray();
				return new SliceIterator(arr, from, to, step);
			} else {
				return EMPTY_ITERATOR;
			}
		}
	}

	public Integer getFrom() {
		return from;
	}

	public Integer getStep() {
		return step;
	}

	public Integer getTo() {
		return to;
	}

}
