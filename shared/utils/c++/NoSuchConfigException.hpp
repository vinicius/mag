#ifndef NoSuchConfigException_HPP
#define NoSuchConfigException_HPP

#include <string>

using std::string;

  class NoSuchConfigException{

    private:

      string what_;

    public:

      NoSuchConfigException(string what):what_(what){}
      
      const string & what() const{return what_; }

  };

#endif//NoSuchConfigException_HPP
