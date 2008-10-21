DOC_DIR=doc
LRM_PATH=resourceProviders/lrm
MAG_PATH=mag/
LUPA_PATH=resourceProviders/lupa
GRM_PATH=clusterManagement/grm
ARSM_PATH=clusterManagement/arsm
ARSC_PATH=shared/arsc
APPREPOS_PATH=clusterManagement/applicationRepository
CLUSTERVIEW_PATH=tools/clusterView
ASCTGUI_PATH=tools/asct
DATACONVERTERS_PATH=libs/dataConverters
BSPLIB_PATH=libs/bspLib
CHECKPOINTING_PATH=libs/checkpointing


export IG_HOME=/home/pos/vinicius/integrade-mag/
export ANT_HOME=/home/pos/vinicius/programas/ant/
export JACORB_HOME=/home/pos/vinicius/programas/JacORB-2.2.3/
export JAVA_HOME=/home/pos/vinicius/programas/java/
export JFREECHART_HOME=
export PATH+=${JACORB_HOME}/bin:${JAVA_HOME}/bin:${ANT_HOME}/bin

all: mag lupa lrm grm applicationRepository asctGui dataConverters bspLib checkpointing

all-security: arsc arsm all

clients: lupa lrm arsc clusterView asctGui dataConverters bspLib checkpointing

servers: arsc arsm grm applicationRepository dataConverters bspLib checkpointing

lupa:
	cd ${LUPA_PATH}; make DEBUG='false'; cd ../..;

lrm:
	cd ${LRM_PATH}; make DEBUG='false'; cd ../..;

asctGui:
	cd ${ASCTGUI_PATH}; ant; cd ../..; 
mag:    grm
	cd ${MAG_PATH}; ant; cd ..;
grm:    
	cd ${GRM_PATH}; ant; cd ../..; 
arsm:    
	cd ${ARSM_PATH}; ant; cd ../..; 
arsc:   
	cd ${ARSC_PATH}; make DEBUG=false ; cd ../..; 

applicationRepository: 
	cd ${APPREPOS_PATH}; ant; cd ../..; 

clusterView:       
	cd ${CLUSTERVIEW_PATH}; ant; cd ../..; 

dataConverters:
	cd ${DATACONVERTERS_PATH}; make DEBUG='false'; cd ../..; 

bspLib:
	cd ${BSPLIB_PATH}; make DEBUG='false'; cd ../..; 

checkpointing:
	cd ${CHECKPOINTING_PATH}; make DEBUG='false'; cd ../..; 

doc:
	doxygen Doxyfile;

    
clean:

	cd ${LUPA_PATH}; make clean; cd ../..; 
	cd ${LRM_PATH}; make clean; cd ../..; 
	cd ${MAG_PATH}; ant clean; cd ..;
	cd ${ASCTGUI_PATH}; ant clean; cd ../..; 
	cd ${GRM_PATH}; ant clean; cd ../..; 
	cd ${ARSM_PATH}; ant clean; cd ../..; 
	cd ${ARSC_PATH}; make clean; cd ../..; 
	cd ${APPREPOS_PATH}; ant clean; cd ../..; 
	cd ${CLUSTERVIEW_PATH}; ant clean; cd ../..; 
	cd ${BSPLIB_PATH}; make clean; cd ../..;
	cd ${CHECKPOINTING_PATH}; make clean; cd ../..;
	cd ${DATACONVERTERS_PATH}; make clean; cd ../..;

