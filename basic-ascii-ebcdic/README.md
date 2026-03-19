### Info


### Usage

```cmd
mvn clean package
```



```cmd
java -cp target\example.ascii-ebcdic.jar;target\lib\* example.Runner -help true
```
```text
Usage: jar -operation=[encode|decode] -data <string> -inputfile <filename> -outputfile <filename> -codepage <codepage>
```

```cmd
java  -cp target/example.ascii-ebcdic.jar;target\lib\* example.Runner  -operation validate -data "818283848586878889919293949596979899A2A3A4A5A6A7A8A9F0F1F2F3F4F5F6F7F8F9" -codepage EBCDIC
```
```text
valid
```


```cmd
java  -cp target/example.ascii-ebcdic.jar;target\lib\* example.Runner  -outputfile sample.txt -operation encode -data "1234567890abcdefghijklmnopqrstuvwxyz"
```
```text
F1F2F3F4F5F6F7F8F9F0818283848586878889919293949596979899A2A3A4A5A6A7A8A9
```

```cmd
type sample.txt
```

```text
%≥≤⌠⌡÷≈°∙≡üéâäàåçêëæÆôöòûùÿÖóúñÑªº¿⌐
```



```sh
java -cp target/example.ascii-ebcdic.jar;target\lib\* example.Runner -inputfile sample.txt -outputfile result.txt -operation decode -codepage cp037 -debug true
```
```text
Doing: decode cp037
1234567890abcdefghijklmnopqrstuvwxyz

Done: decode cp037
```
```java
java -cp target/example.ascii-ebcdic.jar;target\lib\* example.Runner -data C8C5D3D3D6 -operation validate  -debug true -codepage cp037
```
```text
Processing: [-data, C8C5D3D3D6, -operation, validate, -debug, true, -codepage, cp037]
[debug, data, codepage, operation]
Doing: validate cp037
valid
Done: validate cp037
```

```sh
java -cp target/example.ascii-ebcdic.jar;target\lib\* example.Runner -data "C8C500D3D6" -operation validate  -debug true -codepage cp037
```
```text
[debug, data, codepage, operation]
Doing: validate cp037
invalid
Done: validate cp037
```
### Usage
#### Basic

```sh
java -cp target/example.ascii-ebcdic.jar;target\lib\* example.Converter -inputfile sample.txt -outputfile result.txt -operation decode -debug
```

```text
1234567890abcdefghijklmnopqrstuvwxyz
Done: decode
```

```cmd
java  -cp target/example.ascii-ebcdic.jar;target\lib\* example.Converter  -outputfile test.txt -operation encode -data "abcdefghijklmnopqrstuvwxyz0123456789"
```
```text
818283848586878889919293949596979899A2A3A4A5A6A7A8A9F0F1F2F3F4F5F6F7F8F9
```
```cmd
java -cp target/example.ascii-ebcdic.jar;target\lib\* example.ManageConfig -inputfile sample.txt -outputfile result.txt  -operation encode
```

```cmd
java  -cp target/example.ascii-ebcdic.jar;target\lib\* example.Converter  -outputfile test.txt -operation decode -data "818283848586878889919293949596979899A2A3A4A5A6A7A8A9F0F1F2F3F4F5F6F7F8F9"
```
```text
abcdefghijklmnopqrstuvwxyz0123456789
```

### Validation

```sh
java  -cp target/example.ascii-ebcdic.jar;target\lib\* example.Converter  -operation encode -data "abcdefghijklmnopqrstuvwxyz0123456789"
```
```text
818283848586878889919293949596979899A2A3A4A5A6A7A8A9F0F1F2F3F4F5F6F7F8F9
```

```sh
java  -cp target/example.ascii-ebcdic.jar;target\lib\* example.Converter  -operation validate -data "abcdefghijklmnopqrstuvwxyz0123456789" -codepage EBCDIC
```

```
java  -cp target/example.ascii-ebcdic.jar;target\lib\* example.Converter  -operation validate -data "818283848586878889919293949596979899A2A3A4A5A6A7A8A9F0F1F2F3F4F5F6F7F8F9" -codepage ascii -debug
```
```text
Read 36 bytes
invalid
invalid US-ASCII character 0xF9 on 35
```

### Technical Details

EBCDIC has a weird layout but text tends to cluster:

