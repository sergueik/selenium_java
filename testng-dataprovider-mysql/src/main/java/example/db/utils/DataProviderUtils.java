package example.db.utils;

import example.annotations.Entity;
import example.db.dao.DAO;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.*;

public final class DataProviderUtils {

	public static final String GENERIC_DP = "genericDP";

	@SuppressWarnings("unchecked")
	@DataProvider(name = GENERIC_DP)
	public static Iterator<Object[]> provideDbData(final Method method) {
		return Optional.ofNullable(method.getDeclaredAnnotation(Entity.class))
				.map(annotation -> new DAO(annotation.model(), annotation.schema()))
				.map(dao -> (List<Object[]>) dao.selectAllAndTransform())
				.orElse(new ArrayList<>()).iterator();
	}

	private DataProviderUtils() {
		throw new UnsupportedOperationException(
				"Illegal access to private constructor");
	}
}
