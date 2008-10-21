#ifndef BspPut_HPP
#define BspPut_HPP


#include <cstring>
#include <iostream> //FIXME: for debugging only
#include <vector>
#include <cassert>

using namespace std;

  /**
    BspPut - Represents a pending bsp_put/bsp_get request.

    When a BSP task issues a bsp_put/bsp_get request to another task, the request is sent
    immediately, byt only becomes effective at the end of the superstep. BspPut
    holds the bsp_put/bsp_get details until the end of the superstep.

    @author Andrei Goldchleger
    @date November, 2003

  */
  class BspPut{

    private:

      int logicAddr_; /**< The logic address of the DRMA operation. It is mapped to a physical
                          memory address (@see Registration.hpp) */
      int offset_;   /**< Offset from the destination address. Operation(read or write)
                          will start physAddr + offset
                     */
      int nBytes_;   /**< Number of bytes to be written */
      int superstep_;/**< The superstep in which the corresponding bsp_put request was
                          issued
                     */

      vector<unsigned char> memArea_; /**< Contents to be written at dst*/

      int srcPid_;

      char arch_;

    public:

      /**
        Sets the arch of the memory area.
      */
      BspPut & arch(const char & aArch){
        arch_ = aArch;
        return (*this);
      }

      /**
        Sets the pid of the source process.
      */
      BspPut & srcPid(const int & aSrcPid){
        srcPid_ = aSrcPid;
        return (*this);
      }

      /**
        Sets the logic address of a recently created BspPut object (Named Parameter Idiom).
      */
      BspPut & logicAddr(const int & aLogicAddr){
        logicAddr_ = aLogicAddr;
        return (*this);
      }

      /**
        Sets the offset of a recently created BspPut object (Named Parameter Idiom).
      */
      BspPut & offset(const int & aOffset){
        offset_ = aOffset;
        return (*this);
      }

      /**
        Sets the number of bytes of a recently created BspPut object (Named Parameter Idiom)..
      */
      BspPut & nBytes(const int & aNBytes){
        nBytes_ = aNBytes;
        return (*this);
      }

      /**
        Sets the superstep of a recently created BspPut object (Named Parameter Idiom).
      */
      BspPut &  superstep(const int & aSuperstep){
        superstep_ = aSuperstep;
        return (*this);
      }

      //Getters---------------------------------------------------------------------

      /**
        Returns the arch of the memory area.
      */
      char arch() const{ return arch_; }

      /**
        Returns the pid of the source process.
      */
      int srcPid() const{ return srcPid_; }

      /**
        Returns the logic address of a BspPut object.
      */
      int logicAddr() const{ return logicAddr_; }

      /**
        Returns the offset of a BspPut object.
      */
      int offset() const{ return offset_; }

      /**
        Returns the number of bytes destination of a BspPut object.
      */
      int nBytes() const{ return nBytes_; }

      /**
        Returns the superstep of a BspPut object.
      */
      int superstep() const{ return superstep_; }

      /**
        Returns a pointer to the memory contents of a BspPut object.
      */
      const unsigned char * memArea() const{ return &memArea_[0]; }

      //Other Methods---------------------------------------------------------------

      /**
        Copies a memory area into the BspPut object that will be used to make
        a remote bsp_put. nBytes will be copied from src to memArea.

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

        cerr << "logicAddr: " << logicAddr_ << endl
             << "offset: " << offset_ << endl
             << "nBytes: " << nBytes_ << endl
             << "superstep: " << superstep_ << endl
             << "memArea: ";
       if(dumpMem){

          for(int i = 0; i < nBytes_; i++){


          cerr << (int) memArea_[i];
          cerr << " * ";
         }
        cerr << endl;
       }

    }

  };

#endif//BspPut_HPP





