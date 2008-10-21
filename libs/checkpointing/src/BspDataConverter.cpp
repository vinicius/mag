// BspDataConverter.cpp

BspDataConverter::DataConverter (char archType) {
}

BspDataConverter::~DataConverter () {
  // clear dataConverters
}

int BspDataConverter::writeData(void *dst, void *src, char srcArch, int nBytes) {
  if (srcArch >= dataConverters.size())
    dataConverters.resize(srcArch+1, NULL);
  
  // Initialize dataConverter
  if (dataConverters == NULL)
    ;

  // Write data 

  // TODO: Include extra byte containin the architecture to messages
}

