### Info

Invoking cygwin commands on Windows host from Java `Runtime.getRuntime().exec(command)`.

### Usage
#### Legacy Argument Procesing
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
Running the command: c:\cygwin\bin\bash.exe -c "/bin/ls -Q $0 $1 $2 $3 $4 $5 $6 $7 $8 $9" /var/log
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

* try non existent dir, to trigger error:
```cmd
java -cp target\example.cygwin.jar;target\lib\* example.App /baddir
```

```cmd
Listing the dirs: [/baddir]
Running the command: c:\cygwin\bin\bash.exe -c "/bin/ls -Q $0 $1 $2 $3 $4 $5 $6
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
#### New Argument Processing

* in version __0.2.0-SNAPSHOT__ introduced arguments parser. The invocation becomes:
```sh
java -cp target/example.cygwin.jar:target/lib/* example.App -command ls -arguments $(pwd)
```
this outputs
```sh
Listing the dirs: [/home/sergueik/src/selenium_java/basic-cygwin]
Running the command: ls /home/sergueik/src/selenium_java/basic-cygwin
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

To list regular Windows directories, use cygwin paths like

```cmd
java -cp target\example.cygwin.jar;target\lib\* example.App -c ls -a /cygdrive/c/Users/sergueik/Desktop
```

On cygwin there is also a "bash" flag:

```cmd
java -cp target\example.cygwin.jar;target\lib\* example.App -a /var/l*g -c ls -b
```
this results in
```cmd
Running the command: c:\cygwin\bin\bash.exe -c "/bin/ls $0 $1 $2 $3 $4 $5 $6 $
7 $8 $9" /var/l*g
<OUTPUT>lastlog
setup.log
setup.log.full
sshd.log






</OUTPUT>
```

while without the flag
```cmd
java -cp target\example.cygwin.jar;target\lib\* example.App -a /var/l*g -c ls
```
 the arguments are passed to `ls.exe`:
```cmd
Running the command: c:\cygwin\bin\ls.exe /var/l*g
<OUTPUT>lastlog
setup.log
setup.log.full
sshd.log
</OUTPUT>
```
### Run General Command, Python Script From Java

```python
print(123)
```
pass the path to the script as `arguments` argument:
```cmd
java -cp target\example.cygwin.jar;target\lib\* example.App -c c:\Python381\python.exe -a "%CD%\test.py" -d
```
```text
<OUTPUT>123
</OUTPUT>
```
```text
Done: c:\Python381\python.exe c:\developer\sergueik\selenium_java\basic-cygwin\test.py
```

NOTE: trouble passing inline commands
```cmd
java -cp target\example.cygwin.jar;target\lib\* example.App --command "c:\Python381\python.exe" --arguments "-c ""print 123"""
```

```text
Aborting after exception org.apache.commons.cli.MissingArgumentException: Missing argument for option: a
```
```cmd
"c:\Python381\python.exe" -c "print( 123)"
```

```text
123
```

See Also (incorrect exaple disussion)[https://qna.habr.com/q/1098350]
### Notes

One can override the default path to cygwin via
```cmd
set CYGWIN_HOME=c:\cygwin
```
 
### See Also
  * https://stackoverflow.com/questions/6751944/how-to-execute-unix-commands-through-windows-cygwin-using-java

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)

