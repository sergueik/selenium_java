package example;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.awt.Toolkit;

// origin:
// https://www.daniweb.com/programming/software-development/threads/158370/</a>
public class Tone {
	private Tone() {
	}

	/*
	* @param frequency frequency of the generated tone in Hz
	* @param duration duration of the generated tone in milliseconds
	*/
	public static void sound(final int frequency, final int duration) {
		final float sampleRate = 8000;
		final int bitsPerSample = 8;

		byte[] buf = new byte[duration * bitsPerSample];

		for (int i = 0; i < buf.length; i++) {
			double angle = i / (sampleRate / frequency) * 2.0 * Math.PI;
			buf[i] = (byte) (Math.sin(angle) * 80.0);
		}

		final int channels = 1; // MONO
		final boolean signed = true;
		final boolean bigEndian = false; // little-endian
		AudioFormat af = new AudioFormat(sampleRate, bitsPerSample, channels,
				signed, bigEndian);
		try (SourceDataLine sdl = AudioSystem.getSourceDataLine(af)) {
			sdl.open(af);
			sdl.start();
			sdl.write(buf, 0, buf.length);
			sdl.drain();
		} catch (LineUnavailableException e) {
			Toolkit.getDefaultToolkit().beep();
		}
	}
}
