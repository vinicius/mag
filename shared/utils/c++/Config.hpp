#ifndef  CONFIG_HPP
#define CONFIG_HPP

#include <string>
#include <map>

using namespace std;


/**
 Config - Parses a configuration file and stores these configurations.
 
 A configuration is a simple (key, value) pair, stored as strings.


 @author Andrei Goldchleger
*/
class Config{

 private:


   std::map<std::string, std::string> configs; /**< holds our configurations*/

 public:

   /**
     Constructor.

     @param configFile - the path of the configuration file to be parsed.
   */
   Config(string configFile);

   /**
     Adds a  configuration.

     @param conf - configuration's name (key)
     @param value - configuration's value
     
   */
   void addConf(string conf, string value);
   
   
   /**
     Gets a configuration.

     @param conf - configuration's name
   */
   string  getConf(const string & conf) const;

};

#endif//CONFIG_HPP

