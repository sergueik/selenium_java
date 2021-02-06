package example.nodetypes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import example.iterators.OneItemIterator;
import example.iterators.PeekableIterator;

public class NamedPropertyPathNode implements PathNode {
	private String name;

	public NamedPropertyPathNode(String name) {
		this.name = name;
	}

	public PeekableIterator<JsonElement> filter(JsonElement parent) {
		if (parent.isJsonObject()) {
			JsonObject parentObj = parent.getAsJsonObject();
			if (parentObj.has(name)) {
				JsonElement element = parentObj.get(name);
				return new OneItemIterator<JsonElement>(element);
			}
		}
		return EMPTY_ITERATOR;
	}

	@Override
	public String toString() {
		return "\"" + name + "\"";
	}
}