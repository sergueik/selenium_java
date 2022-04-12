### Info


Replica of [sixface/YamlConfig](https://github.com/jsixface/YamlConfig) by Arumugam Jeganathan from version __1.0.1__ 
extended
by adding methods to load Maps:
```java
public Map<String, Object> getMap(String key)
```
and Arrays 
```java 
public ArrayList<Object> getList(String key)
```
 from
```yaml
names:
  - John
  - Paul
  - George
  - Ringo  
```
```yaml
names:
  - first: James
    last: Justinson
  - first: Andrew
    last: Armstrong
```
- or more complex structures
as a whole into matching type subject Java application Properties

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
