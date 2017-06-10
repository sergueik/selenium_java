package software.reinvent.commons.config;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import com.typesafe.config.Config;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CachedConfig {
	private final Config config;
	private final LoadingCache<String, String> stringCache = CacheBuilder
			.newBuilder().build(new CacheLoader<String, String>() {
				public String load(String key) throws Exception {
					return config.getString(key);
				}
			});
	private final LoadingCache<String, List<String>> stringListCache = CacheBuilder
			.newBuilder().build(new CacheLoader<String, List<String>>() {
				public List<String> load(String key) throws Exception {
					return config.getStringList(key);
				}
			});
	private final LoadingCache<String, Boolean> hasPathCache = CacheBuilder
			.newBuilder().build(new CacheLoader<String, Boolean>() {
				public Boolean load(String key) throws Exception {
					return config.hasPath(key);
				}
			});
	private final LoadingCache<String, Boolean> booleanCache = CacheBuilder
			.newBuilder().build(new CacheLoader<String, Boolean>() {
				public Boolean load(String key) throws Exception {
					return config.getBoolean(key);
				}
			});
	private final LoadingCache<String, Integer> intCache = CacheBuilder
			.newBuilder().build(new CacheLoader<String, Integer>() {
				public Integer load(String key) throws Exception {
					return config.getInt(key);
				}
			});
	private final LoadingCache<String, Long> longCache = CacheBuilder.newBuilder()
			.build(new CacheLoader<String, Long>() {
				public Long load(String key) throws Exception {
					return config.getLong(key);
				}
			});

	public CachedConfig(Config config) {
		this.config = config;
	}

	public String getString(String path) {
		try {
			return stringCache.get(path);
		} catch (ExecutionException e) {
			return config.getString(path);
		}
	}

	public boolean hasPath(String path) {
		try {
			return hasPathCache.get(path);
		} catch (ExecutionException e) {
			return config.hasPath(path);
		}
	}

	public boolean getBoolean(String path) {
		try {
			return booleanCache.get(path);
		} catch (ExecutionException e) {
			return config.getBoolean(path);
		}
	}

	public int getInt(String path) {
		try {
			return intCache.get(path);
		} catch (ExecutionException e) {
			return config.getInt(path);
		}
	}

	public long getLong(String path) {
		try {
			return longCache.get(path);
		} catch (ExecutionException e) {
			return config.getLong(path);
		}
	}

	public Config getConfig() {
		return config;
	}
}
