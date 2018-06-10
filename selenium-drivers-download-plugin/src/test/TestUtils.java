import com.github.abhishek8908.util.DriverUtil;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static com.github.abhishek8908.util.DriverUtil.*;

public class TestUtils {


    @Test
    public static void newDownload() throws IOException {

        String fromFile = "https://github.com/mozilla/geckodriver/releases/download/v0.20.1/geckodriver-v0.20.1-win32.zip";
        String toFile = "D:/Driver/geckodriver-v0.20.1-win32.zip";
        //connectionTimeout, readTimeout = 10 seconds
        FileUtils.copyURLToFile(new URL(fromFile), new File(toFile), 10000, 10000);


    }

    @Test
    public void testSystemProperty() throws ConfigurationException {
        System.setProperty("ver", "2.39");
        System.setProperty("os", "linux64");
        System.setProperty("ext", "zip");
        System.out.println(DriverUtil.readProperty("chromedriver.download.url"));
    }

    @Test
    public void urlTest() {

        getFileNameFromUrl("https://chromedriver.storage.googleapis.com/2.39/chromedriver_win32.zip");

    }

    @Test
    public void fileRename() throws IOException {

        changeFileName("D:\\Driver\\chromedriver.exe", "D:\\Driver\\chromedriver-" + "2.38" + ".exe");

    }

    @Test
    public void testCleanDir() {

        cleanDir("D:\\Driver");

    }

    @Test
    public void testDriverExists() throws IOException {

        System.out.println(checkDriverVersionExists("chromedriver", "2.38", "D:/Driver"));

    }

    @Test
    public void testProperty() throws ConfigurationException {

        System.out.println(readProperty("chrome.download.url"));
        System.getProperty("os.name");

    }


}
