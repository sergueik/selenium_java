#!/bin/sh
for F in \
'Lubuntu 16.04 x86.vdi' \
'w7.vdi' \
'w7-x64-ru.vdi' \
'Windows10-x64-lite-ru-dynamic.vdi' \
'Windows 7 Visual Studio 2015 removed.vdi' \
'Windows 7 Visual Studio 2015.vdi' \
'XPSP3.vdi' \
; do echo "$F"; md5sum "$F" |tee -a "$F.sum" ; done
 
