#include "DataConverter_x8664_x86.hpp"

#include <iostream>

vector<datasizes_t> *DataConverter_x8664_x86::createDataSizes( ) {

  vector<datasizes_t> *dataSizes = 
    new vector<datasizes_t>(2*CKPT_UNSIGNED, &DataConverter_x8664_x86::getSizesError);
  
  (*dataSizes)[CKPT_BOOL]    = &DataConverter_x8664_x86::getSizesSignedBool;
  (*dataSizes)[CKPT_CHAR]    = &DataConverter_x8664_x86::getSizesSignedChar;
  (*dataSizes)[CKPT_WCHAR]   = &DataConverter_x8664_x86::getSizesSignedWChar;
  (*dataSizes)[CKPT_SHORT]   = &DataConverter_x8664_x86::getSizesSignedShort;
  (*dataSizes)[CKPT_INT]     = &DataConverter_x8664_x86::getSizesSignedInt;
  (*dataSizes)[CKPT_LONG]    = &DataConverter_x8664_x86::getSizesSignedLong;
  (*dataSizes)[CKPT_FLOAT]   = &DataConverter_x8664_x86::getSizesSignedFloat;
  (*dataSizes)[CKPT_DOUBLE]  = &DataConverter_x8664_x86::getSizesSignedDouble;
  (*dataSizes)[CKPT_LDOUBLE] = &DataConverter_x8664_x86::getSizesSignedLongDouble;
  
  (*dataSizes)[CKPT_CHAR+CKPT_UNSIGNED]  = &DataConverter_x8664_x86::getSizesUnsignedChar;
  (*dataSizes)[CKPT_SHORT+CKPT_UNSIGNED] = &DataConverter_x8664_x86::getSizesUnsignedShort;
  (*dataSizes)[CKPT_INT+CKPT_UNSIGNED]   = &DataConverter_x8664_x86::getSizesUnsignedInt;
  (*dataSizes)[CKPT_LONG+CKPT_UNSIGNED]  = &DataConverter_x8664_x86::getSizesUnsignedLong;
  
  return dataSizes;
  
}

vector<dataconverter_t> *DataConverter_x8664_x86::createDataConverter ( ) {
  
  vector<dataconverter_t> *dataConverter = 
    new vector<dataconverter_t>(2*CKPT_UNSIGNED, &DataConverter_x8664_x86::readError);
  
  (*dataConverter)[CKPT_BOOL]    = &DataConverter_x8664_x86::readSignedBool;
  (*dataConverter)[CKPT_CHAR]    = &DataConverter_x8664_x86::readSignedChar;
  (*dataConverter)[CKPT_WCHAR]   = &DataConverter_x8664_x86::readSignedWChar;
  (*dataConverter)[CKPT_SHORT]   = &DataConverter_x8664_x86::readSignedShort;
  (*dataConverter)[CKPT_INT]     = &DataConverter_x8664_x86::readSignedInt;
  (*dataConverter)[CKPT_LONG]    = &DataConverter_x8664_x86::readSignedLong;
  (*dataConverter)[CKPT_FLOAT]   = &DataConverter_x8664_x86::readSignedFloat;
  (*dataConverter)[CKPT_DOUBLE]  = &DataConverter_x8664_x86::readSignedDouble;
  (*dataConverter)[CKPT_LDOUBLE] = &DataConverter_x8664_x86::readSignedLongDouble;
  
  (*dataConverter)[CKPT_CHAR+CKPT_UNSIGNED]  = &DataConverter_x8664_x86::readUnsignedChar;
  (*dataConverter)[CKPT_SHORT+CKPT_UNSIGNED] = &DataConverter_x8664_x86::readUnsignedShort;
  (*dataConverter)[CKPT_INT+CKPT_UNSIGNED]   = &DataConverter_x8664_x86::readUnsignedInt;
  (*dataConverter)[CKPT_LONG+CKPT_UNSIGNED]  = &DataConverter_x8664_x86::readUnsignedLong;
  
  return dataConverter;
}

long DataConverter_x8664_x86::readError(void *dst, const void *src) {
  std::cerr << "Type not defined!!!!" << endl;
  memcpy(dst, src, 1);
  return 1;
}

