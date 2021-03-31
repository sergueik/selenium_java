package example;

import java.util.UUID;

public class Artist {

	private String name;
	private String plays;
	private static String staticInfo;
	private int id;

	public String getName() {
		return name;
	}

	public void setName(String data) {
		this.name = data;
	}

	public String getPlays() {
		return plays;
	}

	public void setPlays(String data) {
		this.plays = data;
	}

	public int getId() {
		return id;
	}

	public void setId(int data) {
		this.id = data;
	}

	public Artist() {
		staticInfo = UUID.randomUUID().toString();
	}

	public /* static */ String getStaticInfo() {
		return Artist.staticInfo;
	}

	public Artist(int id, String name, String plays) {
		super();
		if (Artist.staticInfo == null) {
			Artist.staticInfo = UUID.randomUUID().toString();
		}
		this.name = name;
		this.id = id;
		this.plays = plays;
	}

}
