#ifndef CKPLOGGER_H_
#define CKPLOGGER_H_

#include <fstream>
#include <string>

using namespace std;

class CkpLogger {
    
private:
    ofstream *out;
public:
	CkpLogger();
	virtual ~CkpLogger();
    
    void debug(const string & message);
};

extern CkpLogger ckpLogger;

#endif /*CKPLOGGER_H_*/
