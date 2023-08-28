package example;

import java.util.List;

public class ComplexConfiguration {
	private String version;
	private List<Service> services;

	public static class Service {
		private Microservice microservice;

		public static class Microservice {
			private String name;
			private String image;
			private String container_name;
			private String restart;
			private String build;
			private List<String> depends_on;
			private List<String> environment;
			private List<String> ports;
			private String property;

		}
	}

	public static class Settings {
		private boolean boolean_setting;
		private Integer integer_setting;

		public boolean isBoolean_setting() {
			return boolean_setting;
		}

		public void setBoolean_setting(boolean boolean_setting) {
			this.boolean_setting = boolean_setting;
		}

		public Integer getInteger_setting() {
			return integer_setting;
		}

		public void setInteger_setting(Integer integer_setting) {
			this.integer_setting = integer_setting;
		}

		public String getString_setting() {
			return string_setting;
		}

		public void setString_setting(String string_setting) {
			this.string_setting = string_setting;
		}

		private String string_setting;
	}
}
