package com.driver.locator.utility;

import java.util.function.Predicate;

public class NullRemove implements Predicate<Object> {

	@Override
	public boolean test(Object obj) {
		return obj == null;
	}

}
