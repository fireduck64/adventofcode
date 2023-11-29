#!/bin/bash

if [ -e $1 ]
then
  echo "Already exists"
  exit 1
fi
rsync -avP ../skel-bz/ $1/

cd $1
aoc-grab.sh
./run.sh


