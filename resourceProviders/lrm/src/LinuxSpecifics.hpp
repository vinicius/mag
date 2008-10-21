#ifndef LinuxSpecifics_HPP
#define LinuxSpecifics_HPP

using std::string;

  class LinuxSpecifics{

    public:

      //TODO: COMMENT ME
      static bool isAppRunning(const string & appPath);
      
      //TODO: COMMENT ME
      static void removeDirectory(const string & path);
      
      static int killProcess(int pid);
      
      static int getLastCkpFileNumber(const string & appId);
      
  };



#endif//LinuxSpecifics_HPP

