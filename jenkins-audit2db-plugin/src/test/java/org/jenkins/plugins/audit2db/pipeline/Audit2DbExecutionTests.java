package org.jenkins.plugins.audit2db.pipeline;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.jenkins.plugins.audit2db.test.TestUtils.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hamcrest.Matchers;
import org.hibernate.SessionFactory;
import org.jenkins.plugins.audit2db.data.BuildDetailsRepository;
import org.jenkins.plugins.audit2db.internal.data.BuildDetailsHibernateRepository;
import org.jenkins.plugins.audit2db.model.BuildDetails;
import org.jenkinsci.plugins.workflow.flow.FlowExecution;
import org.jenkinsci.plugins.workflow.flow.GraphListener;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import hudson.model.Computer;
import hudson.model.Node;
import hudson.model.Run;

public class Audit2DbExecutionTests {

	@Mock private StepContext stepContext ;
	@Mock private Run<?,?> run ;
	@Mock private FlowExecution flowExecution ;
	@Mock private Computer computer ;
	@Mock private Node node;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRunAddsListenerWithValidHibernateRepositoryToStepContext() throws Exception {
		Audit2DbStep step = new Audit2DbStep(JDBC_DRIVER, JDBC_URL, JDBC_USER, JDBC_PASS);
		final List<Audit2DbGraphListener> listeners = new ArrayList<Audit2DbGraphListener>();
		doReturn("/computer/(master)/").when(computer).getUrl();
		doReturn(node).when(computer).getNode();
		doReturn(computer).when(stepContext).get(Computer.class);
		doReturn(run).when(stepContext).get(Run.class);
		doReturn(flowExecution).when(stepContext).get(FlowExecution.class);
		doAnswer(
		    new Answer<Void>() {
				@Override
				public Void answer(InvocationOnMock invocation) throws Throwable {
					listeners.add((Audit2DbGraphListener) invocation.getArguments()[0]);
					return null;
				}
			}
		).when(flowExecution).addListener(Mockito.any(GraphListener.class));

		Audit2DbExecution execution = new Audit2DbExecution(step, stepContext);
		execution.run();
		Audit2DbGraphListener listener = listeners.get(0);

		verify(flowExecution).addListener(listener);
		assertThat("Null BuildDetailsRepository", listener, hasProperty("repository", Matchers.isA(BuildDetailsHibernateRepository.class)));
	}

	@Test
	public void testRunAddsListenerWithValidBuildDetailsToStepContext() throws Exception {
		Audit2DbStep step = new Audit2DbStep(JDBC_DRIVER, JDBC_URL, JDBC_USER, JDBC_PASS);
		final List<Audit2DbGraphListener> listeners = new ArrayList<Audit2DbGraphListener>();
		doReturn("/computer/(master)/").when(computer).getUrl();
		doReturn(node).when(computer).getNode();
		doReturn(computer).when(stepContext).get(Computer.class);
		doReturn("Full Display Name # -99").when(run).getFullDisplayName();
		doReturn(run).when(stepContext).get(Run.class);
		doReturn(flowExecution).when(stepContext).get(FlowExecution.class);

		doAnswer(
		    new Answer<Void>() {
				@Override
				public Void answer(InvocationOnMock invocation) throws Throwable {
					listeners.add((Audit2DbGraphListener) invocation.getArguments()[0]);
					return null;
				}
			}
		).when(flowExecution).addListener(Mockito.any(GraphListener.class));

		Audit2DbExecution execution = new Audit2DbExecution(step, stepContext);
		execution.run();
		Audit2DbGraphListener listener = listeners.get(0);

		verify(flowExecution).addListener(listener);
		assertThat("No BuildDetails", listener, hasProperty("details", Matchers.isA(BuildDetails.class)));
		BuildDetails details = listener.getDetails();
		assertNotNull("Null BuildDetails", details);
		assertEquals("Mis-matched name", "Full Display Name # -99", details.getFullName());
		assertNotNull("Null BuildDetails.node", details.getNode());
		assertEquals("Mis-matched node url", "http://unconfigured-jenkins-server/computer/(master)/", details.getNode().getUrl());
	}

}
