package example.nodetypes;

import java.util.ArrayList;

import com.google.gson.JsonElement;

import example.iterators.ArrayListPeekableIterator;
import example.iterators.PeekableIterator;

public abstract interface PathNode {
	final PeekableIterator<JsonElement> EMPTY_ITERATOR = new ArrayListPeekableIterator<JsonElement>(
			new ArrayList<JsonElement>());

	abstract PeekableIterator<JsonElement> filter(JsonElement parent);
}