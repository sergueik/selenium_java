/*
 * Copyright 2018 midorlo.
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
import org.hamcrest.core.AnyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.containsString;

import org.testng.annotations.Test;

/**
 *
 * @author midorlo
 */
public class EnvironmentTest {

	/**
	 * Tests Plattform. This will fail on non-windows, non windows < 8.
	 */
	@Test
	public void testEnvironment() {
		System.out.println("testEnvironment");
		System.out.println("on " + System.getProperty("os.name"));
		String expResult = null;
		String expResult1 = "Windows 10";
		String expResult2 = "Windows 8";
		String expResult3 = "Windows 8.1";
		String expResult4 = "Windows Server 2012 R2"; // Appveyor
		String result = System.getProperty("os.name");
		Assert.assertTrue((expResult = expResult1).equals(result)
				|| (expResult = expResult2).equals(result)
				|| (expResult = expResult3).equals(result)
				|| (expResult = expResult4).equals(result));
		/*
		assertThat( System.getProperty("os.name"),
				anyOf(containsString(expResult1), containsString(expResult2),
						containsString(expResult3), containsString(expResult4)));
		*/
	}
}
