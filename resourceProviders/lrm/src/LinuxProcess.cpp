#include "LinuxProcess.hpp"

#include "utils/c++/StringTokenizer.hpp"
#include "utils/c++/CharArrayArrayBeautifier.hpp"
#include "utils/c++/NoSuchElementException.hpp"

#include <sys/types.h>
#include <sys/stat.h>
#include <sys/wait.h>
#include <unistd.h>
#include <errno.h>

#include <iostream>
#include <fstream>
#include <cstdio>

  //-------------------------------------------------------------------------------------
  int LinuxProcess::createLinuxProcess(string appPath, string args){

    int pidChld = fork();
    if (pidChld == 0) {  // child
        StringTokenizer st(args);
        //argv should hold appName + args + null
        CharArrayArrayBeautifier btf(st.countTokens() + 2);
        btf.add(appPath.c_str());
        while (st.hasMoreTokens())
            btf.add(st.nextToken().c_str());

        nice(100);
        LinuxProcess::changeDir(appPath);
        LinuxProcess::chmodRWX(appPath);
        LinuxProcess::redirectIo();  
        LinuxProcess::execApp(appPath, btf.getArray());
    } // child
    
    return pidChld; 
  }

  //-------------------------------------------------------------------------------------

  //FIXME: Is it better to remove this method and launch an exception from createDir????
  bool LinuxProcess::canCreateDir(const string & path){

    struct stat statHolder;
    int status = stat(path.c_str(), &statHolder);
    if (status == -1)
      if(errno == ENOENT)
        return true;//File/directory does not exist, ok to create dir
      else{
        cerr << "LinuxProcess::canCreateDir-->> stat failed" << endl
             << "STAT ERROR:" << errno << endl;
        exit(-1);
      }
    if((S_ISREG(statHolder.st_mode) == 1) || (S_ISDIR(statHolder.st_mode) == 1))
      return false;
    return false;//Theoretically cannot be reached

  }

  //-------------------------------------------------------------------------------------
  int LinuxProcess::createDir(const string & path){    
    //cerr << "LinuxProcess::createDir-->>path:  " << path << endl;
    int mkdirStatus = mkdir(path.c_str(),  S_IRUSR | S_IWUSR | S_IXUSR);
    if (mkdirStatus == -1) {
           cerr << "LinuxProcess::createDir--> directory creation failed " << endl;
           cerr << "MKDIR ERROR:" << errno << endl;
	   return -1;
           // exit(-1);
        }
    return 0;
  }

  //-------------------------------------------------------------------------------------
  void LinuxProcess::changeDir(const string & path){

    int chdirStatus = chdir(path.c_str());
    if (chdirStatus == -1) {
           cerr << "LinuxProcess::changeDir--> chdir failed " << endl;
           cerr << "CHDIR ERROR:" << errno << endl;
           exit(-1);
        }
  }

  //-------------------------------------------------------------------------------------
  void LinuxProcess::redirectIo(){

        FILE * errfile = freopen("stderr", "w", stderr);
        FILE * outfile = freopen("stdout", "w", stdout);
        setvbuf(errfile, NULL, _IONBF, 0);
        setvbuf(outfile, NULL, _IONBF, 0);
        fclose(stdin);
  }

  //-------------------------------------------------------------------------------------
  void LinuxProcess::chmodRWX(string filepath){

    int chmodStatus =  chmod(filepath.c_str(), S_IRUSR | S_IWUSR | S_IXUSR);
    if (chmodStatus == -1) {
      cerr << "LinuxProcess::chmodRWX--> chmod failed " << endl;
      cerr << "CHMOD ERROR:" << errno << endl;
      exit(-1);
    }
  }

  //-------------------------------------------------------------------------------------
  void LinuxProcess::execApp(string filepath,char ** args){

    int execStatus = execv(filepath.c_str(), args);
    if (execStatus == -1) {
      cerr << "LinuxProcess::execApp--> exec failed " << endl;
      cerr << "EXEC ERROR:" << errno << endl;
      exit(-1);
    }
  }

