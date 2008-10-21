#include "CheckpointingLib.hpp"
#include "DataConverters.hpp"

#include <vector>

using namespace std;

class DataConverter_x86_PPCG4 {

public:

  static vector<dataconverter_t> *createDataConverter( );
  static vector<datasizes_t> *createDataSizes( );

  static long readError(void *dst, const void *src);
  static long readFilePos(void *dst, const void *src);

  //----------- Signed converters -----------------------------
  static long readSignedBool(void *dst, const void *src);
  static long readSignedChar(void *dst, const void *src);
  static long readSignedWChar(void *dst, const void *src);
  static long readSignedShort(void *dst, const void *src);
  static long readSignedInt(void *dst, const void *src);
  static long readSignedLong(void *dst, const void *src);
  static long readSignedFloat(void *dst, const void *src);
  static long readSignedDouble(void *dst, const void *src);
  static long readSignedLongDouble(void *dst, const void *src);
  
  //----------- Unsigned converters ---------------------------
  static long readUnsignedChar(void *dst, const void *src);    
  static long readUnsignedShort(void *dst, const void *src);
  static long readUnsignedInt(void *dst, const void *src);
  static long readUnsignedLong(void *dst, const void *src);

  //======================================================================

  static void getSizesError(ckp_datasize_t *dst, ckp_datasize_t *src);
  static void getSizesFilePos(ckp_datasize_t *dst, ckp_datasize_t *src);
  //----------- Signed converters -----------------------------
  static void getSizesSignedBool(ckp_datasize_t *dst, ckp_datasize_t *src);
  static void getSizesSignedChar(ckp_datasize_t *dst, ckp_datasize_t *src);
  static void getSizesSignedWChar(ckp_datasize_t *dst, ckp_datasize_t *src);
  static void getSizesSignedShort(ckp_datasize_t *dst, ckp_datasize_t *src);
  static void getSizesSignedInt(ckp_datasize_t *dst, ckp_datasize_t *src);
  static void getSizesSignedLong(ckp_datasize_t *dst, ckp_datasize_t *src);
  static void getSizesSignedFloat(ckp_datasize_t *dst, ckp_datasize_t *src);
  static void getSizesSignedDouble(ckp_datasize_t *dst, ckp_datasize_t *src);
  static void getSizesSignedLongDouble(ckp_datasize_t *dst, ckp_datasize_t *src);
  
  //----------- Unsigned converters ---------------------------
  static void getSizesUnsignedChar(ckp_datasize_t *dst, ckp_datasize_t *src);    
  static void getSizesUnsignedShort(ckp_datasize_t *dst, ckp_datasize_t *src);
  static void getSizesUnsignedInt(ckp_datasize_t *dst, ckp_datasize_t *src);
  static void getSizesUnsignedLong(ckp_datasize_t *dst, ckp_datasize_t *src);
  
};
