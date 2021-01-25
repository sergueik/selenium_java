package tests;

public class User {

	private int id;
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String phone;
	private int userStatus;

	public User() {
	}

	public User setId(int data) {
		id = data;
		return this;
	}

	public User setUsername(String data) {
		username = data;
		return this;
	}

	public User setFirstName(String data) {
		firstName = data;
		return this;
	}

	public User setLastName(String data) {
		lastName = data;
		return this;
	}

	public User setEmail(String data) {
		email = data;
		return this;
	}

	public User setPassword(String data) {
		password = data;
		return this;
	}

	public User setPhone(String data) {
		phone = data;
		return this;
	}

	public User setUserStatus(int data) {
		userStatus = data;
		return this;
	}

	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getPhone() {
		return phone;
	}

	public int getUserStatus() {
		return userStatus;
	}
}
