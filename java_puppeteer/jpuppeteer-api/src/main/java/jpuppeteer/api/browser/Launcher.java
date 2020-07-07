package jpuppeteer.api.browser;

public interface Launcher {

    Browser launch(String... args) throws Exception;

}
