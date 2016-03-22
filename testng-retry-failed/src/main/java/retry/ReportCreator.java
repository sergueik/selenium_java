package retry;

import com.hp.gagawa.java.Document;
import com.hp.gagawa.java.DocumentType;
import com.hp.gagawa.java.elements.*;

import java.util.ArrayList;

public class ReportCreator {
  public static Document document;
  public static Body body;
  String reportFileName;
  public static ArrayList<TestData> list = new ArrayList<TestData>();

  public static void headerImage (){
      Img headerImage = new Img("", "src/main/resources/baad.jpeg");
      headerImage.setCSSClass("headerImage");
      body.appendChild(headerImage);
  }

  // общий блок отчета (все запущенные тесты: успех + неуспех)
  public static void addTestReport(String className, String testName, String status) {
    if (status == "Failure"){
      Div failedDiv = new Div().setCSSClass("AllTestsFailed");
      Div classNameDiv = new Div().appendText(className);
      Div testNameDiv = new Div().appendText(testName);
      Div resultDiv = new Div().appendText(status);
      failedDiv.appendChild(classNameDiv);
      failedDiv.appendChild(testNameDiv);
      failedDiv.appendChild(resultDiv);
      body.appendChild(failedDiv);
    } else {
      Div successDiv = new Div().setCSSClass("AllTestsSuccess");
      Div classNameDiv = new Div().appendText(className);
      Div testNameDiv = new Div().appendText(testName);
      Div resultDiv = new Div().appendText(status);
      successDiv.appendChild(classNameDiv);
      successDiv.appendChild(testNameDiv);
      successDiv.appendChild(resultDiv);
      body.appendChild(successDiv);
    }
  }

  public static void addCommonRunMetrics (int totalCount, int successCount, int failureCount) {
    Div total = new Div().setCSSClass("HeaderTable");
    total.appendText("Total tests count: " + totalCount);
    Div success = new Div().setCSSClass("HeaderTable");
    success.appendText("Passed tests: " + successCount);
    Div failure = new Div().setCSSClass("HeaderTable");
    failure.appendText("Failed tests: " + failureCount);
    body.appendChild(total);
    body.appendChild(success);
    body.appendChild(failure);
  }

  public static void addFailedTestsBlock (String className, String testName, String status) {
    Div failed = new Div().setCSSClass("AfterHeader");
    Div classTestDiv = new Div().appendText(className);
    Div testNameDiv = new Div().appendText(testName);
    Div statusTestDiv = new Div().appendText(status);
    failed.appendChild(classTestDiv);
    failed.appendChild(testNameDiv);
    failed.appendChild(statusTestDiv);
    body.appendChild(failed);
  }

  public static void addfailedWithStacktraces (String className, String testName, String status, String stackTrace) {
    Div failedWithStackTraces = new Div().setCSSClass("Lowest");
    failedWithStackTraces.appendText(className + " " + testName + " " + status + "\n");
    Div stackTraceDiv = new Div();
    stackTraceDiv.appendText(stackTrace);
    body.appendChild(failedWithStackTraces);
    body.appendChild(stackTraceDiv);
  }

  public static void addTestInfo(String testName, String className, String status, String stackTrace) {
    TestData testData = new TestData();
    testData.setTestName(testName);
    testData.setClassName(className);
    testData.setTestResult(status);
    testData.setStackTrace(stackTrace);
    list.add(testData);
  }

  public static String getReport() {
    document = new Document(DocumentType.XHTMLTransitional);
    Head head = document.head;
    Link cssStyle= new Link().setType("text/css").setRel("stylesheet").setHref("src/main/resources/site.css");
    head.appendChild(cssStyle);
    body = document.body;

    int totalCount = list.size();

    ArrayList failedCountArray = new ArrayList();
    for (int f = 0; f < list.size(); f++) {
      if (list.get(f).getTestResult() == "Failure") {
        failedCountArray.add(f);
      }
    }
    int failedCount = failedCountArray.size();
    int successCount = totalCount - failedCount;
    headerImage();
    addCommonRunMetrics(totalCount, successCount, failedCount);
    for (int s = 0; s < list.size(); s++){
      if (list.get(s).getTestResult() == "Failure"){
        addFailedTestsBlock(list.get(s).getClassName(), list.get(s).getTestName(), list.get(s).getTestResult());
      }
    }
    if(list.isEmpty()){
      System.out.println("ERROR: TEST LIST IS EMPTY");
      return "";
    }

    String currentTestClass = "";
    ArrayList constructedClasses = new ArrayList();
    for(int i=0; i < list.size();i++){
      currentTestClass = list.get(i).getClassName();
      boolean isClassConstructed = false;
      for(int j=0;j<constructedClasses.size();j++){
        if(currentTestClass.equals(constructedClasses.get(j))){
          isClassConstructed=true;
        }
      }
      if(!isClassConstructed){
        for (int k=0;k<list.size();k++){
          if(currentTestClass.equals(list.get(k).getClassName())){
            addTestReport(list.get(k).getClassName(), list.get(k).getTestName(),list.get(k).getTestResult());
          }
        }
        constructedClasses.add(currentTestClass);
      }
    }

    for (int z = 0; z < list.size(); z++){
      if (list.get(z).getTestResult() == "Failure"){
        addfailedWithStacktraces(list.get(z).getClassName(), list.get(z).getTestName(), list.get(z).getTestResult(), list.get(z).getStackTrace());
      }
    }
    return document.write();
  }

    public static class TestData{
      String testName;
      String className;
      String testResult;
      String stackTrace;

      public TestData() {}

      public String getTestName() {
        return testName;
      }

      public String getClassName() {
        return className;
      }

      public String getTestResult() {
        return testResult;
      }

      public String getStackTrace() {
        return stackTrace;
      }

      public void setTestName(String testName) {
        this.testName = testName;
      }

      public void setClassName(String className) {
        this.className = className;
      }

      public void setTestResult(String testResult) {
        this.testResult = testResult;
      }

      public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
      }
    }
}
