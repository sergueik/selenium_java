### Info
Eclipse [SWT](https://www.eclipse.org/swt/) hosted [JOGL](http://jogamp.org/jogl/www/) Skeleton App.

![Example](https://github.com/sergueik/selenium_java/blob/master/jodl_stw/screenshots/capture_jogapm.png)
https://focusmm.zendesk.com/hc/en-us/articles/203200801-Check-for-OpenGL-compatibility

https://www.intel.com/content/www/us/en/support/intel-driver-support-assistant.html
### Code Examples


The code examples are based on [jogl-demos](https://github.com/sgothel/jogl-demos) repository
https://github.com/jvm-graphics-labs/hello-triangle/tree/master/screenshots
### Prerequisite

#### Ubuntu
Basically follow the steps in [jogamp wiki](http://jogamp.org/wiki/index.php/Downloading_and_installing_JOG)



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

### Runtime Error Troubleshooting

List of dependencies produced by maven command which used a combo of [gradle-generated](https://stackoverflow.com/questions/17281927/how-to-make-gradle-generate-a-valid-pom-xml-file-at-the-root-of-a-project-for-ma)  `pom.xml` from [jvm-graphcs-labs/hello-triagle](https://github.com/jvm-graphics-labs/hello-triangle) project in and original `pom.xml` topreserve build phases, repositories etc.:


```sh
annotations-13.0.jar
commands-3.3.0-I20070605-0010.jar
common-3.6.200-v20130402-1505.jar
commons-configuration-1.10.jar
commons-exec-1.3.jar
commons-io-2.5.jar
commons-lang-2.6.jar
commons-lang3-3.3.1.jar
commons-logging-1.1.1.jar
gli-6938a4b6cffc5af7ef6344d2f86ccc0f827b1f3a.jar
glm-f63d840cde.jar
gln-1e35c8c7a5f0f1e61a77f0a2cb803111da5ef4df.jar
gluegen-rt-2.3.2.jar
gluegen-rt-2.3.2-natives-android-aarch64.jar
gluegen-rt-2.3.2-natives-android-armv6.jar
gluegen-rt-2.3.2-natives-linux-amd64.jar
gluegen-rt-2.3.2-natives-linux-armv6hf.jar
gluegen-rt-2.3.2-natives-linux-armv6.jar
gluegen-rt-2.3.2-natives-linux-i586.jar
gluegen-rt-2.3.2-natives-macosx-universal.jar
gluegen-rt-2.3.2-natives-solaris-amd64.jar
gluegen-rt-2.3.2-natives-solaris-i586.jar
gluegen-rt-2.3.2-natives-windows-amd64.jar
gluegen-rt-2.3.2-natives-windows-i586.jar
gluegen-rt-main-2.3.2.jar
hamcrest-all-1.3.jar
hamcrest-core-1.3.jar
jface-3.3.0-I20070606-0010.jar
jna-4.5.2.jar
jna-platform-4.5.2.jar
jogl-all-2.3.2.jar
jogl-all-2.3.2-natives-android-aarch64.jar
jogl-all-2.3.2-natives-android-armv6.jar
jogl-all-2.3.2-natives-linux-amd64.jar
jogl-all-2.3.2-natives-linux-armv6hf.jar
jogl-all-2.3.2-natives-linux-armv6.jar
jogl-all-2.3.2-natives-linux-i586.jar
jogl-all-2.3.2-natives-macosx-universal.jar
jogl-all-2.3.2-natives-solaris-amd64.jar
jogl-all-2.3.2-natives-solaris-i586.jar
jogl-all-2.3.2-natives-windows-amd64.jar
jogl-all-2.3.2-natives-windows-i586.jar
jogl-all-main-2.3.2.jar
junit-4.12.jar
kool-07fad04fe49a7963dfb14725b5f689a8796f685f.jar
kotlin-reflect-1.2.70.jar
kotlin-stdlib-1.2.70.jar
kotlin-stdlib-common-1.2.70.jar
kotlin-unsigned-75287b442e5c25ec79ec56a741e6dc4b6f3cd479.jar
log4j-1.2.17.jar
log4j-api-2.11.1.jar
log4j-core-2.11.1.jar
lwjgl-3.2.0.jar
lwjgl-3.2.0-natives-linux.jar
lwjgl-glfw-3.2.0.jar
lwjgl-glfw-3.2.0-natives-linux.jar
lwjgl-jemalloc-3.2.0.jar
lwjgl-jemalloc-3.2.0-natives-linux.jar
lwjgl-openal-3.2.0.jar
lwjgl-openal-3.2.0-natives-linux.jar
lwjgl-opengl-3.2.0.jar
lwjgl-opengl-3.2.0-natives-linux.jar
lwjgl-stb-3.2.0.jar
lwjgl-stb-3.2.0-natives-linux.jar
lwjgl-vulkan-3.2.0.jar
org.eclipse.swt.gtk.linux.x86_64-4.3.jar
streamex-0.6.5.jar
swt-3.5.2.jar
uno-sdk-v0.7.4.jar
```

The execution error observed in a physical machine with Intel HD 5500 video card Intel Corporation Broadwell-U Integrated Graphics (rev 09). Note: same error is reported under Windowx 8.1 and Ubuntu Xenial and Intel 50 video card on Windows 10 netbook:
```sh
Exception in thread "main-AWTAnimator#00" com.jogamp.opengl.util.AnimatorBase$UncaughtAnimatorException: com.jogamp.opengl.GLException: Caught GLException: No shader code found (source nor binary) for src: [shaders/gl3/hello-triangle.vert], bin: null on thread main-AWTAnimator#00
	at com.jogamp.opengl.util.AWTAnimatorImpl.display(AWTAnimatorImpl.java:92)
	at com.jogamp.opengl.util.AnimatorBase.display(AnimatorBase.java:452)
	at com.jogamp.opengl.util.Animator$MainLoop.run(Animator.java:204)
	at java.lang.Thread.run(Thread.java:748)
Caused by: com.jogamp.opengl.GLException: Caught GLException: No shader code found (source nor binary) for src: [shaders/gl3/hello-triangle.vert], bin: null on thread main-AWTAnimator#00
	at com.jogamp.opengl.GLException.newGLException(GLException.java:76)
	at jogamp.opengl.GLDrawableHelper.invokeGLImpl(GLDrawableHelper.java:1327)
	at jogamp.opengl.GLDrawableHelper.invokeGL(GLDrawableHelper.java:1147)
	at com.jogamp.newt.opengl.GLWindow.display(GLWindow.java:759)
	at com.jogamp.opengl.util.AWTAnimatorImpl.display(AWTAnimatorImpl.java:81)
	... 3 more
```
The execution error observed in a virual machine with Virtual Box video card:
```sh
Exception in thread "main-AWTAnimator#00" com.jogamp.opengl.util.AnimatorBase$UncaughtAnimatorException: com.jogamp.opengl.GLException: Caught GLException: Not a GL3 implementation on thread main-AWTAnimator#00
	at com.jogamp.opengl.util.AWTAnimatorImpl.display(AWTAnimatorImpl.java:92)
	at com.jogamp.opengl.util.AnimatorBase.display(AnimatorBase.java:452)
	at com.jogamp.opengl.util.Animator$MainLoop.run(Animator.java:204)
	at java.lang.Thread.run(Thread.java:748)
Caused by: com.jogamp.opengl.GLException: Caught GLException: Not a GL3 implementation on thread main-AWTAnimator#00
	at com.jogamp.opengl.GLException.newGLException(GLException.java:76)
	at jogamp.opengl.GLDrawableHelper.invokeGLImpl(GLDrawableHelper.java:1327)
	at jogamp.opengl.GLDrawableHelper.invokeGL(GLDrawableHelper.java:1147)
	at com.jogamp.newt.opengl.GLWindow.display(GLWindow.java:759)
	at com.jogamp.opengl.util.AWTAnimatorImpl.display(AWTAnimatorImpl.java:81)
	... 3 more
```

### See Also

 * [Gradle guide for Migrating from Maven to Gradle](https://guides.gradle.org/migrating-from-maven/)
 * ["Hello Triangle" OpenGL 4 Up and Running](http://antongerdelan.net/opengl/hellotriangle.html)
