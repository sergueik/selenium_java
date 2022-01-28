@echo OFF
REM origin: https://www.instructables.com/How-to-Run-Volumio-in-PC-or-Laptop/
REM
REM Command for crating usb.VMDK: 
set PATH=%PATH%;"c:\Program Files\Oracle\VirtualBox"

set FILENAME=%1

if /i "%FILENAME%" EQU "" set FILENAME=dummy.vmdk
set DRIVENUM=%2

if /i "%DRIVENUM%" EQU "" set DRIVENUM=1
VBoxManage.exe internalcommands createrawvmdk -filename "%CD%\%FILENAME%" -rawdisk \\.\PhysicalDrive%DRIVENUM%

REM will print 
REM RAW host disk access VMDK file C:\Virtual Machines\dummy.vmdk created successfully.