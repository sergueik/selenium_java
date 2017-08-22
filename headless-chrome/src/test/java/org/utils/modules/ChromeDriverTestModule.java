package org.utils.modules;

import com.google.inject.AbstractModule;
import com.typesafe.config.Config;
import org.openqa.selenium.chrome.ChromeDriver;
import software.reinvent.commons.config.ConfigProvider;
import software.reinvent.headless.chrome.HeadlessChromeDriver;
import software.reinvent.headless.chrome.provider.HeadlessChromeProvider;

public class ChromeDriverTestModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Config.class).toProvider(ConfigProvider.class);
        bind(ChromeDriver.class).toProvider(HeadlessChromeProvider.class);
        bind(HeadlessChromeDriver.class);
    }
}
