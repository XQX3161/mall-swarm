@echo off
REM Simple Maven "wrapper" for Windows: downloads Maven to %USERPROFILE%\.m2\wrappers and runs it.
REM This requires PowerShell to be available.
set MAVEN_VERSION=3.8.7
set MAVEN_BASE=apache-maven-%MAVEN_VERSION%
set MAVEN_DIR=%USERPROFILE%\.m2\wrappers\%MAVEN_BASE%

if not exist "%MAVEN_DIR%\bin\mvn.cmd" (
  echo Maven %MAVEN_VERSION% not found in %MAVEN_DIR%, downloading...
  powershell -Command "$tmp = [IO.Path]::GetTempPath(); $zip = Join-Path $tmp 'maven.zip'; Invoke-WebRequest -Uri 'https://archive.apache.org/dist/maven/maven-3/%MAVEN_VERSION%/binaries/%MAVEN_BASE%-bin.zip' -OutFile $zip; Expand-Archive -Path $zip -DestinationPath $tmp -Force; Move-Item -Path (Join-Path $tmp '%MAVEN_BASE%') -Destination '%MAVEN_DIR%' -Force; Remove-Item $zip -Force" || (echo Download failed & exit /b 1)
)

"%MAVEN_DIR%\bin\mvn.cmd" %*
