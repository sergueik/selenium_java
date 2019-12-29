#!/bin/env bash
function rmIfExist {
    DIR=$1
    if [ -d "$DIR" ]; then
        printf '%s\n' "Removing $DIR"
        rm -rf "$DIR"
    fi
}
MODULE='example.app'
for D in build mods "${MODULE}-image"; do
rmIfExist $D ; done

