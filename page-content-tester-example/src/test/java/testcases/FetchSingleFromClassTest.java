package testcases;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import paco.annotations.Fetch;
import paco.runner.Paco;

@Fetch(url = "localhost/example")
public class FetchSingleFromClassTest extends Paco {

    @Test
    public void can_fetch_from_class_annotation() {
        assertThat(page.get().getTitle()).contains("i'm the title");
    }

}