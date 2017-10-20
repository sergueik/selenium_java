package example.utils;

import org.aspectj.lang.JoinPoint;

import java.util.Optional;

public final class AspectUtils {

	public static Optional<Object> getMethodArgByIndex(final JoinPoint joinPoint, final int index) {
		return Optional.ofNullable(joinPoint.getArgs())
				.filter(args -> index >= 0 && index < args.length)
				.map(args -> args[index]);
	}

	private AspectUtils() {
		throw new UnsupportedOperationException("Illegal access to private constructor");
	}
}
