#ifndef BspSend_HPP
#define BspSend_HPP


#include <cstring>

#include <iostream> //FIXME: for debugging only
#include <vector>
#include <cassert>

using namespace std;

  /**
    BspSend - Represents a pending bsp_send request.

    When a BSP task issues a bsp_put/bsp_get request to another task, the request is sent
    immediately, byt only becomes effective at the end of the superstep. BspSend
    holds the bsp_put/bsp_get details until the end of the superstep.

    @author Carlos Queiroz
    @date August, 2004

  */

  class BspSend {

    private:

      vector<unsigned char> tag_;//de int pra isso
      int nBytes_;
      int superstep_;
      vector<unsigned char> payload_;
      int tagSize_;


    public:

      /**
        Sets the payload length
      */
      BspSend & nBytes(const int & aNBytes){
        nBytes_ = aNBytes;
        return (*this);
      }

      BspSend & tagSize(const int & aTagSize){
        tagSize_ = aTagSize;
        return (*this);
      }


      /**
        Sets the superstep of a recently created BspSend object (Named Parameter Idiom).
      */
      BspSend &  superstep(const int & aSuperstep){
        superstep_ = aSuperstep;
        return (*this);
      }

      //Getters---------------------------------------------------------------------

      /**
        Returns the tag size.
      */
      unsigned char *  tag(){//FIXED: de int pra isso
      	return &tag_[0];
      }


      /**
        Returns the payload length
      */
      int nBytes() const{
      	return nBytes_;
      }

      /**
        Returns the superstep of a BspSend object.
      */
      int superstep() const{
      	return superstep_;
      }

      /**
        Returns payload content.
      */
      const unsigned char * payload() const{
      	return &payload_[0];
      }


      /**
        Dumps the object's contents to stderr. Used only for debugging.

      */
      void dump (bool dumpMem) const{

        cerr << "tagSize: "   << tagSize_   << endl
             << "nBytes: "    << nBytes_    << endl
             << "superstep: " << superstep_ << endl
             << "payload: ";
        if(dumpMem){
          for(int i = 0; i < nBytes_; i++){
            cerr << (int) payload_[i];
            cerr << " * ";
          }
          cerr << endl;
          cerr << "tag: ";
          for(int i = 0; i < tagSize_; i++){
            cerr << (int) tag_[i];
            cerr << " * ";
          }
          cerr << endl;
        }
      }

      void writeInMem(const void * src){

        assert(nBytes_ >= 0);
        payload_.resize(nBytes_);
        memcpy(&payload_[0], src, nBytes_);
      }

      void writeTag(const void * src){

        assert(tagSize_ >= 0);
        tag_.resize(tagSize_);
        memcpy(&tag_[0], src, tagSize_);
      }

      const int & tagSize() const{return tagSize_; }

  };

#endif//BspSend_HPP





