### Info
This directory contains basic example of [pdfbox](https://pdfbox.apache.org)-based pdf document text extractor

###  Usage
```cmd
mvn package
```
* provide input and output file arguments:
```sh
java -cp target/lib/*:target/example.pdf_reader.jar example.ReadingPDF -in alphabet_ru.pdf -out a.txt
cat a.txt
```
```sh
абвгдежзийклмнопрстуфхцчшщъыьэюя
```
* on Windows need the code page to changed to display UTF-8 encoded text:
```cmd
java -cp target\lib\*;target\example.pdf_reader.jar example.ReadingPDF -in alphabet_ru.pdf  -out a.txt
type a.txt
```
```cmd
абвгдежзийклмнопрстуфхцчшщъыьэюя
```

* in the absence of `out` arguments, prints the extracted text to the console, 
```sh
java -cp target/lib/*:target/example.pdf_reader.jar example.ReadingPDF -in alphabet_ru.pdf
абвгдежзийклмнопрстуфхцчшщъыьэюя
```
* on Windows needs an extra option:
```cmd
chcp 65001
set JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8
java -cp target\lib\*;target\example.pdf_reader.jar example.ReadingPDF -in alphabet_ru.pdf
```
```cmd
абвгдежзийклмнопрстуфхцчшщъыьэюя
```
### TODO
* on Windows console fix the Cyrillic text-induced formating defect:

```cmd
set JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8
java -cp target\lib\*;target\example.pdf_reader.jar example.ReadingPDF alphabet_ru.pdf -out -
```
Picked up JAVA_TOOL_OPTIONS: -Dfile.encoding=UTF-8
абвгдежзийклмнопрстуфхцчшщъыьэюя
стуфхцчшщъыьэюя
�ъыьэюя
юя
```
* better detect OS code page
```sh
java -cp target\lib\*;target\example.pdf_reader.jar example.ReadingPDF alphabet_ru.pdf
```
????????????????????????????????


### See Also

  * https://coderlessons.com/tutorials/java-tekhnologii/vyuchit-pdfbox/pdfbox-kratkoe-rukovodstvo (in Russian)

  * https://www.tutorialspoint.com/pdfbox/pdfbox_reading_text.htm
  * https://dzone.com/articles/pdfbox-java-library-to-extract-content-from-a-pdf
  * legacy [.NET version](https://svn.apache.org/repos/asf/pdfbox/site/publish/userguide/dot_net.html?p=1197837) - needs the [IKVM.NET](http://www.ikvm.net) (implementation of Java for Mono and the Microsoft .NET Framework) - presumably better alternatives exist
 * [PDFsharp](http://pdfsharp.net/?AspxAutoDetectCookieSupport=1) - an .NET library for processing PDF
 * old article on [working with PDF files in C# using PdfBox and IKVM](https://www.codeproject.com/articles/538617/working-with-pdf-files-in-csharp-using-pdfbox-and)
  * pdfbox-based __PDF testing library__ with misc. custom matchers [repository](https://github.com/codeborne/pdf-test)

### Author

[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
