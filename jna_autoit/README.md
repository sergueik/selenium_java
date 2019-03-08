### Info

This directory contains a replica of [JNAutoIt - JNA AutoitX dll wrapper](https://github.com/midorlo/JNAutoIt) by Midorlo that generates a java method wrapping all core AutoitX [functions](https://documentation.help/AutoItX/) from the dll that can be e.g. installed from
[Nuget package for AutoitX](https://www.nuget.org/packages/AutoItX/).


The project adds a more java-convenient methods like
```java
public String WinGetText(String title, String text) {
// ...
}
```

compared to underlying
```java
public void AU3_WinGetText(WString title, WString text, LPWSTR resultPointer, int bufSize) {
// ...	
}
```
both wrapping the [WinGetText](https://www.autoitscript.com/autoit3/docs/functions/WinGetText.htm) method for retrieving the text from a window.
```basic
WinGetText
Retrieves the text from a window.

WinGetText ( "title" [, "text"] )
```
Converting all methods is a work in progress.

### See Also
  * [Index of /autoit3/docs/functions](https://www.autoitscript.com/autoit3/docs/functions/)
  * Powershell iAutoIt cmdlets [documentation](https://www.autoitconsulting.com/site/scripting/autoit-cmdlets-for-windows-powershell/)
  * [Autoit forum (in Russian)](http://autoit-script.ru/index.php).

### License
This project is licensed under the terms of the MIT license.

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
