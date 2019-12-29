#!/bin/env bash
MODULE='example.app'
MAIN_CLASS="${MODULE}.ModuleApp"

"${MODULE}-image/bin/java" -m "${MODULE}-image/${MAIN_CLASS}"
