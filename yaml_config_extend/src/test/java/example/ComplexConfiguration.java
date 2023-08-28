package example;

import java.util.List;

public class ComplexConfiguration {
	private String version;
	private List<Services> services;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<Services> getServices() {
		return services;
	}

	public void setServices(List<Services> services) {
		this.services = services;
	}

	public static class Services {
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

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getImage() {
				return image;
			}

			public void setImage(String image) {
				this.image = image;
			}

			public String getContainer_name() {
				return container_name;
			}

			public void setContainer_name(String container_name) {
				this.container_name = container_name;
			}

			public String getRestart() {
				return restart;
			}

			public void setRestart(String restart) {
				this.restart = restart;
			}

			public String getBuild() {
				return build;
			}

			public void setBuild(String build) {
				this.build = build;
			}

			public List<String> getDepends_on() {
				return depends_on;
			}

			public void setDepends_on(List<String> depends_on) {
				this.depends_on = depends_on;
			}

			public List<String> getEnvironment() {
				return environment;
			}

			public void setEnvironment(List<String> environment) {
				this.environment = environment;
			}

			public List<String> getPorts() {
				return ports;
			}

			public void setPorts(List<String> ports) {
				this.ports = ports;
			}

			public String getProperty() {
				return property;
			}

			public void setProperty(String property) {
				this.property = property;
			}

		}
	}

	public static class Settings {
		private boolean boolean_setting;
		private Integer integer_setting;
		private String string_setting;

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

	}
}
