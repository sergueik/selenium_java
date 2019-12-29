#!/bin/env bash
mkdir -p mods

# TODO: read bytecode
MODULE='example.app'
MAIN_CLASS="${MODULE}.ModuleApp"
jar --create --file=mods/${MODULE}@1.0.jar --module-version=1.0 --main-class=$MAIN_CLASS -C build/$MODULE .
MODULE='example.greetings'
unset MAIN_CLASS
jar --create --file=mods/${MODULE}@1.0.jar --module-version=1.0 -C build/$MODULE .
