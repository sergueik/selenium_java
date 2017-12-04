package example;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.awt.Toolkit;

/**
 * Helper class for using the Java Sound API.
 *
 * @author Greg Chabala
 * @since Sep 30, 2010
 */
public class Tone {
    private Tone() { }

    /**
     * Makes a pleasant beep.
     * I didn't invent the logic for this, it's been posted various places online. Earliest example I can find is
     * from 2008:
     * <a href="https://www.daniweb.com/programming/software-development/threads/158370/">
     *          https://www.daniweb.com/programming/software-development/threads/158370/</a>
     *
     * @param hz    frequency of the generated tone in hertz
     * @param msecs duration of the generated tone in milliseconds
     */
    public static void sound(final int hz, final int msecs) {
        final float sampleRate = 8000;
        final int bitsPerSample = 8;

        byte[] buf = new byte[msecs * bitsPerSample];

        for (int i = 0; i < buf.length; i++) {
            double angle = i / (sampleRate / hz) * 2.0 * Math.PI;
            buf[i] = (byte) (Math.sin(angle) * 80.0);
        }

        final int channels = 1;          // MONO
        final boolean signed = true;     /** @see javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED */
        final boolean bigEndian = false; // little-endian
        AudioFormat af = new AudioFormat(sampleRate, bitsPerSample, channels, signed, bigEndian);
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
