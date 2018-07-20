package jcucumberng.framework.api;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.IOException;

import org.openqa.selenium.Dimension;

/**
 * {@code LocalMachine} handles actions relating to the user's machine such as
 * screen resolution or input devices.
 * 
 * @author Kat Rollo <rollo.katherine@gmail.com>
 */
public final class LocalMachine {

	private LocalMachine() {
		// Prevent instantiation
	}

	/**
	 * Gets the native resolution of the local machine.
	 * 
	 * @return Dimension - the screen size in WxH (e.g. 1920x1080)
	 */
	public static Dimension getDimension() {
		java.awt.Dimension awtDimension = Toolkit.getDefaultToolkit().getScreenSize();
		short width = (short) awtDimension.getWidth();
		short height = (short) awtDimension.getHeight();
		Dimension dimension = new Dimension(width, height);
		return dimension;
	}

	/**
	 * Accepts a single key entry. The key is pressed and released immediately.
	 * 
	 * @param key the constant from {@code java.awt.event.KeyEvent}
	 * @throws AWTException
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public static void pressKey(int key) throws AWTException, NumberFormatException, IOException {
		Robot robot = new Robot();
		robot.keyPress(key);
		robot.keyRelease(key);
		robot.delay(Integer.parseInt(ConfigLoader.frameworkConf("keypress.wait")));
		robot = null; // Destroy robot
	}

	/**
	 * Accepts multiple key entries (e.g. shortcut command). The keys are pressed
	 * simultaneously and released in reverse order.
	 * 
	 * @param keys an array of KeyEvent constants from
	 *             {@code java.awt.event.KeyEvent}, specify keys in order
	 * @throws AWTException
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public static void pressKeys(int[] keys) throws AWTException, NumberFormatException, IOException {
		Robot robot = new Robot();
		for (int ctr = 0; ctr < keys.length; ctr++) {
			robot.keyPress(keys[ctr]); // Press and hold keys
		}
		robot.delay(Integer.parseInt(ConfigLoader.frameworkConf("keypress.wait")));
		for (int ctr = keys.length - 1; ctr > -1; ctr--) {
			robot.keyRelease(keys[ctr]); // Release keys in reverse order
		}
		robot = null;
	}

}
