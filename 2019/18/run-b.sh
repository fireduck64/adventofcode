#!/bin/bash

set -eu

target="input-b"
if [ $# -gt 0 ]
then
  target="$1"
fi

javac *.java

if [ ! -e input-b ]
then
  echo "Must download input"
  exit 1
fi
echo "Running $target"
time java -Xmx4g -cp . ProbB $target


