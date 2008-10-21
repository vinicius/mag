#include "DataConverter_x86_x8664.hpp"

#include <iostream>

vector<datasizes_t> *DataConverter_x86_x8664::createDataSizes( ) {

  vector<datasizes_t> *dataSizes = 
    new vector<datasizes_t>(2*CKPT_UNSIGNED, &DataConverter_x86_x8664::getSizesError);
  
  (*dataSizes)[CKPT_BOOL]    = &DataConverter_x86_x8664::getSizesSignedBool;
  (*dataSizes)[CKPT_CHAR]    = &DataConverter_x86_x8664::getSizesSignedChar;
  (*dataSizes)[CKPT_WCHAR]   = &DataConverter_x86_x8664::getSizesSignedWChar;
  (*dataSizes)[CKPT_SHORT]   = &DataConverter_x86_x8664::getSizesSignedShort;
  (*dataSizes)[CKPT_INT]     = &DataConverter_x86_x8664::getSizesSignedInt;
  (*dataSizes)[CKPT_LONG]    = &DataConverter_x86_x8664::getSizesSignedLong;
  (*dataSizes)[CKPT_FLOAT]   = &DataConverter_x86_x8664::getSizesSignedFloat;
  (*dataSizes)[CKPT_DOUBLE]  = &DataConverter_x86_x8664::getSizesSignedDouble;
  (*dataSizes)[CKPT_LDOUBLE] = &DataConverter_x86_x8664::getSizesSignedLongDouble;
  
  (*dataSizes)[CKPT_CHAR+CKPT_UNSIGNED]  = &DataConverter_x86_x8664::getSizesUnsignedChar;
  (*dataSizes)[CKPT_SHORT+CKPT_UNSIGNED] = &DataConverter_x86_x8664::getSizesUnsignedShort;
  (*dataSizes)[CKPT_INT+CKPT_UNSIGNED]   = &DataConverter_x86_x8664::getSizesUnsignedInt;
  (*dataSizes)[CKPT_LONG+CKPT_UNSIGNED]  = &DataConverter_x86_x8664::getSizesUnsignedLong;
  
  return dataSizes;
  
}

vector<dataconverter_t> *DataConverter_x86_x8664::createDataConverter ( ) {
  
  vector<dataconverter_t> *dataConverter = 
    new vector<dataconverter_t>(2*CKPT_UNSIGNED, &DataConverter_x86_x8664::readError);
  
  (*dataConverter)[CKPT_BOOL]    = &DataConverter_x86_x8664::readSignedBool;
  (*dataConverter)[CKPT_CHAR]    = &DataConverter_x86_x8664::readSignedChar;
  (*dataConverter)[CKPT_WCHAR]   = &DataConverter_x86_x8664::readSignedWChar;
  (*dataConverter)[CKPT_SHORT]   = &DataConverter_x86_x8664::readSignedShort;
  (*dataConverter)[CKPT_INT]     = &DataConverter_x86_x8664::readSignedInt;
  (*dataConverter)[CKPT_LONG]    = &DataConverter_x86_x8664::readSignedLong;
  (*dataConverter)[CKPT_FLOAT]   = &DataConverter_x86_x8664::readSignedFloat;
  (*dataConverter)[CKPT_DOUBLE]  = &DataConverter_x86_x8664::readSignedDouble;
  (*dataConverter)[CKPT_LDOUBLE] = &DataConverter_x86_x8664::readSignedLongDouble;
  
  (*dataConverter)[CKPT_CHAR+CKPT_UNSIGNED]  = &DataConverter_x86_x8664::readUnsignedChar;
  (*dataConverter)[CKPT_SHORT+CKPT_UNSIGNED] = &DataConverter_x86_x8664::readUnsignedShort;
  (*dataConverter)[CKPT_INT+CKPT_UNSIGNED]   = &DataConverter_x86_x8664::readUnsignedInt;
  (*dataConverter)[CKPT_LONG+CKPT_UNSIGNED]  = &DataConverter_x86_x8664::readUnsignedLong;
  
  return dataConverter;
}

long DataConverter_x86_x8664::readError(void *dst, const void *src) {
  std::cerr << "Type not defined!!!!" << endl;
  memcpy(dst, src, 1);
  return 1;
}

