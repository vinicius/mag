#include "StringTokenizer.hpp"
#include "NoSuchElementException.hpp"
#include <iostream>


  //-------------------------------------------------------------------------
  StringTokenizer::StringTokenizer(const std::string & tokenizable_):
    tokenizable(tokenizable_),
    nextAnalyzableChar(0),
    remainingTokens(initialTokenCount()){}


  //-------------------------------------------------------------------------
  string StringTokenizer::nextToken(){

    if(remainingTokens == 0)
      throw NoSuchElementException("StringTokenizer::nextToken()-->> No available tokens");

   std::string::size_type tokenBegin = 0;
   std::string::size_type tokenEnd = 0;
   bool inToken = false;

   for(unsigned int i = nextAnalyzableChar; i < tokenizable.length(); i++)
     if(tokenizable.at(i) != ' ')
       if(!inToken){
         tokenBegin = i;
	 inToken = true;
       }
       else{}//nothing; we are stil into the token
     else//found delimiter
       if(inToken){
         tokenEnd = i - 1;
	 inToken = false;
         break;
       }
    if(inToken)//token at end of tokenizable
      tokenEnd = tokenizable.length() - 1;
    if(tokenEnd >= tokenBegin){//At least one char token
      nextAnalyzableChar = tokenEnd + 1;
      remainingTokens--;
      return tokenizable.substr(tokenBegin, tokenEnd - tokenBegin + 1);
    }
    else
     return string("");
  }


  //-------------------------------------------------------------------------
  bool StringTokenizer::hasMoreTokens(){
    return(remainingTokens > 0);
  }

  //-------------------------------------------------------------------------
  int StringTokenizer::countTokens(){
    return remainingTokens;
  }

  //-------------------------------------------------------------------------
  int StringTokenizer::initialTokenCount(){
   bool inToken = false;
   int numTokens = 0;
   for(unsigned int i = 0; i < tokenizable.length(); i++)
     if(tokenizable.at(i) == ' ')
       if(inToken){
         numTokens++;
	 inToken = false;
       }
       else{}
     else
       inToken = true;
   if(inToken)//token at end of tokenizable
     numTokens++;

   return numTokens;
  }

  //-------------------------------------------------------------------------
  string StringTokenizer::getRemainingTokens(){

    for(unsigned int i = nextAnalyzableChar; i < tokenizable.length(); i++)
      if(tokenizable.at(i) != ' ')
        return tokenizable.substr(i, tokenizable.size()  - i + 1);
    return string("");    
  }
