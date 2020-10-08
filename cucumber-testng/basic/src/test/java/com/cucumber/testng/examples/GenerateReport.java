package com.cucumber.testng.examples;

import net.masterthought.cucumber.ReportBuilder;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by amit.rawat on 21/12/15.
 */
public class GenerateReport {
    public static void GenerateMasterthoughtReport(){
        try{
            String RootDir = System.getProperty("user.dir");
            File reportOutputDirectory = new File("target/Masterthought");
            List<String> list = new ArrayList<String>();
            list.add("target/cucumber1.json");
            list.add("target/cucumber2.json");

            String pluginUrlPath = "";
            String buildNumber = "1";
            String buildProject = "cucumber-jvm";
            boolean skippedFails = true;
            boolean pendingFails = true;
            boolean undefinedFails = true;
            boolean missingFails = true;
            boolean flashCharts = true;
            boolean runWithJenkins = false;
            boolean highCharts = false;
            boolean parallelTesting = true;
            boolean artifactsEnabled = false;
            String artifactConfig = "";

            ReportBuilder reportBuilder = new ReportBuilder(list, reportOutputDirectory, pluginUrlPath, buildNumber,
                    buildProject, skippedFails, pendingFails, undefinedFails, missingFails, flashCharts, runWithJenkins,
                    highCharts, parallelTesting);

            reportBuilder.generateReports();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
