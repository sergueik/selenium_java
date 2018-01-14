package example;

import static example.Java2JavascriptUtils.call;
import static java.lang.Thread.sleep;
import static javafx.application.Platform.runLater;

import static java.util.Arrays.asList;
import static java.util.Collections.shuffle;
import netscape.javascript.JSObject;

import java.util.Arrays;
import java.util.List;

public class FruitsService {

	// a database... WARNING: I use a static field, since references from
	// javascript seem not to be taken into account for GC, then,
	// if I set fruits as a local variable inside loadFruits, they could be
	// garbage collected and not seen anymore from
	// javascript

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

	// async function
	public void loadFruits(final JSObject callbackfunction) {

		boolean useStrongTyped = true;
		// launch a background thread
		System.err
				.println("loadFruits called with: " + callbackfunction.toString());
		if (useStrongTyped) {
			new Thread(() -> {
				try {
					shuffleFruits();
					sleep(1000); // add some processing simulation...
					runLater(() -> {
						call(callbackfunction,
								// use strongly typed objects
								(Object) FruitsService.getFruits());
					});

				} catch (InterruptedException e) {
				}
			}).start();

		} else {

			new Thread(() -> {
				try {
					shuffleFruits();
					sleep(1000); // add some processing simulation...
					runLater(() -> {
						call(callbackfunction, 
								Arrays.toString(FruitsService.getFruits())
						// Exception: ReferenceError: Can't find variable: apple, data:
						// '[apple, strawberry, banana, orange]'
						// TODO: move from getName to toString in `controller-angular.js`

						);
					});

				} catch (InterruptedException e) {
				}
			}).start();
		}

	}

	private static void shuffleFruits() {
		List<Fruit> list = Arrays.asList(FruitsService.fruits);
		shuffle(list);
		FruitsService.fruits = list.toArray(new Fruit[] {});
	}
}
