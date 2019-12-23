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
#time java -cp . Prob $target
echo "B"
time java -cp . ProbB $target
echo "B2"
time java -cp . ProbB2 $target


