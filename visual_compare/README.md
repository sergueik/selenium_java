### Info

This directory contains a derivative of project [swtestacademy/VisualAutomationImageMagickSelenium](https://github.com/swtestacademy/VisualAutomationImageMagickSelenium).

Note: when using it on Window, install static or dynamically linked [ImageMagick-7.0.6-3-Q16-x64-static.exe](https://www.imagemagick.org/script/download.php)
and copy `magic.exe` to `convert.exe` and `compare.exe`. 

Note: do not copy the ImageMagick to other directory: it uses Windows Registry to find its own location.
```java
org.im4java.core.CommandException: org.im4java.core.CommandException:
convert.exe: RegistryKeyLookupFailed `CoderModulesPath' @error/module.c/GetMagickModulePath/657.
...
```

The program writes the comparison script to the file
```cmd
@echo off
REM -------------------------------------------------------
REM  Cmd-script autogenerated by im4java
REM  at Fri Jul 28 22:03:47 EDT 2017
REM -------------------------------------------------------

set PATH=C:\Program Files\ImageMagick-7.0.6-Q16;C:\Program Files\ImageMagick-7.0.6-Q16;%PATH%

compare ^
   -fuzz "5.0" ^
  -metric "AE" "ScreenShots\kariyerUzmanCssTest\kariyerUzmanCssTest_Baseline.png" "ScreenShots\kariyerUzmanCssTest\kariyerUzmanCssTest_Actual.png" "ScreenShots\kariyerUzmanCssTest\kariyerUzmanCssTest_Diff.png"

```
### See Also

* http://www.programcreek.com/java-api-examples/index.php?api=org.im4java.core.ConvertCmd
* http://im4java.sourceforge.net/docs/dev-guide.html
* http://codoid.com/automation-testing-comparing-screenshots/ 