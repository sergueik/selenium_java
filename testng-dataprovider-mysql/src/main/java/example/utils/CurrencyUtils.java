package example.utils;

import javaslang.control.Try;
import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public final class CurrencyUtils {

	private static final NumberFormat FORMATTER = DecimalFormat.getCurrencyInstance(Locale.UK);

	public static MonetaryAmount moneyOf(final BigDecimal amount) {
		return Money.of(amount, getCurrencyCode());
	}

	public static MonetaryAmount moneyOf(final String amount) {
		return Try.of(() -> Money.of(FORMATTER.parse(amount), getCurrencyCode()))
				.orElseThrow((ex) -> new IllegalArgumentException("Wrong amount format", ex));
	}

	public static String getCurrencyCode() {
		return FORMATTER.getCurrency().getCurrencyCode();
	}

	private CurrencyUtils() {
		throw new UnsupportedOperationException("Illegal access to private constructor");
	}
}
