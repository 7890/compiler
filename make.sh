#!/bin/sh
#//tb/1802

which java >/dev/null || (echo "java not found" && return 1) || return 1
which javac >/dev/null || (echo "javac not found" && return 1) || return 1
which jar >/dev/null || (echo "jar not found" && return 1) || return 1

mkdir -p _build/_info
find|grep "\.java$"|grep -v test>_build/java_files.txt
javac -d _build @_build/java_files.txt
cp README.md pom.xml _build/_info
cd _build
jar cf compiler.jar com _info
rm -rf com _info
cd ..

#test compile with jar
echo "javac -cp .:_build/compiler.jar -d _build *.java"
javac -cp .:_build/compiler.jar -d _build *.java

#test run
echo "java -cp _build:_build/compiler.jar Test"
echo "start test? (enter to continue)"
read a
time java -cp _build:_build/compiler.jar Test

#EOF
