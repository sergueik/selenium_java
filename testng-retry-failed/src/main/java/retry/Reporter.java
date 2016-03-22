package retry;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class Reporter implements IReporter {

  @Override
  public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
    PrintWriter saver = null;
    try {
      saver = new PrintWriter(new File("report.html"));
      saver.write(ReportCreator.getReport());
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (saver != null) {
        saver.close();
      }
    }
  }
}