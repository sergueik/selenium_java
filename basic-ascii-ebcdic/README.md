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
``
```text
818283848586878889919293949596979899A2A3A4A5A6A7A8A9F0F1F2F3F4F5F6F7F8F9
```
```cmd
java -cp target/example.ascii-ebcdic.jar;target\lib\* example.ManageConfig -inputfile sample.txt -outputfile result.txt  -operation encode

```cmd
java  -cp target/example.ascii-ebcdic.jar;target\lib\* example.Converter  -outputfile test.txt -operation decode -data "818283848586878889919293949596979899A2A3A4A5A6A7A8A9F0F1F2F3F4F5F6F7F8F9"
``
```text
abcdefghijklmnopqrstuvwxyz0123456789
```
### See Also

  * https://www.baeldung.com/java-ebcdic-ascii-conversion
  * https://stackoverflow.com/questions/368603/convert-string-from-ascii-to-ebcdic-in-java


