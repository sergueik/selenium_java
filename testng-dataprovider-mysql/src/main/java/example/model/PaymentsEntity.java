package example.model;

import example.db.entities.BaseEntity;

import javax.money.MonetaryAmount;
import javax.persistence.*;
import java.math.BigDecimal;

import static example.utils.CurrencyUtils.moneyOf;

@Entity
@Table(name = "payments", schema = "automation")
public class PaymentsEntity extends BaseEntity {
	private String account;
	private BigDecimal amount;

	@Basic
	@Column(name = "account", nullable = false, length = 45)
	public String getAccount() {
		return account;
	}

	public void setAccount(final String account) {
		this.account = account;
	}

	@Basic
	@Column(name = "amount", nullable = false, precision = 2)
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(final BigDecimal amount) {
		this.amount = amount;
	}

	@Transient
	public MonetaryAmount getFormattedAmount() {
		return moneyOf(getAmount());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		PaymentsEntity that = (PaymentsEntity) o;

		return getId() == that.getId()
				&& (account != null ? account.equals(that.account)
						: that.account == null && (amount != null
								? amount.equals(that.amount) : that.amount == null));

	}

	@Override
	public int hashCode() {
		int result = getId();
		result = 31 * result + (account != null ? account.hashCode() : 0);
		result = 31 * result + (amount != null ? amount.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "PaymentsEntity{" + "account='" + account + '\'' + ", amount="
				+ amount + '}';
	}
}
