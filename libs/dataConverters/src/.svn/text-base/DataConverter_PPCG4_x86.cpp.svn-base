#include "DataConverter_PPCG4_x86.hpp"

#include <iostream>

vector<datasizes_t> *DataConverter_PPCG4_x86::createDataSizes( ) {

  vector<datasizes_t> *dataSizes = 
    new vector<datasizes_t>(2*CKPT_UNSIGNED, &DataConverter_PPCG4_x86::getSizesError);
  
  (*dataSizes)[CKPT_BOOL]    = &DataConverter_PPCG4_x86::getSizesSignedBool;
  (*dataSizes)[CKPT_CHAR]    = &DataConverter_PPCG4_x86::getSizesSignedChar;
  (*dataSizes)[CKPT_WCHAR]   = &DataConverter_PPCG4_x86::getSizesSignedWChar;
  (*dataSizes)[CKPT_SHORT]   = &DataConverter_PPCG4_x86::getSizesSignedShort;
  (*dataSizes)[CKPT_INT]     = &DataConverter_PPCG4_x86::getSizesSignedInt;
  (*dataSizes)[CKPT_LONG]    = &DataConverter_PPCG4_x86::getSizesSignedLong;
  (*dataSizes)[CKPT_FLOAT]   = &DataConverter_PPCG4_x86::getSizesSignedFloat;
  (*dataSizes)[CKPT_DOUBLE]  = &DataConverter_PPCG4_x86::getSizesSignedDouble;
  (*dataSizes)[CKPT_LDOUBLE] = &DataConverter_PPCG4_x86::getSizesSignedLongDouble;
  (*dataSizes)[CKPT_FPOS_T]  = &DataConverter_PPCG4_x86::getSizesFilePos;
 
  (*dataSizes)[CKPT_CHAR+CKPT_UNSIGNED]  = &DataConverter_PPCG4_x86::getSizesUnsignedChar;
  (*dataSizes)[CKPT_SHORT+CKPT_UNSIGNED] = &DataConverter_PPCG4_x86::getSizesUnsignedShort;
  (*dataSizes)[CKPT_INT+CKPT_UNSIGNED]   = &DataConverter_PPCG4_x86::getSizesUnsignedInt;
  (*dataSizes)[CKPT_LONG+CKPT_UNSIGNED]  = &DataConverter_PPCG4_x86::getSizesUnsignedLong;
  
  return dataSizes;
  
}

vector<dataconverter_t> *DataConverter_PPCG4_x86::createDataConverter ( ) {
  
  vector<dataconverter_t> *dataConverter = 
    new vector<dataconverter_t>(2*CKPT_UNSIGNED, &DataConverter_PPCG4_x86::readError);
  
  (*dataConverter)[CKPT_BOOL]    = &DataConverter_PPCG4_x86::readSignedBool;
  (*dataConverter)[CKPT_CHAR]    = &DataConverter_PPCG4_x86::readSignedChar;
  (*dataConverter)[CKPT_WCHAR]   = &DataConverter_PPCG4_x86::readSignedWChar;
  (*dataConverter)[CKPT_SHORT]   = &DataConverter_PPCG4_x86::readSignedShort;
  (*dataConverter)[CKPT_INT]     = &DataConverter_PPCG4_x86::readSignedInt;
  (*dataConverter)[CKPT_LONG]    = &DataConverter_PPCG4_x86::readSignedLong;
  (*dataConverter)[CKPT_FLOAT]   = &DataConverter_PPCG4_x86::readSignedFloat;
  (*dataConverter)[CKPT_DOUBLE]  = &DataConverter_PPCG4_x86::readSignedDouble;
  (*dataConverter)[CKPT_LDOUBLE] = &DataConverter_PPCG4_x86::readSignedLongDouble;
  (*dataConverter)[CKPT_FPOS_T]  = &DataConverter_PPCG4_x86::readFilePos;
  
  (*dataConverter)[CKPT_CHAR+CKPT_UNSIGNED]  = &DataConverter_PPCG4_x86::readUnsignedChar;
  (*dataConverter)[CKPT_SHORT+CKPT_UNSIGNED] = &DataConverter_PPCG4_x86::readUnsignedShort;
  (*dataConverter)[CKPT_INT+CKPT_UNSIGNED]   = &DataConverter_PPCG4_x86::readUnsignedInt;
  (*dataConverter)[CKPT_LONG+CKPT_UNSIGNED]  = &DataConverter_PPCG4_x86::readUnsignedLong;
  
  return dataConverter;
}

