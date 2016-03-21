package retry;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by user on 07.12.15.
 */
public class Reporter implements IReporter {

    // метод, который стартует после окончания всех тестов и дергает наш getReport для получения html'я в string'е
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