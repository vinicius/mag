#include "CharArrayArrayBeautifier.hpp"

#include <iostream>//FIXME: Remove
using namespace std;

  //--------------------------------------------------------------------------
  char * CharArrayArrayBeautifier::newCopy(const char * cstr){

    const int tam = strlen(cstr) + 1;
    char * temp = new char[tam];
    // We know that strcpy does not launch exceptions
    std::strcpy(temp, cstr);
    return (temp);
  }

  //--------------------------------------------------------------------------
  CharArrayArrayBeautifier::CharArrayArrayBeautifier(int num_)
                                                    : num(num_),
                                                    addedTokens(0){
    assert(num >= 0);
    array = new char *[num];
    int i;
    for (i = 0; i < num; ++i) {
      array[i] = 0;
    }
  }

  //--------------------------------------------------------------------------
  CharArrayArrayBeautifier::~CharArrayArrayBeautifier(){

    int i;
    for (i = 0; i < num; ++i) {
      delete [] (array[i]);
    }
    delete [] (array);
  }

  //--------------------------------------------------------------------------
  void CharArrayArrayBeautifier::set(int i, const char * cstr){

    assert(i >= 0);
    assert(i < num);
    if (cstr != NULL) {
      char * temp = newCopy(cstr);
      delete [] (array[i]);
      array[i] = temp;
      addedTokens++;
    }
    else {
      delete [] (array[i]);
      array[i] = 0;
    }
  }

  //--------------------------------------------------------------------------
  char ** CharArrayArrayBeautifier::getArray(){return (array); }






