#ifndef BspGetReply_HPP
#define BspGetReply_HPP

#include <cassert>

  /**
    BspGetReply - Contaais data relevant to a bsp_get reply

    @author Andrei Goldchleger
    @date January, 2004
  */
  class BspGetReply{

    private:

      int nBytes_;        /**< Number of bytes read */
      int superstep_;     /**< The superstep in which the corresponding bsp_get request was
                               issued
                          */
      void * dst_;        /**< Physical destination address of the bsp_get request */
      vector<unsigned char> memArea_; /**< Contents to be written at dst */

      short type_;
      char arch_;


    public:

      /**
        Sets the arch of the memory area.
      */
      BspGetReply & arch(const char & aArch){
        arch_ = aArch;
        return (*this);
      }

      /**
        Sets the type of the memory area.
      */
      BspGetReply & type(const short & aType){
        type_ = aType;
        return (*this);
      }

      /**
        Sets the destination address of a recently created BspGetReply object (Named Parameter Idiom)..
      */
      BspGetReply & dst(void * aDst){
        dst_ = aDst;
        return (*this);
      }


      /**
        Sets the number of bytes of a recently created BspGetReply object (Named Parameter Idiom)..
      */
      BspGetReply & nBytes(const int & aNBytes){
        nBytes_ = aNBytes;
        return (*this);
      }

      /**
        Sets the superstep of a recently created BspGetReply object (Named Parameter Idiom).
      */
      BspGetReply &  superstep(const int & aSuperstep){
        superstep_ = aSuperstep;
        return (*this);
      }

      //Getters---------------------------------------------------------------------

      /**
        Returns the arch of the memory area.
      */
      char arch() const{ return arch_; }

      /**
        Returns the type of the memory area.
      */
      short type() const{ return type_; }

      /**
        Returns the destination address of a BspGetReply object.
      */
      void * dst() const{return dst_;}

      /**
        Returns the number of bytes destination of a BspGetReply object.
      */
      int nBytes() const{ return nBytes_; }

      /**
        Returns the superstep of a BspGetReply object.
      */
      int superstep() const{ return superstep_; }

      /**
        Returns a pointer to the memory contents of a BspGetReply object.
      */
      const unsigned char * memArea() const{ return &memArea_[0]; }

      //Other Methods---------------------------------------------------------------

      /**
        Copies a memory area into the BspGetReply object that will be then copied
        to <i>dst</i> at the requesting task. nBytes will be copied from src to memArea.

        @param src - address of the memory source to be copied into memArea

      */
      void writeInMem(const void * src){

        assert(nBytes_ >= 0);
        memArea_.resize(nBytes_);
        memcpy(&memArea_[0], src, nBytes_);

      }


      /**
        Dumps the object's contents to stderr. Used only for debugging.

        @param dumpMem - if true, dumps the memory contents.

      */
      void dump (bool dumpMem) const{

        cerr << "dst: " << dst_ << endl
             << "nBytes: " << nBytes_ << endl
             << "superstep: " << superstep_ << endl
             << "memArea: ";
        if (dumpMem){
          for(int i = 0; i < nBytes_; i++){
            cerr << (int) memArea_[i];
            cerr << " * ";
          }
          cerr << endl;
        }
      }

  };//class

#endif//BspGetReply_HPP
