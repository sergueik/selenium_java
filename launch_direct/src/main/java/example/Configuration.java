package example;

import java.util.LinkedHashMap;
import static java.lang.String.format;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

// The type LinkedHashMap<String,Object> cannot be a superinterface of Configuration; 
// a superinterface must be an interface
final class Configuration implements Map<String, Object> {

	public static class Builder {
		public String environment;
		public String datacenter;
		public String role;
		public String hostname;

		public Builder() {
		}

		public Builder Environment(final String data) {
			this.environment = data;
			return this;
		}

		public Builder Datacenter(final String data) {
			this.datacenter = data;
			return this;
		}

		public Builder Role(final String data) {
			this.role = data;
			return this;
		}

		public Builder Hostname(final String data) {
			this.hostname = data;
			return this;
		}

		public Configuration build() {
			return new Configuration(this);
		}
	}

	public Map<String, String> data; // not used
	public String environment;
	public String datacenter;
	public String role;
	public String hostname;

	public Configuration(final Builder builder) {
		this.datacenter = builder.datacenter;
		this.role = builder.role;
		this.environment = builder.environment;
		this.hostname = builder.hostname;
	}

	public String getEnvironment() {
		return environment;
	}

	public String getRole() {
		return role;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(final String data) {
		hostname = data;
	}

	public String getDatacenter() {
		return datacenter;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(final Map<String, String> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return new StringBuilder().append(format("Hostname: %s\n", hostname))
				.append(format("Role: %s\n", role))
				.append(format("Environment: %s\n", environment))
				.append(format("DC: %s\n", datacenter)).toString();
	}

	@Override
	public void clear() {

	}

	@Override
	public boolean containsKey(Object key) {
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		return false;
	}

	@Override
	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		return null;
	}

	@Override
	public Object get(Object key) {
		return null;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public Set<String> keySet() {
		return null;
	}

	@Override
	public Object put(String key, Object value) {
		return null;
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {

	}

	@Override
	public Object remove(Object key) {
		return null;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public Collection<Object> values() {
		return null;
	}
}
