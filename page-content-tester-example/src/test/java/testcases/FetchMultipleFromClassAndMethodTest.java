package testcases;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import paco.annotations.Fetch;
import paco.runner.Paco;

@Fetch(url = "localhost/example")
@Fetch(url = "localhost/example2")
public class FetchMultipleFromClassAndMethodTest extends Paco {

    @Test
    public void can_fetch_from_class_annotation() {
        assertThat(page.get(1).getTitle()).endsWith("title2");
        assertThat(page.get(0).getTitle()).endsWith("title");
    }

    @Fetch(url = "localhost/example")
    @Fetch(url = "localhost/example3")
    @Test
    public void can_fetch_from_class_annotation2() {
        assertThat(page.get(0).getTitle()).endsWith("title");
        assertThat(page.get(1).getTitle()).endsWith("title3");
    }

}