SHARED_DIR=.


all: lsmJ lsmC

lsmJ:
	cd ${SHARED_DIR}/java/; ant;
lsmC:
	cd ${SHARED_DIR}/C++/; make DEBUG='false';

clean:

	cd ${SHARED_DIR}/java; ant clean;
	cd ${SHARED_DIR}/C++; make clean;
