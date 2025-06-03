@echo OFF
goto :EOF
REM I will have to do it along with job search. 
REM I had to resign WF AI Vertex because they ceased to 
REM onboard me technicallty
REM The system cannot find the batch label specified - EOF
REM
REM https://github.com/getffmpeg/ffmpeg/releases/download/v7.1/ffmpeg-setup.exe
put
 Directory of C:\Users\kouzm\AppData\Local\Programs\FFmpeg\bin

06/02/2025  01:44 PM    <DIR>          .
06/02/2025  01:44 PM    <DIR>          ..
10/01/2024  01:05 PM        89,262,080 avcodec-61.dll
10/01/2024  01:05 PM         4,483,072 avdevice-61.dll
10/01/2024  01:05 PM        41,905,152 avfilter-10.dll
10/01/2024  01:05 PM        18,482,176 avformat-61.dll
10/01/2024  01:05 PM         2,814,976 avutil-59.dll
10/01/2024  01:05 PM           431,616 ffmpeg.exe

10/01/2024  01:05 PM        13,053,952 ffplay.exe
10/01/2024  01:05 PM           220,672 ffprobe.exe
10/01/2024  01:05 PM            87,552 postproc-58.dll
10/01/2024  01:05 PM           438,784 swresample-5.dll
10/01/2024  01:05 PM           707,584 swscale-8.dll
              11 File(s)    171,887,616 bytes
               2 Dir(s)  404,121,391,104 bytes free

path=%path%;C:\Users\kouzm\AppData\Local\Programs\FFmpeg\bin

-F
REM  136
-f 136

-f "bv*+ba/best" -o "%(title)s.%(ext)s"

c:\Users\kouzm\Downloads\yt-dlp.exe `-f "bv*+ba/best" -o "%(title)s.%(ext)s" "https://www.youtube.com/playlist?list=PLBu9owL7sQV9BRWQvGzBi-KkmqMR6qWK8"
