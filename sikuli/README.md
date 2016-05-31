Info
----

Project showing basic [Sikuli](http://www.sikuli.org/) WebDriver intergation.
 
Usage
-----
If Sikuli is already installed, update `pom.xml`. Otherwise download `sikuli-script.jar` from elsewhere e.g. from [mubbashir/Sikuli-on-Selenium](https://github.com/mubbashir/Sikuli-on-Selenium/tree/master/lib) and put into `lib` directory.

On first run Sikuli will populate libs subdirectory with the following files:
```
jawt.dll
JIntellitype.dll
libgcc_s_sjlj_64-1.dll
libjpeg-9.dll
liblept-2.dll
libopencv_core244.dll
libopencv_highgui244.dll
libopencv_imgproc244.dll
libpng15-15.dll
libstdc++_64-6.dll
libtesseract-3.dll
MadeForSikuliX64W.txt
VisionProxy.dll
WinUtil.dll
zlib1.dll
```
This directory needs to be added to the user `PATH` environment. 

Author
------
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)