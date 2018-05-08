/*
 * The MIT License
 * 
 * Copyright (c) 2015 IKEDA Yasuyuki
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.chikli.hudson.plugin.naginator;

import static org.junit.Assert.*;

import java.io.IOException;

import hudson.AbortException;
import hudson.Launcher;
import hudson.matrix.Axis;
import hudson.matrix.AxisList;
import hudson.matrix.Combination;
import hudson.matrix.MatrixRun;
import hudson.matrix.MatrixBuild;
import hudson.matrix.MatrixProject;
import hudson.model.AbstractBuild;
import hudson.model.FreeStyleProject;
import hudson.model.BuildListener;
import hudson.model.FreeStyleBuild;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.Builder;

import org.junit.ClassRule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

public class NaginatorPublisherScheduleActionTest {
	@ClassRule
	public static JenkinsRule j = new JenkinsRule();

	private static class FailSpecificAxisBuilder extends Builder {
		private final String combinationFilterToFail;

		public FailSpecificAxisBuilder(String combinationFilterToFail) {
			this.combinationFilterToFail = combinationFilterToFail;
		}

		@Override
		public boolean perform(AbstractBuild<?, ?> build, Launcher launcher,
				BuildListener listener) throws InterruptedException, IOException {
			if (!(build instanceof MatrixRun)) {
				throw new AbortException("only applicable for MatrixRun");
			}

			MatrixRun run = (MatrixRun) build;

			if (run.getParent().getCombination().evalGroovyExpression(
					run.getParent().getParent().getAxes(), combinationFilterToFail)) {
				throw new AbortException("Combination matches the filter.");
			}

			return true;
		}
	}

	@Test
	public void testConfigurationForRegexpOnFreeStyleProject() throws Exception {
		FreeStyleProject p = j.createFreeStyleProject();
		NaginatorPublisher naginator = new NaginatorPublisher(
				"Some regular expression", false, // rerunIfUnstable
				true, // retunMatrixPart
				true, // checkRegexp
				false, // maxScheduleOverrideAllowed
				1, // maxSchedule
				new FixedDelay(0));
		p.getPublishersList().add(naginator);

		NaginatorPublisherScheduleAction a = new NaginatorPublisherScheduleAction(
				naginator);
	}

}
