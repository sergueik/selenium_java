package example.webviewdemo.backend;

import static example.webview.Java2JavascriptUtils.call;
import java.lang.Thread;
import javafx.application.Platform;

import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import netscape.javascript.JSObject;

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
				Thread.sleep(1000); // backend processing simulation
				Platform.runLater(() -> {
					System.err.println("return data: " + Arrays.asList(FruitsService.fruits));
					call(callbackfunction, (Object) FruitsService.fruits);
				});

			} catch (InterruptedException e) {
			}
		}).start();
	}

	private static void shuffleFruits() {
		List<Object> list = Arrays.asList(FruitsService.fruits);
		Collections.shuffle(list);
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
