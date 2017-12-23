package testcases;

import static org.assertj.core.api.Assertions.assertThat;
import static paco.annotations.Fetch.Device.DESKTOP;
import static paco.annotations.Fetch.Device.MOBILE;

import org.junit.Test;

import paco.annotations.Fetch;
import paco.runner.Paco;

public class FetchMultipleFromMethodMultipleTimesTest extends Paco {

    @Test
    @Fetch(url = "localhost/example")
    @Fetch(url = "localhost/example2")
    public void can_fetch_from_method_annotation() {
        assertThat(page.get(0).getTitle()).endsWith("title");
        assertThat(page.get(1).getTitle()).endsWith("title2");
    }

    @Test
    @Fetch(url = "localhost/example2")
    @Fetch(url = "localhost/example")
    public void can_fetch_from_method_annotation2() {
        assertThat(page.get(0).getTitle()).endsWith("title2");
        assertThat(page.get(1).getTitle()).endsWith("title");
    }

    @Test
    @Fetch(url = "localhost/example")
    @Fetch(url = "localhost/example", device = MOBILE)
    public void can_fetch_from_method_annotation3() {
        assertThat(page.get(DESKTOP).getUserAgent()).isEqualTo(DESKTOP.value);
        assertThat(page.get(MOBILE).getUserAgent()).isEqualTo(MOBILE.value);
    }

}