package example;

public class Settings {
	private boolean boolean_setting;
	private Integer integer_setting;
	private String string_setting;

	public Settings(boolean boolean_setting, Integer integer_setting,
			String string_setting) {
		super();
		this.boolean_setting = boolean_setting;
		this.integer_setting = integer_setting;
		this.string_setting = string_setting;
	}

	public Settings() {
	}

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
