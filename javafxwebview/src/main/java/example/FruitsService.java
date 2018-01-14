package example;

import static example.Java2JavascriptUtils.call;
import static java.lang.Thread.sleep;
import static javafx.application.Platform.runLater;

import static java.util.Collections.shuffle;
import netscape.javascript.JSObject;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Arrays;
import java.util.List;

public class FruitsService {

	// DTO mockup 
	public static class Fruit {
		private String name;

		public Fruit(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		@Override
		public String toString() {
			// return getName();
			// Exception: SyntaxError: JSON Parse error: Unexpected identifier
			// "orange", data: '[orange,apple,banana,strawberry]'
			return String.format("\"%s\"", this.name);
		}
	}

	// Service mockup
	private static Fruit[] fruits;
	static {
		fruits = new Fruit[] { new Fruit("orange"), new Fruit("apple"),
				new Fruit("banana"), new Fruit("strawberry") };
	}

	public static Fruit[] getFruits() {
		System.err
				.println("getFruits called and returns: " + Arrays.toString(fruits));
		return fruits;
	}

	@SuppressWarnings("restriction")
	public void loadFruits(final JSObject callback) {

		// launch a background thread
		System.err.println(
				"loadFruits called with: " + callback.toString().substring(0, 100));
		JSONArray fruitList = new JSONArray();
		for (Fruit fruit : Arrays.asList(FruitsService.getFruits())) {
			fruitList.add((Object) fruit);
		}
		new Thread(() -> {
			try {
				shuffleFruits();
				sleep(1000); // add some processing simulation...
				runLater(() -> {
					call(callback, fruitList);
				});

			} catch (InterruptedException e) {
			}
		}).start();

	}

	private static void shuffleFruits() {
		List<Fruit> list = Arrays.asList(FruitsService.fruits);
		shuffle(list);
		FruitsService.fruits = list.toArray(new Fruit[] {});
	}

}
