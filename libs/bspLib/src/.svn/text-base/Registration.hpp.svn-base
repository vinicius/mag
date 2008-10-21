#ifndef Registration_HPP
#define Registration_HPP

 /**
  Registration - Holds a BSP registration.
  
  A BSP registration is a pair (pointer,size), where pointer is a memory address
  of any given structure and size denotes the space it occupies, in bytes. This
  is needed by BSP DRMA to create logical addresses from physical one. A logical
  address is the same at each BSP task, while it may refer to a different physical 
  address at each BSP task.

  @author Andrei Goldchleger
  @date november/2003

 */
class Registration{
  
private:
  
  const void * ptr_;   /**< pointer to the memory area being registered */
  int size_;           /**< size of the memory area being registered */
  int superstep_;      /**< The superstep in which this registration was issued*/
  unsigned long type_; /**< type of the registered memory address */
  
public:
  
  /**
     Creates a Registration Object
     
     @param ptr - pointer to a memory region
     @param size - size of the memory region, in bytes
     @param superstep - The superstep in which this registration was issued
     
  */
  Registration(const void * ptr, int size, int superstep, unsigned long type):
    ptr_(ptr),size_(size),superstep_(superstep),type_(type){}

  /**
     Returns the data type of this registration
  */
  unsigned long type(){ return type_; }
  
  /**
     Returns the pointer of this registration
  */
  const void * ptr(){ return ptr_; }
  
  /**
     Returns the size ofthe memory object represented by this registration
  */
  int size(){ return size_; }
  
  /**
     Returns the superstep in which this registration was issued
  */
  int superstep(){ return superstep_; }
};

#endif//Registration_HPP


