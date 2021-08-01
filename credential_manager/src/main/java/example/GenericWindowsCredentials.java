package example;

public class GenericWindowsCredentials {
	private String address;
	private String username;
	private String password;

	public String getAddress() {
		return address;
	}

	public void setAddress(String data) {
		address = data;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String data) {
		username = data;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String data) {
		password = data;
	}

	@Override
	public String toString() {
		return "address:[" + getAddress() + "], username:[" + getUsername() + "], password:[" + getPassword() + "]";
	}

}
