#!/bin/sh

mkdir -p data
year=$1

mkdir -p data/$year

for day in $(seq 1 25)
do
  
  echo $year $day
  curl https://adventofcode.com/$year/leaderboard/day/$day | ./proc.sh > data/$year/$day
  sleep 3

done
