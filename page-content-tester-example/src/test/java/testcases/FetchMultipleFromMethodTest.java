package testcases;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import paco.annotations.Fetch;
import paco.runner.Paco;

public class FetchMultipleFromMethodTest extends Paco {

    @Test
    @Fetch(url = "localhost/example")
    @Fetch(url = "localhost/example2")
    public void can_fetch_from_method_annotation() {
        assertThat(page.get(1).getTitle()).endsWith("title2");
        assertThat(page.get(0).getTitle()).endsWith("title");
    }

}