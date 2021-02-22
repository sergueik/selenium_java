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
^(?:([^\|]+)#)*( [^#]+): *|#((?:[^#]+#?)*)##(.*$)
```
