#ifndef CharArrayArrayBeautifier_HPP
#define CharArrayArrayBeautifier_HPP

#include <cstring>
#include <cassert>


/**
  CharArrayArrayBeautifier :-)

  "Arrays are evil" is an old? saying. However, in some situations
  we still need them. This class safely allocates a char ** array,
  allows one to add C-like strings to it, and so on, encapsulating
  problems related to direct array manipulation.

  Special thanks to the author who contributed with this class.


  @author Wagner Cesar Bruna (C++ & all-around Guru (in constant training))
*/
class CharArrayArrayBeautifier{

  private:

    char ** array;                    /**< our array*/
    int num;                          /**< array size */
    int addedTokens;                  /**< number of tokens already added to the beautifier */

    /**
      Copies an array.

      @param cstr - The source array to be copied.
      @returns a pointer to the new allocated array/

    */
    char * newCopy(const char * cstr);

  public:

    /**
      Constructs a CharArrayArrayBeautifier.

      @param num_ - size of the array that will be allocated.

    */
    CharArrayArrayBeautifier(int num_);

    /**
      Destructor.
    */
    ~CharArrayArrayBeautifier();

    /**
      Adds another cstring to our char ** array.

      @param i - the index where the string should be added.
      @param cstr - the string to be added
    */
    void set(int i, const char * cstr);


    /**
      Returns the resulting array.
    */
    char ** getArray();

    void add(const char * cstr){ set(addedTokens, cstr); }

};

#endif//CharArrayArrayBeautifier_HPP
