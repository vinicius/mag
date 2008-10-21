#!/bin/sh
# Environment Variables
export IG_HOME=/usr/local/integrade/
export ANT_HOME=/home/pub/ant-1.6.5/
export JACORB_HOME=/home/pub/JacORB/
export JAVA_HOME=/home/pub/jdk/
export JFREECHART_HOME=/home/pub/jfreechart-0.9.14/
export PATH=${JACORB_HOME}/bin:${JAVA_HOME}/bin:${ANT_HOME}/bin:$PATH
export NS_PORT=47039
case "$1" in
    "servers")
       echo "Stoping servers"

       pid=`ps aux | grep "TradingService" | grep -v "grep" | awk '{print $2}'`
       echo "Stoping Trader (${pid})"
       kill -9 ${pid} 2>/dev/null  1>/dev/null

       pid=`ps aux | grep "GrmLauncher" | grep -v "grep" | awk '{print $2}'`
       echo "Stoping Grm (${pid})"
       kill -9 ${pid} 2>/dev/null 1>/dev/null

       pid=`ps aux | grep "ApplicationRepository" | grep -v "grep" | awk '{print $2}'`
       echo "Stoping Application Repository (${pid})"
       kill -9 ${pid}  2>/dev/null 1>/dev/null

       pid=`ps aux | grep "NameServer" | grep -v "grep" | awk '{print $2}'`
       echo "Stoping Name Server (${pid})"
       kill -9 ${pid}  2>/dev/null 1>/dev/null


       echo Done!
       shift
    ;;
    "lrm")

       pid=`ps aux | grep "LrmLauncher" | grep -v "grep" | awk '{print $2}'`
       echo "Stoping Lrm (${pid})"
       if [ "$pid" != "" ]
       then
                kill -9 ${pid}
                echo Done!
       else
                echo failed!
       fi

    ;;
    *)
    echo " servers     -> Initializes server side modules(GRM and Application Repository)"
    echo " lrm         -> Initializes the Local Resorce Manager"
    ;;
esac
