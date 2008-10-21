#!/bin/bash
set -x
javac LsmImpl.java
javah -o LsmImplJ.hpp -jni -classpath . LsmImpl 

g++  -I../../../ -I /usr/local/j2sdk1.4.2_03/include/ -I /usr/local/j2sdk1.4.2_03/include/linux  -c -o objects/LsmImplJ.o LsmImplJ.cpp
g++ -static -I../../../ -idirafter /usr/local/lua-5.0-plus/include/  -Wall -pedantic -ansi -pthread -O2  -fPIC -shared   -g -O0 -fno-inline -o liblsm.so -I /usr/local/j2sdk1.4.2_03/include/linux -I /usr/local/j2sdk1.4.2_03/include/ -fPIC   -g -O0 -fno-inline  objects/LsmImplJ.o objects/LsmImpl.o objects/TokenGSS.o objects/LsmSkeleton.o objects/GsmStub.o ../../../utils/c++/objects/CharArrayArrayBeautifier.o ../../../utils/c++/objects/StringTokenizer.o ../../../utils/c++/objects/Config.o ../../../utils/c++/objects/LuaUtils.o ../../../utils/c++/objects/O2Utils.o ../../../utils/c++/objects/NetInfo.o ../../../utils/c++/objects/Condition.o -L/usr/local/lua-5.0-plus/lib/ -Wl,-rpath,/usr/local/lua-5.0-plus/lib/ -lm -llualib -llua -lpthread -L/usr/local/lib -lkrb5 -lk5crypto -lcom_err -lgssapi_krb5 
cp LsmImpl.java  ../asctGui/src/
