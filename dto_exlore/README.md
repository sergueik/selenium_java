### Info

This project shows custom with
custom property
```java
public class ArtistSerializer implements JsonSerializer<Artist> {
	private List<String> reportFields = new ArrayList<>();
	public void setReportFields(List<String> data) {
		reportFields = data;
	}

	public void setReportFields(String... fields) {
		for (int cnt = 0; cnt != fields.length; cnt++) {
			reportFields.add(fields[cnt]);
		}
	}
	public void resetReportFields() {
		reportFields.clear();
	}

	@Override
	public JsonElement serialize(final Artist data, final Type type,
			final JsonSerializationContext context) {
		JsonObject result = new JsonObject();

		if (reportFields.size() == 0 || reportFields.contains("name")) {
			String name = data.getName();
			if (name != null && !name.isEmpty()) {
				result.add("name", new JsonPrimitive(name));
			}
		}
		return result;
	}
}
```
that controls which fields to serialize. It may be  usefule e.g. when indexing the test results on ELK

The class is used in the adapter role:
```java


ArtistSerializer serializer = new ArtistSerializer();
Gson gson = new GsonBuilder().registerTypeAdapter(Artist.class, new ArtistSerializer()).create();
serializer.setReportFields("name");
Artist artist = new Artist(1, "Paul", "vocals")
JsonElement rowJson = serializer.serialize(artist, null, null);
System.err.println("JSON serialization or artist:\n" + rowJson.toString());
```
it will print:
```json
{"id":3,"staticInfo":"4e61c229-1555-4135-b938-4ddf4d40aa82","name":"paul"}
```
while if
```java
serializer.resetReportFields();
```
was called then
```json
{
  "id": 1,
  "staticInfo": "7b2ade68-3800-4d1a-9b72-7d22d9482a35",
  "name": "paul",
  "plays": "vocals"
}

```
will be printed
(the `id` and `guid` fields are chosen to be shown regardless of the state of  `reportFields`
### TODO

The names  of getter methods may be funky.
One may [Determine method name through reflection](https://github.com/Blastman/DtoTester/blob/master/src/test/java/com/objectpartners/DtoTest.java
), and once caching is implement, improve the class above
(repeated discovery could be expensive)

### See Also


### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
