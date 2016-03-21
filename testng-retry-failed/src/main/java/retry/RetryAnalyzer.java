package retry;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Created by user on 11.11.15.
 */
public class RetryAnalyzer implements IRetryAnalyzer {
    private int retryCount = 0;
    private int retryMaxCount = 3;

    // решаем, требует ли тест перезапуска
    @Override
    public boolean retry(ITestResult testResult) {
        boolean result = false;
        if (testResult.getAttributeNames().contains("retry") == false) {
            System.out.println("retry count = " + retryCount + "\n" +"max retry count = " + retryMaxCount);
            if(retryCount < retryMaxCount){
                System.out.println("Retrying " + testResult.getName() + " with status "
                        + testResult.getStatus() + " for the try " + (retryCount+1) + " of "
                        + retryMaxCount + " max times.");

                retryCount++;
                result = true;
            }else if (retryCount == retryMaxCount){
                // тут будем складывать в отчет неуспешные тесты
                // получаем все необходимые параметры теста
                String testName = testResult.getName();
                String className = testResult.getTestClass().toString();
                String resultOfTest = resultOfTest(testResult);
                String stackTrace = testResult.getThrowable().fillInStackTrace().toString();
                System.out.println(stackTrace);
                // и записываем в массив тестов
                ReportCreator.addTestInfo(testName, className, resultOfTest, stackTrace);
            }
        }
        return result;
    }
    // простенький метод для записи в результат теста  saccess / failure
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
