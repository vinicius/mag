#include <string>
#include <cstring>

#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <errno.h>


#include <iostream>

#include "LinuxSpecifics.hpp"

//For directory cleanup
#include <sys/types.h>
#include <dirent.h>

//To kill processes
#ifndef __USE_POSIX
#define __USE_POSIX
#endif
#include <signal.h>
#include <cstdlib>


using std::string;
using std::cerr;
using std::endl;

  //-----------------------------------------------------------------------------
  int LinuxSpecifics::getLastCkpFileNumber(const string & appId){
    DIR * dir = opendir(appId.c_str());
    if(dir == NULL){
      cerr << "LinuxSpecifics::getLastCkpFileNumber-->>Failed to open directory "
           << "'" << appId << "'" << endl;
      return 0 ;
    }
    int maxCkpNumber = 0;
    struct dirent * dirEntry;
    while((dirEntry = readdir(dir)) != NULL){
      string fileName = dirEntry->d_name;
      string::size_type pos = fileName.find (".ckp", 0);
      if (pos != string::npos) {
        int ckpNumber = atoi(fileName.substr(0, pos).c_str());
        if (ckpNumber > maxCkpNumber)
          maxCkpNumber = ckpNumber;
      }
    }
    return maxCkpNumber;
  }

  //-----------------------------------------------------------------------------
  bool LinuxSpecifics::isAppRunning(const string & appPath){

    int fd = open(appPath.c_str(), O_WRONLY);
    if(fd == -1)
      if(errno == ETXTBSY)
        return true;
      else{
        cerr << "LinuxSpecifics::isAppRunning-->>Error: "
             << strerror(errno)
             << endl;
        return false;
      }
    else{
      close(fd);
      return false;
    }
  }

  //----------------------------------------------------------------------------
  void LinuxSpecifics::removeDirectory(const string & path){

    DIR * dir = opendir(path.c_str());
    if(dir == NULL){
      cerr << "LinuxSpecifics::removeDirectory-->>Failed to open directory "
           << "'" << path << "'. Error reported: " << strerror(errno) << endl;
      return;
    }
    struct dirent * dirEntry;
    while((dirEntry = readdir(dir)) != NULL){
      string fullPath = path + "/" + dirEntry->d_name;
      struct stat statHolder;
      if(stat(fullPath.c_str(), &statHolder) == 0){
        if((string(dirEntry->d_name) == ".") || (string(dirEntry->d_name) == ".."))
          continue;
        if(S_ISDIR(statHolder.st_mode) == 1){
          cerr << "Recursing: " << dirEntry->d_name << endl;
          LinuxSpecifics::removeDirectory(fullPath);
        }
        else if(S_ISREG(statHolder.st_mode) == 1){
          cerr << "Deleting: " << fullPath << endl;
          if(unlink(fullPath.c_str()) != 0)
            cerr << "LinuxSpecifics::removeDirectory-->>Failed to delete file "
                 << "'" << fullPath << "'. Error reported: " << strerror(errno) << endl;
        }
      }
      else{
        cerr << "LinuxSpecifics::removeDirectory-->>Failed to stat "
        << "'" << fullPath << "'. Error reported: " << strerror(errno) << endl;
      }
    }//while
    if(rmdir(path.c_str()) != 0)
      cerr << "LinuxSpecifics::removeDirectory-->>Failed to delete dir "
           << "'" << path.c_str() << "'. Error reported: " << strerror(errno) << endl;
    closedir(dir);
  }//method

  //----------------------------------------------------------------------------
  int LinuxSpecifics::killProcess(int pid){

    int killReturn = kill(pid, SIGTERM);
    if(killReturn)
      cerr << "LinuxSpecifics::kill-->>Failed to kill app "
      << "'" << pid << "'. Error reported: " << strerror(errno) << endl;
    return killReturn;  
  }
