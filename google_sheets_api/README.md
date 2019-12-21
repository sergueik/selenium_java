### Info 
This directory contains a subset of the [https://github.com/eugenp/tutorials](https://github.com/eugenp/tutorials)
intended to be used with the [testNg-DataProviders](https://github.com/sergueik/testng-dataproviders).

The Google Spreadsheet API secret file `client_secret.json` is loaded from a hidden resource 
directory `.secret` under user profile (home) directory - that file must not be checked in. The credentials are valid for one hour (this is probably configurable through Google Developer web interface).
In the absence of the secret file the tests would fail.

### Usage
The sample test case initializes the `sheetsService` using the singleton `sheetsServiceUtil` proxy class:
```java
@Before
public void setup() throws GeneralSecurityException, IOException {
	final String applicationName = "Google Sheets Example";
	sheetsServiceUtil.setApplicationName(applicationName);
final String secretFilePath = Paths.get(System.getProperty("user.home")).resolve(".secret").resolve("client_secret.json").toAbsolutePath().toString();
	sheetsServiceUtil.setSecretFilePath(secretFilePath);
	sheetsService = sheetsServiceUtil.getSheetsService();
}
```
then reads the spreadsheet data:
```java
@Test
public void test1() throws IOException {
	final String id = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx-xxxxxxxx";
	final String range = "Users!A2:C";
	System.err.println(
		"Examine spreadsheets in specified id: " + id + " and range: " + range);
	List<List<Object>> values = sheetsService.spreadsheets().values()
		.get(id, range).execute().getValues();
	assertThat(values, notNullValue());
	assertThat(values.size() != 0, is(true));
	System.err.println("Reading " + values.size() + " value rows");
	for (List<Object> row : values) {
		System.err.println("Got: " + row);
	}
}
```
### Data Provider
One can also create a `DataProvider` method to load the test method parameters in a similar fashion to [Excel data provider](https://github.com/sergueik/testng-dataproviders):
```java
	public List<Object[]> readGoogleSpreadsheet(String spreadsheetId,
			String sheetName) {
		String range = String.format("%s!A2:Z", sheetName);
		List<Object[]> result = new LinkedList<>();

		try {
			ValueRange response = sheetsService.spreadsheets().values()
					.get(spreadsheetId, range).execute();

			List<List<Object>> resultRows = response.getValues();
			assertThat(resultRows, notNullValue());
			assertThat(resultRows.size() != 0, is(true));

			System.err.println("Got " + resultRows.size() + " result rows");
			for (List<Object> resultRow : resultRows) {
				// System.err.println("Got: " + resultRow);
				result.add(resultRow.toArray());
			}
		} catch (IOException e) {
		}
		return result;
	}

```
### See Also

 * [how to use Google Sheets API to read data from Spreadsheet](http://www.seleniumeasy.com/selenium-tutorials/read-data-from-google-spreadsheet-using-api)
 * [Interact with Google Sheets from Java](https://www.baeldung.com/google-sheets-java-client)
 * very detaled [publication](https://gist.github.com/zmts/802dc9c3510d79fd40f9dc38a12bccfc) on Token-Based Authentication and JSON Web Tokens (JWT) (in Russian)
 
### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)

