### Info
Skeleton eclipse SWT hosted JOGL App

![Example](https://github.com/sergueik/selenium_java/blob/master/jodl_stw/screenshots/capture_jogapm.png)
 
### Prerequisite

#### Ubuntu i386
Basically follow the steps in [j wiki](http://jogamp.org/wiki/index.php/Downloading_and_installing_JOG)

```sh
wget https://jogamp.org/deployment/jogamp-current/archive/jogamp-all-platforms.7z
sudo apt-get  install p7zip
p7zip -d  jogamp-all-platforms.7z
```
check if java is available
```sh
update-alternatives --list java
/usr/lib/jvm/java-9-openjdk-i386/bin/java
```
make sure `test.sh` is executable and the libraries are in the loader path.
```sh
pushd etc ;  chmod +x ./test.sh ; popd
export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:`pwd`/lib/linux-i586
```
run the vendor tests
```sh
./etc/test.sh
```
unfortuately for Unbuntu x86, this is where it stops with an NPE:
```java
Exception in thread "main" java.lang.NullPointerException
	at jogamp.opengl.egl.EGLGraphicsConfigurationFactory.getAvailableCapabilities(EGLGraphicsConfigurationFactory.java:191)
	at jogamp.opengl.egl.EGLDrawableFactory.getAvailableCapabilitiesImpl(EGLDrawableFactory.java:1015)
```

### Windows 8.1 amd64

Repeat the venros smoke test, notice it did quickly launch a visible window and does not crash.
In the project copy the native dlls from the archive ocation to the loader default location:
```cmd
mkdir natives\windows-amd64				
copy jogamp-all-platforms\lib\windows-amd64\gluegen-rt.dll natives\windows-amd64
```

if this is not done, expect the runtime exception:
```cmd		
	Exception in thread "main" java.lang.UnsatisfiedLinkError: Can't load library:
	C:\developer\sergueik\selenium_java\jodl_stw\natives\windows-amd64\\gluegen-rt.dll
	    at java.lang.ClassLoader.loadLibrary(ClassLoader.java:1827)
	    at java.lang.Runtime.load0(Runtime.java:809)
	    at java.lang.System.load(System.java:1086)
```

### Build and run standalone class from the maven project:
```cmd
mvn install
```

For the author's baseline [standalone app](https://github.com/sgothel/jogl-demos/blob/master/maven/jp4da/jp4da-desktop/src/main/java/com/io7m/examples/jp4da/DesktopExample.java)
```cmd
java -cp target\canvasex-0.0.1-SNAPSHOT.jar;target\lib\* example.OwnWindowEx
```
For an SWT hosted app
```
java -cp target\canvasex-0.0.1-SNAPSHOT.jar;target\lib\* example.CanvasEx
```
NOTE:  do not put the on the canvas, it does not seem to draw there. put it on R

### See Also

  * https://jogamp.org/deployment/jogamp-current/javadoc/jogl/javadoc/com/jogamp/opengl/util/FPSAnimator.html
  * https://jogamp.org/deployment/jogamp-current/javadoc/jogl/javadoc/com/jogamp/opengl/swt/GLCanvas.html
  * https://github.com/sgothel/jogl-demos/blob/master/maven/jp4da/pom.xml

