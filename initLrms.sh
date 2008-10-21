#!/bin/bash

~vinicius/integrade-mag/startservices.sh lrm >& /tmp/$HOSTNAME-lrm.out
ssh -fn ilhabela "~vinicius/integrade-mag/startservices.sh lrm" >& /tmp/$HOSTNAME-lrm.out
ssh -fn orlandia "~vinicius/integrade-mag/startservices.sh lrm" >& /tmp/$HOSTNAME-lrm.out
ssh -fn giga     "~vinicius/integrade-mag/startservices.sh lrm" >& /tmp/$HOSTNAME-lrm.out
#ssh -fn taubate  "~vinicius/integrade-mag-64/startservices.sh lrm" >& /tmp/$HOSTNAME-lrm.out
ssh -fn motuca   "~vinicius/integrade-mag-64/startservices.sh lrm" >& /tmp/$HOSTNAME-lrm.out
