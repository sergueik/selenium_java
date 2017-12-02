package com.driver.locator.writer.gson;

import java.lang.reflect.Type;

import com.driver.locator.model.LocatorModel;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class LocatorSerialiser implements JsonSerializer<LocatorModel> {

	@Override
	public JsonElement serialize(LocatorModel model, Type arg1,
			JsonSerializationContext arg2) {
		final JsonObject json = new JsonObject();
		json.addProperty(model.getLocatorType(), model.getLocatorValue());
		return json;
	}

}
