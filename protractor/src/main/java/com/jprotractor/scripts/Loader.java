package com.jprotractor.scripts;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Loads client side scripts from classpath.
 */
final class Loader {
    /**
     * File name in classpath.
     */
    private final transient String filename;

    /**
     * Ctor.
     * @param file File name in classpath.
     */
    Loader(final String file) {
        this.filename = file + ".js";
    }

    /**
     * Get the contents of the given file.
     * @return File contents.
     */
    String content() {	   
	    try { 
			InputStream is = Loader.class.getClassLoader().getResourceAsStream(this.filename);
			byte[] bytes = new byte[is.available()];
			is.read(bytes);
			return new String(bytes, "UTF-8");
		} catch ( IOException e) {
			throw new RuntimeException(e);
		}
		
	   // ry-with-resources is not supported in -source 1.6
	   /*
        try (
            final InputStream stream = Loader.class
                .getClassLoader().getResourceAsStream(this.filename)
        ) {
            if (stream == null) {
                throw new ScriptLoadException(this.filename);
            }
            final byte[] bytes = new byte[stream.available()];
            stream.read(bytes);
            return new String(bytes, "UTF-8");
        } catch (final IOException err) {
            throw new ScriptLoadException(err, this.filename);
        }
		*/
    }
}
