package org.utils;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.utils.FailedRetryCount;

public class SampleTest {
	private int cnt = 0;

	@Test
	@FailedRetryCount(5)
	public void test1() {
		Assert.assertEquals(false, true);
	}

	@Test
	@FailedRetryCount(5)
	public void test2() {
		cnt++;
		Assert.assertEquals(cnt, 2);
	}

	@Test
	public void test3() {
		Assert.assertEquals(false, true);
	}
}
