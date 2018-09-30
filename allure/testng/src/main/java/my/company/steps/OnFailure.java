package my.company.steps;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * based on 
 */
public class OnFailure extends TestListenerAdapter {

	@Step("The testng listener")
	@Override
	public void onTestFailure(ITestResult tr) {
		createAttachment();
	}

	@Attachment("The  testng listener attachment")
	public String createAttachment() {
		return "The attachment body";
	}
}
