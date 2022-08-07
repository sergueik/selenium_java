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

### Usage

```sh

```

### Integration


Failed tests:
```text
  getStringArrayElement(example.YamlConfigExtendedTest): (..)
  getInt(example.YamlConfigExtendedTest): (..)
  getCompactCollectionElement(example.YamlConfigExtendedTest): (..)
  getDocString1(example.YamlConfigExtendedTest): (..)
  getDocString2(example.YamlConfigExtendedTest): (..)
  getDocString3(example.YamlConfigExtendedTest): (..)
  getCompactArrayElement(example.YamlConfigExtendedTest): (..)
  getMap2(example.YamlConfigExtendedTest): expect the classpath notation to work for map_setting.boolean_setting(..)
  getString(example.YamlConfigExtendedTest): (..)
  getStringNumber(example.YamlConfigExtendedTest): (..)
  getQuotesRemovedString(example.YamlConfigExtendedTest): (..)
```
total tests:
```sh
grep -i -A 1 @test src/test/java/example/YamlConfigExtendedTest.java   | grep -i 'public'
```
```text
        public void getStringArrayElement() {
        public void getStringOutOfIndex() {
        public void getStringInvalidKey() {
        public void getStringNumber() {
        public void getString() {
        public void getInt() {
        public void getBadInt() {
        public void getBoolean() {
        public void getNullString() {
        public void getQuotesRemovedString() {
        public void getMap() {
        public void getMap2() {
        public void getNullInMap() {
        public void getList() {
        public void getListWithHere() {
        public void getListWithHereWithNewline() {
        public void getCompactArrayList() {
        public void getCompactArrayElement() {
        public void getCompactCollectionElement() {
        public void getDocString1() {
        public void getDocString2() {
        public void getDocString3() {
```
### See Also

  * https://www.vogella.com/tutorials/Hamcrest/article.html
  * [discussion](https://www.cyberforum.ru/shell/thread3007272.html)(in Russian) of using the rundown values with [gitlab]()
  * multiline string YAML fragment [builder](https://yaml-multiline.info) (does not appear to show all cases) - see the definiton for "block scalar" and "flow scalar" entities
  * related [stackoverfow](https://stackoverflow.com/questions/3790454/how-do-i-break-a-string-in-yaml-over-multiple-lines) post 
  * YAML [cheat sheet](https://lzone.de/cheat-sheet/YAML)

### Author

[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
