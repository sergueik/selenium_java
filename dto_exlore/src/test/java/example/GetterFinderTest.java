package example;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.math.BigDecimal;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.UUID;
import java.util.function.Supplier;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

public class GetterFinderTest {

	private static Method getter;
	private static Artist artist = null;
	private final static Map<String, Method> propertyGetters = new HashMap<>();

	@Test
	public void test1() throws Exception {

		artist = new Artist(1, "paul", "vocals", "field1 data", "field2 data");
		if (artist != null) {
			if (propertyGetters.keySet().size() == 0) {
				for (final Method method : artist.getClass().getMethods()) {
					final String methodName = method.getName();

					String propertyName;
					if (methodName.startsWith("get")
							&& method.getParameters().length == 0) {
						System.err.println("Found the get method: " + methodName);
						propertyName = methodName.substring("get".length());
						if (!propertyGetters.containsKey(propertyName)) {
							propertyGetters.put(propertyName, method);
						}
					}
				}

			}
		}
		Method getter = propertyGetters.get(capitalize("name"));
		final Object result = getter.invoke(artist);
		assertThat(result.toString(), is("paul"));
	}

	@Test
	public void test2() throws Exception {

		Utils utils = Utils.getInstance();
		artist = new Artist(1, "paul", "vocals", "field1 data", "field2 data");
		utils.scan(artist);
		utils.scan(artist);
		utils.scan(artist);
		assertThat(utils.getGetter("name").invoke(artist).toString(), is("paul"));
	}

	// based on:
	// http://www.java2s.com/Code/Java/Data-Type/Capitalizethefirstcharacterofthegivenstring.htm
	public static String capitalize(final String data) {
		if ((data == null) || (data.equals("")))
			throw new IllegalArgumentException("invalid argument");
		return Character.toUpperCase(data.charAt(0)) + data.substring(1);
	}

	public static class Utils {

		private static Utils instance = new Utils();
		private boolean debug = false;
		private final static Map<String, Method> propertyGetters = new HashMap<>();
		// TODO: use for caching multiple classes scan - unsure this is really
		// needed
		private final static Map<String, Boolean> scannedClasses = new HashMap<>();

		public void setDebug(boolean value) {
			debug = value;
		}

		private Utils() {
		}

		public static Utils getInstance() {
			return instance;
		}

		public Method getGetter(String data) {
			return propertyGetters.get(unCapitalize(data));
		}

		public void scan(Object target) {
			if (target != null) {
				if (propertyGetters.keySet().size() == 0) {
					for (final Method method : target.getClass().getMethods()) {
						final String methodName = method.getName();

						String propertyName;
						if (methodName.startsWith("get")
								&& method.getParameters().length == 0) {
							System.err.println("Found the get method: " + methodName);
							propertyName = methodName.substring("get".length());
							if (!propertyGetters.containsKey(propertyName)) {
								propertyGetters.put(unCapitalize(propertyName), method);
							}
						}
					}

				}
			}
		}

		private static String unCapitalize(final String data) {
			if ((data == null) || (data.equals("")))
				throw new IllegalArgumentException("invalid argument");

			return Character.toLowerCase(data.charAt(0)) + data.substring(1);
		}
	}
}
