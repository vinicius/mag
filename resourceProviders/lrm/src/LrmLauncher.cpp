#include <iostream>
#include <fstream>
#include <unistd.h>
#include <string>
#include <cstdlib>
#include <pthread.h>

#include "LrmImpl.hpp"
#include "utils/c++/Config.hpp"

  /**
   \class LrmLauncher

    Launches a LrmImpl object.

    LrmLauncher reads a config file or parses the command line and launches an LrmImpl
    object.

   @author Andrei Goldchleger
  */


using namespace std;

  /**
   \fn usage
   Prints usage information at standard output.
  */

  void usage(){

    cout << "Lrmd - Launches a LRM" << endl << endl;
    cout << "Options:" << endl << endl;
    cout << "-ior <ior>   - Connects to a GRM at <ior>" << endl << endl;
    cout << "-f <file>    - Connects to a GRM reachable by <file> that contais its IOR" << endl << endl;
    cout << "-s <time>    - Verifies information at <time> seconds intervals" << endl << endl;
    cout << "-k <time>    - Keep Alive at <time> seconds intervals" << endl << endl;
    cout << "-t <percent> - Percentual to consider a significant change in resources" << endl << endl;

  }

  /**
   \fn main
   Launches a LrmImpl object , based on configurations passed through the command line,
   config file, or both.

   @param argc
   @param argv
  */

  int main(int argc, char **argv) {

    Config config("lrm.conf");

    //Determines options

    int i = 0;
    //  long sampleInterval = 120;//FIXME: Use Default Parameters at Construvtors
    //  long keepAliveInterval = 600;//FIXME: Use Default Parameters at Construvtors
    // 	float threshold = 1;


    while(i < argc - 1){
      string token(argv[++i]);

      if (!token.compare("-ior"))
        config.addConf(string("grmIor"), string(argv[++i]));
      else if(!token.compare("-f")){
        ifstream ifs(argv[++i]);
	    string grmIor;
        ifs >> grmIor;
        ifs.close();
        config.addConf(string("grmIor"), grmIor);
      }
      else if(!token.compare("-apprepos")){
        ifstream ifs(argv[++i]);
	    string appReposIor;
        ifs >> appReposIor;
        ifs.close();
	    config.addConf(string("appReposIor"), appReposIor);
      }
      else if(!token.compare("-s"))
        config.addConf(string("sampleInterval"),string(argv[++i]));
      else if(!token.compare("-k"))
	   config.addConf(string("keepAliveInterval"),string(argv[++i]));
      else if(!token.compare("-t"))
	   config.addConf(string("threshold"),string(argv[++i]));
      else
        cout << "Ignoring Token: " << argv[i] << endl;
    }

    LrmImpl & lrm = LrmImpl::init(config);
    lrm.updateLoop(); //Blocking method

     return 0;
  }

