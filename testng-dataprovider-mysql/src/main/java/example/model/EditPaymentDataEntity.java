package example.model;

import example.db.entities.BaseEntity;

import javax.money.MonetaryAmount;
import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "edit_payment_data", schema = "automation")
public class EditPaymentDataEntity extends BaseEntity {

	@OneToOne
	@JoinColumn(name = "user_id")
	private UsersEntity user;

	@OneToOne
	@JoinColumn(name = "profile_id")
	private ProfilesEntity profile;

	@OneToOne
	@JoinColumn(name = "first_payment_id")
	private PaymentsEntity firstPayment;

	@OneToOne
	@JoinColumn(name = "second_payment_id")
	private PaymentsEntity secondPayment;

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

	public PaymentsEntity getFirstPayment() {
		return firstPayment;
	}

	public void setFirstPayment(final PaymentsEntity firstPayment) {
		this.firstPayment = firstPayment;
	}

	public PaymentsEntity getSecondPayment() {
		return secondPayment;
	}

	public void setSecondPayment(final PaymentsEntity secondPayment) {
		this.secondPayment = secondPayment;
	}

	@Transient
	public List<MonetaryAmount> getFormattedAmounts() {
		return Arrays.asList(getFirstPayment().getFormattedAmount(),
				getSecondPayment().getFormattedAmount());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof EditPaymentDataEntity))
			return false;

		EditPaymentDataEntity that = (EditPaymentDataEntity) o;

		return getId() == that.getId() && user.equals(that.user)
				&& profile.equals(that.profile)
				&& firstPayment.equals(that.firstPayment)
				&& secondPayment.equals(that.secondPayment);
	}

	@Override
	public int hashCode() {
		int result = getId();
		result = 31 * result + user.hashCode();
		result = 31 * result + profile.hashCode();
		result = 31 * result + firstPayment.hashCode();
		result = 31 * result + secondPayment.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "EditPaymentDataEntity{" + "user=" + user + ", profile=" + profile
				+ ", first payment=" + firstPayment + ", second payment="
				+ secondPayment + '}';
	}
}
