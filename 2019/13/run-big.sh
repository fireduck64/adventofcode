#!/bin/bash

set -eu

target="input"
if [ $# -gt 0 ]
then
  target="$1"
fi

javac *.java

if [ ! -e input ]
then
  echo "Must download input"
  exit 1
fi
echo "Running $target"
java -Xmx50g -cp . Prob $target


