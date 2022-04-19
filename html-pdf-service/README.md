### Info

This directory contains a replica of [HTML-PDF-Service](https://github.com/farrukhmpk/html-pdf-service) application
that is using [Flying-Saucer](https://flyingsaucerproject.github.io/flyingsaucer/r8/guide/users-guide-R8.html)
and [iText](https://itextpdf.com/en/resources/api-documentation)
and hosts HTML pages for input HTML and CSS separately (also hosts JSON input for handlebar template).

### Usage

```sh
mvn package
java -jar target/html-pdf-service.war
```
this will open interactive form on `http://localhost:8080`.

To test rendering capabilities use combination of basic table style examples from
[HTML Tables pros and cons](https://html.com/tables/) and from [building HTML Tables tutorial](https://guru-css.com/page/html-tablicy-polnyj-kurs) (in Russian)

enter the following into the BODY:
```html
<table>
  <caption>Объединение ячеек по 2-ум направлениям</caption>
  <tr>
    <th>Столбец 1</th>
    <th>Столбец 2</th>
    <th>Столбец 3</th>
    <th>Столбец 4</th>
  </tr>
  <tr>
    <td colspan="3">Строка2 Ячейка1 (соединена с ячейками 2 и 3)</td>
    <td rowspan="3">Строка2 Ячейка4 (соединена с ячейкой 4 в строках 3 и 4)</td>
  </tr>
  <tr>
    <td colspan="2" rowspan="2">Строка3 Ячейка1 (соединена с ячейкой 2 строки 3 и ячейками 1 и 2 строки 4)</td>
    <td>Строка3 Ячейка3</td>
  </tr>
  <tr>
    <td>Строка4 Ячейка3</td>
  </tr>
</table>
<table>
  <caption>A complex table
</caption>
  <thead>
    <tr> 
      <th colspan="3">Invoice #123456789</th>
      <th>14 January 2025</th>
    </tr>
    <tr>
      <td colspan="2"><strong>Pay to:</strong><br/> Acme Billing Co.<br/> 123 Main St.<br/> Cityville, NA 12345</td>
      <td colspan="2"><strong>Customer: </strong><br/> John Smith<br/> 321 Willow Way<br/> Southeast Northwestershire, MA 54321 </td>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>Name / Description</th>
      <th>Qty. </th>
      <th>@</th>
      <th>Cost</th>
    </tr>
    <tr>
      <td>Paperclips</td>
      <td>1000</td>
      <td>0.01</td>
      <td>10.00</td>
    </tr>
    <tr>
      <td>Staples (box)</td>
      <td>100</td>
      <td>1.00</td>
      <td>100.00</td>
    </tr>
  </tbody>
  <tfoot>
    <tr>
      <th colspan="3">Subtotal</th>
      <td> 110.00</td>
    </tr>
    <tr>
      <th colspan="2">Tax</th>
      <td> 8%</td>
      <td>8.80</td>
    </tr>
    <tr>
      <th colspan="3">Grand Total</th>
      <td>$ 118.80</td>
    </tr>
  </tfoot>
</table>

```
and the following style into CSS input:
```css
@font-face {
  font-family: 'HabraFont';
  src: url(file:///C:/windows/fonts/Montserrat-Medium.ttf);
    -fs-pdf-font-embed: embed;
  -fs-pdf-font-encoding: Identity-H;
}

@page {
  margin: 0px;
  padding: 0px;
  size: A4 portrait;
}

@media print {
  .new_page {
    page-break-after: always;
  }
}

@page {
  margin: 0px;
  padding: 0px;
  size: A4 portrait;
}

@media print {
  .new_page {
    page-break-after: always;
  }
}

body *{
  padding: 0;
  margin: 0;
}

* {
  font-family: 'HabraFont';
}

#block {
  width: 90%;
  margin: auto;
  background-color: white;
  border: dashed #dbdbdb 1px;
}

#logo {
  margin-top: 5px;
  width: 100%;
  text-align: center;
  border-bottom: dashed #dbdbdb 1px;
}

#content {
  padding-left: 10px;
}

h1 {color: blue;text-align: center;}
strong {color: red;}
p {font-family: verdana;font-size: 20px;}

table{
  margin: 50px 0;
  text-align: left;
  border-collapse: separate;
  border: 1px solid #ddd;
  border-spacing: 10px;
  border-radius: 3px;
  background: #fdfdfd;
  font-size: 14px;
  width: auto;
}

td,th{
  border: 1px solid #ddd;
  padding: 5px;
  border-radius: 3px;
}
th{
  background: #E4E4E4;
}
caption{
  font-style: italic;
  text-align: right;
  color: #547901;
}

table, th, td {
  border: 1px solid black;
}
table {
  width: 100%;
  border-collapse: collapse;
}
```
on Linux replace the font URI with `file:///usr/share/fonts/truetype/Montserrat-Regular.ttf` or some other installed font. User-installed
paths like `file:///home/sergueik/.local/share/fonts/Montserrat-Medium.ttf` work too.

### Running in Docker
```sh
mvn package dockerfile:build
```
```
CONTAINER=html-pdf-service
docker run -e "SPRING_PROFILES_ACTIVE=DEVELOPMENT" –p 8080:8080 -v /opt/html-pdf-service-volume:/logs $CONTAINER
```

![Example](https://github.com/sergueik/selenium_java/blob/master/html-pdf-service/screenshots/capture.png)

### TODO


* convert to more recent Springboot (challenge is with upgrading the `MvcConfig` class)
* cleanup maven project file towards directly using Docker commands which is a better opton on low-resource host
### See Also

  * [itext documentation](https://devcolibri.com/%D0%BA%D0%BE%D0%BD%D0%B2%D0%B5%D1%80%D1%82%D0%B0%D1%86%D0%B8%D1%8F-html-%D0%B2-pdf-%D0%B8%D1%81%D0%BF%D0%BE%D0%BB%D1%8C%D0%B7%D1%83%D1%8F-java/) (in Russian)
  * https://github.com/caireserison/spring-html-pdf-flying-saucer


