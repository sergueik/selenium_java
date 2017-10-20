package example.model;

import example.db.entities.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "users", schema = "automation")
public class UsersEntity extends BaseEntity {

	private String email;
	private String password;
	private String username;

	@Basic
	@Column(name = "email", nullable = false, length = 45)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Basic
	@Column(name = "password", nullable = false, length = 45)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Basic
	@Column(name = "username", nullable = false, length = 45)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		UsersEntity that = (UsersEntity) o;

		return getId() == that.getId()
				&& (email != null ? email.equals(that.email)
						: that.email == null
								&& (password != null ? password.equals(that.password)
										: that.password == null
												&& (username != null ? username.equals(that.username)
														: that.username == null)));

	}

	@Override
	public int hashCode() {
		int result = getId();
		result = 31 * result + (email != null ? email.hashCode() : 0);
		result = 31 * result + (password != null ? password.hashCode() : 0);
		result = 31 * result + (username != null ? username.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "UsersEntity{" + "email='" + email + '\'' + ", password='" + password
				+ '\'' + ", username='" + username + '\'' + '}';
	}
}
