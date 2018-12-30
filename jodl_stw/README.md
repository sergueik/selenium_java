### Info
Eclipse [SWT](https://www.eclipse.org/swt/) hosted [JOGL](http://jogamp.org/jogl/www/) Skeleton App.

![Example](https://github.com/sergueik/selenium_java/blob/master/jodl_stw/screenshots/capture_jogapm.png)

### Prerequisite

#### Ubuntu
Basically follow the steps in [j wiki](http://jogamp.org/wiki/index.php/Downloading_and_installing_JOG)

```sh
wget https://jogamp.org/deployment/jogamp-current/archive/jogamp-all-platforms.7z
sudo apt-get -qqy install p7zip
p7zip -d jogamp-all-platforms.7z
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
unfortuately with Unbuntu i386, this is where it stops with an NPE:
```java
Exception in thread "main" java.lang.NullPointerException
	at jogamp.opengl.egl.EGLGraphicsConfigurationFactory.getAvailableCapabilities(EGLGraphicsConfigurationFactory.java:191)
	at jogamp.opengl.egl.EGLDrawableFactory.getAvailableCapabilitiesImpl(EGLDrawableFactory.java:1015)
```
on Ubuntu amd64, the following will work:

Install the jogamp-all-platforms.7z into the project directory, 

run the vendor smoke tests
```sh
./etc/test.sh | tee /tmp/vendor-health-check.log
```
 -  will be no noticeable UI activity, can review the output.
In the project link the native library direcory from the exploded archive ocation to the loader default path:
```sh
./etc/test.sh | tee /tmp/vendor-health-check.log
ln -fs `pwd`/jogamp-all-platforms/lib/linux-amd64/ natives/linux-amd64
ls -l natives/linux-amd64/libgluegen-rt.so
```
Compile, package, install and run
```sh
mvn install
java -cp target/canvasex-0.0.1-SNAPSHOT.jar:target/lib/* example.CanvasEx
```
![Example](https://github.com/sergueik/selenium_java/blob/master/jodl_stw/screenshots/capture_jogapm_ubuntu-trustry-oracle-jdk8-amd64.png)

This has been verified to work with __Oracle HotSpot(TM)__ 64-Bit Server VM JRE build __1.8.0.191__ and __GTK 2__ build __2.4.23__.
and the very basic `GLEventListener`.

### Windows 8.1 amd64

Repeat the vendor smoke test, notice a quickly flashing window launched and confirm the batch file does not crash.
Copy the native dlls from the archive ocation to the loader default location in In the project directory:
```cmd
mkdir natives\windows-amd64				
copy jogamp-all-platforms\lib\windows-amd64\gluegen-rt.dll natives\windows-amd64
```

Without the last step, would experience the following exception at runtime:
```cmd		
Exception in thread "main" java.lang.UnsatisfiedLinkError: Can't load library:
C:\developer\sergueik\selenium_java\jodl_stw\natives\windows-amd64\\gluegen-rt.dll
  at java.lang.ClassLoader.loadLibrary(ClassLoader.java:1827)
  at java.lang.Runtime.load0(Runtime.java:809)
  at java.lang.System.load(System.java:1086)
```

Build and run standalone class from the maven project (assuming Maven and JDK are in the `PATH`,
currently only tested with Java 8):
```cmd
mvn install
```

For the author's basic [standalone example app](https://github.com/sgothel/jogl-demos/blob/master/maven/jp4da/jp4da-desktop/src/main/java/com/io7m/examples/jp4da/DesktopExample.java)
run
```cmd
java -cp target\canvasex-0.0.1-SNAPSHOT.jar;target\lib\* example.OwnWindowEx
```
For an SWT hosted app
```
java -cp target\canvasex-0.0.1-SNAPSHOT.jar;target\lib\* example.CanvasEx
```
### Notes

Do not construct the `GLCanvas` from the `Canvas` to avoid problem with rendering: GL does not seem to drawing there.
Put it on `Shell` (root of the window) or in a `Composite` widget container as covered in the javadoc of underlying classes
* [GLCanvas](https://jogamp.org/deployment/jogamp-current/javadoc/jogl/javadoc/com/jogamp/opengl/swt/GLCanvas.html)
* [GLAutoDrawable](https://jogamp.org/deployment/jogamp-current/javadoc/jogl/javadoc/com/jogamp/opengl/GLAutoDrawable.html)

which is likely the superclasses of everything else of interest in `com.jogamp.opengl`.