//----------- Signed converters -----------------------------
  long DataConverter_x8664_x86::readSignedBool(void *dst, const void *src) {
    memcpy(dst, src, CKPT_BOOL_X86_SIZE);
    return CKPT_BOOL_X8664_SIZE;
  }
  
  long DataConverter_x8664_x86::readSignedChar(void *dst, const void *src) {
    memcpy(dst, src, CKPT_CHAR_X86_SIZE);
    return CKPT_CHAR_X8664_SIZE;
  }
  
  long DataConverter_x8664_x86::readSignedWChar(void *dst, const void *src) {
    memcpy(dst, src, CKPT_WCHAR_X86_SIZE);
    return CKPT_WCHAR_X8664_SIZE;
  }
  
  long DataConverter_x8664_x86::readSignedShort(void *dst, const void *src) {
    memcpy(dst, src, CKPT_SHORT_X86_SIZE);
    return CKPT_SHORT_X8664_SIZE;
  }
  
  long DataConverter_x8664_x86::readSignedInt(void *dst, const void *src) {
    memcpy(dst, src, CKPT_INT_X86_SIZE);
    return CKPT_INT_X8664_SIZE;
  }
  
  long DataConverter_x8664_x86::readSignedLong(void *dst, const void *src) {
    // Test if an overflow occurs
    // --> First CKPT_LONG_X86_SIZE+1 bits must be all 1's or all 0's
    memcpy(dst, src, CKPT_LONG_X86_SIZE);  
    return CKPT_LONG_X8664_SIZE;
  }
  
  long DataConverter_x8664_x86::readSignedFloat(void *dst, const void *src) {
    memcpy(dst, src, CKPT_FLOAT_X86_SIZE);
    return CKPT_FLOAT_X8664_SIZE;
  }
  
  long DataConverter_x8664_x86::readSignedDouble(void *dst, const void *src) {
    memcpy(dst, src, CKPT_DOUBLE_X86_SIZE);
    return CKPT_DOUBLE_X8664_SIZE;
  }
  
  long DataConverter_x8664_x86::readSignedLongDouble(void *dst, const void *src) {
    // x86-64 'long double' uses 16 bytes, but only the first 10 bytes are meaningful 
    memcpy(dst, src, CKPT_LDOUBLE_X86_SIZE);
    return CKPT_LDOUBLE_X8664_SIZE;
  }
  
//----------- Unsigned converters ---------------------------
  long DataConverter_x8664_x86::readUnsignedChar(void *dst, const void *src) {
    memcpy(dst, src, CKPT_CHAR_X86_SIZE);
    return CKPT_CHAR_X8664_SIZE;
  }
  
  long DataConverter_x8664_x86::readUnsignedShort(void *dst, const void *src) {
    memcpy(dst, src, CKPT_SHORT_X86_SIZE);
    return CKPT_SHORT_X8664_SIZE;
  }
  
  long DataConverter_x8664_x86::readUnsignedInt(void *dst, const void *src) {
    memcpy(dst, src, CKPT_INT_X86_SIZE);
    return CKPT_INT_X8664_SIZE;
  }
  
  long DataConverter_x8664_x86::readUnsignedLong(void *dst, const void *src) {
    // Test if an overflow occurs
    // --> First CKPT_LONG_X86_SIZE+1 bits must be all 1's or all 0's
    memcpy(dst, src, CKPT_LONG_X86_SIZE);
    return CKPT_LONG_X8664_SIZE;
  }

//==========================================================================

void DataConverter_x8664_x86::getSizesError(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = 1;
  *src = 1;
}

//----------- Signed converters -----------------------------
void DataConverter_x8664_x86::getSizesSignedBool(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_BOOL_X86_SIZE;
  *src = CKPT_BOOL_X8664_SIZE;
}

void DataConverter_x8664_x86::getSizesSignedChar(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_CHAR_X86_SIZE;
  *src = CKPT_CHAR_X8664_SIZE;
}

void DataConverter_x8664_x86::getSizesSignedWChar(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_WCHAR_X86_SIZE;
  *src = CKPT_WCHAR_X8664_SIZE;
}

void DataConverter_x8664_x86::getSizesSignedShort(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_SHORT_X86_SIZE;
  *src = CKPT_SHORT_X8664_SIZE;
}

void DataConverter_x8664_x86::getSizesSignedInt(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_INT_X86_SIZE;
  *src = CKPT_INT_X8664_SIZE;
}

void DataConverter_x8664_x86::getSizesSignedLong(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_LONG_X86_SIZE;
  *src = CKPT_LONG_X8664_SIZE;
}

void DataConverter_x8664_x86::getSizesSignedFloat(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_FLOAT_X86_SIZE;
  *src = CKPT_FLOAT_X8664_SIZE;
}

void DataConverter_x8664_x86::getSizesSignedDouble(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_DOUBLE_X86_SIZE;
  *src = CKPT_DOUBLE_X8664_SIZE;
}

void DataConverter_x8664_x86::getSizesSignedLongDouble(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_LDOUBLE_X86_SIZE;
  *src = CKPT_LDOUBLE_X8664_SIZE;
}

//----------- Unsigned converters ---------------------------
void DataConverter_x8664_x86::getSizesUnsignedChar(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_CHAR_X86_SIZE;
  *src = CKPT_CHAR_X8664_SIZE;
}    

void DataConverter_x8664_x86::getSizesUnsignedShort(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_SHORT_X86_SIZE;
  *src = CKPT_SHORT_X8664_SIZE;
}

void DataConverter_x8664_x86::getSizesUnsignedInt(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_INT_X86_SIZE;
  *src = CKPT_INT_X8664_SIZE;
}

void DataConverter_x8664_x86::getSizesUnsignedLong(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_LONG_X86_SIZE;
  *src = CKPT_LONG_X8664_SIZE;
}
