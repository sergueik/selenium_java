### Info


Replica of [JavaFX on JDK 11 with Maven](https://github.com/mhrimaz/javafx11-demo) project

### Usage

* download zulu JDK 11
* modify `JAVA_HOME` and `PATH` to load JDK11 first.
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
java.lang.UnsatisfiedLinkError: C:\Users\sergueik\.openjfx\cache\11-ea\prism_d3d.dll: Can't load AMD 64-bit .dll on a IA 32-bit platform
        at java.base/java.lang.ClassLoader$NativeLibrary.load0(Native Method)
```
