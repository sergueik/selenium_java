@echo OFF
REM 
set CLASS=DateRangeDialog
set CLASS=GUIMain
java --module-path gui\target\gui-0.2.0-SNAPSHOT.jar;logic\target\logic-0.2.0-SNAPSHOT.jar;"%USERPROFILE%\.m2\repository\org\openjfx\javafx-base\11.0.2\javafx-base-11.0.2-win.jar";"%USERPROFILE%\.m2\repository\org\openjfx\javafx-graphics\11.0.2\javafx-graphics-11.0.2-win.jar";"%USERPROFILE%\.m2\repository\org\openjfx\javafx-controls\11.0.2\javafx-controls-11.0.2-win.jar" -m gui/example.gui.%CLASS%
