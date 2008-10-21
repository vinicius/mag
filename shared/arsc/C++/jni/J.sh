#!/bin/bash
set -x
javac LsmImpl.java
javah -o LsmImplJ.hpp -jni -classpath . LsmImpl 

g++-3.3  -I../../../ -I $JAVA_HOME/include/ -I $JAVA_HOME/include/linux  -c -o objects/LsmImplJ.o LsmImplJ.cpp
g++-3.3 -static -I../../../ -idirafter /home/pos/vinicius/programas/lua/include/  -Wall -pedantic -ansi -pthread -O2  -fPIC -shared   -g -O0 -fno-inline -o liblsm.so -I $JAVA_HOME/include/linux -I $JAVA_HOME/include/ -fPIC   -g -O0 -fno-inline  objects/LsmImplJ.o objects/LsmImpl.o objects/TokenGSS.o objects/LsmSkeleton.o objects/GsmStub.o ../../../utils/c++/objects/CharArrayArrayBeautifier.o ../../../utils/c++/objects/StringTokenizer.o ../../../utils/c++/objects/Config.o ../../../utils/c++/objects/LuaUtils.o ../../../utils/c++/objects/O2Utils.o ../../../utils/c++/objects/NetInfo.o ../../../utils/c++/objects/Condition.o -L/home/pos/vinicius/programas/lua/lib/ -Wl,-rpath,/home/pos/vinicius/programas/lua/lib/ -lm -llualib -llua -lpthread -L/usr/local/lib -lkrb5 -lk5crypto -lcom_err -lgssapi_krb5 
cp LsmImpl.java  ../asctGui/src/
