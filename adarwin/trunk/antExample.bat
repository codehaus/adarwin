set MAVEN_REPO="%USERPROFILE%\.maven\repository"

java -classpath %MAVEN_REPO%\ant\jars\ant-1.5.2.jar;%MAVEN_REPO%\asm\jars\asm-1.3.3.jar;target\classes org.apache.tools.ant.Main -buildfile antExample.xml %1 %2 %3 %4 %5