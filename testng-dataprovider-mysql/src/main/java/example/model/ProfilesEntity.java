package example.model;

import example.db.entities.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "profiles", schema = "automation")
public class ProfilesEntity extends BaseEntity {
	private String name;

	@Basic
	@Column(name = "name", nullable = false, length = 30)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ProfilesEntity that = (ProfilesEntity) o;

		return getId() == that.getId() && (name != null ? name.equals(that.name) : that.name == null);

	}

	@Override
	public int hashCode() {
		int result = getId();
		result = 31 * result + (name != null ? name.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "ProfilesEntity{" +
				"name='" + name + '\'' +
				'}';
	}
}
