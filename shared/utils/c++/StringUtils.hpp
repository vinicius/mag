#ifndef StringUtils_HPP
#define StringUtils_HPP

#include <string>
#include <sstream>

using std::string;
using std::istringstream;
using std::ostringstream;

  class StringUtils{

    public:

      //---------------------------------------------------------------------
      static void filterComments(string & str){

        unsigned long index = str.find_first_of('#');
        if (index != string::npos)
          str.erase(index, str.size() - index);
      }

      //---------------------------------------------------------------------
      //FIXME: It's better to use boost lexical-cast
      static int string2int(const string & str){

        istringstream iss(str);
        int result;
        iss >> result;
        return result;

      }

      //---------------------------------------------------------------------
      //FIXME: It's better to use boost lexical-cast
      static float string2float(const string & str){

        istringstream iss(str);
        float result;
        iss >> result;
        return result;
      }

      //---------------------------------------------------------------------
      //FIXME: It's better to use boost lexical-cast
      static string int2string(const int & i){
        ostringstream sstr;
        sstr << i;
        return sstr.str();
      }

     //---------------------------------------------------------------------
     template<typename T> static  string num2String(const T & num){
       stringstream sstr;
       sstr << num;
       return sstr.str();
     }

     //---------------------------------------------------------------------
     static string stripPath(const string & path){
       string tmpFilePath = path;
       unsigned long lastSlashIndex = tmpFilePath.find_last_of('/');
       if(lastSlashIndex != string::npos)
         tmpFilePath = tmpFilePath.substr(lastSlashIndex + 1);
       return tmpFilePath;
     }

  };
#endif//StringUtils_HPP

