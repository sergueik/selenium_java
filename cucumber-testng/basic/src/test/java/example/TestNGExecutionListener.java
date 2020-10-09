package example;

import org.testng.IExecutionListener;

public class TestNGExecutionListener implements IExecutionListener {
	@Override
	public void onExecutionStart() {
		System.out.println("TestNG is starting the execution");
	}

	@Override
	public void onExecutionFinish() {
		System.out.println("Generating the Masterthought Report");
		GenerateReport.GenerateMasterthoughtReport();
		System.out.println("TestNG execution has finished");
	}
}
