### Info
This directory contains basic example of [pdfbox](https://pdfbox.apache.org)-based pdf document text extractor

###  Usage
```cmd
mvn package
```
```
java -cp target\lib\*;target\pdf_reader-0.1.0-SNAPSHOT.jar example.ReadingPDF alphabet_en.pdf
abcdefghijklmnopqrstuvwxyz
```
```
set JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8

java -cp target\lib\*;target\pdf_reader-0.1.0-SNAPSHOT.jar example.ReadingPDF alphabet_ru.pdf
```
Picked up JAVA_TOOL_OPTIONS: -Dfile.encoding=UTF-8
абвгдежзийклмнопрстуфхцчшщъыьэюя
стуфхцчшщъыьэюя
�ъыьэюя
юя
```
### TODO
better detect OS code page
```sh
java -cp target\lib\*;target\pdf_reader-0.1.0-SNAPSHOT.jar example.ReadingPDF alphabet_ru.pdf
```
????????????????????????????????


### See Also
https://coderlessons.com/tutorials/java-tekhnologii/vyuchit-pdfbox/pdfbox-kratkoe-rukovodstvo (in Russian)

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
