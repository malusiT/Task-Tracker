#!/bin/bash
set -e # exit if any command fails

CLASSPATH="./lib/gson-2.13.1.jar"

echo "Compiling Java files..."
javac -d build -cp "$CLASSPATH" src/*.java

echo "Running programm..."
java -cp "build:$CLASSPATH" TaskTracker
