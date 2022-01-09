set JAVA_HOME=c:\portablesw\jdk11.0.2
set PATH=%JAVA_HOME%\bin;%PATH%

rem c:
rem cd c:\Users\trohr\Documents\Projects\DBDC\dbdc-core\target

set CLASSPATH=ojdbc8.jar;terajdbc4.jar;tdgssconfig.jar;dbdc-core-0.1-jar-with-dependencies.jar
rem copy src\main\resources\global.properties target\

pushd target
java -cp "%CLASSPATH%"  org.tomasrohr.dbdc.Main

popd

pause