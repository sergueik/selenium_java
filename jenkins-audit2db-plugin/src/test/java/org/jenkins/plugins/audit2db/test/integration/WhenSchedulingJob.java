/**
 * 
 */
package org.jenkins.plugins.audit2db.test.integration;

import hudson.model.FreeStyleBuild;
import hudson.model.Result;
import hudson.model.BooleanParameterDefinition;
import hudson.model.FreeStyleProject;
import hudson.model.ParameterDefinition;
import hudson.model.ParametersDefinitionProperty;
import hudson.model.StringParameterDefinition;
import hudson.tasks.Publisher;

import java.util.concurrent.Future;

import junit.framework.Assert;

import org.jenkins.plugins.audit2db.DbAuditPublisher;
import org.jenkins.plugins.audit2db.DbAuditPublisherDescriptor;
import org.jenkins.plugins.audit2db.data.BuildDetailsRepository;
import org.jenkins.plugins.audit2db.internal.DbAuditPublisherImpl;
import org.jenkins.plugins.audit2db.internal.model.BuildDetailsImpl;
import org.jenkins.plugins.audit2db.model.BuildDetails;
import org.junit.Before;
import org.junit.Test;
import org.jvnet.hudson.test.HudsonTestCase;

/**
 * @author Marco Scata
 *
 */
public class WhenSchedulingJob extends HudsonTestCase {
    private static final String jdbcDriver = "org.hsqldb.jdbc.JDBCDriver";
    private static final String jdbcUrl = "jdbc:hsqldb:mem:test";
    private static final String jdbcUser = "SA";
    private static final String jdbcPassword = "";

    private DbAuditPublisher getPlugin() {
        final DbAuditPublisher publisher = new DbAuditPublisherImpl();
        final DbAuditPublisherDescriptor descriptor = (DbAuditPublisherDescriptor) publisher.getDescriptor();
        descriptor.setJdbcDriver(jdbcDriver);
        descriptor.setJdbcUrl(jdbcUrl);
        descriptor.setJdbcUser(jdbcUser);
        descriptor.setJdbcPassword(jdbcPassword);

        return publisher;
    }

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testPlainJobShouldSaveNoParameters() throws Exception {
        final FreeStyleProject project = createFreeStyleProject("PlainJob");

        // enable audit2db plugin
        final DbAuditPublisher plugin = getPlugin();
        project.getPublishersList().add((Publisher) plugin);

        // build now
        final Future<FreeStyleBuild> futureBuild = project.scheduleBuild2(0);
        final FreeStyleBuild build = futureBuild.get();
        Assert.assertNotNull(build);
        Assert.assertEquals("Unexpected build result", Result.SUCCESS, build.getResult());

        // check data persistence
        final BuildDetailsRepository repository = plugin.getRepository();
        final BuildDetails actual = repository.getBuildDetailsForBuild(build);
        final BuildDetails expected = new BuildDetailsImpl(build);
        Assert.assertEquals("Unexpected build details", expected, actual);
        Assert.assertNotNull("Unexpected null end date", actual.getEndDate());
        Assert.assertTrue("Unexpected duration", actual.getDuration() > 0L);
        Assert.assertEquals("Unexpected number of params", 0, actual.getParameters().size());
    }

    @Test
    public void testParameterisedJobShouldSaveAllParameters() throws Exception {
        final FreeStyleProject project = createFreeStyleProject("ParameterisedJob");

        // set parameters
        final ParameterDefinition param1 = new StringParameterDefinition("myStringParam", "myStringValue", "My String Parameter");
        final ParameterDefinition param2 = new BooleanParameterDefinition("myBooleanParam", false, "My Boolean Parameter");
        project.addProperty(new ParametersDefinitionProperty(param1, param2));

        // enable audit2db plugin
        final DbAuditPublisher plugin = getPlugin();
        project.getPublishersList().add((Publisher) plugin);

        // build now
        final Future<FreeStyleBuild> futureBuild = project.scheduleBuild2(0);
        final FreeStyleBuild build = futureBuild.get();
        Assert.assertNotNull(build);
        Assert.assertEquals("Unexpected build result", Result.SUCCESS, build.getResult());

        // check data persistence
        final BuildDetailsRepository repository = plugin.getRepository();
        final BuildDetails actual = repository.getBuildDetailsForBuild(build);
        final BuildDetails expected = new BuildDetailsImpl(build);
        Assert.assertEquals("Unexpected build details", expected, actual);
        Assert.assertNotNull("Unexpected null end date", actual.getEndDate());
        Assert.assertTrue("Unexpected duration", actual.getDuration() > 0L);
        Assert.assertEquals("Unexpected number of params", 2, actual.getParameters().size());
    }
}
