/**
 * 
 */
package org.jenkins.plugins.audit2db.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.jenkins.plugins.audit2db.internal.model.BuildDetailsImpl;
import org.jenkins.plugins.audit2db.internal.model.BuildParameterImpl;
import org.jenkins.plugins.audit2db.model.BuildDetails;
import org.jenkins.plugins.audit2db.model.BuildParameter;
import org.junit.Test;

/**
 * Unit tests for the {@link BuildDetailsImpl} class.
 * 
 * @author Marco Scata
 *
 */
public class BuildDetailsImplTests {
    private BuildDetails getBuildDetails() {
        final BuildDetails build = new BuildDetailsImpl();
        build.setDuration(Long.valueOf(60));
        build.setEndDate(new Date(
                build.getStartDate().getTime() + (build.getDuration() * 1000)));
        build.setFullName("BUILD FULL NAME");
        build.setId("BUILD ID");
        build.setName("BUILD NAME");
        build.setUserId("BUILD USER ID");
        build.setUserName("BUILD USER NAME");

        final List<BuildParameter> params = new ArrayList<BuildParameter>();
        params.add(new BuildParameterImpl(
                "PARAM_ID", "PARAM NAME", "PARAM VALUE", build));

        build.setParameters(params);

        return build;
    }

    @Test
    public void differentAttributesShouldPreserveEquality(){
        final BuildDetails expected = getBuildDetails();
        final BuildDetails actual = getBuildDetails();
        Assert.assertEquals("Broken equality", expected, actual);

        final Calendar spaceman = Calendar.getInstance();
        spaceman.set(1961, 3, 12);

        actual.setDuration(actual.getDuration() + 1000);
        actual.setEndDate(spaceman.getTime());
        actual.setFullName(actual.getFullName() + "CHANGED");
        actual.setName(actual.getName() + "CHANGED");
        actual.setParameters(new ArrayList<BuildParameter>());
        actual.setStartDate(spaceman.getTime());
        actual.setUserId(actual.getUserId() + "CHANGED");
        actual.setUserName(actual.getUserName() + "CHANGED");
        Assert.assertEquals("Broken equality", expected, actual);
    }

    @Test
    public void differentIdShouldBreakEquality() {
        final BuildDetails expected = getBuildDetails();
        final BuildDetails actual = getBuildDetails();
        Assert.assertEquals("Broken equality", expected, actual);

        actual.setId(actual.getId() + "CHANGED");
        Assert.assertFalse("Broken inequality logic", actual.equals(expected));
    }

    @Test
    public void equalsNullShouldBeFalse() {
        final BuildDetails actual = getBuildDetails();
        Assert.assertFalse("Broken inequality logic", actual.equals(null));
    }

    @Test
    public void equalsSomethingElseShouldBeFalse() {
        final BuildDetails actual = getBuildDetails();
        Assert.assertFalse("Broken inequality logic", actual.equals("SOMESTRING"));
    }
}
