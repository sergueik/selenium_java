#!/bin/bash
# Launch JavaFX Markdown Viewer app, fallback to JDK 8 if JavaFX SDK is missing

APP_CLASS=com.markdown.MarkdownViewerApp
JAR_NAME=markdown-viewer.jar
JAR_PATH="./target/${JAR_NAME}"
JFX_DIR="$HOME/javafx-sdk-11/lib"

# Detect Java 11 first, fallback to Java 8
JAVA_BIN=$(update-alternatives --list java | grep -E 'java-11' | head -n1)
if [[ -z "$JAVA_BIN" ]]; then
  echo "Java 11 not found — trying Java 8"
  JAVA_BIN=$(update-alternatives --list java | grep -E 'java-1\.8' | head -n1)
fi

if [[ -z "$JAVA_BIN" ]]; then
  echo "No compatible Java (11+ or 8) found via update-alternatives."
  exit 1
fi

JAVA_VERSION=$("$JAVA_BIN" -version 2>&1 | head -n1)
echo "Using Java: $JAVA_VERSION"

# Check for JavaFX SDK if Java 11 is in use
if [[ "$JAVA_VERSION" =~ \"11 ]] || [[ "$JAVA_VERSION" =~ \"17 ]]; then
  if [[ -f "$JFX_DIR/javafx.controls.jar" ]]; then
    echo "JavaFX SDK found in: $JFX_DIR"
    exec "$JAVA_BIN" \
      --module-path "$JFX_DIR" \
      --add-modules javafx.controls,javafx.fxml \
      -cp "$JAR_PATH" \
      "$APP_CLASS"
  else
    echo "JavaFX SDK not found in: $JFX_DIR"
    echo "Please download JavaFX 11 SDK from: https://gluonhq.com/products/javafx/"
    echo "Or install Java 8 which includes JavaFX by default."
    exit 1
  fi
fi

# If Java 8, just run (JavaFX is included)
if [[ "$JAVA_VERSION" =~ \"1\.8 ]]; then
  echo "Running on Java 8 with built-in JavaFX support."
  exec "$JAVA_BIN" -cp "$JAR_PATH" "$APP_CLASS"
fi

echo "Unsupported Java version. Only 11+ with JavaFX SDK or 1.8 are supported."
exit 1

mvn -f ./pom-java11.xml clean package
echo 'Launching with classpath + modulepath semantics'
java -Dprism.order=sw -cp target/example.javafx_markdown.jar:target/lib/* \
--module-path target/lib \
--add-modules=javafx.controls,javafx.base,javafx.graphics,commons.configuration \
example.Example
# surprisingly, observed the error
# java.lang.module.FindException: Module org.apache.logging.log4j not found
# when log4j jar was included in pom-java11.xml but unused in application
