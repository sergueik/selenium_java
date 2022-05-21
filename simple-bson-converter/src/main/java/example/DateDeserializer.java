package example;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class DateDeserializer extends JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {

		try {
			// parsed string
			String timestamp = jp.getText();

			// parsing the timestamp to a Date
			DateFormat df = new java.text.SimpleDateFormat(
					"EEE MMM dd kk:mm:ss zzzz yyyy", Locale.ENGLISH);
			Date date = df.parse(timestamp);

			return date;

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}
}
