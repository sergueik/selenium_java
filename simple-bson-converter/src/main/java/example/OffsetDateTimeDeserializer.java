
package example;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneOffset;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class OffsetDateTimeDeserializer
		extends JsonDeserializer<OffsetDateTime> {

	@Override
	public OffsetDateTime deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		// overview
		// Bson represents objects of type OffsetDateTime as a number corresponding
		// to the milliseconds passed since 1970.
		// The jsonParser converts this number to the following format: "EEE MMM dd
		// kk:mm:ss zzzz yyyy".

		try {
			// parsed string
			String timestamp = jp.getText();

			// parsing the timestamp to a Date
			DateFormat df = new java.text.SimpleDateFormat(
					"EEE MMM dd kk:mm:ss zzzz yyyy", Locale.ENGLISH);
			Date date = df.parse(timestamp);

			// extracting the properties needed to build a OffsetDateTime object
			Calendar c = Calendar.getInstance();
			c.setTime(date);

			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH) + 1; // month is zero-based
			int day = c.get(Calendar.DAY_OF_MONTH);
			int hours = c.get(Calendar.HOUR);
			int mins = c.get(Calendar.MINUTE);
			int sec = c.get(Calendar.SECOND);
			int nanos = 0;
			ZoneOffset zoneOffset = OffsetDateTime.now().getOffset();

			// building the OffsetDateTim object
			OffsetDateTime x = OffsetDateTime.of(year, month, day, hours, mins, sec,
					nanos, zoneOffset);

			return x;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}