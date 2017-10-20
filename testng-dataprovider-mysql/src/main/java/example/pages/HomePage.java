package example.pages;
import ru.yandex.qatools.allure.annotations.Step;

import static example.pages.PageObjectSupplier.$;

public class HomePage {

	@Step("Open \"New Payments\" page.")
	public NewPaymentPage openNewPaymentsPage() {
		return $(NewPaymentPage.class);
	}

	public String getActualUsername() {
		return "Serhii Korol";
	}
}
