package example;

import java.util.UUID;

public class Artist {

	private String name;
	private String plays;
	private static String staticInfo;
	private int id;
	private String field1;
	private String field2;

	public String getName() {
		return name;
	}

	public void setName(String data) {
		name = data;
	}

	public String getPlays() {
		return plays;
	}

	public void setPlays(String data) {
		plays = data;
	}

	public int getId() {
		return id;
	}

	public void setId(int data) {
		id = data;
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

	public Artist(int id, String name, String plays, String field1,
			String field2) {
		super();
		if (Artist.staticInfo == null) {
			Artist.staticInfo = UUID.randomUUID().toString();
		}
		this.name = name;
		this.id = id;
		this.plays = plays;
		this.field1 = field1;
		this.field2 = field2;
	}

	public String getField1() {
		return field1;
	}

	public void setField1(String data) {
		field1 = data;
	}

	public String getField2() {
		return field2;
	}

	public void setField2(String data) {
		field2 = data;
	}

}
