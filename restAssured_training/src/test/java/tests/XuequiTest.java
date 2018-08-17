package tests;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

// based on: https://github.com/damengwang/restassuredDemo
public class XuequiTest {

	@Test(enabled = false)
	public void testSearch() {
		useRelaxedHTTPSValidation();
		// Certificate

		String code = given().queryParam("code", "sogo")
				.header("Cookie",
						"__utma=1.1567865697.1524209803.1524209803.1524209803.1; __utmz=1.1524209803.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); device_id=0833471adc5fe06b6586f09927402356; aliyungf_tc=AQAAAAep+w684gUA0Cz+cq//x2tpprdn; xq_a_token=7443762eee8f6a162df9eef231aa080d60705b21; xq_a_token.sig=3dXmfOS3uyMy7b17jgoYQ4gPMMI; xq_r_token=9ca9ab04037f292f4d5b0683b20266c0133bd863; xq_r_token.sig=6hcU3ekqyYuzz6nNFrMGDWyt4aU; u=901530620640863; Hm_lvt_1db88642e346389874251b5a1eded6e3=1530620641; _ga=GA1.2.1567865697.1524209803; _gid=GA1.2.588455621.1530620641; _gat_gtag_UA_16079156_4=1; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1530621495")
				.when().get("http://xueqiu.com/stock/search.json").then().log().all()
				.statusCode(200).body("stocks.name", hasItem("搜狗"))
				.body("stocks.code", hasItem("SOGO")).extract().path("name");
		System.out.print(code);

	}
}
