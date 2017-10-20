package example.model;

import example.db.entities.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "login_with_valid_credentials_data", schema = "automation")
public class LoginWithValidCredentialsData extends BaseEntity {

	@OneToOne
	@JoinColumn(name = "user_id")
	private UsersEntity user;

	public UsersEntity getUser() {
		return user;
	}

	public void setUser(final UsersEntity user) {
		this.user = user;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof LoginWithValidCredentialsData))
			return false;

		LoginWithValidCredentialsData that = (LoginWithValidCredentialsData) o;

		return getId() == that.getId() && user.equals(that.user);
	}

	@Override
	public int hashCode() {
		int result = getId();
		result = 31 * result + user.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "LoginWithValidCredentialsData{" + "user=" + user + '}';
	}
}
