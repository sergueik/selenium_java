package example;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

// TODO: get rid of
import com.neovisionaries.ws.client.WebSocketException;

import example.messaging.MessageBuilder;
import example.messaging.ServiceWorker;
import example.utils.Utils;

public class HeadlessTest extends BaseTest {
	private String URL = null;
	private String responseMessage = null;
	private JSONObject result = null;
	private String imageName = null;
	private final String filePath = System.getProperty("user.dir") + "/target";

	@Before
	public void beforeTest() throws IOException {
		super.setHeadless(true);
		super.beforeTest();
	}

	// @Ignore
	// TODO: headless versus on-screen
	// No message received
	// {"error":{"code":-32000,"message":"PrintToPDF is not implemented"}}
	@Test
	public void doprintPDF() throws Exception {
		URL = "https://www.wikipedia.com/";
		imageName = "cdp_img_" + (new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")).format(new Date()) + ".pdf";

		driver.navigate().to(URL);
		CDPClient.sendMessage(MessageBuilder.buildPrintPDFMessage(id));
		// responseMessage = CDPClient.getResponseBodyMessage(id);
		// TODO: assertNull
		responseMessage = CDPClient.getResponseDataMessage(id);
		System.err.println("Here is the base 64 PDF response: " + responseMessage.substring(0, 100));
		byte[] bytes = Base64.getDecoder().decode(responseMessage);
		File f = new File(filePath + "/" + imageName);
		if (f.exists())
			f.delete();
		Files.write(f.toPath(), bytes);
	}
}