long DataConverter_PPCG4_x86::readError(void *dst, const void *src) {
  std::cerr << "Type not defined!!!!" << endl;
  memcpy(dst, src, 1);
  return 1;
}

  long DataConverter_PPCG4_x86::readFilePos(void *dst, const void *src) {
    memcpy((char *)dst,  (char *)src+7, 1);
    memcpy((char *)dst+1,(char *)src+6, 1);
    memcpy((char *)dst+2,(char *)src+5, 1);
    memcpy((char *)dst+3,(char *)src+4, 1);
    //    cout << "FilePos Read!!!!" << *(double *)dst << endl;

    return CKPT_FPOS_T_PPCG4_SIZE;
  }

//----------- Signed converters -----------------------------
  long DataConverter_PPCG4_x86::readSignedBool(void *dst, const void *src) {
    memcpy(dst, (char *)src+3, 1);
    return CKPT_BOOL_PPCG4_SIZE;
  }
  
  long DataConverter_PPCG4_x86::readSignedChar(void *dst, const void *src) {
    memcpy(dst, src, CKPT_CHAR_X86_SIZE);
    //    cout << "Char Read!!!!" << *(char *)dst << endl;
    return CKPT_CHAR_PPCG4_SIZE;
  }
  
  long DataConverter_PPCG4_x86::readSignedWChar(void *dst, const void *src) {
    memcpy((char *)dst,  (char *)src+1, 1);
    memcpy((char *)dst+1,(char *)src,   1);
    //    cout << " Read!!!!" << *(wchar_t *)dst << endl;

    return CKPT_WCHAR_PPCG4_SIZE;
  }
  
  long DataConverter_PPCG4_x86::readSignedShort(void *dst, const void *src) {
    memcpy((char *)dst,  (char *)src+1, 1);
    memcpy((char *)dst+1,(char *)src,   1);
    //    cout << "Short Read!!!!" << *(short *)dst << endl;

    return CKPT_SHORT_PPCG4_SIZE;
  }
  
  long DataConverter_PPCG4_x86::readSignedInt(void *dst, const void *src) {
    memcpy((char *)dst,  (char *)src+3, 1);
    memcpy((char *)dst+1,(char *)src+2, 1);
    memcpy((char *)dst+2,(char *)src+1, 1);
    memcpy((char *)dst+3,(char *)src  , 1);
    //    cout << "Int Read!!!!" << *(int *)dst << endl;

    return CKPT_INT_PPCG4_SIZE;
  }
  
  long DataConverter_PPCG4_x86::readSignedLong(void *dst, const void *src) {
    memcpy((char *)dst,  (char *)src+3, 1);
    memcpy((char *)dst+1,(char *)src+2, 1);
    memcpy((char *)dst+2,(char *)src+1, 1);
    memcpy((char *)dst+3,(char *)src  , 1);
    //    cout << "Long Read!!!!" << *(long *)dst << endl;

    return CKPT_LONG_PPCG4_SIZE;
  }
  
  long DataConverter_PPCG4_x86::readSignedFloat(void *dst, const void *src) {
    memcpy((char *)dst,  (char *)src+3, 1);
    memcpy((char *)dst+1,(char *)src+2, 1);
    memcpy((char *)dst+2,(char *)src+1, 1);
    memcpy((char *)dst+3,(char *)src  , 1);
    //    cout << "Float Read!!!!" << *(float *)dst << endl;

    return CKPT_FLOAT_PPCG4_SIZE;
  }
  
  long DataConverter_PPCG4_x86::readSignedDouble(void *dst, const void *src) {
    memcpy((char *)dst,  (char *)src+7, 1);
    memcpy((char *)dst+1,(char *)src+6, 1);
    memcpy((char *)dst+2,(char *)src+5, 1);
    memcpy((char *)dst+3,(char *)src+4, 1);
    memcpy((char *)dst+4,(char *)src+3, 1);
    memcpy((char *)dst+5,(char *)src+2, 1);
    memcpy((char *)dst+6,(char *)src+1, 1);
    memcpy((char *)dst+7,(char *)src,   1);
    //    cout << "Double Read!!!!" << *(double *)dst << endl;

    return CKPT_DOUBLE_PPCG4_SIZE;
  }
  
  long DataConverter_PPCG4_x86::readSignedLongDouble(void *dst, const void *src) {
    DataConverter_PPCG4_x86::readSignedDouble(dst, src);
    *(double long *)dst = *(double *)dst;
    //    cout << "Long Double Read!!!!" << *(long double *)dst << endl;
    return CKPT_LDOUBLE_PPCG4_SIZE;
  }
  
