#!/bin/bash

for y in $(seq 2015 2022)
do
  ./run.sh $y > $y.txt
done
