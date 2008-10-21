#ifndef Deregistration_HPP
#define Deregistration_HPP

 /**
    Deregistration - Represents a memory deregistration request. When performed,
    the last occurrence of a given registered memory address is invalidated for
    further remote access.

    @author Andrei Goldchleger
    @date December, 2003
  */
 class Deregistration{

   private:

     const void * ptr_;   /**< pointer to the memory area being registered */
     int superstep_;/**< the superstep in which the deregistration request was issued */
     

   public:

     /**
       Creates a Deregistration Object.

       @param ptr  - pointer to the memory address to be registered
       @param superstep  - the superstep in which the deregistration request is going to be issued

     */
     Deregistration(const void * ptr, int superstep):
                                 ptr_(ptr),superstep_(superstep){}

     /**
       Returns the pointer of this Deregistration
     */
     const void * ptr(){ return ptr_; }

     /**
       Returns the superstep of this Deregistration
     */
     int superstep(){ return superstep_; }
 };

#endif//Deregistration_HPP


