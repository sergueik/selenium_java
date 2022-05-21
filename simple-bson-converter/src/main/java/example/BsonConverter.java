package example;

import java.io.ByteArrayOutputStream;
import java.util.Date;

import org.threeten.bp.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import de.undercouch.bson4jackson.BsonFactory;

public class BsonConverter {

	public byte[] toBson(Object input) {

		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ObjectMapper mapper = getBsonMapper();

			mapper.writeValue(outputStream, input);
			return outputStream.toByteArray();
		} catch (Exception e) {
			return null;
		}
	}

	public Object fromBson(byte[] output, Class<?> type) {
		try {
			ObjectMapper mapper = getBsonMapper();
			Object result = mapper.readValue(output, type);

			return result;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	private ObjectMapper getBsonMapper() {

		ObjectMapper mapperExplicit = new ObjectMapper(new BsonFactory());
		mapperExplicit.setSerializationInclusion(Include.NON_NULL);
		mapperExplicit.disable(SerializationFeature.FLUSH_AFTER_WRITE_VALUE);
		mapperExplicit.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

		// add custom deserializers

		// OffsetDateTime
		SimpleModule offsetDateTimeDes = new SimpleModule(
				"OffsetDateTimeDesirializer");
		offsetDateTimeDes.addDeserializer(OffsetDateTime.class,
				new OffsetDateTimeDeserializer());
		mapperExplicit.registerModule(offsetDateTimeDes);
		// Date
		SimpleModule dateDes = new SimpleModule("DateDesirializer");
		dateDes.addDeserializer(Date.class, new DateDeserializer());
		mapperExplicit.registerModule(dateDes);

		return mapperExplicit;
	}

}