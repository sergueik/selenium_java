package org.jenkins.plugins.audit2db.pipeline;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

public class Audit2DbStepTests {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testStartWithStepContextReturnsAudit2DbExecution()
			throws Exception {
		StepContext context = Mockito.mock(StepContext.class);
		Audit2DbStep step = new Audit2DbStep();

		StepExecution execution = step.start(context);

		assertNotNull("Null StepExecution returned.", execution);
		assertTrue("Expected Audit2DbExecution",
				Audit2DbExecution.class.isInstance(execution));
		assertThat("StepContext should be injected into execution.", execution,
				hasProperty("context", sameInstance(context)));
		assertThat("Step should be injected into execution.", execution,
				hasProperty("step", sameInstance(step)));
	}

}
