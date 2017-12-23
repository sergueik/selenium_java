/**
 *
 */
package org.jenkins.plugins.audit2db;

import hudson.model.Describable;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Publisher;

import org.jenkins.plugins.audit2db.data.BuildDetailsRepository;

/**
 * Interface for the plugin publisher.
 *
 * @author Marco Scata
 *
 */
public interface DbAuditPublisher extends Describable<Publisher> {
    /**
     * @return the plugin descriptor.
     */
    @Override
    BuildStepDescriptor<Publisher> getDescriptor();

    /**
     * @return a reference to the repository class.
     */
    BuildDetailsRepository getRepository();
}
