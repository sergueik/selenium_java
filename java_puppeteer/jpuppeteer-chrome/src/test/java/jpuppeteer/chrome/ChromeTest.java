package jpuppeteer.chrome;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class ChromeTest {

	private static final Logger logger = LoggerFactory
			.getLogger(ChromeTest.class);

	private ChromeBrowser browser;

	@Before
	public void setUp() throws Exception {
		LoggerContext logger = (LoggerContext) LoggerFactory.getILoggerFactory();
		logger.getLogger("root").setLevel(Level.DEBUG);
		browser = new ChromeLauncher(Constant.CHROME_EXECUTABLE_PATH).launch();
	}

	@After
	public void tearDown() throws Exception {
		browser.close();
	}

	@Test
	public void testInterceptor() throws Exception {
		ChromePage page = browser.defaultContext().newPage();
		// page.addListener(new AbstractListener<FrameLifecycle>() {
		// @Override
		// public void accept(FrameLifecycle frameLifecycle) {
		// PageLifecyclePhase phase = frameLifecycle.phase();
		// Frame frame = frameLifecycle.frame();
		// try {
		// if (frame.url() != null &&
		// frame.url().getPath().equals("/mini_login.htm") &&
		// PageLifecyclePhase.LOAD.equals(phase)) {
		// System.out.println(frame.querySelector("#fm-sms-login-id"));
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		// });
		// page.addListener(new AbstractListener<PageCrashed>() {
		// @Override
		// public void accept(PageCrashed crashEvent) {
		// System.out.println(crashEvent.error());
		// }
		// });
		// page.addListener(new AbstractListener<PageReady>() {
		// @Override
		// public void accept(PageReady pageReady) {
		// System.out.println("page dom ready duration=" + pageReady.duration());
		// try {
		// page.waitSelector("#J_IMGSeachUploadBtn", 5, TimeUnit.SECONDS)
		// .uploadFile(new
		// File("D:\\tmp\\1a3636c2-1ca8-46cf-a3ec-f0ec98a591de.jpg"));
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		// });
		page.navigate("https://www.taobao.com");
		TimeUnit.DAYS.sleep(1);
	}

}
