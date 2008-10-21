#include "Config.hpp"
#include "StringTokenizer.hpp"
#include "StringUtils.hpp"
#include "NoSuchConfigException.hpp"
#include <iostream>
#include <fstream>
#include <string>


  //-------------------------------------------------------------------------------------
  Config::Config(std::string fileName){

    ifstream configFile(fileName.c_str());
    if(! configFile.good()){
      throw "Config::Config-->Bad Config File";//FIXME: Improve Error Handling
      cerr << "Config::Config-->Bad Config File" << endl;
    }
    std::string buf;
    while (std::getline(configFile, buf)) {
      StringUtils::filterComments(buf);
      StringTokenizer st(buf);
      if (st.countTokens() != 2)
        continue;
      //TODO: Define what should we do in case of empty options(i.e. 1 token only)
      std::string key = st.nextToken();
      std::string value = st.nextToken();
      configs[key] = value;
      buf = "";
    }
    configFile.close();
    

  }

  //-------------------------------------------------------------------------------------
  std::string Config::getConf(const std::string & conf) const{
    std::map<std::string, std::string>::const_iterator it = configs.find(conf);
    if(it != configs.end())
      return (*it).second;
    else
      throw NoSuchConfigException("Config::getConf()-->No such confiuration: " + conf);
  }

/*   //-------------------------------------------------------------------------------------
   std::string Config::getConf(const char * conf) const{
    string tmp = conf;
    return getConf(tmp);
  }
*/

  //-------------------------------------------------------------------------------------
  void Config::addConf(string conf, string value){
    configs[conf] = value;
  }


