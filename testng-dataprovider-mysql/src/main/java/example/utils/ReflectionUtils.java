package example.utils;

import javaslang.control.Try;

import java.lang.reflect.Field;

public final class ReflectionUtils {

	public static Try<Object> getFieldValue(final Object entity, final String fieldName) {
		return getReleasedField(entity, fieldName)
				.mapTry(field -> field.get(entity));
	}

	public static void setFieldValue(final Object entity, final String fieldName, final Object value) {
		getReleasedField(entity, fieldName).onSuccess(field -> Try.run(() -> field.set(entity, value)));
	}

	public static Try<Field> getReleasedField(final Object entity, final String fieldName) {
		return Try.of(() -> entity.getClass().getDeclaredField(fieldName))
				.onSuccess(field -> field.setAccessible(true));
	}

	private ReflectionUtils() {
		throw new UnsupportedOperationException("Illegal access to private constructor");
	}
}
