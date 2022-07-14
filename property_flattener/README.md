### Info

Tool to convert the hieradata inspired HEREDOC 'extension' (example in `custom.properties`:
```sh
x: |
a
b
c

y:a,c,b

z:d,e
t:|
x
y

u: |
a

v1,v2,v3: |
b
c
d,e,f
g,h

w:c,d,e
```
into a custom __UCD__ property file with a many-to-many format:

```sh
x:a,b,c
t:x,y
y:a,c,b

z:d,e
u:a
v1,v2,v3:b,c,d,e,f,g,h
w:c,d,e
```
 by applying the pattern:
```sh
^(?:([^\|]+)#)*([^#]+): *\|#((?:[^#]+#?)*)##(.*$)
```
### Usage

* build
```sh
mvn clean package
```
* run with debug enabled with `custom.properties` file:
mvn clean package```sh
java -cp target/example.property_flattener.jar:target/lib/* example.Flattener -in $(pwd)/src/main/resources/custom.properties -out generated.properties -op  flatten -debug
```
(on Windows, update the command with path separarors:
```cmd
java -cp target/example.property_flattener.jar;target/lib/* example.Flattener -in src\main\resources\custom.properties -out generated.properties -op  flatten -debug
```
```sh
Pattern: ^(?:([^\|]+)#)*([^#]+): *\|#((?:[^#]+#?)*)##(.*$)
Data: x: |#a#b#c##y:a,c,b##z:d,e#t:|#x#y##u: |#a##v1,v2,v3: |#b#c#d,e,f#g,h##w:c,d,e##
Data: y:a,c,b##z:d,e#t:|#x#y##u: |#a##v1,v2,v3: |#b#c#d,e,f#g,h##w:c,d,e##
Data: u: |#a##v1,v2,v3: |#b#c#d,e,f#g,h##w:c,d,e##
Data: v1,v2,v3: |#b#c#d,e,f#g,h##w:c,d,e##
Data: w:c,d,e##
Done: flatten
```
 - one does not have to run it in with `debug` flag of course
```sh
cat generated.properties
```
```text

x:a,b,c
t:x,y
y:a,c,b

z:d,e
u:a
v1,v2,v3:b,c,d,e,f,g,h
w:c,d,e

```
### TODO

  * add Ruby example of same functionality

### See Aso
  * [similar](https://github.com/sergueik/puppetmaster_vagrant/blob/master/delap.py) tool written in Python
  * [Similar](https://github.com/sergueik/puppetmaster_vagrant/blob/master/delap.pl) tool written in Perl
  * [Similar](https://github.com/sergueik/puppetmaster_vagrant/blob/master/delap.sh) tool written in Bash
### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
