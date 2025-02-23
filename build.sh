#!/bin/bash
mkdir -p bin
/usr/bin/find ./src/com -name "*.java" > sources.txt
javac -d bin -sourcepath /src/com @sources.txt
rm sources.txt
echo "Seluruh program berhasil di Compile!"