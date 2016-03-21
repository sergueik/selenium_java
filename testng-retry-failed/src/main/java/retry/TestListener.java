package retry;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

/**
 * Created by user on 07.12.15.
 */
public class TestListener extends TestListenerAdapter {

    // успешные всегда заходят в onSuccess юзаем его
    @Override
    public void onTestSuccess(ITestResult testResult) {
        System.out.println("on success");
        // в этом методе складываем в массив  успешные тесты, определяем их параметры
        String testName = testResult.getName();
        String className = testResult.getTestClass().toString();
        String resultOfTest = resultOfTest(testResult);
        String stackTrace = "";
        ReportCreator.addTestInfo(testName, className, resultOfTest, stackTrace);
    }

    // еще 1 простенький метод для записи в результат теста  saccess / failure
    public String resultOfTest (ITestResult testResult) {
        int status = testResult.getStatus();
        if (status == 1) {
            String TR = "Success";
            return TR;
        }
        if (status == 2) {
            String TR = "Failure";
            return TR;
        }
        else {
            String unknownResult = "not interested for other results";
            return unknownResult;
        }
    }
}