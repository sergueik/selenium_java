package org.jenkins.plugins.audit2db.pipeline;

import java.util.Date;
import java.util.logging.Logger;

import org.jenkins.plugins.audit2db.data.BuildDetailsRepository;
import org.jenkins.plugins.audit2db.model.BuildDetails;
import org.jenkinsci.plugins.workflow.cps.CpsFlowExecution;
import org.jenkinsci.plugins.workflow.flow.FlowExecution;
import org.jenkinsci.plugins.workflow.flow.GraphListener;
import org.jenkinsci.plugins.workflow.graph.FlowEndNode;
import org.jenkinsci.plugins.workflow.graph.FlowNode;

public class Audit2DbGraphListener implements GraphListener {

    private static final Logger LOGGER = Logger.getLogger(Audit2DbGraphListener.class.getName());

	private BuildDetailsRepository repository ;
	private BuildDetails details ;

	public Audit2DbGraphListener(BuildDetailsRepository repository, BuildDetails details) {
		super();
		this.repository = repository;
		this.details = details;
	}

	@Override
	public void onNewHead(FlowNode node) {
		if (! FlowEndNode.class.isInstance(node) ) { return ; }

		LOGGER.fine("Found the FlowEndNode.");
		FlowExecution execution = node.getExecution();
		LOGGER.fine("FlowExecution.isComplete: " + execution.isComplete());

		if (! CpsFlowExecution.class.isInstance(execution) ) {
			throw new IllegalStateException("Cannot get a pipeline result from a FlowExecution that is not an instance of CpsFlowExecution: " + execution.getClass().getName());
		}

		CpsFlowExecution cpsFlowExecution = (CpsFlowExecution) execution ;
		LOGGER.fine("CpsFlowExecution.result: " + cpsFlowExecution.getResult());
		BuildDetailsResolver.updateResultAndEndTime(details, cpsFlowExecution, new Date());

		LOGGER.fine("Persisting BuildDetails");
		repository.saveBuildDetails(details);
		LOGGER.fine("Audit2DbGraphListener.onNewHead(): complete");
	}

	public BuildDetailsRepository getRepository() {
		return repository;
	}

	public BuildDetails getDetails() {
		return details;
	}
}