//----------- Signed converters -----------------------------
  long DataConverter_x86_x8664::readSignedBool(void *dst, const void *src) {
    memcpy(dst, src, CKPT_BOOL_X8664_SIZE);
    return CKPT_BOOL_X86_SIZE;
  }
  
  long DataConverter_x86_x8664::readSignedChar(void *dst, const void *src) {
    memcpy(dst, src, CKPT_CHAR_X8664_SIZE);
    return CKPT_CHAR_X86_SIZE;
  }
  
  long DataConverter_x86_x8664::readSignedWChar(void *dst, const void *src) {
    memcpy(dst, src, CKPT_WCHAR_X8664_SIZE);
    return CKPT_WCHAR_X86_SIZE;
  }
  
  long DataConverter_x86_x8664::readSignedShort(void *dst, const void *src) {
    memcpy(dst, src, CKPT_SHORT_X8664_SIZE);
    return CKPT_SHORT_X86_SIZE;
  }
  
  long DataConverter_x86_x8664::readSignedInt(void *dst, const void *src) {
    memcpy(dst, src, CKPT_INT_X8664_SIZE);
    return CKPT_INT_X86_SIZE;
  }
  
  long DataConverter_x86_x8664::readSignedLong(void *dst, const void *src) {
    // Necessary to mantain the number signal
    (*(char *)src >= 0) ? memset((char *)dst+4, 0, 4) : memset((char *)dst+4, 0xff, 4);
    memcpy(dst, src, 4);  
    
    cout << "Long --> " << *(long *)dst << endl;
    
    return CKPT_LONG_X86_SIZE;
  }
  
  long DataConverter_x86_x8664::readSignedFloat(void *dst, const void *src) {
    memcpy(dst, src, CKPT_FLOAT_X8664_SIZE);
    return CKPT_FLOAT_X86_SIZE;
  }
  
  long DataConverter_x86_x8664::readSignedDouble(void *dst, const void *src) {
    memcpy(dst, src, CKPT_DOUBLE_X8664_SIZE);
    return CKPT_DOUBLE_X86_SIZE;
  }
  
  long DataConverter_x86_x8664::readSignedLongDouble(void *dst, const void *src) {
    // x86-64 'long double' uses 16 bytes, but only the first 10 bytes are meaningful 
    memcpy(dst, src, CKPT_LDOUBLE_X86_SIZE);
    return CKPT_LDOUBLE_X86_SIZE;
  }
  
//----------- Unsigned converters ---------------------------
  long DataConverter_x86_x8664::readUnsignedChar(void *dst, const void *src) {
    memcpy(dst, src, CKPT_CHAR_X8664_SIZE);
    return CKPT_CHAR_X86_SIZE;
  }
  
  long DataConverter_x86_x8664::readUnsignedShort(void *dst, const void *src) {
    memcpy(dst, src, CKPT_SHORT_X8664_SIZE);
    return CKPT_SHORT_X86_SIZE;
  }
  
  long DataConverter_x86_x8664::readUnsignedInt(void *dst, const void *src) {
    memcpy(dst, src, CKPT_INT_X8664_SIZE);
    return CKPT_INT_X86_SIZE;
  }
  
  long DataConverter_x86_x8664::readUnsignedLong(void *dst, const void *src) {

    memset((char *)dst+4, 0, 4);
    memcpy(dst, src, 4);  
    //    memset(dst, 0, 4);
    //memcpy((char *)dst+4, src, 4);  
    return CKPT_LONG_X86_SIZE;
  }

//==========================================================================

void DataConverter_x86_x8664::getSizesError(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = 1;
  *src = 1;
}

//----------- Signed converters -----------------------------
void DataConverter_x86_x8664::getSizesSignedBool(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_BOOL_X8664_SIZE;
  *src = CKPT_BOOL_X86_SIZE;
}

void DataConverter_x86_x8664::getSizesSignedChar(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_CHAR_X8664_SIZE;
  *src = CKPT_CHAR_X86_SIZE;
}

void DataConverter_x86_x8664::getSizesSignedWChar(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_WCHAR_X8664_SIZE;
  *src = CKPT_WCHAR_X86_SIZE;
}

void DataConverter_x86_x8664::getSizesSignedShort(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_SHORT_X8664_SIZE;
  *src = CKPT_SHORT_X86_SIZE;
}

void DataConverter_x86_x8664::getSizesSignedInt(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_INT_X8664_SIZE;
  *src = CKPT_INT_X86_SIZE;
}

void DataConverter_x86_x8664::getSizesSignedLong(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_LONG_X8664_SIZE;
  *src = CKPT_LONG_X86_SIZE;
}

void DataConverter_x86_x8664::getSizesSignedFloat(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_FLOAT_X8664_SIZE;
  *src = CKPT_FLOAT_X86_SIZE;
}

void DataConverter_x86_x8664::getSizesSignedDouble(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_DOUBLE_X8664_SIZE;
  *src = CKPT_DOUBLE_X86_SIZE;
}

void DataConverter_x86_x8664::getSizesSignedLongDouble(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_LDOUBLE_X8664_SIZE;
  *src = CKPT_LDOUBLE_X86_SIZE;
}

//----------- Unsigned converters ---------------------------
void DataConverter_x86_x8664::getSizesUnsignedChar(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_CHAR_X8664_SIZE;
  *src = CKPT_CHAR_X86_SIZE;
}    

void DataConverter_x86_x8664::getSizesUnsignedShort(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_SHORT_X8664_SIZE;
  *src = CKPT_SHORT_X86_SIZE;
}

void DataConverter_x86_x8664::getSizesUnsignedInt(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_INT_X8664_SIZE;
  *src = CKPT_INT_X86_SIZE;
}

void DataConverter_x86_x8664::getSizesUnsignedLong(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_LONG_X8664_SIZE;
  *src = CKPT_LONG_X86_SIZE;
}
