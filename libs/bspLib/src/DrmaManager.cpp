#include "DrmaManager.hpp"
#include "BaseProcess.hpp"
#include "BspProxyStubPool.hpp"
#include "NoSuchRegistrationException.hpp"
#include "InvalidBoundsOnDrmaOperationException.hpp"

#include "DrmaManager.hpp"

#include<iostream>


  DrmaManager::DrmaManager(BspProxyStubPool  * stubPool, DataConverters *dataConverter):
    stubPool_(stubPool), dataConverter_(dataConverter) {

      dstArch = dataConverter->getArchitecture();
      pthread_mutex_init(&pendingRegistrationsLock, NULL);
      pthread_mutex_init(&pendingDeregistrationsLock, NULL);
      pthread_mutex_init(&pendingPutsLock, NULL);
      pthread_mutex_init(&pendingGetRequestsLock, NULL);
      pthread_mutex_init(&pendingGetRepliesLock, NULL);

  }

  //***********************************************************************
  //"Pending" Methods------------------------------------------------------
  //***********************************************************************

  //-----------------------------------------------------------------------
  void DrmaManager::addPendingRegistration(const void * ptr, int size, unsigned long type){

    pthread_mutex_lock(&pendingRegistrationsLock);
      Registration reg(ptr, size, baseProcess_->superstep(), type);
      pendingRegistrations.push_back(reg);
    pthread_mutex_unlock(&pendingRegistrationsLock);

  }

  //-----------------------------------------------------------------------
  void DrmaManager::addPendingDeregistration(const void * ptr){

    pthread_mutex_lock(&pendingDeregistrationsLock);
      pendingDeregistrations.push_back(Deregistration(ptr, baseProcess_->superstep()));
    pthread_mutex_unlock(&pendingDeregistrationsLock);
  }

  //-----------------------------------------------------------------------
  void DrmaManager::addPendingPut(const BspPut & bspPut){

    pthread_mutex_lock(&pendingPutsLock);
      pendingPuts.push_back(bspPut);
    pthread_mutex_unlock(&pendingPutsLock);
  }

  //-----------------------------------------------------------------------
  void DrmaManager::addPendingGetRequest(const BspGetRequest & request){

    pthread_mutex_lock(&pendingGetRequestsLock);
      pendingGetRequests.push_back(request);
    pthread_mutex_unlock(&pendingGetRequestsLock);
  }

  //-----------------------------------------------------------------------
  void DrmaManager::addPendingGetReply(const BspGetReply & reply){

    pthread_mutex_lock(&pendingGetRepliesLock);
      pendingGetReplies.push_back(reply);
      pendingGetRepliesCount.dec();
      if (pendingGetRepliesCount.value() == 0)
        noPendingGetReplies.signal();
    pthread_mutex_unlock(&pendingGetRepliesLock);
  }
  
  //***********************************************************************
  //"Commit" Methods-------------------------------------------------------
  //***********************************************************************

  //-----------------------------------------------------------------------
  void DrmaManager::deregister(Deregistration & dereg){

    std::vector<Registration>::reverse_iterator it = registrations.rbegin();
    while(it != registrations.rend()){
      if ((*it).ptr() == dereg.ptr()){
         vector<Registration>::iterator puf = it.base();//Thanks to Wagner
         --puf;
          registrations.erase(puf);
        return;
      }
      else{
       it++;
      }
    }
    if(it == registrations.rend()){
      cerr << "[ERROR] DrmaManager::deregister->Trying to deregister unregistered address: "
           << dereg.ptr() << endl << endl;
      throw NoSuchRegistrationException();
    }
  }

  //-----------------------------------------------------------------------
  void DrmaManager::commitRegistrations(){

    pthread_mutex_lock(&pendingRegistrationsLock);

      vector<Registration>::iterator it = pendingRegistrations.begin();
      vector<Registration> temp;
      for ( ;it != pendingRegistrations.end(); it++){
        if((*it).superstep() == baseProcess_->superstep()){
          registrations.push_back(*it);
        }
        else
          temp.push_back(*it);

      }
      pendingRegistrations.swap(temp);

    pthread_mutex_unlock(&pendingRegistrationsLock);
  }

  //-----------------------------------------------------------------------
  void DrmaManager::commitDeregistrations(){
    //FIXME: Acho que tenho de deregistrar de tras pra frente
    pthread_mutex_lock(&pendingDeregistrationsLock);

      vector<Deregistration>::iterator it = pendingDeregistrations.begin();
      vector<Deregistration> temp;
      for ( ;it != pendingDeregistrations.end(); it++)
        if((*it).superstep() == baseProcess_->superstep())
          deregister(*it);
        else
          temp.push_back(*it);
      pendingDeregistrations.swap(temp);

    pthread_mutex_unlock(&pendingDeregistrationsLock);

  }

  //-----------------------------------------------------------------------
  void DrmaManager::commitPendingPuts(){
   
    pthread_mutex_lock(&pendingPutsLock);
        
    vector<BspPut>::iterator it = pendingPuts.begin();
    vector<BspPut> temp;
    for ( ;it != pendingPuts.end(); it++){
      if ((*it).superstep() == baseProcess_->superstep()) {
        Registration & reg = registrations[(*it).logicAddr()];
        //FIXME: check bounds
        unsigned char * destPtr = (unsigned char *) reg.ptr();
        /** Portable communication */
        char srcArch = (*it).arch();
        cerr << "[RYC] DrmaManager::bspPut-> dstArch=" << (int)dstArch << " srcArch=" << (int)srcArch  << " | type=" << reg.type() << endl;

        if (srcArch == dstArch)
          memcpy(destPtr + (*it).offset(), (*it).memArea(), (*it).nBytes());
        else {
          dataConverter_->setDataConverter (srcArch);  
          dataConverter_->readDataVector (destPtr + (*it).offset(), (*it).memArea(), (*it).nBytes(), reg.type());
        }
      }
      else
        temp.push_back(*it);
      }
      pendingPuts.swap(temp);

      pthread_mutex_unlock(&pendingPutsLock);

  }

  //-----------------------------------------------------------------------
  void DrmaManager::commitPendingGetRequests(){


    pthread_mutex_lock(&pendingGetRequestsLock);

      vector<BspGetRequest>::iterator it = pendingGetRequests.begin();
      vector<BspGetRequest> temp;
      for ( ;it != pendingGetRequests.end(); it++){
        if ((*it).superstep() == baseProcess_->superstep()){

          Registration & reg = registrations[(*it).logicSrc()];
          unsigned char * srcPtr = (unsigned char *) reg.ptr();
          BspGetReply reply;
          reply.nBytes((*it).nBytes())
	       .type(reg.type())
    	       .arch(dstArch)
	       .superstep((*it).superstep())
               .dst((*it).dst());
          reply.writeInMem(srcPtr + (*it).offset());
          if((*it).pid() == baseProcess_->getMyPid())
            addPendingGetReply(reply);
          else
            stubPool_->bspGetReply((*it).pid(), reply);
        }
        else{
          temp.push_back(*it);
        }
      }
      pendingGetRequests.swap(temp);

    pthread_mutex_unlock(&pendingGetRequestsLock);

  }

  //-----------------------------------------------------------------------
  void DrmaManager::commitPendingGetReplies(){

    /** ---------------- RYC ------------- */
    //cerr << "[RYC] DrmaManager::bspPut->commitPendingGetReplies " << endl;

    pthread_mutex_lock(&pendingGetRepliesLock);
    vector<BspGetReply>::iterator it = pendingGetReplies.begin();
    vector<BspGetReply> temp;
    for ( ;it != pendingGetReplies.end(); it++){

      if ((*it).superstep() == baseProcess_->superstep()){

	/** Portable communication */
	char srcArch = (*it).arch();
	cerr << "[RYC] DrmaManager::bspPut->dstArch=" << (int)dstArch << " srcArch=" << (int)srcArch 
	     << " | type=" << (*it).type() << endl;

	if (srcArch == dstArch)
	  memcpy((*it).dst(),(*it).memArea() , (*it).nBytes());
	else {
	  dataConverter_->setDataConverter (srcArch);  
	  dataConverter_->readDataVector ((*it).dst(), (*it).memArea(), (*it).nBytes(), (*it).type());
	}

      }
      else{
        temp.push_back(*it);
      }
    }
    pendingGetReplies.swap(temp);
    pthread_mutex_unlock(&pendingGetRepliesLock);

    /** ---------------- RYC ------------- */
    //    cerr << "[RYC] DrmaManager::bspPut->commitPendingGetReplies OK" << endl;

  }



    //-----------------------------------------------------------------------

  int DrmaManager::findLastRegistration(const void * ptr){
    std::vector<Registration>::reverse_iterator it = registrations.rbegin();
    int index = registrations.size() - 1;
    while(it != registrations.rend()){
      if ((*it).ptr() == ptr)
        return index;
      else{
       it++;
       index--;
      }
    }
    cerr << "[ERROR] DrmaManager::findLastRegistration->No such registered address: "
         << ptr << endl ;
    throw NoSuchRegistrationException();
  }

  //-----------------------------------------------------------------------
  Registration & DrmaManager::getRegistration(int pos){//FIXME: Make it const

    return registrations[pos];

  }

  //-----------------------------------------------------------------------
  void DrmaManager::processPendingOperations(){

    //FIXME: Check if ordering is correct, according to the BSP model
    commitPendingGetRequests();
    while (pendingGetRepliesCount.value() > 0){//FIXME: Better to use a semaphore?
      noPendingGetReplies.wait();
    }
    pendingGetRepliesCount.reset();

    commitPendingGetReplies();
    commitPendingPuts();
    commitRegistrations();
    commitDeregistrations();
  }

  //-----------------------------------------------------------------------
  void DrmaManager::bspPut(int pid, const void * src, void * dst, int offset, int nBytes){

    assert(0 <= pid < baseProcess_->getTotalNumProcs());
    int logicDst = findLastRegistration(dst);
    Registration & reg = getRegistration(logicDst);
    if (offset + nBytes > reg.size()){
      cerr << "[ERROR] DrmaManager::BspPut->Invalid bounds on operation" << endl;
      throw InvalidBoundsOnDrmaOperationException();
    }
    BspPut bspPut;
    bspPut.logicAddr(logicDst)
          .offset(offset)
          .nBytes(nBytes)
          .superstep(baseProcess_->superstep())
          .arch(dstArch);
    bspPut.writeInMem(src);
    if (pid != baseProcess_->getMyPid()){
      stubPool_->bspPut(pid, bspPut);
    }
    else{
     addPendingPut(bspPut);
    }

    /** ---------------- RYC ------------- */
    //    cerr << "[RYC] DrmaManager::bspPut->sendingData OK" << endl;

  }

    //-----------------------------------------------------------------------

  void DrmaManager::bspGet(int pid,const  void * src, int offset, void * dst, int nBytes){

    assert(0 <= pid < baseProcess_->getTotalNumProcs());
    int logicSrc = findLastRegistration(src);
    Registration & reg = getRegistration(logicSrc);
    if (offset + nBytes > reg.size()){
      cerr << "[ERROR] DrmaManager::BspGet->Invalid bounds on operation" << endl;
      throw InvalidBoundsOnDrmaOperationException();
    }
    BspGetRequest request;
    request.pid(baseProcess_->getMyPid())
           .logicSrc(logicSrc)
           .offset(offset)
           .nBytes(nBytes)
           .superstep(baseProcess_->superstep())
           .dst(dst); // Put in a local vector (Care with superstep number)
                      // Use a logical number and dual vector with a certain size

    /** ---------------- RYC ------------- */
    //    cerr << "[RYC] DrmaManager::bspGet->Address:" << dst << endl;

    if(pid != baseProcess_->getMyPid())
      stubPool_->bspGetRequest(pid, request);
    else
      addPendingGetRequest(request);
    pendingGetRepliesCount.inc();
  }




