### Info

Tool to convert 
the hieradata inspired HEREDOC 'extension' to a property files
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
ito a regular format:
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

```sh
mvn clean package
```
```sh
java -cp target/example.property_flattener.jar:target/lib/* example.Flattener -in $(pwd)/src/main/resources/custom.properties -out generated.properties -op  flatten -debug
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


### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
