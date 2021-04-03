### Info


Replica of [JavaFX on JDK 11 with Maven](https://github.com/mhrimaz/javafx11-demo) project

### Usage

* download [zulu JDK 11](https://www.azul.com/downloads/zulu-community/?package=jdk)
* modify `JAVA_HOME` and `PATH` to load JDK 11 first.
```cmd
pushd c:\java\zulu11.45.27-ca-jdk11.0.10-win_x64
set JAVA_HOME=%CD%
path=%JAVA_HOME%\bin;%PATH%
popd
```
* build project
```sh
mvn package
```
* run
```cmd
run.cmd
```
### Note

Cannot run on 32 bit Windows wth error:
```sh
Loading library prism_d3d from resource failed: java.lang.UnsatisfiedLinkError:
C:\Users\sergueik\.openjfx\cache\11-ea\prism_d3d.dll: Can't load AMD 64-bit .dll on a IA 32-bit platform
java.lang.UnsatisfiedLinkError: C:\Users\sergueik\.openjfx\cache\11-ea\prism_d3d.dll: Can't load AMD 64-bit .dll on a IA 32-bit platform at java.base/java.lang.ClassLoader$NativeLibrary.load0(Native Method)
```
- have to switch to a [Liberica Full JDK 11 x85](https://bell-sw.com/pages/downloads/) release (https://download.bell-sw.com/java/11.0.10+9/bellsoft-jdk11.0.10+9-windows-i586-full.msi) or some later release from [Gluon](https://gluonhq.com/products/javafx/)

### See Also

  * https://gluonhq.com/services/javafx-support/
  * https://github.com/AlmasB/JavaFX11-example
  * https://www.infoworld.com/article/3305073/removed-from-jdk-11-javafx-11-arrives-as-a-standalone-module.html
  * https://blog.idrsolutions.com/2019/05/using-javafx-with-java-11/
