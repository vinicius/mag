#include "CheckpointingLib.hpp"
#include "DataConverters.hpp"
#include "DataConverter_x86_x8664.hpp"
#include "DataConverter_x86_PPCG4.hpp"
#include "DataConverter_x8664_x86.hpp"
#include "DataConverter_PPCG4_x86.hpp"

#include <sys/utsname.h>
#include <string>

DataConverters::~DataConverters() {

  std::vector<std::vector<dataconverter_t> *>::iterator it = dataConverterVector.begin();
  for ( ;it != dataConverterVector.end(); it++)
    if (*it) delete (*it);
	       
}

void DataConverters::initializeDataConverter(int srcArch) {
  
  if (dstArch == X86) {
    
    if (srcArch == X86_64) {
      dataConverterVector[srcArch] = DataConverter_x8664_x86::createDataConverter();
      dataSizesVector[srcArch]     = DataConverter_x8664_x86::createDataSizes();
    }
    else if (srcArch == PPCG4) {
      dataConverterVector[srcArch] = DataConverter_PPCG4_x86::createDataConverter();
      dataSizesVector[srcArch]     = DataConverter_PPCG4_x86::createDataSizes();
    }
  }

  //---------------------------------------------------
  else if (dstArch == X86_64) {

    if (srcArch == X86) {
      dataConverterVector[srcArch] = DataConverter_x86_x8664::createDataConverter();
      dataSizesVector[srcArch]     = DataConverter_x86_x8664::createDataSizes();
    }
  }

  //--------------------------------------------------
  else if (dstArch == PPCG4) {

    if (srcArch == X86) {
      dataConverterVector[srcArch] = DataConverter_x86_PPCG4::createDataConverter();
      dataSizesVector[srcArch]     = DataConverter_x86_PPCG4::createDataSizes();
    }

  }

}

ckp_arch_t DataConverters::getProcessorArchitecture() {
  struct utsname *machine = new struct utsname;
  uname(machine);
  std::string archS = machine->machine;
  delete machine;

  if (!archS.compare("i686") || 
      !archS.compare("i586") || 
      !archS.compare("i486") || 
      !archS.compare("i386"))
    return X86;
  else if (!archS.compare("x86_64"))
    return X86_64;
  else if (!archS.compare("Power Macintosh"))
    return PPCG4;

  return -1;
}
