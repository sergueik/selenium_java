package testcases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jsoup.Connection.Method.POST;
import static paco.configurations.FetcherMethodOptions.params;
import static paco.fetcher.FetchedPage.fetcher;

import org.json.JSONObject;
import org.junit.Test;

import paco.fetcher.Page;
import paco.runner.Paco;

public class UsingParamsBuilderTest extends Paco {

    @Test
    public void get_page_and_check_title() {
        final Page page = fetcher(params()
                .urlToFetch("http://localhost:8089/example")
                .build());

        assertThat(page.getTitle()).isEqualTo("i'm the title");
    }

    @Test
    public void post_and_check_response_body() {
        final Page page = fetcher(params()
                .urlToFetch("http://localhost:8089/example")
                .method(POST)
                .build());

        assertThat(page.getContentType()).isEqualTo("application/json");
        assertThat(page.getJsonResponse().get("data")).isEqualTo("some value");
    }

    @Test
    public void post_with_json_request_body_and_check_response_body() {
        JSONObject json = new JSONObject();
        json.put("data", "test");

        final Page page = fetcher(params()
                .urlToFetch("http://localhost:8089/replay-post")
                .method(POST)
                .requestBody(json.toString())
                .build());

        assertThat(page.getContentType()).isEqualTo("application/json");
        assertThat(page.getJsonResponse().get("data")).isEqualTo("test");
    }

    @Test
    public void post_with_string_request_body_and_check_response_body() {
        String body = "{\"data\":\"test2\"}";
        final Page page = fetcher(params()
                .urlToFetch("http://localhost:8089/replay-post")
                .method(POST)
                .requestBody(body)
                .build());

        assertThat(page.getContentType()).isEqualTo("application/json");
        assertThat(page.getJsonResponse().get("data")).isEqualTo("test2");
    }
}
