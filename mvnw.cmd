@echo off
setlocal
set MAVEN_HOME=%~dp0.mvn
set WRAPPER_JAR=%MAVEN_HOME%\wrapper\maven-wrapper.jar
java -cp "%WRAPPER_JAR%" org.apache.maven.wrapper.MavenWrapperMain %*
