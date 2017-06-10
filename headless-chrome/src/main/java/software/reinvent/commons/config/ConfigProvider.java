
package software.reinvent.commons.config;

import com.google.inject.Provider;

import com.typesafe.config.Config;

public class ConfigProvider implements Provider<Config> {
	@Override
	public Config get() {
		return ConfigLoader.load();
	}
}
