package example.model;

import example.db.entities.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "create_payment_data", schema = "automation")
public class CreatePaymentDataEntity extends BaseEntity {

	@OneToOne
	@JoinColumn(name = "user_id")
	private UsersEntity user;

	@OneToOne
	@JoinColumn(name = "profile_id")
	private ProfilesEntity profile;

	@OneToOne
	@JoinColumn(name = "payment_id")
	private PaymentsEntity payment;

	public UsersEntity getUser() {
		return user;
	}

	public void setUser(final UsersEntity user) {
		this.user = user;
	}

	public ProfilesEntity getProfile() {
		return profile;
	}

	public void setProfile(final ProfilesEntity profile) {
		this.profile = profile;
	}

	public PaymentsEntity getPayment() {
		return payment;
	}

	public void setPayment(final PaymentsEntity payment) {
		this.payment = payment;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof CreatePaymentDataEntity)) return false;

		CreatePaymentDataEntity that = (CreatePaymentDataEntity) o;

		return getId() == that.getId() && user.equals(that.user) &&
				profile.equals(that.profile) && payment.equals(that.payment);
	}

	@Override
	public int hashCode() {
		int result = getId();
		result = 31 * result + user.hashCode();
		result = 31 * result + profile.hashCode();
		result = 31 * result + payment.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "CreatePaymentDataEntity{" +
				"user=" + user +
				", profile=" + profile +
				", payment=" + payment +
				'}';
	}
}
