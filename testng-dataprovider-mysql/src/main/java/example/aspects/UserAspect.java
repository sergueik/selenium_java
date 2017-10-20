package example.aspects;

import example.model.UsersEntity;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import static example.utils.AspectUtils.*;
import static example.core.BaseTest.*;
import static example.utils.ReflectionUtils.*;

@Aspect
public class UserAspect {

	private static final String DEFAULT_USER_FIELD_NAME = "user";

	@Before("execution(@org.testng.annotations.Test * com.blogspot.notes.automation.qa.tests..*.*(..))")
	public void takeUser(final JoinPoint joinPoint) {
		getMethodArgByIndex(joinPoint, 0).ifPresent(arg -> getPool().ifPresent(
				pool -> setFieldValue(arg, DEFAULT_USER_FIELD_NAME, pool.take())));
	}

	@After("execution(@org.testng.annotations.Test * com.blogspot.notes.automation.qa.tests..*.*(..))")
	public void releaseUser(final JoinPoint joinPoint) {
		getMethodArgByIndex(joinPoint, 0)
				.ifPresent(arg -> getFieldValue(arg, DEFAULT_USER_FIELD_NAME)
						.onSuccess(value -> getPool()
								.ifPresent(pool -> pool.restore((UsersEntity) value))));
	}
}
