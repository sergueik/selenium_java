package org.jenkins.plugins.audit2db.pipeline;

import java.util.Properties;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SessionFactory;
import org.jenkins.plugins.audit2db.data.BuildDetailsRepository;
import org.jenkins.plugins.audit2db.internal.data.BuildDetailsHibernateRepository;
import org.jenkins.plugins.audit2db.internal.data.HibernateUtil;
import org.jenkins.plugins.audit2db.model.BuildDetails;
import org.jenkinsci.plugins.workflow.flow.FlowExecution;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.SynchronousNonBlockingStepExecution;

import hudson.model.Computer;
import hudson.model.Run;

public class Audit2DbExecution
		extends SynchronousNonBlockingStepExecution<Void> {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger
			.getLogger(Audit2DbStep.class.getName());

	private transient Audit2DbStep step;

	public Audit2DbExecution(Audit2DbStep step, StepContext context) {
		super(context);
		this.step = step;
	}

	@Override
	protected Void run() throws Exception {
		StepContext context = getContext();
		Run<?, ?> run = context.get(Run.class);
		LOGGER.fine("StepContext.Run: " + run);

		Properties props = HibernateUtil.getExtraProperties(step.getJdbcDriver(),
				step.getJdbcUrl(), step.getJdbcUsername(), step.getJdbcPassword());
		if (StringUtils.isNotBlank(step.getHibernateDialect())) {
			props.put("hibernate.dialect", step.getHibernateDialect());
		}
		LOGGER.fine("props: " + props);
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory(props);
		LOGGER.fine("sessionFactory: " + sessionFactory);
		BuildDetailsRepository repository = new BuildDetailsHibernateRepository(
				sessionFactory);
		BuildDetails details = BuildDetailsResolver.resolveBuildDetails(run,
				context.get(Computer.class));

		Audit2DbGraphListener listener = new Audit2DbGraphListener(repository,
				details);
		context.get(FlowExecution.class).addListener(listener);

		return null;
	}

	public Audit2DbStep getStep() {
		return step;
	}

}
