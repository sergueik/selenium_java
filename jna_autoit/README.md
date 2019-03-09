### Info

This project started from a replica of [JNAutoIt - JNA AutoitX dll wrapper](https://github.com/midorlo/JNAutoIt)
project by Michael Dorlöchter that offers a [Java Native Access](https://en.wikipedia.org/wiki/Java_Native_Access)
based  providing dynamically invoked core AutoitX [functions](https://documentation.help/AutoItX/) methods
wrappers `AU3_*` that are exported from the vendor dll which can be e.g. installed from
[Nuget package for AutoitX](https://www.nuget.org/packages/AutoItX/) with no
COM dll registration nor application installation.

Since code differences are somewhat big, no upstream PR is available at this time.

### Project Goals

Unfortunately for the Java developer the original `AutoItXLibrary` interface method signatures are too low level

This project adds a few more java-friendly methods like
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
to `AutoItX`.  This greately simplifies interacting with AutoIt, see below.

Currently extended with Java-friendly signatures are the following metods:

  * `AutoItSetOption`
  * `ControlSend`
  * `Run`
  * `Send`
  * `WinActivate`
  * `WinActive`
  * `WinClose`
  * `WinExists`
  * `WinGetText`
  * `WinGetTitle`
  * `WinKill`
  * `WinWaitActive`

This addresses the anticipated needs of AutoIt with Selenium testing.

Converting all methods is a work in progress (adequate tests might be a  bit of a challenge).

### Example Usage

#### Save the downloaded File under desired Path

This is much lightweight compared to the
original  [file Upload Example using AutoIT and with Selenium Webdriver](https://www.guru99.com/use-autoit-selenium.html) recipe.
which requires an full "compile script to exe"
Windows executable generation from the following sample `.au3` [script](https://automated-testing.info/t/webdriver-features-rabota-s-upload-popup-windows-native-okno-pri-pomoshhi-selenium-web-driver/2288):
```basic
WinWaitActive("Open") // ждем активности Windows окна Open
Send("D:\AutoIT-commands\TestingVideo.mp4") //Отправляем иму путь к файлу (фокус по умолчанию стоит на текст-боксе где прописываеться путь к файлу)
Send("{ENTER}") //эмулируем нажатие клавиши Enter (а-ля загрузить)
```

```java
import example.AutoItX;
// pending PR
// import de.midorlo.jnautoit.jna.AutoItX;

  String windowTitle = "Save";
  String windowText = "";
  AutoItX instance = AutoItX.getInstance();
  String filePath =	"C:\\Users\\user\\Downloads\\TestingVideo.mp4";
  // Selenium test launches file download
  instance.WinWaitActive(windowTitle, windowText);
  instance.Send(filePath);
  instance.Send("\n");
```

#### Change page Zoom or restore 100% zoom Settings

The JNA verion supports the genuine [AutoIt keys and modifiers](https://www.autoitscript.com/autoit3/docs/appendix/SendKeys.htm)

```java
import example.AutoItX;

	@Test(enabled = true)
	public void testZoomFirefoxBrowser() {
		System.err.println("Close Mozilla Firefox Browser");
		title = "Mozilla Firefox Start Page";
		instance.AutoItSetOption("SendKeyDownDelay", 30);
		instance.AutoItSetOption("SendKeyDelay", 10);
    // zoom out four times
		for (int cnt = 0 ; cnt!=4; cnt++){
      instance.Send("^-", true);
      sleep(1000);
		}
    // zoom 100 %
		instance.Send("^0", true);
		sleep(1000);
		// CTLR + is a bit tricky since the '+' itself has a special meaning
    // zoom in 2 times
		instance.Send("^{+}^{+}", true);
		sleep(1000);
		instance.WinClose(title, text);
```
### See Also

  * [Java Native Access](https://github.com/java-native-access/jna) project on github
  * [Index of /autoit3/docs/functions](https://www.autoitscript.com/autoit3/docs/functions/)
  * Powershell iAutoIt cmdlets [documentation](https://www.autoitconsulting.com/site/scripting/autoit-cmdlets-for-windows-powershell/)
  * [Autoit forum (in Russian)](http://autoit-script.ru/index.php).
  * [Misc. autoit-related topics in QA forum (in Russian, mostly)](https://automated-testing.info/search?q=autoit)

### License
This project is licensed under the terms of the MIT license.

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
