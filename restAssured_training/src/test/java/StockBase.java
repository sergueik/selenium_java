import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;

// TODO: convert parameterized test
// https://github.com/damengwang/restassuredDemo/blob/master/src/test/java/com/api/xueqiu/stocks/TestStocks.java
//from junit to testng

public class StockBase {
	public static RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
	public static RequestSpecification requestSpecification;

	@BeforeClass
	public static void beforeClass() {
		requestSpecBuilder.addHeader("Host", "api.xueqiu.com");
		requestSpecBuilder.addHeader("User-agent", "Xueqiu iPhone 11.3");
		requestSpecBuilder.addQueryParam("x", "0.67");
		requestSpecification = requestSpecBuilder.build();

	}
}

/*

import io.restassured.RestAssured;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import  static  io.restassured.RestAssured.*;
import  static  io.restassured.matcher.RestAssuredMatchers.*;
import  static  org.hamcrest.Matchers.*;


@RunWith(Parameterized.class)
public class TestStocks extends  StockBase{
    @Parameterized.Parameters
    public  static List<Object[]> data(){
        return Arrays.asList(new Object[] []{
                {2,1}
        });

    }
    @Parameterized.Parameter
    public Integer category;
    @Parameterized.Parameter(1)
    public Integer notice;

    @Test
    public void addStock(){
        useRelaxedHTTPSValidation();
        //信任证书
        RestAssured.proxy("127.0.0.1",7778);
        //指定代理
        given().spec(requestSpecification)
                //.header("Host","api.xueqiu.com")
                //.header("User-agent","Xueqiu iPhone 11.3")
                .cookie("xq_a_token","21ec8db7be0dfd37dc4e0ed53cb802b0440dff95")
                .cookie("u","3037241121")
                .formParam("category",category)
                .formParam("isnotice",notice)
                .formParam("symbol","SZ300222")
                .queryParam("_","1533539199033")
                //.queryParam("x","0.67")
                .queryParam("_s","a81f4b")
                .queryParam("_t","A3E93463-5E78-4F56-917E-B7871A771C8E.3037241121.1533538987852.1533539199035")
                .when().log().all().post("https://101.201.62.20/v4/stock/portfolio/addstock.json")
                .then().log().all()
                .statusCode(200);

    }
}

 */
