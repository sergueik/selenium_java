package example;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ArtistSerializer implements JsonSerializer<Artist> {
	private List<String> reportFields = new ArrayList<>();

	public void setReportFields(List<String> data) {
		reportFields = data;
	}

	public void reset() {
		reportFields.clear();
	}

	// will collide ?
	public void setReportFields(String... fields) {
		for (int cnt = 0; cnt != fields.length; cnt++) {
			reportFields.add(fields[cnt]);
		}
	}

	@SuppressWarnings("unused")
	@Override
	public JsonElement serialize(final Artist data, final Type type,
			final JsonSerializationContext context) {
		JsonObject result = new JsonObject();
		int id = data.getId();
		if (id != 0) {
			result.add("id", new JsonPrimitive(id));
		}
		// added static info from the serialized class
		// NPE
		if (type != null) {
			result.add("staticInfo",
					new JsonPrimitive(((Artist) type).getStaticInfo()));
		} else {
			String staticInfo = data.getStaticInfo();
			System.err.println("Static info: " + staticInfo);
			if (staticInfo != null) {
				result.add("staticInfo", new JsonPrimitive(staticInfo));
			}
		}
		// filter what to (not) serialize

		if (reportFields.size() == 0 || reportFields.contains("name")) {
			String name = data.getName();
			// the getter name may be funky. Determine method name through reflection
			// could be
			// expensive
			if (name != null && !name.isEmpty()) {
				result.add("name", new JsonPrimitive(name));
			}
		}

		if (reportFields.size() == 0 || reportFields.contains("plays")) {
			String plays = data.getPlays();
			if (plays != null && !plays.isEmpty()) {
				result.add("plays", new JsonPrimitive(plays));
			}
		}
		/*
		 * Float price = data.getPrice(); result.add("price", new
		 * JsonPrimitive(price));
		 */
		return result;
	}
}
