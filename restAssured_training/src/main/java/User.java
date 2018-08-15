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

	public User setId(int id) {
		this.id = id;
		return this;
	}

	public User setUsername(String username) {
		this.username = username;
		return this;
	}

	public User setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public User setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public User setEmail(String email) {
		this.email = email;
		return this;
	}

	public User setPassword(String password) {
		this.password = password;
		return this;
	}

	public User setPhone(String phone) {
		this.phone = phone;
		return this;
	}

	public User setUserStatus(int userStatus) {
		this.userStatus = userStatus;
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
