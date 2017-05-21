package stories;

import org.jbehave.core.annotations.Then;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestSteps {

	@Then("Home page is accessible")
	public void checkHomePage() throws IOException {

		String baseUrl = System.getProperty("docker.url");
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(baseUrl, String.class);
		assertThat("Default 'Hello World!' string expected", result,
				is("Hello World!"));
	}
}
