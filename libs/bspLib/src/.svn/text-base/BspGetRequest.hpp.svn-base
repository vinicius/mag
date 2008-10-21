#ifndef BspGetRequest_HPP
#define BspGetRequest_HPP

  /**
    BspGetRequest - Contais information related to a bsp_get request

    @author Andrei Goldchleger
    @date January, 2004
  */
  class BspGetRequest{


    private:

      int pid_;       /**< The BSP process ID of the requesting task*/

      int logicSrc_;  /**< Logic addres from where data will be copied*/

      int offset_;    /**< Offset from the destination address. bsp_put will start writing
                          to memory at dst + offset
                      */
      int nBytes_;    /**< Number of bytes to be read */
      int superstep_; /**< The superstep in which the corresponding bsp_get request was
                          issued
                      */
      void * dst_;    /**< Physical destination address of the bsp_get request */

    public:

      /**
        Sets the pid of a recently created BspGetRequest object (Named Parameter Idiom).
      */
      BspGetRequest & pid(const int & aPid){
        pid_ = aPid;
        return (*this);
      }

      /**
        Sets the logicSrc of a recently created BspGetRequest object (Named Parameter Idiom).
      */
      BspGetRequest & logicSrc(const int & aLogicSrc){
        logicSrc_ = aLogicSrc;
        return (*this);
      }

      /**
        Sets the dst of a recently created BspGetRequest object (Named Parameter Idiom).
      */
      BspGetRequest & dst(void * aDst){
        dst_ = aDst;
        return (*this);
      }

      /**
        Sets the offset of a recently created BspGetRequest object (Named Parameter Idiom).
      */
      BspGetRequest & offset(const int & aOffset){
        offset_ = aOffset;
        return (*this);
      }

      /**
        Sets the number of bytes of a recently created BspGetRequest object (Named Parameter Idiom)..
      */
      BspGetRequest & nBytes(const int & aNBytes){
        nBytes_ = aNBytes;
        return (*this);
      }

      /**
        Sets the superstep of a recently created BspGetRequest object (Named Parameter Idiom).
      */
      BspGetRequest &  superstep(const int & aSuperstep){
        superstep_ = aSuperstep;
        return (*this);
      }

      //Getters---------------------------------------------------------------------

      /**
        Returns the pid of a BspGetRequest object.
      */
      int pid() const{ return pid_; }

      /**
        Returns the logicSrc of a BspGetRequest object.
      */
      int logicSrc() const{ return logicSrc_; }

      /**
        Returns the dst of a BspGetRequest object.
      */
      void * dst() const{return dst_;}

      /**
        Returns the offset of a BspGetRequest object.
      */
      int offset() const{ return offset_; }

      /**
        Returns the number of bytes destination of a BspGetRequest object.
      */
      int nBytes() const{ return nBytes_; }

      /**
        Returns the superstep of a BspGetRequest object.
      */
      int superstep() const{ return superstep_; }


      //Other Methods---------------------------------------------------------------


      /**
        Dumps the object's contents to stderr. Used only for debugging.

        @param dumpMem - if true, dumps the memory contents.

      */
//       void dump () const{
//
//         cerr << "logicSrc: " << logicSrc_ << endl
//              << "offset: " << offset_ << endl
//              << "nBytes: " << nBytes_ << endl
//              << "superStep: " << superStep_ << endl
//              << "memArea: ";
//       }

  };

#endif//BspGetRequest_HPP
