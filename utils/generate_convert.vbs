' origin: https://www.cyberforum.ru/vbscript-wsh/thread2794648.html
Option Explicit
Const iExt = "mp4"
Const oExt = "mkv"
Const List = "filelist.txt"
Dim oWSH, oShA, oWMI, oFSO, SINK, PID, TF, c,_
oPids, oList, Str, oFolder, oItems, F, iName, fName

Set oWSH = CreateObject("WScript.Shell")
Set oShA = CreateObject("Shell.Application")
Set oWMI = GetObject("winmgmts:\root\CIMV2")
Set oFSO = CreateObject("Scripting.FileSystemObject")
Set SINK = WSH.CreateObject("WbemScripting.SWbemSink", "SINK_")
With oWMI.Get("Win32_Process.Handle='" & oWSH.Exec("rundll32 kernel32,Sleep").ProcessId & "'")
  PID = .ParentProcessID: .Terminate
End With
oWMI.ExecNotificationQueryAsync SINK, "SELECT * FROM __InstanceCreationEvent " &_
"WITHIN 1 WHERE TargetInstance ISA 'Win32_Process' AND TargetInstance.Name='ffmpeg.exe'"

TF = oFSO.GetSpecialFolder(2) & "\2PIDs.tmp": Del
Set oList = oFSO.OpenTextFile(List)
Do: Str = oList.ReadLine
  If Str <> Empty Then
    If oFSO.FolderExists(Str) Then
      ' https://learn.microsoft.com/en-us/windows/win32/shell/shell-namespace     
      Set oItems = oShA.NameSpace(Str).Items
      ' https://learn.microsoft.com/en-us/windows/win32/api/shobjidl_core/ne-shobjidl_core-_shcontf
      ' SHCONTF_NONFOLDERS
      ' SHCONTF_FASTITEMS
      oItems.Filter 8256, "*." & iExt
      For Each F In oItems
        iName = oFSO.GetBaseName(F): fName = iName & "." & oExt
        While oFSO.FileExists(oFSO.BuildPath(Str, fName))
          c = c + 1: fName = iName & "_" & c & "." & oExt
        Wend
        If oWSH.Run("C:\tools\ffmpeg-4.0.2-win64-static\bin\ffmpeg.exe -loglevel -8 -i """ & F.Path & """ -c:a copy -c:v libx264 " &_
        "-qmin 0 """ & oFSO.BuildPath(Str, fName) & """", 0, 1) = 0 Then oFSO.DeleteFile F.Path, 1
      Next
    End If
  End If
Loop Until oList.AtEndOfStream: oList.Close
' NOTE: not used
If IsObject(oPids) Then oPids.Close :End If: Del
Set oList = Nothing: Set oFSO = Nothing: Set oShA = Nothing: Set oWSH = Nothing
MsgBox "Done!", 4160, " Conversion *." & iExt & " into *." & oExt & "     "

Sub Del
  If oFSO.FileExists(TF) Then oFSO.DeleteFile TF, 1
End Sub

Sub SINK_OnObjectReady(oEvent, oAsyncContext)
  If oEvent.TargetInstance.ParentProcessId = PID Then
    With oFSO.CreateTextFile(TF, 1) .Write PID & " " & oEvent.TargetInstance.Handle: .Close: End With
  End If
End Sub
