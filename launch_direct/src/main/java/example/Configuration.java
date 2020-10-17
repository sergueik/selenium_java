package example;

import static java.lang.String.format;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

final class Configuration implements Map<String, Object> {

	public Map<String, String> data; // not used
	public String environment;
	public String datacenter;
	public String role;
	public String hostname;

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String data) {
		environment = data;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String data) {
		role = data;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String data) {
		hostname = data;
	}

	public String getDatacenter() {
		return datacenter;
	}

	public void setDatacenter(String data) {
		datacenter = data;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
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
