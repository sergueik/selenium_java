package example.webviewdemo.backend;

import static example.webview.Java2JavascriptUtils.call;
import static java.lang.Thread.sleep;
import static javafx.application.Platform.runLater;

import static java.util.Arrays.asList;
import static java.util.Collections.shuffle;
import netscape.javascript.JSObject;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("restriction")
public class FruitsService {

	// since references from Javascript appear not taken into account for GC,
	// when fruits is a local variable in the method,
	// is may be garbage collected too early
	private static Fruit[] fruits;
	static {
		fruits = new Fruit[] { new Fruit("apricot"), new Fruit("banana"), new Fruit("cherry"), new Fruit("durian"),
				new Fruit("elderberry"), new Fruit("fig"), new Fruit("grape") };
	}

	// async function
	public void loadFruits(final JSObject callbackfunction) {
		new Thread(() -> {
			try {
				shuffleFruits();
				sleep(1000); // backend processing simulation
				runLater(() -> {
					System.err.println("return data: " + Arrays.asList(FruitsService.fruits));
					call(callbackfunction, (Object) FruitsService.fruits);
				});

			} catch (InterruptedException e) {
			}
		}).start();
	}

	private static void shuffleFruits() {
		List<Object> list = asList(FruitsService.fruits);
		shuffle(list);
		FruitsService.fruits = list.toArray(new Fruit[] {});
	}

	public static class Fruit {
		private String name;

		public Fruit(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public String toString() {
			return String.format("%s: %s", this.getClass().getCanonicalName(), name);
		}
	}
}
