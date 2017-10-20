package example;

import example.annotations.Entity;
import example.core.BaseTest;
import example.db.utils.DataProviderUtils;

import static example.db.utils.DataProviderUtils.*;
import static example.pages.PageObjectSupplier.*;

import example.enums.Schema;

import example.enums.URL;
import example.model.CreatePaymentDataEntity;
import example.model.EditPaymentDataEntity;
import example.model.LoginWithValidCredentialsData;
import example.pages.HomePage;
import example.pages.NewPaymentPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DataProviderTests extends BaseTest {

	@Entity(model = CreatePaymentDataEntity.class, schema = Schema.AUTOMATION)
	@Test(dataProviderClass = DataProviderUtils.class, dataProvider = GENERIC_DP)
	public void createPayment(final CreatePaymentDataEntity data) {
		loadSiteUrl(URL.DEV).loginWith(data.getUser()).openNewPaymentsPage()
				.selectPaymentProfile(data.getProfile().getName()).addNewPayment()
				.fillInPaymentInfoAndSave(data.getPayment());

		Assert.assertEquals($(NewPaymentPage.class).getActualAmount(),
				data.getPayment().getFormattedAmount());
	}

	@Entity(model = EditPaymentDataEntity.class, schema = Schema.AUTOMATION)
	@Test(dataProviderClass = DataProviderUtils.class, dataProvider = GENERIC_DP)
	public void editPayment(final EditPaymentDataEntity data) {
		loadSiteUrl(URL.DEV).loginWith(data.getUser()).openNewPaymentsPage()
				.selectPaymentProfile(data.getProfile().getName()).addNewPayment()
				.fillInPaymentInfoAndSave(data.getFirstPayment()).editPayment()
				.fillInPaymentInfoAndSave(data.getSecondPayment());

		Assert.assertEquals($(NewPaymentPage.class).getActualAmount(),
				data.getSecondPayment().getFormattedAmount());
	}

	@Entity(model = LoginWithValidCredentialsData.class, schema = Schema.AUTOMATION)
	@Test(dataProviderClass = DataProviderUtils.class, dataProvider = GENERIC_DP)
	public void loginWithValidCredentials(
			final LoginWithValidCredentialsData data) {
		loadSiteUrl(URL.DEV).loginWith(data.getUser());

		Assert.assertEquals($(HomePage.class).getActualUsername(),
				data.getUser().getUsername());
	}
}
