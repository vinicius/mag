#ifndef GuardedVariable_HPP
#define GuardedVariable_HPP

#include <iostream>

using namespace std;

#include <pthread.h>

  /**
    Guarded variable - protects a number variable from race conditions.
    
    A guarded variable contais a templatized number variable. All access
    to this variable is synchronized by the use of a lock.

    @author Andrei Goldchleger
    @date November, 2003

  */
  template<typename T>
  class GuardedVariable{

    private:

      volatile T variable; /**< The variable to be protected*/
      pthread_mutex_t lock;/**< lock to prevent race conditions*/

    public:

      /**
        Constructs a GuardedVariable object.

        @param initialValue - initial values of the guarded variable (Default: 0)
      */
      GuardedVariable(T initialValue=0)
                   :variable(initialValue){

        pthread_mutex_init(&lock, NULL);

      }

      /**
        Increments the guarded variable.

        @returns the variable value <b>PRIOR</b> to the incrementation
      */
      T inc(){

        T tmp;
        pthread_mutex_lock(&lock);
        tmp = variable++;
        pthread_mutex_unlock(&lock);
        return tmp;

      }
      
      /**
        Decrements the guarded variable.

        @returns the variable value <b>PRIOR</b> to the decrementation
      */
      T dec(){

        T tmp;
        pthread_mutex_lock(&lock);
        tmp = variable--;
        pthread_mutex_unlock(&lock);
        return tmp;

      }

      /**
       Returns the value of the guarded variable.
      */
      T value(){

        T tmp;
        pthread_mutex_lock(&lock);
        tmp = variable;
        pthread_mutex_unlock(&lock);
        return tmp;

      }
      
      /**
        Sets the guarded variable to a new value

        @param newValue - new value of the guarded variable.
      */
      void set(T newValue){
         pthread_mutex_lock(&lock);
          variable = newValue;
        pthread_mutex_unlock(&lock);
      }


      /**
        Reset the guarded variable.
      */
      void reset(){
        set(0);
      }



  };


#endif//GuardedVariable_HPP
