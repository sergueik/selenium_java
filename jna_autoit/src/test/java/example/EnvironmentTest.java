/*
 * Copyright 2018 midorlo.
 * Updated 2019 by sergueik
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

import org.testng.Assert;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.containsString;

import org.testng.annotations.Test;

/**
 *
 * @authors midorlo, sergueik
 */
public class EnvironmentTest {

	/**
	 * Tests Platform. This will fail on non-windows OS.
	 */
	@Test
	public void testEnvironment() {
		System.out.println("testEnvironment");
		System.out.println("on " + System.getProperty("os.name"));
		String expResult = null;
		String expResult1 = "Windows 10";
		String expResult2 = "Windows 8";
		String expResult3 = "Windows 8.1";
		String expResult4 = "Windows 7";
		String expResult5 = "Windows Server 2012 R2"; // Appveyor
		String result = System.getProperty("os.name");
		Assert.assertTrue((expResult = expResult1).equals(result)
				|| (expResult = expResult2).equals(result)
				|| (expResult = expResult3).equals(result)
				|| (expResult = expResult4).equals(result));

		// NOTE: does not work with testng ?
		// the method assertThat(String, anyOf(containsString(expResult1),
		// containsString(expResult2))) is undefined
		// see also:
		// https://www.programcreek.com/java-api-examples/index.php?api=org.hamcrest.core.AnyOf
		/*
		assertThat(System.getProperty("os.name"),
				anyOf(containsString(expResult1), containsString(expResult2)));
				assertThat( System.getProperty("os.name"),
						anyOf(containsString(expResult1), containsString(expResult2),
								containsString(expResult3), containsString(expResult4)));
				*/
	}
}
