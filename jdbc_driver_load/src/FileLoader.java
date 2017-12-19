
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Trying to load a file.
 */
public class FileLoader {

    /**
     * Loading a jar to class loader.
     *
     * @param pathToJar  it could be absolute path to jar file, or it could be file name residing
     *                   on class path or in lib folder
     * @return  class loader where the jar is loaded in
     */
    public static ClassLoader loadJar(final String pathToJar) {
        URL url = getFileURL(pathToJar);
        final URLClassLoader loader = new URLClassLoader (new URL[] {url}, Thread.currentThread().getContextClassLoader());
        return loader;
    }

    /**
     * Searching for file (you could provide filename in form of file URL - it means file:/...)
     * 1. as absolute path
     * 2. as resource of class loader
     */
    public static File getFile(final String fileName) {
        // Consult absolute path
        File file = new File(fileName);

        // Working with URLs inside of try block
        try {
            // if url then convert to file
            if(!file.exists() && fileName.startsWith("file:")) {
                URL url = new URL(fileName);
                file = urlToFile(url);
            }
            // load as resource of class loader
            if(!file.exists()) {
                URL url = ClassLoader.getSystemClassLoader().getResource(file.getPath());
                if(url != null) {
                    file = urlToFile(url);
                }
            }
            // not able to find file neither at classloader path nor library path - exception
            if(!file.exists()) {
                throw new IllegalStateException("Can't load file " + fileName);
            }
        } catch (MalformedURLException mue) {
            throw new RuntimeException(mue);
        }
        return file;
    }

    /**
     * See {@link #getFile(String)}.
     */
    public static URL getFileURL(final String fileName) {
        try {
            return getFile(fileName).toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private static File urlToFile(final URL url) {
        return new File(url.getFile());
    }
}
