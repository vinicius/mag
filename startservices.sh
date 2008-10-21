#startservices.sh - starts the various InteGrade modules
#Author: Jose de Ribamar Braga Pinheiro Junior
#Modified by: Andrei Goldchleger, Ricardo Luiz de Andrade Abrantes
#

#!/bin/bash
#set -x
wait=10
in=false
secureAppRepos=false
LRM_PRIORITY="-19"
# Environment Variables
export IG_HOME=/home/pos/vinicius/integrade-mag/
export ANT_HOME=/home/pos/vinicius/programas/ant/
export JACORB_HOME=/home/pos/vinicius/programas/JacORB-2.2.3/
export JAVA_HOME=/home/pos/vinicius/programas/java/
export JADE_HOME=/home/pos/vinicius/programas/jade.new/
export JFREECHART_HOME=
export PATH=${JACORB_HOME}/bin:${JAVA_HOME}/bin:${ANT_HOME}/bin:$PATH
export NS_PORT=47039

#For each command line parameter, executes the associated module
while [ "$1" !=  "" -o "$in" == "false" ]
do
   in=true
   case "$1" in
    "env")
    echo Done!
    shift
    ;;
    "servers")
      echo Initializing servers

		if [ "$GRM_PARENT_IOR" != "" ]
		then
			 echo "GRM will try to contact provided GRM as parent."
		elif [ "$GRM_PARENT_NAMING_SERVER_IOR" != "" ]
		then
			 echo "GRM will try to obtain a GRM from provided name server and make it his parent."
		else
			 echo "This will be a root GRM, there will be no parents."
		fi

      cd $JACORB_HOME/bin
      ./ns -DOAPort=$NS_PORT &
      cd $IG_HOME/clusterManagement/grm/launchTrader/
      ./settr &
      sleep $wait
      cd $IG_HOME/clusterManagement/grm/classes/
      ./runit &
      sleep $wait
      if [ ${secureAppRepos} = "true" ]
      then
	  cd $IG_HOME/clusterManagement/arsm/classes/
	  ./runit &
	  sleep $wait
      fi
      cd $IG_HOME/clusterManagement/applicationRepository/classes/
      ./runit &
      cd $IG_HOME
      ./runjade &
      shift
    ;;
    "lrm")
      echo Initializing agentHandler
      sleep $wait
      cd $IG_HOME/mag/classes
      ./runit &
	
      echo Initializing lrm
      sleep $wait
      cd $IG_HOME/resourceProviders/lrm
      nice "${LRM_PRIORITY}"  ./LrmLauncher  &
      shift
    ;;
    "asctGui")
      echo Initializing asctGui
      cd $IG_HOME/tools/asct/classes
      ./runit &
      sleep $wait
      shift
    ;;
    "clusterview")
      cd $IG_HOME/tools/clusterView/classes
      ./runit &
      sleep $wait
      shift
    ;;
    "all")
      shift
      echo Initializing all
      . $0 env
      $0 servers
      $0 clusterview
      $0 lrm
      $0 asctGui
    ;;
    *)
      echo " "
      echo "Usage: $0 <lrm> <asct> <asctGui> <servers> <clusterview> <env> <all>"
      echo
      echo "env         -> Sets environment variables. Execute with a leading 'dot' (e.g.) . $0"
      echo "servers     -> Initializes server side modules(GRM and Application Repository)"
      echo "clusterview -> Initializes the graphical resource monitor"
      echo "lrm         -> Initializes the Local Resorce Manager"
      echo "asctGui     -> Initializes the Graphical Application submittion and Control tool"
      echo "all         -> Initializes all modules"
      echo
      shift
   esac
done
