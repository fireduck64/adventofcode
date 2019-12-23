#!/bin/bash

set -eu

javac *.java

if [ ! -e input ]
then
  echo "Must download input"
  exit 1
fi
java -cp . Prob input


