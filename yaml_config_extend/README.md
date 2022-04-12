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
as a whole into matching type subject Java application Properties:
e.g. with 
```YAML
map_setting:
  boolean_setting: true
  integer_setting: 42
  string_setting: somethings
```

one knows the keys of
```java
String key = "map_setting";
Map<String, Object> map_value = config.getMap(key);
map_value.keySet();
```

and then one can either do
```java
boolean boolean_value = Boolean.parseBoolean(map_value.get("boolean_setting").toString());
```
or

```java
boolean boolean_value = config.getBoolean("map_setting.boolean_setting");
```

and similarly with other sub keys
### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
