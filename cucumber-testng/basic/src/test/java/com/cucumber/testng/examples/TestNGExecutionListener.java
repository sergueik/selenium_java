package com.cucumber.testng.examples;

import org.testng.IExecutionListener;

/**
 * Created by amit.rawat on 06/01/16.
 */
public class TestNGExecutionListener implements IExecutionListener {
    @Override
    public void onExecutionStart() {
        System.out.println("TestNG is staring the execution");
    }
    @Override
    public void onExecutionFinish() {
        System.out.println("Generating the Masterthought Report");
        GenerateReport.GenerateMasterthoughtReport();
        System.out.println("TestNG has finished, the execution");
    }
}
