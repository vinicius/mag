#ifndef LinuxProcess_HPP
#define LinuxProcess_HPP

#include <string>

using namespace std;

 /**
   LinuxProcess - Creates and lauches a linux prosses.

   This class for now has only a single class method to
   launch a process. However, it will soon evolve to include
   features such as process control(start, stop, kill....),
   monitoring, etc...

   @author Andrei Goldchleger
 */
 class LinuxProcess{

   public:

    /**
      Creates and launches a linux process.

      @param appPath - the executable to be launched
      @param args - command line options
    */
    static int createLinuxProcess(string appPath, string args);

    /**
      Creates a new directory. Kills the program in case of errors.

      @param path - path to the directory being created
    */
    static int createDir(const string & path);

    /**
      Changes the current directory. Kills the program in case of errors.

      @param path - path to were the current working directory will be
      changed
    */
    static void changeDir(const string & path);

    /**
      Returns true if <i>path</i> does not represent an existing file or
      directory.

      @param path - File or directory being probed for existence

    */
    static bool canCreateDir(const string & path);

  private:

    static void redirectIo();
    static void chmodRWX(string appPath);
    static void execApp(string appPath,char ** args);
};

#endif//LinuxProcess_HPP

