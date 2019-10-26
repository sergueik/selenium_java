### Info

This project started from a replica of [JNAutoIt - JNA AutoitX dll wrapper](https://github.com/midorlo/JNAutoIt)
project by Michael Dorlöchter that offers a [Java Native Access](https://en.wikipedia.org/wiki/Java_Native_Access)
based  providing dynamically invoked core AutoitX [functions](https://documentation.help/AutoItX/) methods
wrappers `AU3_*`
that are exported from the vendor dll which can be e.g. installed from
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

### TODO:


One can generate the dump list of the `AutoItX3_x64.dll` exports using Visual Studio linker tool (platform-specific version):

```cmd

"c:\Program Files (x86)\Microsoft Visual Studio 11.0\VC\bin\amd64\link.exe" /dump /exports AutoItX3_x64.dll 
  AU3_AutoItSetOption
  AU3_ClipGet
  AU3_ClipPut
  AU3_ControlClick
  AU3_ControlClickByHandle
  AU3_ControlCommand
  AU3_ControlCommandByHandle
  AU3_ControlDisable
  AU3_ControlDisableByHandle
  AU3_ControlEnable
  AU3_ControlEnableByHandle
  AU3_ControlFocus
  AU3_ControlFocusByHandle
  AU3_ControlGetFocus
  AU3_ControlGetFocusByHandle
  AU3_ControlGetHandle
  AU3_ControlGetHandleAsText
  AU3_ControlGetPos
  AU3_ControlGetPosByHandle
  AU3_ControlGetText
  AU3_ControlGetTextByHandle
  AU3_ControlHide
  AU3_ControlHideByHandle
  AU3_ControlListView
  AU3_ControlListViewByHandle
  AU3_ControlMove
  AU3_ControlMoveByHandle
  AU3_ControlSend
  AU3_ControlSendByHandle
  AU3_ControlSetText
  AU3_ControlSetTextByHandle
  AU3_ControlShow
  AU3_ControlShowByHandle
  AU3_ControlTreeView
  AU3_ControlTreeViewByHandle
  AU3_DriveMapAdd
  AU3_DriveMapDel
  AU3_DriveMapGet
  AU3_Init
  AU3_IsAdmin
  AU3_MouseClick
  AU3_MouseClickDrag
  AU3_MouseDown
  AU3_MouseGetCursor
  AU3_MouseGetPos
  AU3_MouseMove
  AU3_MouseUp
  AU3_MouseWheel
  AU3_Opt
  AU3_PixelChecksum
  AU3_PixelGetColor
  AU3_PixelSearch
  AU3_ProcessClose
  AU3_ProcessExists
  AU3_ProcessSetPriority
  AU3_ProcessWait
  AU3_ProcessWaitClose
  AU3_Run
  AU3_RunAs
  AU3_RunAsWait
  AU3_RunWait
  AU3_Send
  AU3_Shutdown
  AU3_Sleep
  AU3_StatusbarGetText
  AU3_StatusbarGetTextByHandle
  AU3_ToolTip
  AU3_WinActivate
  AU3_WinActivateByHandle
  AU3_WinActive
  AU3_WinActiveByHandle
  AU3_WinClose
  AU3_WinCloseByHandle
  AU3_WinExists
  AU3_WinExistsByHandle
  AU3_WinGetCaretPos
  AU3_WinGetClassList
  AU3_WinGetClassListByHandle
  AU3_WinGetClientSize
  AU3_WinGetClientSizeByHandle
  AU3_WinGetHandle
  AU3_WinGetHandleAsText
  AU3_WinGetPos
  AU3_WinGetPosByHandle
  AU3_WinGetProcess
  AU3_WinGetProcessByHandle
  AU3_WinGetState
  AU3_WinGetStateByHandle
  AU3_WinGetText
  AU3_WinGetTextByHandle
  AU3_WinGetTitle
  AU3_WinGetTitleByHandle
  AU3_WinKill
  AU3_WinKillByHandle
  AU3_WinMenuSelectItem
  AU3_WinMenuSelectItemByHandle
  AU3_WinMinimizeAll
  AU3_WinMinimizeAllUndo
  AU3_WinMove
  AU3_WinMoveByHandle
  AU3_WinSetOnTop
  AU3_WinSetOnTopByHandle
  AU3_WinSetState
  AU3_WinSetStateByHandle
  AU3_WinSetTitle
  AU3_WinSetTitleByHandle
  AU3_WinSetTrans
  AU3_WinSetTransByHandle
  AU3_WinWait
  AU3_WinWaitActive
  AU3_WinWaitActiveByHandle
  AU3_WinWaitByHandle
  AU3_WinWaitClose
  AU3_WinWaitCloseByHandle
  AU3_WinWaitNotActive
  AU3_WinWaitNotActiveByHandle
  AU3_error
```
The plain java library generator is failing on a vanilla system:

```cmd
java -cp target\jnautoit-0.0.4-SNAPSHOT.jar;target\lib\* example.AutoItXLibraryGenerator
Exception in thread "main" java.lang.UnsatisfiedLinkError:
Unable to load library 'AutoItX3.dll':
Can't obtain InputStream for win32-x86/AutoItX3.dll -
```
To solve this one may need a checked version of the dll or place some missing dependency 
into the System32 folder (all imports listed by link.exe are already in `c:\Windows\System32`:
```cmd

"c:\Program Files (x86)\Microsoft Visual Studio 11.0\VC\bin\amd64\link.exe" /dump /imports AutoItX3_x64.dll |  findstr /i \.dll
Dump of file AutoItX3_x64.dll
  MPR.dll
  WINMM.dll
  COMCTL32.dll
  USERENV.dll
  KERNEL32.dll
  USER32.dll
  GDI32.dll
  ADVAPI32.dll
  SHELL32.dll
  ole32.dll
  OLEAUT32.dll
  RPCRT4.dll
```
### See Also

  * Older Autoit [home page](https://www.autoitscript.com/site/)
  * [Java Native Access](https://github.com/java-native-access/jna) project on github
  * [Index of /autoit3/docs/functions](https://www.autoitscript.com/autoit3/docs/functions/)
  * Powershell AutoIt cmdlets [documentation](https://www.autoitconsulting.com/site/scripting/autoit-cmdlets-for-windows-powershell/)
  * Russian Autoit tester community [documentation](http://forum.ru-board.com/topic.cgi?forum=5&limit=1&m=9&start=0&topic=33902)(in Russian)
  * [Autoit forum](https://www.autoitscript.com/forum/forum/35-windows-client/)
  * [Autoit forum](http://autoit-script.ru/index.php)(in Russian)
  * Misc. autoit-related topics in [QA forum](https//automated-testing.info/search?q=autoit)(in Russian)
  * Yet another [Autoit forum](http://www.cyberforum.ru/autoit/) (in Russian)

### License
This project is licensed under the terms of the MIT license.

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
