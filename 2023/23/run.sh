#!/bin/bash

set -eu

target="input"
if [ $# -gt 0 ]
then
  target="$1"
fi

bazel build :all :Prob_deploy.jar

if [ ! -e "$target" ]
then
  echo "Must download input: $target"
  exit 1
fi
echo "Running $target"
#time bazel-bin/Prob $target

time java -Xss800m -jar bazel-bin/Prob_deploy.jar $target
