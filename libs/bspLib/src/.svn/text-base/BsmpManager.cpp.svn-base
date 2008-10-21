#include "BsmpManager.hpp"
#include "BaseProcess.hpp"
#include "BspProxyStubPool.hpp"

#include<iostream>


  BsmpManager::BsmpManager(BspProxyStubPool * stubPool, DataConverters *dataConverter):
    stubPool_( stubPool), dataConverter_(dataConverter) {

      pthread_mutex_init(&messageQueueLock_, NULL);
      //pthread_mutex_init(&MovesLock, NULL);
  }


  //-----------------------------------------------------------------------
  void BsmpManager::addSend(const BspSend & bspSend){
    pthread_mutex_lock(&messageQueueLock_);
      messageQueue_.push_back(bspSend);
    pthread_mutex_unlock(&messageQueueLock_);

  }

  //-----------------------------------------------------------------------
  void BsmpManager::commitPendingSends(){

    //GuardedVariable <int> tmp; nao entendi o swap
    //cerr << "BsmpManager::commitSends()"  << endl;
    //FIXED: Precisa usar container TMP
    pthread_mutex_lock(&messageQueueLock_);
      vector<BspSend>::iterator it = messageQueue_.begin();
      vector<BspSend> temp;
      for ( ;it != messageQueue_.end(); it++){
        if ((*it).superstep() >= baseProcess_->superstep()){
          temp.push_back(*it);
        }
//        else{
    //     cout << "BsmpManager::commitPendingSends-> existe mensagens na fila do superpasso: " << (*it).superstep() << endl;
  //      }
      }
      messageQueue_.swap(temp);

    pthread_mutex_unlock(&messageQueueLock_);
    //FIXED: Nao entendi. porque swap dos valores?
    //tmp.set(last_tag_size_.value());
    //last_tag_size_.set(tag_size_.value());
    //tag_size_.set(tmp.value());
    currentTagSize_.set(nextTagSize_.value());


  }


  //-----------------------------------------------------------------------
  void BsmpManager::processPendingOperations(){
//    cerr << "CLIENT_SIDE::BsmpManager::processPendingOperations"  << endl;

    commitPendingSends();
  }


  //-----------------------------------------------------------------------
  void BsmpManager::bspSend(int pid,const void *tag, const void * payload,int payload_bytes){

    assert(0 <= pid < baseProcess_->getTotalNumProcs());

    BspSend bspSend;
    bspSend.nBytes(payload_bytes).superstep(baseProcess_->superstep()).tagSize(currentTagSize_.value());
    bspSend.writeInMem(payload);
    bspSend.writeTag(tag);
    //cerr << "BsmpManager::bspSend->sending message to task: " << pid << endl;
    //bspSend.dump(true);
    if (pid != baseProcess_->getMyPid())
      stubPool_->bspSend(pid, bspSend);
    else
     addSend(bspSend);

  }


  //-----------------------------------------------------------------------
  /**
   * The operation bsp_move copies the payload of the first  mes-
     sage  in  the  buffer into payload, and removes that message
     from the buffer. recpetion_bytes specifies the size  of  the
     reception  area  where  the  payload will be copied into. At
     most reception_nbytes will be copied into payload.

     Note that bsp_move serves to flush the corresponding message
     from the buffer, while bsp_get_tag(3) does not.  This allows
     a program to get the tag of a message (as well as  the  pay-
     load size in bytes) before obtaining the payload of the mes-
     sage.  It does, however, require that even if a program only
     uses  the  fixed-length tag of incoming messages the program
     must call bsp_move to get successive message tags.
     */
  void BsmpManager::bspMove(void * payload, int reception_bytes){



    //int * status;
    //int * tag;
    //bspGetTag(status,tag);
    //if(* status == -1) return;
    //FIXED: estava usando outro lock aqui, mas eh um lock por container
    bool read = false;
    pthread_mutex_lock(&messageQueueLock_);
    unsigned int queueSize = messageQueue_.size();
    vector<BspSend>::iterator it = messageQueue_.begin();
    for(; it != messageQueue_.end(); it++){
      if ((*it).superstep() == baseProcess_->superstep() - 1){//FIXED: -1 pq eh previous superstep
       //FIXED: e se o tamanho da mensagem for != de reception_bytes
         memcpy(payload,(*it).payload() , (reception_bytes < (*it).nBytes())?reception_bytes:(*it).nBytes());
         messageQueue_.erase(it);
         read = true;
        break;
      }
    }
    if(!read)
      cerr << "BsmpManager::bspMove->tentei ler da fila, mas nao achei nada"  << endl;
    assert(messageQueue_.size() == (queueSize - 1));

    pthread_mutex_unlock(&messageQueueLock_);
  }
  //-----------------------------------------------------------------------
  /**
   * To receive a message, the user  should  use  the  procedures
     bsp_get_tag  and  bsp_move(3).  The  effect of the operation
     bsp_get_tag is that the argument status  has  the  value  -1
     assigned  to  it  if  system  buffer is empty.  Otherwise it
     becomes the length of the payload of the  first  message  in
     the buffer. This length can be used to allocate an appropri-
     ately sized data structure for  copying  the  payload  using
     bsp_move(3).  The  memory  referenced by tag is unchanged if
     the system buffer is empty. Otherwise it is assigned the tag
     of the first message in the buffer.
     */
  void BsmpManager::bspGetTag(int * status, void * tag){
    pthread_mutex_lock(&messageQueueLock_);

      if(messageQueue_.empty()) {//FIXED: replaced temp with query
        *status = -1;
        pthread_mutex_unlock(&messageQueueLock_);//FIXED!: podia sair e trancar o container para sempre
        return;
      }
      BspSend & ref = messageQueue_.front();//FIXED: local para ref
      *status = ref.nBytes();
      memcpy(tag, ref.tag(), currentTagSize_.value());//FIXED!
      //tag =  ref.tag();
      //cerr << "BsmpManager::bspGetTag->Tag addr in funct: " << (int) ref.tag() << endl;
      //cerr << "BsmpManager::bspGetTag->Tag addr in funct: " << (int) tag << endl;

    pthread_mutex_unlock(&messageQueueLock_);
  }

  //-----------------------------------------------------------------------
  /**
   *
	The  function  bsp_qsize(3)  is  an  enquiry  function  that
     returns  the  number of messages that were sent to this pro-
     cess in the previous superstep and have not  yet  been  con-
     sumed  by  a  bsp_move(3). Before any message is consumed by
     bsp_move(3), the total  number  of  messages  received  will
     match those sent by any bsp_send(3) operations in the previ-
     ous superstep.  function also returns the  accumulated  size
     of  all the payloads of the unconsumed messages. This opera-
     tion is intended to help the user to allocate  an  appropri-
     ately  sized  data  structure  to hold all the messages that
     were sent to a process during a superstep.
   *
   *
   */
  void  BsmpManager::bspQSize(int *packets, int *accum_nbytes){

    pthread_mutex_lock(&messageQueueLock_);
      *packets = 0; //FIXED: adicionei
      *accum_nbytes = 0;//FIXED: adicionei
      vector<BspSend>::iterator it = messageQueue_.begin();

      for ( ;it != messageQueue_.end(); it++){

        if ((*it).superstep() == baseProcess_->superstep() - 1){//FIXED: -1 pq eh previous superstep
          *packets = *packets + 1;//FIXED: dereferenciei(ARGH) ponteiros
          *accum_nbytes = *accum_nbytes + (*it).nBytes();//FIXED: dereferenciei(ARGH) ponteiros
        }

      }
    pthread_mutex_unlock(&messageQueueLock_);
  }

  
  //-----------------------------------------------------------------------
  /**
   * Allowing the user to set the tag size  enables  the  use  of
     tags that are appropriate for the communication requirements
     of each superstep.  This should be  particularly  useful  in
     the development of subroutines either in user programs or in
     libraries.

     The procedure must be called collectively by all  processes.
     A  change  in  tag size takes effect in the following super-
     step; it then becomes valid.

     The value of tag_bytes on entry to the procedure,  specifies
     the size of the fixed-length portion of every message in the
     current and succeeding supersteps; the default tag  size  is
     zero.  On return from the procedure, tag_bytes is changed to
     reflect the previous value of the tag size.
   * 
   * 
   */
  void  BsmpManager::bspSetTagSize(int * tag_bytes){

//	  	last_tag_size_.set(*tag_bytes);	//FIXED: usando variaveis renomeadas
    nextTagSize_.set(*tag_bytes);
    *tag_bytes = currentTagSize_.value();//FIXED: adicionei. combina com a descr do C_EXAMPLES


  }




