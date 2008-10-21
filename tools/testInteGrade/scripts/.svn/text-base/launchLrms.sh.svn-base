#!/bin/bash
#set -x
host=$1
for i in `cat host.lst`
do
host=`echo $i | cut -f1 -d":" `
user=`echo $i | cut -f2 -d":" `
nfs=`echo $i | cut -f3 -d":" `
grm=`echo $i | cut -f4 -d":" `

if [ "`echo $i | grep \"#\" `" != "" ]
then 
#echo Desprezando $i # Linha com comentários 
continue
fi

echo Launching lrm at $host with $user
ssh -fn $user@$host "killall -9 LrmLauncher" >& /dev/null
ssh -fn $user@$host "~/integrade/trunk/integrade/startservices.sh lrm" >& /tmp/$host-lrm.out

done
