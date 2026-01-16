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
java -cp target/example.ascii-ebcdic.jar;target\lib\* example.ManageConfig -inputfile sample.txt -outputfile result.txt -operation decode -debug
```

```text
1234567890abcdefghijklmnopqrstuvwxyz
Done: decode
```

```cmd
java -cp target/example.ascii-ebcdic.jar;target\lib\* example.ManageConfig -inputfile sample.txt -outputfile result.txt  -operation encode
```
### See Also

  * https://www.baeldung.com/java-ebcdic-ascii-conversion
  * https://stackoverflow.com/questions/368603/convert-string-from-ascii-to-ebcdic-in-java


