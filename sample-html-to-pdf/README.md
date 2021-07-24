### Info

  Replica of [ruslanys/sample-html-to-pdf](https://github.com/ruslanys/sample-html-to-pdf) converted to standalone console applicatiion


### Usage

```sh
mvn clean package
java -cp target/web-to-pdf.jar example.App file:///home/sergueik/src/selenium_java/sample-html-to-pdf/src/main/resources/example.html  >a.pdf
```
### Note
Style have to be embedded (styles are critical for locale support):
```css
@font-face {
  font-family: "HabraFont";
  src: url(file:///home/sergueik/src/selenium_java/sample-html-to-pdf/src/main/webapp/resources/fonts/tahoma.ttf);
  -fs-pdf-font-embed: embed;
  -fs-pdf-font-encoding: Identity-H;
}

* {
  font-family: HabraFont;
}
```
Attempt to print the following sample
```html
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="styles.css"/>
</head>
<body>
  пример
</body>
</html>
```
leads to error:
```java
java.io.IOException: Stream closed
	at java.io.BufferedInputStream.getInIfOpen(BufferedInputStream.java:159)
	at java.io.BufferedInputStream.read1(BufferedInputStream.java:284)
	at java.io.BufferedInputStream.read(BufferedInputStream.java:345)
	at sun.nio.cs.StreamDecoder.readBytes(StreamDecoder.java:284)
	at sun.nio.cs.StreamDecoder.implRead(StreamDecoder.java:326)
	at sun.nio.cs.StreamDecoder.read(StreamDecoder.java:178)
	at java.io.InputStreamReader.read(InputStreamReader.java:184)
	at org.xhtmlrenderer.css.parser.Lexer.zzRefill(Lexer.java:1634)
	at org.xhtmlrenderer.css.parser.Lexer.yylex(Lexer.java:1865)
	at org.xhtmlrenderer.css.parser.CSSParser.next(CSSParser.java:1824)
	at org.xhtmlrenderer.css.parser.CSSParser.la(CSSParser.java:1836)
	at org.xhtmlrenderer.css.parser.CSSParser.stylesheet(CSSParser.java:159)
	at org.xhtmlrenderer.css.parser.CSSParser.parseStylesheet(CSSParser.java:89)
	at org.xhtmlrenderer.context.StylesheetFactoryImpl.parse(StylesheetFactoryImpl.java:78)
	at org.xhtmlrenderer.context.StylesheetFactoryImpl.parse(StylesheetFactoryImpl.java:95)
	at org.xhtmlrenderer.context.StylesheetFactoryImpl.getStylesheet(StylesheetFactoryImpl.java:174)
	at org.xhtmlrenderer.context.StyleReference.readAndParseAll(StyleReference.java:123)
	at org.xhtmlrenderer.context.StyleReference.setDocumentContext(StyleReference.java:107)
	at org.xhtmlrenderer.pdf.ITextRenderer.setDocument(ITextRenderer.java:176)
	at org.xhtmlrenderer.pdf.ITextRenderer.setDocument(ITextRenderer.java:143)
	at org.xhtmlrenderer.pdf.ITextRenderer.setDocumentFromString(ITextRenderer.java:160)
	at org.xhtmlrenderer.pdf.ITextRenderer.setDocumentFromString(ITextRenderer.java:153)
	at example.App.performPdfDocument(App.java:48)
	at example.App.main(App.java:27)
```
Using the __Fira Sans__ woff fonts from [хабр](https://habr.com/ru) from local file or url,
```css
//  origin:  view-source:https://habr.com/ru/post/217561/
/* cyrillic */
@font-face {
  font-family: 'Fira Sans';
  font-style: normal;
  font-weight: 500;
  font-display: swap;
  src: url(https://fonts.gstatic.com/s/firasans/v11/va9B4kDNxMZdWfMOD5VnZKveQhf6TF0.woff2) format('woff2');
  unicode-range: U+0400-045F, U+0490-0491, U+04B0-04B1, U+2116;
}

```

or downloading __Arial Unicode MS Regular__ from [FreeFontsDownload.net](https://freefontsdownload.net/free-arial-unicode-ms-font-36926.htm):
```css
@font-face {
  font-family: 'Arial Unicode MS Regular';
  src: url(file:///home/sergueik/src/selenium_java/sample-html-to-pdf/src/main/webapp/resources/fonts/ArialUnicodeMS.ttf);
  -fs-pdf-font-embed: embed;
  -fs-pdf-font-encoding: Identity-H;
}
```
silently fails to render cyrillic text (must be encoding-sensitive)
### See Also

  * the original [post](https://habrahabr.ru/post/217561/) (in Russian)
  * [wkhtmltopdf](https://wkhtmltopdf.org)
  * [stackoverflow](https://stackoverflow.com/questions/30889217/html-to-pdf-convert-cyrillic-characters-not-displayed-properly)

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
