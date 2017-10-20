package example.pages;

import example.enums.URL;
import com.esotericsoftware.reflectasm.ConstructorAccess;
import ru.yandex.qatools.allure.annotations.Step;

public final class PageObjectSupplier {

	public static <T> T $(Class<T> pageObject) {
		return ConstructorAccess.get(pageObject).newInstance();
	}

	@Step("Open browser and go to the following url: {0}.")
	public static LoginPage loadSiteUrl(final URL url) {
		return $(LoginPage.class);
	}

	private PageObjectSupplier() {
		throw new UnsupportedOperationException("Illegal access to private constructor");
	}
}
