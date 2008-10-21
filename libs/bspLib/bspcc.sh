#!/bin/bash
bspLib=/home/pos/vinicius/integrade-mag/libs/bspLib/
libLuaPath=/home/pos/vinicius/programas/lua/lib/
printHelp(){
  echo "bspcc - compiles a c program that uses the InteGrade BSP library"
  echo "usage: bspcc <compiler_parameters>"


}

if [ "${#}" -eq "0" ]; then
  printHelp;
fi


gcc   -I${bspLib}/include \
      -L${bspLib}/lib \
      -L${libLuaPath} \
      -Wl,-rpath,${bspLib}/lib \
      -Wl,-rpath,${libLuauaPath} \
      -lm -llualib -llua -lpthread -lbsp \
      "$@" 

