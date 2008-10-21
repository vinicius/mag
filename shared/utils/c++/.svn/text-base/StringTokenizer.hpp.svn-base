#ifndef STRINGTOKENIZER_HPP
#define STRINGTOKENIZER_HPP


#include <string>

using namespace std;

//This class is not multithread safe
/**
  StringTokenizer - decompose a string into tokens.

  Those methods have the same name as the ones in java.util.StringTokenizer.
  It was developed with this objective.

  @author Andrei Goldchleger
*/
class StringTokenizer{

  private:

    std::string tokenizable;  /**< the string to be tokenized */
    int nextAnalyzableChar;   /**< keeps the index of the next possible token */
    int remainingTokens;      /**< number of remaining tokens */
    int initialTokenCount();  /**< scans the string and count the number of tokens*/

  public:

    /**
      Constructs a Tokenizer.
      
      @param tokenizable_ - the string to be tokenized
    */
    StringTokenizer(const std::string & tokenizable_);
    
    /**
      Returns the next token contained in the string.
    */
    std::string nextToken();
    
    /**
      Indicates if more tokens are available.
    */
    bool hasMoreTokens();
    
    /**
      Returns the number of available tokens in the string
    */
    int countTokens();
    
    /**
      Returns the string containing remaining tokens. DOES NOT advance the token
      pointer!!!!
    */
    string getRemainingTokens();

};



#endif //STRINGTOKENIZER_HPP
