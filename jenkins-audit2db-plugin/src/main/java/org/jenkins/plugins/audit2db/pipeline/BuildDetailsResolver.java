package org.jenkins.plugins.audit2db.pipeline;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.jenkins.plugins.audit2db.internal.model.BuildDetailsImpl;
import org.jenkins.plugins.audit2db.internal.model.BuildNodeImpl;
import org.jenkins.plugins.audit2db.internal.model.BuildParameterImpl;
import org.jenkins.plugins.audit2db.model.BuildDetails;
import org.jenkins.plugins.audit2db.model.BuildNode;
import org.jenkins.plugins.audit2db.model.BuildParameter;
import org.jenkinsci.plugins.workflow.cps.CpsFlowExecution;

import hudson.model.Action;
import hudson.model.Computer;
import hudson.model.Node;
import hudson.model.ParameterValue;
import hudson.model.ParametersAction;
import hudson.model.Run;
import hudson.model.Cause.UserIdCause;
import jenkins.model.Jenkins;
import jenkins.model.Jenkins.MasterComputer;

public final class BuildDetailsResolver {

    private static final Logger LOGGER = Logger.getLogger(BuildDetailsResolver.class.getName());

	/**
	 * This method creates a BuildDetails object with infomation from the
	 * jobs run context.
	 *
	 * <b>Note:</b> The <code>endDate</code>, <code>duration</code>, and <code>result</code>
	 * properties are <b>not</b> set, since that information is not accessiable from a pipeline
	 * job without a {@link CpsFlowExecution}, which we may not have at call time.
	 *
	 * @param run This pipeline's run context
	 * @param computer The node this job is currently running on. Since this is a pipeline job, it may be inconsistent.
	 * @return The populated BuildDetails.
	 */
	public static BuildDetails resolveBuildDetails(Run<?,?> run, Computer computer) {
		LOGGER.fine("resolving BuildDetails");
		BuildDetailsImpl details = new BuildDetailsImpl();
		LOGGER.fine("computer: " + computer);
		LOGGER.fine("run: " + run);
		LOGGER.fine("start time: " + run.getStartTimeInMillis());
		details.setFullName( run.getFullDisplayName() );
		details.setId( run.getId() );
		details.setName( run.getDisplayName() );
		details.setStartDate( new Date(run.getStartTimeInMillis()) );
		LOGGER.finer("base BuildDetails: " + details);

		try {
			addParametersFromContext(details, run);
			addUserInfoFromContext(details, run);
			addBuildNodeFromContext(details, run, computer);
		} catch (Exception e) { // IOException, InterruptedException
			throw new Audit2DbPipelineStepException(e);
		}

		return details;
	}

	/**
	 * This method updates the <code>result</code>, <code>endDate</code>, and <code>duration</code>
	 * properties of the given BuildDetails. The duration is calculated based on the BuildDetail's
	 * startDate and given endDate, while result is extracted from the {@link CpsFlowExecution}.
	 *
	 * @param details the BuildDetails to update
	 * @param cpsFlowExecution the FlowExection containing the results.
	 * @param endDate the time the build "ended".
	 */
	public static void updateResultAndEndTime(BuildDetails details, CpsFlowExecution cpsFlowExecution, Date endDate) {
		LOGGER.finest("Updating BuildDetails endTime, duration, and result.");
		details.setEndDate(endDate);
		details.setDuration( details.getEndDate().getTime() - details.getStartDate().getTime() );
		String result = cpsFlowExecution.getResult() != null ? cpsFlowExecution.getResult().toString() : null;
		details.setResult(result);
	}

	private static void addParametersFromContext(BuildDetailsImpl details, Run<?,?> run) throws IOException, InterruptedException {
		LOGGER.finest("resolving build parameters");
		List<BuildParameter> buildParameters = new ArrayList<BuildParameter>();
		for (Action action : run.getAllActions()) {
		    if (! ParametersAction.class.isInstance(action) ) { continue ; }
			ParametersAction paramAction = (ParametersAction) action;
			for (ParameterValue param : paramAction.getParameters()) {
				LOGGER.finest("found parameter: " + param);
				String paramId = run.getId() + "-" + param.getName();
				buildParameters.add(
				    new BuildParameterImpl(paramId, param.getName(), param.getValue().toString(), details)
				);
			}
		}
		details.setParameters(buildParameters);
	}

	private static void addUserInfoFromContext(BuildDetailsImpl details, Run<?,?> run) throws IOException, InterruptedException {
		LOGGER.finest("resolving user info");
		for (Object cause: run.getCauses()) {
			if (cause instanceof UserIdCause) {
				LOGGER.finest("Found UserIdCause");
				details.setUserId( ((UserIdCause) cause).getUserId() );
				details.setUserName( ((UserIdCause) cause).getUserName() );
			}
		}
		LOGGER.finest("userId: " + details.getUserId());
		LOGGER.finest("userName: " + details.getUserName());
	}

	private static void addBuildNodeFromContext(BuildDetailsImpl details, Run<?,?> run, Computer computer) throws IOException, InterruptedException {
		LOGGER.finer("resolving build node");
		Jenkins jenkins = Jenkins.getInstance();
		LOGGER.finer("jenkins: " + jenkins);
		LOGGER.finer("computer: " + computer);
		Node node = computer.getNode();
		LOGGER.finer("node: " + node);

		String rootUrl = jenkins != null ? jenkins.getRootUrl() :  "http://unconfigured-jenkins-server/";
		LOGGER.finer("rootUrl: " + rootUrl);
		URL url = new URL(rootUrl);
		String masterHostname = url.getHost();
	    String urlString = String.format("%s/%s", rootUrl, computer.getUrl()).replaceAll("(\\w)(\\/{2,})(\\w)", "$1/$3");
		BuildNode buildNode = new BuildNodeImpl(
	    	resolveMasterIpAddress(computer),
	    	masterHostname,
			computer.getDisplayName(),
			urlString, // url
			node.getNodeName(),
			node.getNodeDescription(),
			node.getLabelString()
		);
		LOGGER.finer("buildNode: " + buildNode);
	    details.setNode(buildNode);
	}

	private static String resolveMasterIpAddress(Computer computer) {
		try {
			if (MasterComputer.class.isInstance(computer)) {
				return Inet4Address.getLocalHost().getHostAddress();
			}
		} catch (UnknownHostException e) {
			throw new Audit2DbPipelineStepException(e);
		}
		return "UNKNOWN";
	}
}
