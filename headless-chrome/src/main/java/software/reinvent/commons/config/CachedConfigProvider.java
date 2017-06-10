package software.reinvent.commons.config;

import com.google.inject.Provider;

public class CachedConfigProvider implements Provider<CachedConfig> {
	@Override
	public CachedConfig get() {
		return ConfigLoader.loadCached();
	}
}
