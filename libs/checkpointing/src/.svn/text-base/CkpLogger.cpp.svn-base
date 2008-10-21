#include "CkpLogger.hpp"

#include <iostream>
using namespace std;

CkpLogger::CkpLogger() {
    out = new ofstream ("ckp.log", ios_base::app);
    cout << "Logging ckp events at 'ckp.log'." << endl;
}

CkpLogger::~CkpLogger()
{
}

void CkpLogger::debug(const string & message) {
    *out << "DEBUG: " << message << endl;
}

CkpLogger ckpLogger;
