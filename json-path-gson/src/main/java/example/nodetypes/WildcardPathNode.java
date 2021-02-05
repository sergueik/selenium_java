package example.nodetypes;

import com.google.gson.JsonElement;

import example.iterators.PeekableIterator;
import example.iterators.WildcardIterator;

public class WildcardPathNode implements PathNode {
	public PeekableIterator<JsonElement> filter(JsonElement parent) {
		return new WildcardIterator(parent);
	}

	@Override
	public String toString() {
		return "*";
	}
}