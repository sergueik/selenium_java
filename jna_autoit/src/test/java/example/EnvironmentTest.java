/*
 * Copyright 2018-2019 midorlo, sergueik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author midorlo
 * @author sergueik
 */
public class EnvironmentTest {

	/**
	 * Tests Plattform. This will fail on non-windows, 
	 * and also on windows xp and older (chosen voluntarily).
	 */
	@Test(enabled = true)
	public void testEnvironment() {
		System.err.println("OS test is Running: " + System.getProperty("os.name"));
		String result = System.getProperty("os.name");

		List<String> osNames = Arrays.asList("Windows 10", "Windows 8",
				"Windows 8.1", "Windows 7", "Windows Server 2012 R2");
		Assert.assertTrue(osNames.contains(result));
		// see also:
		// https://www.programcreek.com/java-api-examples/index.php?api=org.hamcrest.core.AnyOf
		// NOTE: anyOf(containsString(expResult1),...) does not work with testng ?
	}

}