//----------- Unsigned converters ---------------------------
  long DataConverter_PPCG4_x86::readUnsignedChar(void *dst, const void *src) {
    memcpy(dst, src, CKPT_CHAR_X86_SIZE);
    //    cout << "UChar Read!!!!" << *(unsigned char *)dst << endl;
    return CKPT_CHAR_PPCG4_SIZE;
  }
  
  long DataConverter_PPCG4_x86::readUnsignedShort(void *dst, const void *src) {
    memcpy((char *)dst,  (char *)src+1, 1);
    memcpy((char *)dst+1,(char *)src,   1);
    //    cout << "UShort Read!!!!" << *(unsigned short *)dst << endl;

    return CKPT_SHORT_PPCG4_SIZE;
  }
  
  long DataConverter_PPCG4_x86::readUnsignedInt(void *dst, const void *src) {
    memcpy((char *)dst,  (char *)src+3, 1);
    memcpy((char *)dst+1,(char *)src+2, 1);
    memcpy((char *)dst+2,(char *)src+1, 1);
    memcpy((char *)dst+3,(char *)src  , 1);
    //    cout << "UInt Read!!!!" << *(unsigned int *)dst << endl;

    return CKPT_INT_PPCG4_SIZE;
  }
  
  long DataConverter_PPCG4_x86::readUnsignedLong(void *dst, const void *src) {
    memcpy((char *)dst,  (char *)src+3, 1);
    memcpy((char *)dst+1,(char *)src+2, 1);
    memcpy((char *)dst+2,(char *)src+1, 1);
    memcpy((char *)dst+3,(char *)src  , 1);
    //    cout << "ULong Read!!!!" << *(unsigned long *)dst << endl;

    return CKPT_LONG_PPCG4_SIZE;
  }

//==========================================================================

void DataConverter_PPCG4_x86::getSizesError(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = 1;
  *src = 1;
}

void DataConverter_PPCG4_x86::getSizesFilePos(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_FPOS_T_X86_SIZE;
  *src = CKPT_FPOS_T_PPCG4_SIZE;
}

//----------- Signed converters -----------------------------
void DataConverter_PPCG4_x86::getSizesSignedBool(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_BOOL_X86_SIZE;
  *src = CKPT_BOOL_PPCG4_SIZE;
}

void DataConverter_PPCG4_x86::getSizesSignedChar(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_CHAR_X86_SIZE;
  *src = CKPT_CHAR_PPCG4_SIZE;
}

void DataConverter_PPCG4_x86::getSizesSignedWChar(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_WCHAR_X86_SIZE;
  *src = CKPT_WCHAR_PPCG4_SIZE;
}

void DataConverter_PPCG4_x86::getSizesSignedShort(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_SHORT_X86_SIZE;
  *src = CKPT_SHORT_PPCG4_SIZE;
}

void DataConverter_PPCG4_x86::getSizesSignedInt(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_INT_X86_SIZE;
  *src = CKPT_INT_PPCG4_SIZE;
}

void DataConverter_PPCG4_x86::getSizesSignedLong(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_LONG_X86_SIZE;
  *src = CKPT_LONG_PPCG4_SIZE;
}

void DataConverter_PPCG4_x86::getSizesSignedFloat(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_FLOAT_X86_SIZE;
  *src = CKPT_FLOAT_PPCG4_SIZE;
}

void DataConverter_PPCG4_x86::getSizesSignedDouble(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_DOUBLE_X86_SIZE;
  *src = CKPT_DOUBLE_PPCG4_SIZE;
}

void DataConverter_PPCG4_x86::getSizesSignedLongDouble(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_LDOUBLE_X86_SIZE;
  *src = CKPT_LDOUBLE_PPCG4_SIZE;
}

//----------- Unsigned converters ---------------------------
void DataConverter_PPCG4_x86::getSizesUnsignedChar(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_CHAR_X86_SIZE;
  *src = CKPT_CHAR_PPCG4_SIZE;
}    

void DataConverter_PPCG4_x86::getSizesUnsignedShort(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_SHORT_X86_SIZE;
  *src = CKPT_SHORT_PPCG4_SIZE;
}

void DataConverter_PPCG4_x86::getSizesUnsignedInt(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_INT_X86_SIZE;
  *src = CKPT_INT_PPCG4_SIZE;
}

void DataConverter_PPCG4_x86::getSizesUnsignedLong(ckp_datasize_t *dst, ckp_datasize_t *src){
  *dst = CKPT_LONG_X86_SIZE;
  *src = CKPT_LONG_PPCG4_SIZE;
}
