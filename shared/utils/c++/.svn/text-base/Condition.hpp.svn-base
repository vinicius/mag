#ifndef Condition_HPP
#define Condition_HPP

#include <pthread.h>

  /**
    Condition - Used to synchronize threads.

    One thread waits for the condition to become true. Another thread signals
    when the condition becomes true. After the waiting thread is released, it
    sets the condition value to false.

    @author Andrei Goldchleger
    @date December, 2003

  */
  class Condition{

    private:

      volatile bool cond;          /**< the condition flag*/
      pthread_mutex_t condLock;    /**< lock for the critical section*/
      pthread_cond_t condCond;     /**< condition  variable*/

    public:

      /**
        Creates a Condition object
      */
      Condition();

      /**
        Blocks the calling thread until the condition becomes true

      */
      void wait();

      /**
        Makes the condition true and signal the situation to a blocked
        thread, if any.

      */
      void signal();


  };

#endif//Condition_HPP
