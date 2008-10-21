#include <iostream>
#include <fstream>
#include <unistd.h>
#include <string>
#include <cstdlib>
#include <pthread.h>
#include <stdio.h>

#include "ArscImpl.hpp"
#include "MessagePacket.hpp"
#include "utils/c++/Config.hpp"

/**
 \class LsmLauncher

  Launches a ArscImpl object.

  LsmLauncher reads a config file or parses the command line and launches an ArscImpl
  object.

 @author Braga Junior.
*/


using namespace std;

  /**
    Prints usage information at standard output.

  */
  void usage(){

    cout << "Lsmd - Launches a LSM" << endl << endl;
    cout << "Options:" << endl << endl;
    cout << "-ior <ior>   - Connects to a arsm at <ior>" << endl << endl;
    cout << "-f <file>    - Connects to a arsm reachable by <file> that contais its IOR" << endl << endl;
  }

  /**
   Launches a ArscImpl object , based on configurations passed through the command line,
   config file, or both.

   @param argc
   @param argv

  */
  int main(int argc, char **argv) {




       Config config("arsc.conf");


         //Determines options

        int i = 0;

       ArscImpl & arsc = ArscImpl::init(config); 
       string msg = std::string("http://gsd.ime.usp.br/integrade");
  try{
       cout << "Message `" << msg << "' will be sent to arsm ..." << endl;
       cout << "Signing message..." << endl;
       msg=arsc.requestSignature(msg);
       cout << "Sending message..." << endl;
       msg = arsc.test(msg);
       cout << "Message received. Checking..." << endl;
       msg=arsc.checkSignature(msg);
       if(msg.length()> 0) 
	cout << "Message was successfully confirmed: " << msg << endl;
  }catch(exception e)
  {
  		cerr << e.what() << endl;
  }
       
       arsc.finish();
       exit(0);
       


}

