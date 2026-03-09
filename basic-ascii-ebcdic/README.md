### Info



Example external jar-clean java class for convert EBCDIC to ASCII and back	
### Usage
#### Basic
```cmd
mvn clean package
```

```cmd
type sample.txt
```

```text
%≥≤⌠⌡÷≈°∙≡üéâäàåçêëæÆôöòûùÿÖóúñÑªº¿⌐
```

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

```sh
java  -cp target/example.ascii-ebcdic.jar;target\lib\* example.Converter  -operation validate -data "818283848586878889919293949596979899A2A3A4A5A6A7A8A9F0F1F2F3F4F5F6F7F8F9" -codepage EBCDIC
```
```text
valid
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
