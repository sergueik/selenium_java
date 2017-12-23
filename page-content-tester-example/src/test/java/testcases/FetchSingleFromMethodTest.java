package testcases;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import paco.annotations.Fetch;
import paco.runner.Paco;

public class FetchSingleFromMethodTest extends Paco {

    @Test
    @Fetch(url = "localhost/example")
    public void can_fetch_from_method_annotation() {
        assertThat(page.get().getTitle()).contains("i'm the title");
    }

}