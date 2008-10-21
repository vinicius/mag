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

echo Killing lrm at $host with user $user
ssh $user@$host "killall -9 LrmLauncher" >& /dev/null
#ssh $user@$host "killall -9 CkpRepositoryLauncher" >& /dev/null

done
