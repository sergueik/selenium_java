package example;

import java.util.List;

public class Services {
	private Microservice microservice;

	public Services() {
	}

	public Services(Microservice microservice) {
		this.microservice = microservice;
	}

	public Microservice getMicroservice() {
		return microservice;
	}

	public void setMicroservice(Microservice microservice) {
		this.microservice = microservice;
	}

}
