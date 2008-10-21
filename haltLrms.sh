#!/bin/bash

~vinicius/integrade-mag/stopservices.sh lrm
ssh -fn ilhabela "~vinicius/integrade-mag/stopservices.sh lrm"
ssh -fn orlandia "~vinicius/integrade-mag/stopservices.sh lrm"
ssh -fn giga     "~vinicius/integrade-mag/stopservices.sh lrm"
ssh -fn taubate  "~vinicius/integrade-mag-64/stopservices.sh lrm"
ssh -fn motuca   "~vinicius/integrade-mag-64/stopservices.sh lrm"

