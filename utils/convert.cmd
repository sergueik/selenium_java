@echo OFF
set SIZE=%2
set S=%~1
set F=%~n1
echo ffmpeg.exe -i "%S%" -c:v vp9 -s %SIZE% -v 0 "%F%.mkv"
ffmpeg.exe -i "%S%" -c:v vp9 -s %SIZE% -v 0 "%F%.mkv"