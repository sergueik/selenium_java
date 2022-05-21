package example;

import org.junit.Before;
import org.junit.Test;

public class BsonConverterTest {

	@Test
	public void test() {

		Person person = new Person();
		person.setAge(34);
		person.setName("John");

		BsonConverter bsonConverter = new BsonConverter();
		byte[] bson = bsonConverter.toBson(person);
		Person myReturnPerson = (Person) bsonConverter.fromBson(bson, Person.class);

		System.out.println("Done");
	}

	private static class Person {

		private String name;
		private int age;

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
