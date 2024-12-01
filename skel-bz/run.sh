#!/bin/bash

set -eu

bazel build :all

time bazel-bin/Prob $*


