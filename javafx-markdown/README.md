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

* The project does not compile with JDK 11. To reproduce, make the following settings in the vanilla console:
```cmd
set JAVA_HOME=c:\java\zulu11.45.27-ca-jdk11.0.10-win_x64
call c:\java\init.cmd
```
then call
```cmd
call test.cmd
```

Will get errors:
```text
[ERROR] COMPILATION ERROR : 
[INFO] -------------------------------------------------------------
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[3,26] package javafx.application does not exist
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[4,20] package javafx.scene does not exist
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[5,20] package javafx.scene does not exist
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[6,20] package javafx.scene does not exist
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[7,28] package javafx.scene.control does not exist
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[8,28] package javafx.scene.control does not exist
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[9,28] package javafx.scene.control does not exist
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[10,27] package javafx.scene.layout does not exist
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[11,20] package javafx.stage does not exist
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[21,30] cannot find symbol
  symbol: class Application
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[26,27] cannot find symbol
  symbol:   class Stage
  location: class example.Example
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[11,26] package javafx.application does not exist
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[12,29] package javafx.beans.property does not exist
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[13,29] package javafx.beans.property does not exist
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[14,23] package javafx.geometry does not exist
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[15,20] package javafx.scene does not exist
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[16,28] package javafx.scene.control does not exist
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[18,25] package javafx.scene.text does not exist
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[19,25] package javafx.scene.text does not exist
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[20,19] package javafx.util does not exist
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[31,37] cannot find symbol
  symbol: class VBox
[ERROR] /../javafx-markdown/src/main/java/example/MarkdownView.java:[3,29] package javafx.beans.property does not exist
[ERROR] /../javafx-markdown/src/main/java/example/MarkdownView.java:[4,29] package javafx.beans.property does not exist
[ERROR] /../javafx-markdown/src/main/java/example/MarkdownView.java:[5,20] package javafx.scene does not exist
[ERROR] /../javafx-markdown/src/main/java/example/MarkdownView.java:[7,26] package javafx.scene.image does not exist
[ERROR] /../javafx-markdown/src/main/java/example/MarkdownView.java:[8,26] package javafx.scene.image does not exist
[ERROR] /../javafx-markdown/src/main/java/example/MarkdownView.java:[9,27] package javafx.scene.layout does not exist
[ERROR] /../javafx-markdown/src/main/java/example/MarkdownView.java:[12,35] cannot find symbol
  symbol: class VBox
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[42,23] cannot find symbol
  symbol:   class Pair
  location: class example.MDFXNodeHelper
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[48,9] cannot find symbol
  symbol:   class VBox
  location: class example.MDFXNodeHelper
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[50,9] cannot find symbol
  symbol:   class GridPane
  location: class example.MDFXNodeHelper
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[53,9] cannot find symbol
  symbol:   class TextFlow
  location: class example.MDFXNodeHelper
[ERROR] /../javafx-markdown/src/main/java/example/MarkdownView.java:[14,17] cannot find symbol
  symbol:   class SimpleStringProperty
  location: class example.MarkdownView
[ERROR] /../javafx-markdown/src/main/java/example/MarkdownView.java:[33,16] cannot find symbol
  symbol:   class StringProperty
  location: class example.MarkdownView
[ERROR] /../javafx-markdown/src/main/java/example/MarkdownView.java:[49,29] cannot find symbol
  symbol:   class Node
  location: class example.MarkdownView
[ERROR] /../javafx-markdown/src/main/java/example/MarkdownView.java:[54,16] cannot find symbol
  symbol:   class Node
  location: class example.MarkdownView
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[17,1] package javafx.scene.layout does not exist
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[25,9] method does not override or implement a method from a supertype
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[30,44] cannot find symbol
  symbol:   method getClass()
  location: class example.Example
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[42,45] cannot find symbol
  symbol: class Node
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[51,32] cannot find symbol
  symbol: class Node
[ERROR] /../javafx-markdown/src/main/java/example/MarkdownView.java:[14,53] cannot find symbol
  symbol:   class SimpleStringProperty
  location: class example.MarkdownView
[ERROR] /../javafx-markdown/src/main/java/example/MarkdownView.java:[19,17] cannot find symbol
  symbol:   method getStylesheets()
  location: class example.MarkdownView
[ERROR] /../javafx-markdown/src/main/java/example/MarkdownView.java:[29,17] cannot find symbol
  symbol:   method getChildren()
  location: class example.MarkdownView
[ERROR] /../javafx-markdown/src/main/java/example/MarkdownView.java:[30,17] cannot find symbol
  symbol:   method getChildren()
  location: class example.MarkdownView
[ERROR] /../javafx-markdown/src/main/java/example/MarkdownView.java:[56,36] cannot find symbol
  symbol:   class Group
  location: class example.MarkdownView
[ERROR] /../javafx-markdown/src/main/java/example/MarkdownView.java:[58,36] cannot find symbol
  symbol:   class ImageView
  location: class example.MarkdownView
[ERROR] /../javafx-markdown/src/main/java/example/MarkdownView.java:[58,50] cannot find symbol
  symbol:   class Image
  location: class example.MarkdownView
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[44,48] cannot find symbol
  symbol: variable Cursor
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[53,52] cannot find symbol
  symbol: class ColorPicker
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[60,17] cannot find symbol
  symbol:   class TextArea
  location: class example.Example
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[60,41] cannot find symbol
  symbol:   class TextArea
  location: class example.Example
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[63,29] cannot find symbol
  symbol:   method getStylesheets()
  location: variable markdownView of type example.MarkdownView
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[65,17] cannot find symbol
  symbol:   class ScrollPane
  location: class example.Example
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[65,42] cannot find symbol
  symbol:   class ScrollPane
  location: class example.Example
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[70,17] cannot find symbol
  symbol:   class HBox
  location: class example.Example
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[70,33] cannot find symbol
  symbol:   class HBox
  location: class example.Example
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[72,17] cannot find symbol
  symbol:   class Scene
  location: class example.Example
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[72,35] cannot find symbol
  symbol:   class Scene
  location: class example.Example
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[88,42] cannot find symbol
  symbol: method getClass()
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[42,84] cannot find symbol
  symbol:   class Pair
  location: class example.MDFXNodeHelper
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[48,25] cannot find symbol
  symbol:   class VBox
  location: class example.MDFXNodeHelper
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[65,17] cannot find symbol
  symbol:   class TextFlow
  location: class example.MDFXNodeHelper
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[65,40] cannot find symbol
  symbol:   class TextFlow
  location: class example.MDFXNodeHelper
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[88,21] cannot find symbol
  symbol: method getChildren()
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[134,25] cannot find symbol
  symbol:   class Label
  location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[134,43] cannot find symbol
  symbol:   class Label
  location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[137,25] cannot find symbol
  symbol:   class Region
  location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[137,43] cannot find symbol
  symbol:   class Region
  location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[153,25] cannot find symbol
  symbol:   class VBox
  location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[154,36] cannot find symbol
  symbol:   class VBox
  location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[196,25] cannot find symbol
  symbol:   class Label
  location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[196,43] cannot find symbol
  symbol:   class Label
  location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[198,25] cannot find symbol
  symbol:   class VBox
  location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[198,41] cannot find symbol
  symbol:   class VBox
  location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[242,25] cannot find symbol
  symbol:   class VBox
  location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[244,25] cannot find symbol
  symbol:   class VBox
  location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[244,44] cannot find symbol
  symbol:   class VBox
  location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[251,25] cannot find symbol
  symbol:   class Label
  location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[251,43] cannot find symbol
  symbol:   class Label
  location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[256,25] cannot find symbol
  symbol:   class HBox
  location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[256,41] cannot find symbol
  symbol:   class HBox
  location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[259,43] cannot find symbol
  symbol:   variable Pos
  location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[292,25] cannot find symbol
  symbol:   class VBox
  location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[293,36] cannot find symbol
  symbol:   class VBox
  location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[305,25] cannot find symbol
  symbol:   class VBox
  location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[306,36] cannot find symbol
  symbol:   class VBox
  location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[344,34] cannot find symbol
  symbol:   class Pair
  location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[352,25] cannot find symbol
  symbol:   variable Platform
  location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[353,33] cannot find symbol
  symbol:   class BooleanProperty
  location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[353,65] cannot find symbol
  symbol:   class SimpleBooleanProperty
  location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[357,57] cannot find symbol
  symbol:   method isHover()
  location: variable node of type com.vladsch.flexmark.util.ast.Node
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[365,69] cannot find symbol
  symbol:   method getStyleClass()
  location: variable node of type com.vladsch.flexmark.util.ast.Node
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[367,69] cannot find symbol
  symbol:   method getStyleClass()
  location: variable node of type com.vladsch.flexmark.util.ast.Node
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[377,45] cannot find symbol
  symbol:   method hoverProperty()
  location: variable node of type com.vladsch.flexmark.util.ast.Node
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[428,25] cannot find symbol
  symbol:   class TextFlow
  location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[429,36] cannot find symbol
  symbol:   class GridPane
  location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[438,33] cannot find symbol
  symbol:   class ColumnConstraints
  location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[438,68] cannot find symbol
  symbol:   class ColumnConstraints
  location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[468,25] cannot find symbol
  symbol:   class TextFlow
  location: class example.MDFXNodeHelper.MDParser
[INFO] 100 errors 
[INFO] -------------------------------------------------------------
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  36.979 s
[INFO] Finished at: 2023-07-06T11:32:35-04:00
[INFO] ------------------------------------------------------------------------
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.6.1:compile (default-compile) on project javafx_markdown: Compilation failure: Compilation failure: 
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[3,26] package javafx.application does not exist
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[4,20] package javafx.scene does not exist
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[5,20] package javafx.scene does not exist
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[6,20] package javafx.scene does not exist
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[7,28] package javafx.scene.control does not exist
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[8,28] package javafx.scene.control does not exist
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[9,28] package javafx.scene.control does not exist
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[10,27] package javafx.scene.layout does not exist
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[11,20] package javafx.stage does not exist
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[21,30] cannot find symbol
[ERROR]   symbol: class Application
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[26,27] cannot find symbol
[ERROR]   symbol:   class Stage
[ERROR]   location: class example.Example
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[11,26] package javafx.application does not exist
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[12,29] package javafx.beans.property does not exist
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[13,29] package javafx.beans.property does not exist
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[14,23] package javafx.geometry does not exist
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[15,20] package javafx.scene does not exist
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[16,28] package javafx.scene.control does not exist
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[18,25] package javafx.scene.text does not exist
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[19,25] package javafx.scene.text does not exist
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[20,19] package javafx.util does not exist
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[31,37] cannot find symbol
[ERROR]   symbol: class VBox
[ERROR] /../javafx-markdown/src/main/java/example/MarkdownView.java:[3,29] package javafx.beans.property does not exist
[ERROR] /../javafx-markdown/src/main/java/example/MarkdownView.java:[4,29] package javafx.beans.property does not exist
[ERROR] /../javafx-markdown/src/main/java/example/MarkdownView.java:[5,20] package javafx.scene does not exist
[ERROR] /../javafx-markdown/src/main/java/example/MarkdownView.java:[7,26] package javafx.scene.image does not exist
[ERROR] /../javafx-markdown/src/main/java/example/MarkdownView.java:[8,26] package javafx.scene.image does not exist
[ERROR] /../javafx-markdown/src/main/java/example/MarkdownView.java:[9,27] package javafx.scene.layout does not exist
[ERROR] /../javafx-markdown/src/main/java/example/MarkdownView.java:[12,35] cannot find symbol
[ERROR]   symbol: class VBox
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[42,23] cannot find symbol
[ERROR]   symbol:   class Pair
[ERROR]   location: class example.MDFXNodeHelper
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[48,9] cannot find symbol
[ERROR]   symbol:   class VBox
[ERROR]   location: class example.MDFXNodeHelper
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[50,9] cannot find symbol
[ERROR]   symbol:   class GridPane
[ERROR]   location: class example.MDFXNodeHelper
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[53,9] cannot find symbol
[ERROR]   symbol:   class TextFlow
[ERROR]   location: class example.MDFXNodeHelper
[ERROR] /../javafx-markdown/src/main/java/example/MarkdownView.java:[14,17] cannot find symbol
[ERROR]   symbol:   class SimpleStringProperty
[ERROR]   location: class example.MarkdownView
[ERROR] /../javafx-markdown/src/main/java/example/MarkdownView.java:[33,16] cannot find symbol
[ERROR]   symbol:   class StringProperty
[ERROR]   location: class example.MarkdownView
[ERROR] /../javafx-markdown/src/main/java/example/MarkdownView.java:[49,29] cannot find symbol
[ERROR]   symbol:   class Node
[ERROR]   location: class example.MarkdownView
[ERROR] /../javafx-markdown/src/main/java/example/MarkdownView.java:[54,16] cannot find symbol
[ERROR]   symbol:   class Node
[ERROR]   location: class example.MarkdownView
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[17,1] package javafx.scene.layout does not exist
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[25,9] method does not override or implement a method from a supertype
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[30,44] cannot find symbol
[ERROR]   symbol:   method getClass()
[ERROR]   location: class example.Example
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[42,45] cannot find symbol
[ERROR]   symbol: class Node
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[51,32] cannot find symbol
[ERROR]   symbol: class Node
[ERROR] /../javafx-markdown/src/main/java/example/MarkdownView.java:[14,53] cannot find symbol
[ERROR]   symbol:   class SimpleStringProperty
[ERROR]   location: class example.MarkdownView
[ERROR] /../javafx-markdown/src/main/java/example/MarkdownView.java:[19,17] cannot find symbol
[ERROR]   symbol:   method getStylesheets()
[ERROR]   location: class example.MarkdownView
[ERROR] /../javafx-markdown/src/main/java/example/MarkdownView.java:[29,17] cannot find symbol
[ERROR]   symbol:   method getChildren()
[ERROR]   location: class example.MarkdownView
[ERROR] /../javafx-markdown/src/main/java/example/MarkdownView.java:[30,17] cannot find symbol
[ERROR]   symbol:   method getChildren()
[ERROR]   location: class example.MarkdownView
[ERROR] /../javafx-markdown/src/main/java/example/MarkdownView.java:[56,36] cannot find symbol
[ERROR]   symbol:   class Group
[ERROR]   location: class example.MarkdownView
[ERROR] /../javafx-markdown/src/main/java/example/MarkdownView.java:[58,36] cannot find symbol
[ERROR]   symbol:   class ImageView
[ERROR]   location: class example.MarkdownView
[ERROR] /../javafx-markdown/src/main/java/example/MarkdownView.java:[58,50] cannot find symbol
[ERROR]   symbol:   class Image
[ERROR]   location: class example.MarkdownView
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[44,48] cannot find symbol
[ERROR]   symbol: variable Cursor
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[53,52] cannot find symbol
[ERROR]   symbol: class ColorPicker
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[60,17] cannot find symbol
[ERROR]   symbol:   class TextArea
[ERROR]   location: class example.Example
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[60,41] cannot find symbol
[ERROR]   symbol:   class TextArea
[ERROR]   location: class example.Example
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[63,29] cannot find symbol
[ERROR]   symbol:   method getStylesheets()
[ERROR]   location: variable markdownView of type example.MarkdownView
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[65,17] cannot find symbol
[ERROR]   symbol:   class ScrollPane
[ERROR]   location: class example.Example
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[65,42] cannot find symbol
[ERROR]   symbol:   class ScrollPane
[ERROR]   location: class example.Example
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[70,17] cannot find symbol
[ERROR]   symbol:   class HBox
[ERROR]   location: class example.Example
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[70,33] cannot find symbol
[ERROR]   symbol:   class HBox
[ERROR]   location: class example.Example
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[72,17] cannot find symbol
[ERROR]   symbol:   class Scene
[ERROR]   location: class example.Example
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[72,35] cannot find symbol
[ERROR]   symbol:   class Scene
[ERROR]   location: class example.Example
[ERROR] /../javafx-markdown/src/main/java/example/Example.java:[88,42] cannot find symbol
[ERROR]   symbol: method getClass()
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[42,84] cannot find symbol
[ERROR]   symbol:   class Pair
[ERROR]   location: class example.MDFXNodeHelper
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[48,25] cannot find symbol
[ERROR]   symbol:   class VBox
[ERROR]   location: class example.MDFXNodeHelper
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[65,17] cannot find symbol
[ERROR]   symbol:   class TextFlow
[ERROR]   location: class example.MDFXNodeHelper
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[65,40] cannot find symbol
[ERROR]   symbol:   class TextFlow
[ERROR]   location: class example.MDFXNodeHelper
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[88,21] cannot find symbol
[ERROR]   symbol: method getChildren()
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[134,25] cannot find symbol
[ERROR]   symbol:   class Label
[ERROR]   location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[134,43] cannot find symbol
[ERROR]   symbol:   class Label
[ERROR]   location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[137,25] cannot find symbol
[ERROR]   symbol:   class Region
[ERROR]   location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[137,43] cannot find symbol
[ERROR]   symbol:   class Region
[ERROR]   location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[153,25] cannot find symbol
[ERROR]   symbol:   class VBox
[ERROR]   location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[154,36] cannot find symbol
[ERROR]   symbol:   class VBox
[ERROR]   location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[196,25] cannot find symbol
[ERROR]   symbol:   class Label
[ERROR]   location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[196,43] cannot find symbol
[ERROR]   symbol:   class Label
[ERROR]   location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[198,25] cannot find symbol
[ERROR]   symbol:   class VBox
[ERROR]   location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[198,41] cannot find symbol
[ERROR]   symbol:   class VBox
[ERROR]   location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[242,25] cannot find symbol
[ERROR]   symbol:   class VBox
[ERROR]   location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[244,25] cannot find symbol
[ERROR]   symbol:   class VBox
[ERROR]   location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[244,44] cannot find symbol
[ERROR]   symbol:   class VBox
[ERROR]   location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[251,25] cannot find symbol
[ERROR]   symbol:   class Label
[ERROR]   location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[251,43] cannot find symbol
[ERROR]   symbol:   class Label
[ERROR]   location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[256,25] cannot find symbol
[ERROR]   symbol:   class HBox
[ERROR]   location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[256,41] cannot find symbol
[ERROR]   symbol:   class HBox
[ERROR]   location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[259,43] cannot find symbol
[ERROR]   symbol:   variable Pos
[ERROR]   location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[292,25] cannot find symbol
[ERROR]   symbol:   class VBox
[ERROR]   location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[293,36] cannot find symbol
[ERROR]   symbol:   class VBox
[ERROR]   location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[305,25] cannot find symbol
[ERROR]   symbol:   class VBox
[ERROR]   location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[306,36] cannot find symbol
[ERROR]   symbol:   class VBox
[ERROR]   location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[344,34] cannot find symbol
[ERROR]   symbol:   class Pair
[ERROR]   location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[352,25] cannot find symbol
[ERROR]   symbol:   variable Platform
[ERROR]   location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[353,33] cannot find symbol
[ERROR]   symbol:   class BooleanProperty
[ERROR]   location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[353,65] cannot find symbol
[ERROR]   symbol:   class SimpleBooleanProperty
[ERROR]   location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[357,57] cannot find symbol
[ERROR]   symbol:   method isHover()
[ERROR]   location: variable node of type com.vladsch.flexmark.util.ast.Node
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[365,69] cannot find symbol
[ERROR]   symbol:   method getStyleClass()
[ERROR]   location: variable node of type com.vladsch.flexmark.util.ast.Node
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[367,69] cannot find symbol
[ERROR]   symbol:   method getStyleClass()
[ERROR]   location: variable node of type com.vladsch.flexmark.util.ast.Node
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[377,45] cannot find symbol
[ERROR]   symbol:   method hoverProperty()
[ERROR]   location: variable node of type com.vladsch.flexmark.util.ast.Node
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[428,25] cannot find symbol
[ERROR]   symbol:   class TextFlow
[ERROR]   location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[429,36] cannot find symbol
[ERROR]   symbol:   class GridPane
[ERROR]   location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[438,33] cannot find symbol
[ERROR]   symbol:   class ColumnConstraints
[ERROR]   location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[438,68] cannot find symbol
[ERROR]   symbol:   class ColumnConstraints
[ERROR]   location: class example.MDFXNodeHelper.MDParser
[ERROR] /../javafx-markdown/src/main/java/example/MDFXNodeHelper.java:[468,25] cannot find symbol
[ERROR]   symbol:   class TextFlow
[ERROR]   location: class example.MDFXNodeHelper.MDParser
[ERROR] -> [Help 1]
[ERROR] 
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR] 
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MojoFailureException
done

```
### See Also
   * javascript and JavaFX powered [Markdown Editor](https://github.com/arildyk/simple-markdown-editor) (need to convert from visual studio code project)

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)


