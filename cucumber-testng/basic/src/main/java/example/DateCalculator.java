package example;

import java.util.Date;

// sut 
public class DateCalculator {
	private Date now;

	public DateCalculator(Date date) {
		now = date;
	}

	public String isDateInThePast(Date date) {
		return (date.before(now)) ? "yes" : "no";
	}
}
