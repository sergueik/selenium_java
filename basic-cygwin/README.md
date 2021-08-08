### Info

Invoking cygwin commands on Windows host from Java `Runtime.getRuntime().exec(command)`.

### Usage
* build
```cmd
mvn package
```
* run via `CLASSPATH`:
```
java -cp target\example.cygwin.jar;target\lib\* example.App /var/log
```
this outputs
```cmd
Listing the dirs: [/var/log]
Running the command: c:\cygwin\bin\bash.exe -c  "/bin/ls -Q $0 $1 $2 $3 $4 $5 $6 $7 $8 $9" /var/log
```
```cmd
<OUTPUT>lastlog
setup.log
setup.log.full
sshd.log</OUTPUT>
```

This matches the output of the
```sh
$ ls -1 /var/log/
```
command  run from Cygwin:

```sh
lastlog
setup.log
setup.log.full
sshd.log
```

* run bad example, to trigger error:
```cmd
java -cp target\example.cygwin.jar;target\lib\* example.App /baddir
```

```cmd
Listing the dirs: [/baddir]
Running the command: c:\cygwin\bin\bash.exe -c  "/bin/ls -Q $0 $1 $2 $3 $4 $5 $6
 $7 $8 $9" /baddir
```
```cmd
Process exit code: 2
<ERROR>/bin/ls: cannot access "/baddir": No such file or directory</ERROR>
```
* test on linux:
```sh
mvn package
java -cp target/example.cygwin.jar:target/lib/* example.App $(pwd)
```
this produces
```sh
Listing the dirs: [/home/sergueik/src/selenium_java/basic-cygwin]
Running the command: ls -Q /home/sergueik/src/selenium_java/basic-cygwin
<OUTPUT>pom.xml
README.md
src
target</OUTPUT>
```
### New Version

* in version __0.2.0-SNAPSHOT__ introduced arguments parser. The invocation becomes:
```sh
java -cp target/example.cygwin.jar:target/lib/* example.App -command ls -arguments $(pwd)
```
this outputs
```sh
Listing the dirs: [/home/sergueik/src/selenium_java/basic-cygwin]
Running the command: ls  /home/sergueik/src/selenium_java/basic-cygwin
<OUTPUT>pom.xml
README.md
repo
src
target
</OUTPUT>
```
also, controls options to be provided:

```sh
java -cp target/example.cygwin.jar:target/lib/* example.App -arguments $(pwd)
```
complains with
```
Missing required argument: command
```
and `quote` is now an optional argument:
```sh
java -cp target/example.cygwin.jar:target/lib/* example.App -command ls -arguments $(pwd) -quote
```
```sh
Listing the dirs: [/home/sergueik/src/selenium_java/basic-cygwin]
Running the command: ls -Q /home/sergueik/src/selenium_java/basic-cygwin
<OUTPUT>pom.xml
README.md
repo
src
target
</OUTPUT>
```
one can pass multiple dirs separated by commans via `-arguments`:
```sh
java -cp target/example.cygwin.jar:target/lib/* example.App -command ls -arguments $(pwd),/tmp -quote
```
### See Also
  * https://stackoverflow.com/questions/6751944/how-to-execute-unix-commands-through-windows-cygwin-using-java

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoc.com)
