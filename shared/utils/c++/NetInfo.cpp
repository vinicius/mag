// netinfo.cpp

#ifndef _BSD_SOURCE
#define _BSD_SOURCE
#endif
#include <unistd.h>




// ginástica para contentar compiladores infelizes
namespace
{
  void gethostname_wrapper(char *name, size_t len)
  {
    ::gethostname(name, len);
  }
}

#include "NetInfo.hpp"

namespace netinfo{


  std::string getHostName(){

   char foo[255];
   gethostname_wrapper(foo, 255);
   string ret(foo);
   return ret;

  }
}


