package example;

import java.util.List;

public class Services {
	private List<Microservice> microservice;

	public Services() {
	}

	public Services(List<Microservice> microservice) {
		this.microservice = microservice;
	}

	public List<Microservice> getMicroservice() {
		return microservice;
	}

	public void setMicroservice(List<Microservice> microservice) {
		this.microservice = microservice;
	}

}
