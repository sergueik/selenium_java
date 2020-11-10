		### Info
This directory contains basic example of [pdfbox](https://pdfbox.apache.org)-based pdf document text extractor

###  Usage
```cmd
mvn package
```
```cmd
chcp 65001
java -cp target\lib\*;target\example.pdf_reader.jar example.ReadingPDF -in alphabet_ru.pdf -out a.txt
type a.txt
```
```sh
абвгдежзийклмнопрстуфхцчшщъыьэюя
```
```
set JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8
java -cp target\lib\*;target\example.pdf_reader.jar example.ReadingPDF alphabet_ru.pdf -out -
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
java -cp target\lib\*;target\example.pdf_reader.jar example.ReadingPDF alphabet_ru.pdf
```
????????????????????????????????


### See Also

  * https://coderlessons.com/tutorials/java-tekhnologii/vyuchit-pdfbox/pdfbox-kratkoe-rukovodstvo (in Russian)

  * https://www.tutorialspoint.com/pdfbox/pdfbox_reading_text.htm
  * https://dzone.com/articles/pdfbox-java-library-to-extract-content-from-a-pdf

https://svn.apache.org/repos/asf/pdfbox/site/publish/userguide/dot_net.html?p=1197837#:~:text=Even though PDFBox is written,NET framework.

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
