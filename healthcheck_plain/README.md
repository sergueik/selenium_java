### Info



### Testing

* launch few dummy browswers
```cmd
start node.cmd "" 4441
start node.cmd "" 4443
start node.cmd "" 4445
start node.cmd "" 4447
```
* build app
```cmd
mvn package
```
run app
```cmd
java -cp target\healthcheck-0.1-SNAPSHOT.jar;target\lib\* example.App
```
this will print the findings

```txt
Status: true
Status Code: 200
Page HTML: <html><head><script ...
Page as Jsoup: <html>
 <head>
  <sc...
Searching  via jsoup selector "#leftColumn"
Processing element:id : http://SERGUEIK53:5555, OS : WIN8_1
Processing element:id : http://SERGUEIK53:4441, OS : WIN8_1
Searching  via jsoup selector "#rightColumn"
Processing element:id : http://SERGUEIK53:4443, OS : WIN8_1
Processing element:id : http://SERGUEIK53:4445, OS : WIN8_1
```


### See Also 

  * [simple HTTP REquest in plain Java](https://www.baeldung.com/java-http-request)

