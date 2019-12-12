package example;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.openqa.selenium.By;

public class NotificationTest extends BaseNotificationServiceTest {
	private String URL = null;

	@Test
	public void doWebNotificationTesting() throws Exception {
		URL = "https://pushjs.org/#";
		driver.navigate().to(URL);
		uiNotificationService.startWebNotificationListener();
		driver.findElement(By.id("demo_button")).click();
		utils.waitFor(2);

		Map<String, String> notificationFilter = new HashMap<>();
		notificationFilter.put("title", "Hello world!");
		boolean flag = uiNotificationService
				.isNotificationPresent(notificationFilter, "web");
		uiNotificationService.stopWebNotificationListener();
	}

	@Test
	public void doWebPushNotificationTesting() throws Exception {
		URL = "https://framework.realtime.co/demo/web-push";
		driver.navigate().to(URL);
		uiNotificationService.startPushNotificationListener(
				"https://framework.realtime.co/demo/web-push");
		driver.findElement(By.cssSelector("#sendButton")).click();
		utils.waitFor(4);

		Map<String, String> notificationFilter = new HashMap<>();
		notificationFilter.put("title", "Web Push Notification");

		boolean flag = uiNotificationService
				.isNotificationPresent(notificationFilter, "push");
		uiNotificationService.stopPushNotificationListener();
	}
}
