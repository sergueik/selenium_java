import org.apache.commons.configuration.ConfigurationException;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.github.abhishek8908.util.DriverUtil.download;

public class TestDriverDownload {


    @Test
    public void donloadTest() throws IOException, ConfigurationException {
        System.setProperty("ver", "2.39");
        System.setProperty("os", "win32");
        System.setProperty("ext", "zip");
        download("chromedriver", "D:\\Driver", "2.39");
    }


}
