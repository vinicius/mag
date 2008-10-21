// BspDataConverter.hpp

class BspDataConverter {

  /** Converters are initialized as necessary*/
  vector<DataConverter *> dataConverters;

public:
  DataConverter (char archType);
  ~DataConverter ();
  int writeData(void *dst, void *src, char srcArch, int nBytes);

}
