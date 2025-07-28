### Info

This directory contains the replica of
[markdown-javafx-renderer](https://github.com/JPro-one/markdown-javafx-renderer) `MarkdownView` class which uses
[vsch/flexmark-java](https://github.com/vsch/flexmark-java) Java implementation of markdown [spec](https://spec.commonmark.org/0.28/) and [real time CSS reloading](https://github.com/mcfoggy/cssfx)
converted temporarily to maven and Java 1.8. Also switched to classic `pom.xml` rom using [JPro custom  build process](https://github.com/JPro-one/JPro-from-Jars)

### Usage

```cmd
mvn clean package
java -jar target\example.javafx_markdown.jar
```
or if you need to explicitly load assemblies
```cmd
java -cp target\example.javafx_markdown.jar;target\lib\* example.Example
```
and
```sh
mvn clean package
java -jar target/example.javafx_markdown.jar
```
or if you need to explicitly load assemblies
```sh
java -cp target/example.javafx_markdown.jar:target/lib/* example.Example
```

NOTE: on Windows, uses `;` as class path argument separator. For unix do `:`


### TODO

* The project does not run with Azul JDK 11. 

To reproduce, call:
```cmd
call test-jdk11.cmd
```

Will get error (after significant delay) :

```text

Exception in Application start method
Exception in thread "main" java.lang.reflect.InvocationTargetException
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
        at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.base/java.lang.reflect.Method.invoke(Method.java:566)
        at java.base/sun.launcher.LauncherHelper$FXHelper.main(LauncherHelper.java:1093)
Caused by: java.lang.RuntimeException: Exception in Application start method
        at javafx.graphics@19/com.sun.javafx.application.LauncherImpl.launchApplication1(LauncherImpl.java:901)
        at javafx.graphics@19/com.sun.javafx.application.LauncherImpl.lambda$launchApplication$2(LauncherImpl.java:196)
        at java.base/java.lang.Thread.run(Thread.java:829)
Caused by: java.lang.NoClassDefFoundError: org/fxmisc/cssfx/CSSFX
        at example.Example.start(Example.java:28)
        at javafx.graphics@19/com.sun.javafx.application.LauncherImpl.lambda$launchApplication1$9(LauncherImpl.java:847)
        at javafx.graphics@19/com.sun.javafx.application.PlatformImpl.lambda$runAndWait$12(PlatformImpl.java:484)
        at javafx.graphics@19/com.sun.javafx.application.PlatformImpl.lambda$runLater$10(PlatformImpl.java:457)
        at java.base/java.security.AccessController.doPrivileged(Native Method)
        at javafx.graphics@19/com.sun.javafx.application.PlatformImpl.lambda$runLater$11(PlatformImpl.java:456)
        at javafx.graphics@19/com.sun.glass.ui.InvokeLaterDispatcher$Future.run(InvokeLaterDispatcher.java:96)
        at javafx.graphics@19/com.sun.glass.ui.win.WinApplication._runLoop(Native Method)
        at javafx.graphics@19/com.sun.glass.ui.win.WinApplication.lambda$runLoop$3(WinApplication.java:184)
        ... 1 more
Caused by: java.lang.ClassNotFoundException: org.fxmisc.cssfx.CSSFX
`org.fxmisc.cssfx` to `--add-modules` argument

leads to build error:
```java.lang.module.FindException: Module org.fxmisc.cssfx not found
```
        at java.base/jdk.internal.loader.BuiltinClassLoader.loadClass(BuiltinClassLoader.java:581)
        at java.base/jdk.internal.loader.ClassLoaders$AppClassLoader.loadClass(ClassLoaders.java:178)
        at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:527)
        ... 10 more

```
adding `org.fxmisc.cssfx` to `--add-modules` argument

leads to build error:

```text
java.lang.module.FindException: Module org.fxmisc.cssfx not found
```
### Testing

#### Linux with JDK 8



```sh
java -version
```
```text
openjdk version "11.0.27" 2025-04-15
OpenJDK Runtime Environment (build 11.0.27+6-post-Ubuntu-0ubuntu122.04)
OpenJDK 64-Bit Server VM (build 11.0.27+6-post-Ubuntu-0ubuntu122.04, mixed mode, sharing)
```
```sh
sudo  apt-get install openjdk-8-jdk
```
```text
  The following additional packages will be installed:
  fonts-dejavu-extra libatk-wrapper-java libatk-wrapper-java-jni libgif7
  libice-dev libpthread-stubs0-dev libsm-dev libx11-dev libxau-dev libxcb1-dev
  libxdmcp-dev libxt-dev openjdk-8-jdk-headless openjdk-8-jre
  openjdk-8-jre-headless x11proto-dev xorg-sgml-doctools xtrans-dev
Suggested packages:
  libice-doc libsm-doc libx11-doc libxcb-doc libxt-doc openjdk-8-demo
  openjdk-8-source visualvm fonts-nanum fonts-ipafont-gothic
  fonts-ipafont-mincho fonts-wqy-microhei fonts-wqy-zenhei fonts-indic
The following NEW packages will be installed:
  fonts-dejavu-extra libatk-wrapper-java libatk-wrapper-java-jni libgif7
  libice-dev libpthread-stubs0-dev libsm-dev libx11-dev libxau-dev libxcb1-dev
  libxdmcp-dev libxt-dev openjdk-8-jdk openjdk-8-jdk-headless openjdk-8-jre
  openjdk-8-jre-headless x11proto-dev xorg-sgml-doctools xtrans-dev
0 upgraded, 19 newly installed, 0 to remove and 52 not upgraded.
Need to get 48.0 MB of archives.
After this operation, 163 MB of additional disk space will be used.
Do you want to continue? [Y/n]
```
```sh
Y
```
```text

Get:1 http://us.archive.ubuntu.com/ubuntu jammy/main amd64 fonts-dejavu-extra all 2.37-2build1 [2,041 kB]
Get:2 http://us.archive.ubuntu.com/ubuntu jammy/main amd64 libatk-wrapper-java all 0.38.0-5build1 [53.1 kB]
Get:3 http://us.archive.ubuntu.com/ubuntu jammy/main amd64 libatk-wrapper-java-jni amd64 0.38.0-5build1 [49.0 kB]
Get:4 http://us.archive.ubuntu.com/ubuntu jammy-updates/main amd64 libgif7 amd64 5.1.9-2ubuntu0.1 [33.9 kB]
Get:5 http://us.archive.ubuntu.com/ubuntu jammy/main amd64 xorg-sgml-doctools all 1:1.11-1.1 [10.9 kB]
Get:6 http://us.archive.ubuntu.com/ubuntu jammy/main amd64 x11proto-dev all 2021.5-1 [604 kB]
Get:7 http://us.archive.ubuntu.com/ubuntu jammy/main amd64 libice-dev amd64 2:1.0.10-1build2 [51.4 kB]
Get:8 http://us.archive.ubuntu.com/ubuntu jammy/main amd64 libpthread-stubs0-dev amd64 0.4-1build2 [5,516 B]
Get:9 http://us.archive.ubuntu.com/ubuntu jammy/main amd64 libsm-dev amd64 2:1.2.3-1build2 [18.1 kB]
Get:10 http://us.archive.ubuntu.com/ubuntu jammy/main amd64 libxau-dev amd64 1:1.0.9-1build5 [9,724 B]
Get:11 http://us.archive.ubuntu.com/ubuntu jammy/main amd64 libxdmcp-dev amd64 1:1.1.3-0ubuntu5 [26.5 kB]
Get:12 http://us.archive.ubuntu.com/ubuntu jammy/main amd64 xtrans-dev all 1.4.0-1 [68.9 kB]
Get:13 http://us.archive.ubuntu.com/ubuntu jammy/main amd64 libxcb1-dev amd64 1.14-3ubuntu3 [86.5 kB]
Get:14 http://us.archive.ubuntu.com/ubuntu jammy-updates/main amd64 libx11-dev amd64 2:1.7.5-1ubuntu0.3 [744 kB]
Get:15 http://us.archive.ubuntu.com/ubuntu jammy/main amd64 libxt-dev amd64 1:1.2.1-1 [396 kB]
Get:16 http://us.archive.ubuntu.com/ubuntu jammy-updates/universe amd64 openjdk-8-jre-headless amd64 8u452-ga~us1-0ubuntu1~22.04 [30.8 MB]
Get:17 http://us.archive.ubuntu.com/ubuntu jammy-updates/universe amd64 openjdk-8-jre amd64 8u452-ga~us1-0ubuntu1~22.04 [75.3 kB]
Get:18 http://us.archive.ubuntu.com/ubuntu jammy-updates/universe amd64 openjdk-8-jdk-headless amd64 8u452-ga~us1-0ubuntu1~22.04 [8,878 kB]
Get:19 http://us.archive.ubuntu.com/ubuntu jammy-updates/universe amd64 openjdk-8-jdk amd64 8u452-ga~us1-0ubuntu1~22.04 [4,039 kB]
Fetched 48.0 MB in 6s (8,625 kB/s)
Selecting previously unselected package fonts-dejavu-extra.
(Reading database ... 244117 files and directories currently installed.)
Preparing to unpack .../00-fonts-dejavu-extra_2.37-2build1_all.deb ...
Unpacking fonts-dejavu-extra (2.37-2build1) ...
Selecting previously unselected package libatk-wrapper-java.
Preparing to unpack .../01-libatk-wrapper-java_0.38.0-5build1_all.deb ...
Unpacking libatk-wrapper-java (0.38.0-5build1) ...
Selecting previously unselected package libatk-wrapper-java-jni:amd64.
Preparing to unpack .../02-libatk-wrapper-java-jni_0.38.0-5build1_amd64.deb ...
Unpacking libatk-wrapper-java-jni:amd64 (0.38.0-5build1) ...
Selecting previously unselected package libgif7:amd64.
Preparing to unpack .../03-libgif7_5.1.9-2ubuntu0.1_amd64.deb ...
Unpacking libgif7:amd64 (5.1.9-2ubuntu0.1) ...
Selecting previously unselected package xorg-sgml-doctools.
Preparing to unpack .../04-xorg-sgml-doctools_1%3a1.11-1.1_all.deb ...
Unpacking xorg-sgml-doctools (1:1.11-1.1) ...
Selecting previously unselected package x11proto-dev.
Preparing to unpack .../05-x11proto-dev_2021.5-1_all.deb ...
Unpacking x11proto-dev (2021.5-1) ...
Selecting previously unselected package libice-dev:amd64.
Preparing to unpack .../06-libice-dev_2%3a1.0.10-1build2_amd64.deb ...
Unpacking libice-dev:amd64 (2:1.0.10-1build2) ...
Selecting previously unselected package libpthread-stubs0-dev:amd64.
Preparing to unpack .../07-libpthread-stubs0-dev_0.4-1build2_amd64.deb ...
Unpacking libpthread-stubs0-dev:amd64 (0.4-1build2) ...
Selecting previously unselected package libsm-dev:amd64.
Preparing to unpack .../08-libsm-dev_2%3a1.2.3-1build2_amd64.deb ...
Unpacking libsm-dev:amd64 (2:1.2.3-1build2) ...
Selecting previously unselected package libxau-dev:amd64.
Preparing to unpack .../09-libxau-dev_1%3a1.0.9-1build5_amd64.deb ...
Unpacking libxau-dev:amd64 (1:1.0.9-1build5) ...
Selecting previously unselected package libxdmcp-dev:amd64.
Preparing to unpack .../10-libxdmcp-dev_1%3a1.1.3-0ubuntu5_amd64.deb ...
Unpacking libxdmcp-dev:amd64 (1:1.1.3-0ubuntu5) ...
Selecting previously unselected package xtrans-dev.
Preparing to unpack .../11-xtrans-dev_1.4.0-1_all.deb ...
Unpacking xtrans-dev (1.4.0-1) ...
Selecting previously unselected package libxcb1-dev:amd64.
Preparing to unpack .../12-libxcb1-dev_1.14-3ubuntu3_amd64.deb ...
Unpacking libxcb1-dev:amd64 (1.14-3ubuntu3) ...
Selecting previously unselected package libx11-dev:amd64.
Preparing to unpack .../13-libx11-dev_2%3a1.7.5-1ubuntu0.3_amd64.deb ...
Unpacking libx11-dev:amd64 (2:1.7.5-1ubuntu0.3) ...
Selecting previously unselected package libxt-dev:amd64.
Preparing to unpack .../14-libxt-dev_1%3a1.2.1-1_amd64.deb ...
Unpacking libxt-dev:amd64 (1:1.2.1-1) ...
Selecting previously unselected package openjdk-8-jre-headless:amd64.
Preparing to unpack .../15-openjdk-8-jre-headless_8u452-ga~us1-0ubuntu1~22.04_amd64.deb ...
Unpacking openjdk-8-jre-headless:amd64 (8u452-ga~us1-0ubuntu1~22.04) ...
Selecting previously unselected package openjdk-8-jre:amd64.
Preparing to unpack .../16-openjdk-8-jre_8u452-ga~us1-0ubuntu1~22.04_amd64.deb ...
Unpacking openjdk-8-jre:amd64 (8u452-ga~us1-0ubuntu1~22.04) ...
Selecting previously unselected package openjdk-8-jdk-headless:amd64.
Preparing to unpack .../17-openjdk-8-jdk-headless_8u452-ga~us1-0ubuntu1~22.04_amd64.deb ...
Unpacking openjdk-8-jdk-headless:amd64 (8u452-ga~us1-0ubuntu1~22.04) ...
Selecting previously unselected package openjdk-8-jdk:amd64.
Preparing to unpack .../18-openjdk-8-jdk_8u452-ga~us1-0ubuntu1~22.04_amd64.deb ...
Unpacking openjdk-8-jdk:amd64 (8u452-ga~us1-0ubuntu1~22.04) ...
Setting up libpthread-stubs0-dev:amd64 (0.4-1build2) ...
Setting up xtrans-dev (1.4.0-1) ...
Setting up openjdk-8-jre-headless:amd64 (8u452-ga~us1-0ubuntu1~22.04) ...
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/jre/bin/orbd to provide /usr/bin/orbd (orbd) in auto mode
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/jre/bin/servertool to provide /usr/bin/servertool (servertool) in auto mode
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/jre/bin/tnameserv to provide /usr/bin/tnameserv (tnameserv) in auto mode
Setting up libgif7:amd64 (5.1.9-2ubuntu0.1) ...
Setting up fonts-dejavu-extra (2.37-2build1) ...
Setting up xorg-sgml-doctools (1:1.11-1.1) ...
Setting up libatk-wrapper-java (0.38.0-5build1) ...
Setting up libatk-wrapper-java-jni:amd64 (0.38.0-5build1) ...
Setting up openjdk-8-jdk-headless:amd64 (8u452-ga~us1-0ubuntu1~22.04) ...
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/bin/clhsdb to provide /usr/bin/clhsdb (clhsdb) in auto mode
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/bin/extcheck to provide /usr/bin/extcheck (extcheck) in auto mode
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/bin/hsdb to provide /usr/bin/hsdb (hsdb) in auto mode
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/bin/idlj to provide /usr/bin/idlj (idlj) in auto mode
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/bin/jar to provide /usr/bin/jar (jar) in auto mode
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/bin/jarsigner to provide /usr/bin/jarsigner (jarsigner) in auto mode
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/bin/javac to provide /usr/bin/javac (javac) in auto mode
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/bin/javadoc to provide /usr/bin/javadoc (javadoc) in auto mode
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/bin/javah to provide /usr/bin/javah (javah) in auto mode
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/bin/javap to provide /usr/bin/javap (javap) in auto mode
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/bin/jcmd to provide /usr/bin/jcmd (jcmd) in auto mode
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/bin/jdb to provide /usr/bin/jdb (jdb) in auto mode
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/bin/jdeps to provide /usr/bin/jdeps (jdeps) in auto mode
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/bin/jfr to provide /usr/bin/jfr (jfr) in auto mode
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/bin/jhat to provide /usr/bin/jhat (jhat) in auto mode
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/bin/jinfo to provide /usr/bin/jinfo (jinfo) in auto mode
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/bin/jmap to provide /usr/bin/jmap (jmap) in auto mode
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/bin/jps to provide /usr/bin/jps (jps) in auto mode
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/bin/jrunscript to provide /usr/bin/jrunscript (jrunscript) in auto mode
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/bin/jsadebugd to provide /usr/bin/jsadebugd (jsadebugd) in auto mode
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/bin/jstack to provide /usr/bin/jstack (jstack) in auto mode
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/bin/jstat to provide /usr/bin/jstat (jstat) in auto mode
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/bin/jstatd to provide /usr/bin/jstatd (jstatd) in auto mode
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/bin/native2ascii to provide /usr/bin/native2ascii (native2ascii) in auto mode
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/bin/rmic to provide /usr/bin/rmic (rmic) in auto mode
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/bin/schemagen to provide /usr/bin/schemagen (schemagen) in auto mode
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/bin/serialver to provide /usr/bin/serialver (serialver) in auto mode
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/bin/wsgen to provide /usr/bin/wsgen (wsgen) in auto mode
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/bin/wsimport to provide /usr/bin/wsimport (wsimport) in auto mode
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/bin/xjc to provide /usr/bin/xjc (xjc) in auto mode
Setting up openjdk-8-jre:amd64 (8u452-ga~us1-0ubuntu1~22.04) ...
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/jre/bin/policytool to provide /usr/bin/policytool (policytool) in auto mode
Setting up openjdk-8-jdk:amd64 (8u452-ga~us1-0ubuntu1~22.04) ...
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/bin/appletviewer to provide /usr/bin/appletviewer (appletviewer) in auto mode
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/bin/jconsole to provide /usr/bin/jconsole (jconsole) in auto mode
Processing triggers for hicolor-icon-theme (0.17-2) ...
Processing triggers for gnome-menus (3.36.0-1ubuntu3) ...
Processing triggers for libc-bin (2.35-0ubuntu3.10) ...
Processing triggers for man-db (2.10.2-1) ...
Processing triggers for sgml-base (1.30) ...
Processing triggers for mailcap (3.70+nmu1ubuntu1) ...
Setting up x11proto-dev (2021.5-1) ...
Processing triggers for fontconfig (2.13.1-4.2ubuntu5) ...
Processing triggers for desktop-file-utils (0.26-1ubuntu3) ...
Setting up libxau-dev:amd64 (1:1.0.9-1build5) ...
Setting up libice-dev:amd64 (2:1.0.10-1build2) ...
Setting up libsm-dev:amd64 (2:1.2.3-1build2) ...
Setting up libxdmcp-dev:amd64 (1:1.1.3-0ubuntu5) ...
Setting up libxcb1-dev:amd64 (1.14-3ubuntu3) ...
Setting up libx11-dev:amd64 (2:1.7.5-1ubuntu0.3) ...
Setting up libxt-dev:amd64 (1:1.2.1-1) ...
sergueik@sergueik47:~$ java -version
openjdk version "11.0.27" 2025-04-15
OpenJDK Runtime Environment (build 11.0.27+6-post-Ubuntu-0ubuntu122.04)
OpenJDK 64-Bit Server VM (build 11.0.27+6-post-Ubuntu-0ubuntu122.04, mixed mode, sharing)
```
```sh
dpkg -l | grep openj
```
```text
ii  libopenjp2-7:amd64                     2.4.0-6ubuntu0.3                        amd64        JPEG 2000 image compression/decompression library
ii  openjdk-11-jre-headless:amd64          11.0.27+6~us1-0ubuntu1~22.04            amd64        OpenJDK Java runtime, using Hotspot JIT (headless)
ii  openjdk-8-jdk:amd64                    8u452-ga~us1-0ubuntu1~22.04             amd64        OpenJDK Development Kit (JDK)
ii  openjdk-8-jdk-headless:amd64           8u452-ga~us1-0ubuntu1~22.04             amd64        OpenJDK Development Kit (JDK) (headless)
ii  openjdk-8-jre:amd64                    8u452-ga~us1-0ubuntu1~22.04             amd64        OpenJDK Java runtime, using Hotspot JIT
ii  openjdk-8-jre-headless:amd64           8u452-ga~us1-0ubuntu1~22.04             amd64        OpenJDK Java runtime, using Hotspot JIT (headless)
```

```sh
sudo  apt-get remove openjdk-11-jre
```
```text
Reading package lists... Done
Building dependency tree... Done
Reading state information... Done
Package 'openjdk-11-jre' is not installed, so not removed
The following packages were automatically installed and are no longer required:
  libpython2-stdlib libpython2.7-minimal libpython2.7-stdlib python2-minimal
  python2.7 python2.7-minimal
Use 'sudo apt autoremove' to remove them.
0 upgraded, 0 newly installed, 0 to remove and 52 not upgraded.
sergueik@sergueik47:~$ sudo  apt-get remove openjdk-11-jre-headless
Reading package lists... Done
Building dependency tree... Done
Reading state information... Done
The following packages were automatically installed and are no longer required:
  libpython2-stdlib libpython2.7-minimal libpython2.7-stdlib python2-minimal
  python2.7 python2.7-minimal
Use 'sudo apt autoremove' to remove them.
The following packages will be REMOVED:
  default-jre-headless openjdk-11-jre-headless
0 upgraded, 0 newly installed, 2 to remove and 52 not upgraded.
After this operation, 176 MB disk space will be freed.
Do you want to continue? [Y/n]
```
```text
(Reading database ... 245165 files and directories currently installed.)
Removing default-jre-headless (2:1.11-72build2) ...
Removing openjdk-11-jre-headless:amd64 (11.0.27+6~us1-0ubuntu1~22.04) ...
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/jre/bin/java to provide /usr/bin/java (java) in auto mode
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/jre/bin/jjs to provide /usr/bin/jjs (jjs) in auto mode
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/jre/bin/keytool to provide /usr/bin/keytool (keytool) in auto mode
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/jre/bin/rmid to provide /usr/bin/rmid (rmid) in auto mode
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/jre/bin/rmiregistry to provide /usr/bin/rmiregistry (rmiregistry) in auto mode
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/jre/bin/pack200 to provide /usr/bin/pack200 (pack200) in auto mode
update-alternatives: using /usr/lib/jvm/java-8-openjdk-amd64/jre/bin/unpack200 to provide /usr/bin/unpack200 (unpack200) in auto mode
```

### .Net Framework Alternatives

<ol>
<li> 
Use a Markdown-to-HTML converter like [Markdig](https://github.com/xoofx/markdig)  that can  be added as nuget dependency 
[Markdig](https://www.nuget.org/packages/Markdig) 
(modern, pure .NET) or [CommonMark.NET](https://www.nuget.org/packages/CommonMark.NET) to perform Markdown to HTML conversion then render in
 WebBrowser control embedded in an Windows Forms or in WPF through `System.Windows.Forms.WebBrowser`:

```c#
using Markdig;

string markdown = File.ReadAllText("README.md");
string html = Markdown.ToHtml(markdown);

string fullHtml = $"<html><head><meta charset='utf-8'></head><body>{html}</body></html>";

webBrowser1.DocumentText = fullHtml;

```
this would  work even on .NET 4.0+ (Windows has .Net Framework 4.8  ). 
The WebBrowser uses IE rendering mode, which one can tweak via registry 
(FEAT URE_BROWSER_EMULATION) to look like specific version of the browswer, up to __IE11__.

</li>
<li> Use Markdig with WPF + WebView2 (optional)
 
Add  [WebView2](https://www.nuget.org/packages/Microsoft.Web.WebView2/1.0.3296.44#supportedframeworks-body-tab)
dependency, this will work  with .NET Framework __4.6.2__ and later and offer slightly 
more *modern* Chromium-based rendering engine, but the latter is ultra heavy:

```c#

string html = Markdown.ToHtml(markdownText);
await webView2Control.EnsureCoreWebView2Async();
webView2Control.NavigateToString(html);

```
</li>
<li>Use `RichTextBox` or `FlowDocument` (NOTE: lower fidelity)
There’s no built-in markdown parser, but if you only need bold/italic/lists and want native .NET controls 
(e.g., RichTextBox, FlowDocumentViewer), you can parse Markdown manually or via a 
Add nuget dependency [Markdown.Xaml](https://www.nuget.org/packages/Markdown.Xaml):

```
var md = new Markdown.Xaml.Markdown();
var flowDoc = md.Transform("**bold** _italic_");
myFlowDocumentViewer.Document = flowDoc;
```

NOTE: This provides basic styling but not a full GitHub-style rendering.

</li>
</ol>

### Installing Azul JDK

Unlike OpenJDK 11 and Oracle JDK 11, the [Azul JDK](https://www.azul.com/core-post-download/?endpoint=zulu&uuid=e0449078-aad8-4719-aba8-78ae22fea199) has JavaFX included.


* Make sure to uncheck both

+ add to PATH
+ set JAVA_HOME variable	

when installing the MSI
Also, make sure to install under `C:\java\zulu-jdk11`, not the default location `C:\Program Files\Zulu\zulu-11\`
NOTE: install 32 or 64 bit Azul JDK (`zulu11.82.19-ca-jdk11.0.28-win_i686.msi`/`zulu11.82.19-ca-jdk11.0.28-win_x64.msi`) in the same location

### See Also
   * javascript and JavaFX powered [Markdown Editor](https://github.com/arildyk/simple-markdown-editor) (need to convert from visual studio code project)

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)


