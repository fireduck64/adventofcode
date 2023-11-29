#!/bin/bash

set -eu

cat /dev/stdin |sed "s#</p>#\n#g" | sed "s#</div>#\n#g"| grep "^<div class=\"leaderboard-entry\"" 



