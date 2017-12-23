/**
 *
 */
package org.jenkins.plugins.audit2db.test;

import java.util.Date;

import junit.framework.Assert;

import org.jenkins.plugins.audit2db.internal.model.BuildDetailsImpl;
import org.jenkins.plugins.audit2db.internal.model.BuildParameterImpl;
import org.jenkins.plugins.audit2db.model.BuildDetails;
import org.jenkins.plugins.audit2db.model.BuildParameter;
import org.junit.Test;

/**
 * Unit tests for the {@link BuildParameterImpl} class.
 *
 * @author Marco Scata
 *
 */
public class BuildParameterImplTests {
    private final BuildDetails details = new BuildDetailsImpl(
            "BUILDID", "BUILD NAME", "BUILD_FULLNAME", new Date(),
            new Date(), 10L, "USERID", "USERNAME", null, null);

    private final BuildDetails otherDetails = new BuildDetailsImpl(
            "BUILDIDXXX", "BUILD NAME", "BUILD_FULLNAME", new Date(),
            new Date(), 10L, "USERID", "USERNAME", null, null);

    private final BuildParameter expected = new BuildParameterImpl(
            "PARAM_ID", "PARAM NAME", "PARAM VALUE", details);

    @Test
    public void differentAttributesShouldPreserveEquality(){
        final BuildParameter actual = new BuildParameterImpl(
                expected.getId(),
                expected.getName() + "DIFFERENT",
                expected.getValue() + "DIFFERENT",
                otherDetails);
        Assert.assertEquals("Broken equality", expected, actual);
    }

    @Test
    public void differentIdShouldBreakEquality(){
        final BuildParameter actual = new BuildParameterImpl(
                expected.getId() + "DIFFERENT",
                expected.getName(),
                expected.getValue(),
                expected.getBuildDetails());
        Assert.assertFalse("Broken inequality logic", actual.equals(expected));
    }

    @Test
    public void equalsNullShouldBeFalse() {
        Assert.assertFalse("Broken inequality logic", expected.equals(null));
    }

    @Test
    public void equalsSomethingElseShouldBeFalse() {
        Assert.assertFalse("Broken inequality logic", expected.equals("SOMESTRING"));
    }
}
