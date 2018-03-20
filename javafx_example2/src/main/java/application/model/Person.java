package application.model;
import java.time.LocalDate;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {
	private final StringProperty firstName;
	private final StringProperty lastName;
	private final StringProperty phone;
	private final StringProperty city;
	private final StringProperty state;
	private final IntegerProperty zip;
	private final BooleanProperty hasContract;
	
	
	public Person() {
		this(null, null, null, null, null, null, 0, false);
	}
	
	public Person(String firstName, String lastName, String phoneNumber, String phone, String city, String state, Integer zip, boolean hasContract)
	{
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.phone = new SimpleStringProperty(phone);
		this.city = new SimpleStringProperty(city);
		this.state = new SimpleStringProperty(state);
		this.zip = new SimpleIntegerProperty(zip);
		this.hasContract = new SimpleBooleanProperty(hasContract);
	}
	
	// Property Getters
	
	public StringProperty getFirstNameProperty() {
		return firstName;
	}

	public StringProperty getLastNameProperty() {
		return lastName;
	}

	public StringProperty getPhoneProperty() {
		return phone;
	}

	public StringProperty getCityProperty() {
		return city;
	}

	public StringProperty getStateProperty() {
		return state;
	}

	public IntegerProperty getZipProperty() {
		return zip;
	}

	public BooleanProperty getHasContractProperty() {
		return hasContract;
	}
	
	// Getter
	public String getFirstName() {
		return this.firstName.get();
	}
	
	public String getLastName() {
		return this.lastName.get();
	}
	
	public String getPhone() {
		return this.phone.get();
	}
	
	public String getCity() {
		return this.city.get();
	}
	
	public String getState() {
		return this.state.get();
	}
	
	public Integer getZip() {
		return this.zip.get();
	}
	
	public boolean getHasContract() {
		return this.hasContract.get();
	}
	
	// Setter
	public void setFirstName(String value) {
		this.firstName.set(value);
	}
	
	public void setLastName(String value) {
		this.lastName.set(value);
	}
	
	public void setPhone(String value) {
		this.phone.set(value);
	}
	
	public void setCity(String value) {
		this.city.set(value);
	}
	
	public void setState(String value) {
		this.state.set(value);
	}
	
	public void setZip(Integer value) {
		this.zip.set(value);
	}
	
	public void setHasContract(boolean value) {
		this.hasContract.set(value);
	}
	
}
