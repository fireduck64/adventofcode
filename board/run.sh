#!/bin/bash

set -eu

bazel build :all

echo "Running $1"
time bazel-bin/Prob $1