![charmap](https://github.com/sergueik/selenium_java/blob/master/basic-ascii-ebcdic/screenshots/capture-charmap.jpg)

			
| Category       | Hex range                              |
| ---------------- | -------------------------------------- |
| space	         | 0x40                                   |
| lowercase      | 0x81–0x89, 0x91–0x99, 0xA2–0xA9         |
| uppercase      | 0xC1–0xC9, 0xD1–0xD9, 0xE2–0xE9        |
| digits         | 0xF0–0xF9                              |
| punctuation    | 0x4B, 0x6B, 0x5A, 0x7A, 0x60–0x6F      |
| fallback bytes | 0x45, 0xCE, 0xE9, 0xD3 , 0xC7          |



This leads to  the following "in the range" probe:

```java
boolean valid =
  // space
  charCode == 0x40 || 
  // digits
  (charCode >= 0xF0 && charCode <= 0xF9) ||
  // uppercase
  (charCode >= 0xC1 && charCode <= 0xC9) || (charCode >= 0xD1 && charCode <= 0xD9) || (charCode >= 0xE2 && charCode <= 0xE9) ||
  // lowercase
  (charCode >= 0x81 && charCode <= 0x89) || (charCode >= 0x91 && charCode <= 0x99) || (charCode >= 0xA2 && charCode <= 0xA9) ||
  // basic punctuation window
  (charCode >= 0x4A && charCode <= 0x6F) ||
  // fallback bytes for accented letters of Western European or symbols outside ASCII,
  charCode == 0x45 ||charCode == 0xCE || charCode == 0xE9 || charCode == 0xD3 || charCode == 0xC7;
```

### Full European Character Scan

to construct a full alphabet covering phrase in Eutopean languages, one may pick that language equivalent of "the quick brown fox" phrase:

![charmap es](https://github.com/sergueik/selenium_java/blob/master/basic-ascii-ebcdic/screenshots/capture-charmap-es.jpg)

![charmap fr](https://github.com/sergueik/selenium_java/blob/master/basic-ascii-ebcdic/screenshots/capture-charmap-fr.jpg)

to workaround unrecognized fallback character code errors, one has to provid additional accepted character codes:
```java
	// EBCDIC predicate: non-contiguous valid ranges, including digits, letters,
	// punctuation and fallback
	// EBCDIC isn’t contiguous like ASCII
	private static IntPredicate isValidEBCDICChar = charCode ->
		// space
		charCode == 0x40 ||
		// digits
		(charCode >= 0xF0 && charCode <= 0xF9) ||
		// uppercase letters
		(charCode >= 0xC1 && charCode <= 0xC9) || (charCode >= 0xD1 && charCode <= 0xD9)
		|| (charCode >= 0xE2 && charCode <= 0xE9) ||
		// lowercase letters
		(charCode >= 0x81 && charCode <= 0x89) || (charCode >= 0x91 && charCode <= 0x99)
		|| (charCode >= 0xA2 && charCode <= 0xA9) ||
		// basic punctuation
		(charCode >= 0x4A && charCode <= 0x6F) ||
		// generic fallback bytes for Western European accented  characters
		// Use with caution: feeding it arbitrary unknown input
		// e.g., passport names or company names entered from localized keyboards
		// may pass validation even though the bytes do not accurately represent the original characters
		charCode == 0x3F ||  // '?' fallback for unmapped characters
		charCode == 0x45 ||  // generic accented/fallback
		charCode == 0x49 ||  // generic accented/fallback
		charCode == 0x7D ||  // generic accented/fallback
		charCode == 0xCE ||  // generic accented/fallback
		charCode == 0xDE ||  // generic accented/fallback
		charCode == 0xD3 ||  // generic accented/fallback
		charCode == 0xC7 ||  // generic accented/fallback
		charCode == 0xE9 ||  // generic accented/fallback
		charCode == 0xDC;    // generic accented/fallback


```
this makes the tests pass:
```java
	static Stream<Arguments> samples() {
    		return Stream.of(Arguments.of("Spanish accented characters in CP1047", "El veloz murciélago hindú comía feliz cardillo y kiwi; la cigüeña tocaba el saxofón detrás del palenque de paja", true),
				Arguments.of("Canadian French accented characters in CP1047", "Voix ambiguë d'un cœur qui au zéphyr préfère les jattes de kiwi", true));
;
	}

	@DisplayName("EBCDIC strict validation for non-US")
	@ParameterizedTest
	@MethodSource("sample")
	void test1(String description, String input, boolean expected) {

		// validate encoded string
		ValidationResult result = Converter.validateGeneric(input.getBytes(Charset.forName(codePage)), codePage,
				charset, Converter.getIntPredicate(codePage), null);

		assertThat(description + " input=" + input + " message=" + result.getMessage(), result.isValid(), is(expected));

	}

```

```sh
mvn test -Dtest=example.InternationalEBCDICValidationTest
```
```text
[INFO]
[INFO] Results:
[INFO]
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
```
### Flow

#### Separate Validators → Simpler Control Flow, Easy Diagrams

![strict](https://github.com/sergueik/selenium_java/blob/master/basic-ascii-ebcdic/screenshots/validate-straight-flow.png)


![threshold](https://github.com/sergueik/selenium_java/blob/master/basic-ascii-ebcdic/screenshots/validate-threshold-flow.png)

#### Merged Generic Validator → No Code Duplicatiom ,  Heavier Logic Graph



### See Also

  * https://www.baeldung.com/java-ebcdic-ascii-conversion
  * https://en.wikipedia.org/wiki/EBCDIC

### Author

* [Serguei Kouzmine](kouzmine_serguei@yahoo.com)
