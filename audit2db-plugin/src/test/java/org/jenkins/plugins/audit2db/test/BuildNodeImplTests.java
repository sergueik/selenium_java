/**
 * 
 */
package org.jenkins.plugins.audit2db.test;

import junit.framework.Assert;

import org.jenkins.plugins.audit2db.internal.model.BuildNodeImpl;
import org.jenkins.plugins.audit2db.model.BuildNode;
import org.junit.Test;

/**
 * Unit tests for the {@link BuildNodeImpl} class.
 * 
 * @author Marco Scata
 * 
 */
public class BuildNodeImplTests {
    private final BuildNode expected = new BuildNodeImpl("NODE_ADDRESS",
	    "NODE_HOST", "NODE_DISPLAYNAME", "NODE_URL", "NODE_NAME",
	    "NODE_DESC", "NODE_LABEL");

    @Test
    public void differentAttributesWithSameUrlShouldPreserveEquality() {
	final BuildNode actual = new BuildNodeImpl(
		expected.getMasterAddress() + "DIFFERENT",
		expected.getMasterHostName() + "DIFFERENT", 
		expected.getDisplayName() + "DIFFERENT", 
		expected.getUrl(), 
		expected.getName() + "DIFFERENT", 
		expected.getDescription() + "DIFFERENT",
		expected.getLabel() + "DIFFERENT");
	Assert.assertEquals("Broken equality", expected, actual);
    }

    @Test
    public void differentUrlShouldBreakEquality() {
	final BuildNode actual = new BuildNodeImpl(
		expected.getMasterAddress(),
		expected.getMasterHostName(), 
		expected.getDisplayName(), 
		expected.getUrl() + "DIFFERENT", 
		expected.getName(), 
		expected.getDescription(),
		expected.getLabel());
	Assert.assertFalse("Broken inequality logic", actual.equals(expected));
    }

    @Test
    public void equalsNullShouldBeFalse() {
	Assert.assertFalse("Broken inequality logic", expected.equals(null));
    }

    @Test
    public void equalsSomethingElseShouldBeFalse() {
	Assert.assertFalse("Broken inequality logic",
		expected.equals("SOMESTRING"));
    }
}
